package Serializacao;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final int PORTA = 1234;
        ServerSocket serverSocket = new ServerSocket(PORTA);
        Controle controle = new Controle();
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        Mensagem mensagem = null;
        Mensagem reply = null;
        Socket socket = null;
        try {
            System.out.println("Servidor iniciado na porta " + PORTA);
            socket = serverSocket.accept();
            System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());
            while (true) {
                ois = new ObjectInputStream(socket.getInputStream());
                mensagem = (Mensagem) ois.readObject();
                System.out.println("Mensagem recebida: " + mensagem.getConteudo());
                if (mensagem.getConteudo().equals("troca")) {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    reply = new Mensagem(
                            "Bem-vindo a troca");
                    oos.writeObject(reply);
                    ois = new ObjectInputStream(socket.getInputStream());
                    mensagem = (Mensagem) ois.readObject();
                    String[] info = mensagem.getConteudo().split(", ");
                    String tipoProduto = info[0];
                    for (int i = 0; i < info.length; i++) {
                        System.out.println(info[i]);
                    }
                    if (tipoProduto.equals("1")) {
                        String nomeCartaAntiga = info[1];
                        String nomeCartaNova = info[2];
                        String remetenteNovo = info[3];
                        String destinatarioNovo = info[4];
                        String assuntoNovo = info[5];
                        controle.trocarCarta(nomeCartaNova, remetenteNovo, destinatarioNovo, remetenteNovo, assuntoNovo);
                        System.out.println("Objeto foi trocado");
                    } else if (tipoProduto.equals("2")) {
                        String nomeEncomendaAntiga = info[1];
                        String nomeEncomendaNova = info[2];
                        String remetenteNovo = info[3];
                        String destinatarioNovo = info[4];
                        String assuntoNovo = info[5];
                        controle.trocarEncomenda(nomeEncomendaNova, remetenteNovo, destinatarioNovo, remetenteNovo, assuntoNovo);
                    } else if (tipoProduto.equals("3")) {
                        String nomeTelegramaAntiga = info[1];
                        String nomeTelegramaNova = info[2];
                        String remetenteNovo = info[3];
                        String destinatarioNovo = info[4];
                        int ano = Integer.parseInt(info[5]);
                        controle.trocarTelegrama(nomeTelegramaAntiga, remetenteNovo, destinatarioNovo, remetenteNovo, nomeTelegramaNova);
                    }
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    reply = new Mensagem("Objeto dos correios foi trocado");
                    oos.writeObject(reply);
                } else if (mensagem.getConteudo().equals("cadastro")) {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    reply = new Mensagem(
                            "Bem-vindo ao cadastro");
                    oos.writeObject(reply);
                    ois = new ObjectInputStream(socket.getInputStream());
                    mensagem = (Mensagem) ois.readObject();
                    Correios correios = mensagem.getProduto();
                    if (correios instanceof Cartas) {
                        Cartas cartas = (Cartas) correios;
                        controle.addCartas(cartas.nome, cartas.destinatario, cartas.remetente, cartas.assunto);
                    } else if (correios instanceof Encomendas) {
                        Encomendas encomendas = (Encomendas) correios;
                        controle.addEncomendas(encomendas.nome, encomendas.destinatario, encomendas.remetente, encomendas.loja);
                    } else if (correios instanceof Telegrama) {
                        Telegrama telegrama = (Telegrama) correios;
                        controle.addTelegrama(telegrama.nome, telegrama.destinatario, telegrama.remetente, telegrama.ano);
                    }
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    reply = new Mensagem("Item foi cadastrado");
                    oos.writeObject(reply);
                } else if (mensagem.getConteudo().equals("remocao")) {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    reply = new Mensagem(
                            "Bem-vindo a entrega");
                    oos.writeObject(reply);
                    ois = new ObjectInputStream(socket.getInputStream());
                    mensagem = (Mensagem) ois.readObject();
                    String[] info = mensagem.getConteudo().split(", ");
                    String tipoProduto = info[0];
                    String nomeProduto = info[1];
                    if (tipoProduto.equals("1")) {
                        controle.removerCarta(nomeProduto);
                    } else if (tipoProduto.equals("2")) {
                        controle.removerEncomenda(nomeProduto);
                    } else if (tipoProduto.equals("3")) {
                        controle.removerTelegrama(nomeProduto);
                    }
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    reply = new Mensagem("O item foi enviado");
                    oos.writeObject(reply);
                } else if (mensagem.getConteudo().equals("listagem")) {
                    Correios[] produtos = controle.getCorreios();
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    reply = new Mensagem(produtos);
                    oos.writeObject(reply);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        } finally {
            socket.close();
            serverSocket.close();
            oos.close();
            ois.close();
        }
    }
}