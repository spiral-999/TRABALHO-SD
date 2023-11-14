package RepresentacaoExterna;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    private static final String SERVER_ADDRESS = "localhost";
    private static final String UDP_SERVER_ADDRESS = "224.0.0.1";
    private static final int SERVER_PORT_TCP = 12345;
    private static final int SERVER_PORT_UDP = 1234;
    private static MulticastSocket multicastClient;
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT_TCP);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            System.out.println("Digite o cpf para votar ou admin para fazer operacoes de administrador");
            Scanner sc = new Scanner(System.in);
            String cpf = sc.nextLine();
            output.writeUTF(cpf);
            if (cpf.equals("admin")) {
                administrador(input, output);
            } else {
                multicastClient = new MulticastSocket(SERVER_PORT_UDP);
                InetAddress group = InetAddress.getByName(UDP_SERVER_ADDRESS);
                multicastClient.joinGroup(group);
                eleitor(input, output);
                receberInformativos();
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void eleitor(DataInputStream input, DataOutputStream output) throws IOException {
        Scanner sc = new Scanner(System.in);
        LerCandidatos(input);
        System.out.println("Digite o numero do candidato que deseja votar : ");
        int numeroCandidato = sc.nextInt();
        output.writeInt(numeroCandidato);
        sc.close();
        int votado = input.readInt();
        if (votado == 1) {
            System.out.println("Voto contabilizado");
        } else if (votado == 0) {
            System.out.println("Candidato não existe");
        } else {
            System.out.println("Voto já foi apurado");
        }
    }
    private static void administrador(DataInputStream input, DataOutputStream output) throws IOException {
        boolean votingOpen = true;
        Scanner sc = new Scanner(System.in);
        while (votingOpen) {
            System.out.println("Digite o que deseja fazer");
            System.out.println("1 - Mostrar lista de candidatos");
            System.out.println("2 - Inserir candidato");
            System.out.println("3 - Remover candidato");
            System.out.println("4 - Enviar informativo");
            System.out.println("5 - Sair");
            String entrada = sc.nextLine();
            int value = Integer.parseInt(entrada);
            if (value == 1) {
                output.writeInt(value);
                LerCandidatos(input);
            } else if (value == 2) {
                output.writeInt(value);
                InserirCandidato(input, output, sc);
            } else if (value == 3) {
                output.writeInt(value);
                RemoverCandidato(input, output, sc);
            } else if (value == 4) {
                output.writeInt(value);
                EnviarInformativos(output, sc);
            } else if (value == 5) {
                output.writeInt(value);
                votingOpen = false;
            } else {
                System.out.println("Ação inválida");
            }
        }
        sc.close();
    }
    private static void RemoverCandidato(DataInputStream input, DataOutputStream output, Scanner sc) throws IOException {
        System.out.println("Digite o número do candidato a ser removido : ");
        LerCandidatos(input); 
        String entrada = sc.nextLine();
        int valor = Integer.parseInt(entrada);
        output.writeInt(valor);
        int bool = input.readInt();
        if (bool == 1) {
            System.out.println("Candidato removido");
        } else {
            System.out.println("Candidato não encontrado");
        }
    }
    private static void InserirCandidato(DataInputStream input, DataOutputStream output, Scanner sc) throws IOException {
        System.out.println("Digite o nome do novo candidato : ");
        String nome = sc.nextLine();
        output.writeUTF(nome);
        int bool = input.readInt();
        if (bool == 1) {
            System.out.println("Candidato " + nome + " inserido.");
        }
    }
    private static void LerCandidatos(DataInputStream input) throws IOException {
        int quantidadeCandidatos = input.readInt();
        if (quantidadeCandidatos > 0) {
            String[] candidatos = new String[quantidadeCandidatos];
            for (int i = 0; i < quantidadeCandidatos; i += 1) {
                String candidato = input.readUTF();
                candidatos[i] = candidato;
            }
            System.out.println("Lista de candidatos : ");
            int i = 1;
            for (String candidato : candidatos) {
                System.out.println(i + "-" + candidato);
                i += 1;
            }
            i = 0;
        } else {
            System.out.println("Não há candidatos");
        }
    }
    private static void EnviarInformativos(DataOutputStream output, Scanner sc) throws IOException {
        System.out.println("Digite o que deseja informar : ");
        String info = sc.nextLine();
        output.writeUTF(info);
    }
    private static void receberInformativos() {
        while (true) {
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastClient.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                if (message.equals("end")) {
                    break;
                }
                System.out.println("Recebido: " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}