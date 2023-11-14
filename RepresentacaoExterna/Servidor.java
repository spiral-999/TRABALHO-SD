package RepresentacaoExterna;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Servidor {
    private static final String UDP_IP_ADDRESS = "224.0.0.1"; 
    private static final int SERVER_PORT_TCP = 12345; 
    private static final int SERVER_PORT_UDP = 1234;
    private static List<Candidato> candidates = new CopyOnWriteArrayList<>();
    private static List<Voto> votes = new CopyOnWriteArrayList<>(); 
    private static boolean votingOpen = true; 
    private static int votos = 0; 
    public static void main(String[] args) {
        candidates.add(new Candidato("Mateus Lima", 0));
        candidates.add(new Candidato("Miguel Lima Rodrigues", 0));
        candidates.add(new Candidato("Maria Beatriz Fernandes", 0));
        candidates.add(new Candidato("Branco", 0));
        candidates.add(new Candidato("Nulo", 0));
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT_TCP)) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Digite o tempo da votacao : ");
            int duracao = sc.nextInt();
            long tempoInicial = System.currentTimeMillis();
            System.out.println("Server is running on port " + SERVER_PORT_TCP);
            MulticastSocket multicastServer = new MulticastSocket(SERVER_PORT_UDP);
            InetAddress group = InetAddress.getByName(UDP_IP_ADDRESS);
            while (votingOpen) {
                Socket clientSocket = serverSocket.accept();
                long tempoAtual = System.currentTimeMillis();
                if ((tempoAtual - tempoInicial) > duracao) {
                    System.out.println("Os votos estao sendo apurados, confira o cliente");
                    List<Candidato> ganhadores = Ganhadores();
                    String info = "Eleito : ";
                    byte[] messageBytes = info.getBytes();
                    DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, group, multicastServer.getLocalPort());
                    multicastServer.send(packet);
                    for (Candidato candidato : candidates) {
                        info = "";
                        info += candidato.getNome() + " - ";
                        info += candidato.getVotos() + " votos (" + candidato.getPercentagem() + "%) ";
                        if (ganhadores.contains(candidato)) {
                            info += " --- vencedor";
                        }
                        messageBytes = info.getBytes();
                        packet = new DatagramPacket(messageBytes, messageBytes.length, group, multicastServer.getLocalPort());
                        multicastServer.send(packet);
                    }
                    info = "end";
                    messageBytes = info.getBytes();
                    packet = new DatagramPacket(messageBytes, messageBytes.length, group, multicastServer.getLocalPort());
                    multicastServer.send(packet);
                    break;
                }
                System.out.println("Conectado");
                new Thread(new ClientHandler(clientSocket, multicastServer, group)).start();
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static List<Candidato> Ganhadores() {
        List<Candidato> ganhadores = new CopyOnWriteArrayList<>();
        Candidato ganhador = candidates.get(0);
        for (Candidato candidato : candidates) {
            if (ganhador.getVotos() < candidato.getVotos()) {
                ganhador = candidato;
            }
            String percentagem = String.format("%.0f", (100.0 * candidato.getVotos() / votos));
            candidato.setPercentagem(percentagem);
        }
        for (Candidato candidato : candidates) {
            if (ganhador.getVotos() == candidato.getVotos()) {
                ganhadores.add(candidato);
            }
        }
        return ganhadores;
    }
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private String cpf;
        private MulticastSocket multicastServer;
        private InetAddress group;
        public ClientHandler(Socket socket, MulticastSocket multicastServer, InetAddress group) {
            this.socket = socket;
            this.multicastServer = multicastServer;
            this.group = group;
        }
        @Override
        public void run() {
            try (
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                DataInputStream reader = new DataInputStream(input);
                DataOutputStream writer = new DataOutputStream(output)) {
                this.cpf = reader.readUTF();
                System.out.println(cpf + " conectado.");
                if (cpf.equals("admin")) {
                    while (votingOpen) {
                        int value = reader.readInt();
                        if (value == 1) {
                            sendCandidateList(writer);
                        } else if (value == 2) {
                            InserirCandidato(reader, writer);
                        } else if (value == 3) {
                            RemoverCandidato(reader, writer);
                        } else if (value == 4) {
                            EnviarInformativos(reader, writer);
                        } else if (value == 5) {
                            break;
                        }
                    }
                } else {
                    eleitor(reader, writer);
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void sendCandidateList(DataOutputStream writer) throws IOException, EOFException {
            List<Candidato> candidatesCopy = candidates;
            writer.writeInt(candidatesCopy.size());

            if (candidates.size() > 0) {
                for (Candidato candidate : candidatesCopy) {
                    writer.writeUTF(candidate.getNome());
                }
            }
        }
        private void verificarCandidato(DataInputStream reader, DataOutputStream writer) throws IOException, EOFException {
            int numeroCandidato = reader.readInt();
            if (numeroCandidato <= candidates.size() && numeroCandidato > 0) {
                Voto voto = new Voto(cpf, candidates.get(numeroCandidato - 1).getNome());
                if (!votes.contains(voto)){
                    votes.add(voto);
                    candidates.get(numeroCandidato - 1).addVoto();
                    votos +=1;
                    System.out.println("Voto contabilizado");
                    writer.writeInt(1);
                }
               else{
                writer.writeInt(2);
               } 
            } else {
                    writer.writeInt(0);
            }
        }
            private void eleitor(DataInputStream reader, DataOutputStream writer) throws IOException, EOFException {
                sendCandidateList(writer);
                verificarCandidato(reader, writer);
            }
            private void InserirCandidato(DataInputStream reader, DataOutputStream writer) throws IOException, EOFException {
                String nomeCandidato = reader.readUTF();
                candidates.add(new Candidato(nomeCandidato, 0));
                writer.writeInt(1); 
            }
            private void RemoverCandidato(DataInputStream reader, DataOutputStream writer) throws IOException, EOFException {
                sendCandidateList(writer);
                int valor = reader.readInt();
                if (valor > 0 && valor <= candidates.size()) {
                    candidates.remove(valor - 1);
                    writer.writeInt(1); 
                } else {
                    writer.writeInt(0); 
                }
            }
            private void EnviarInformativos(DataInputStream reader, DataOutputStream writer) throws IOException, EOFException {
                String info = reader.readUTF();
                byte[] messageBytes = info.getBytes();
                DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, group, this.multicastServer.getLocalPort());
                this.multicastServer.send(packet);
            }
        }
    }