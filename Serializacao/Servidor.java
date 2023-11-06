package Serializacao;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final int PORTA = 1234;
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor iniciado na porta " + PORTA);
            Controle controle = new Controle();
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Mensagem mensagem = (Mensagem) ois.readObject();
                if (mensagem.getConteudo().equals("cadastro")) {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    Mensagem reply = new Mensagem(
                            "Menu de Cadastro:");
                    oos.writeObject(reply);
                    ois = new ObjectInputStream(socket.getInputStream());
                    mensagem = (Mensagem) ois.readObject();
                    Correios correios = mensagem.getCorreios();
                    if (correios instanceof Cartas) {
                        Cartas cartas = (Cartas) correios;
                        controle.addCartas(cartas.nome, cartas.destinatario, cartas.remetente, cartas.assunto);
                    } else if (correios instanceof Encomendas) {
                        Encomendas encomendas = (Encomendas) correios;
                        controle.addEncomendas(encomendas.nome, encomendas.destinatario, encomendas.remetente, encomendas.loja);
                    }
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    reply = new Mensagem("Produto cadastrado com sucesso!");
                    oos.writeObject(reply);
                    oos.close();
                    ois.close();
                } else if (mensagem.getConteudo().equals("remocao")) {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    Mensagem reply = new Mensagem(
                            "Menu de Remoção :");
                    oos.writeObject(reply);
                    ois = new ObjectInputStream(socket.getInputStream());
                    mensagem = (Mensagem) ois.readObject();
                    String[] info = mensagem.getConteudo().split(", ");
                    String tipoProduto = info[0];
                    String nomeProduto = info[1];
                    if (tipoProduto.equals("Carta")) {
                        controle.removerCarta(nomeProduto);
                    } else if (tipoProduto.equals("Encomeda")) {
                        controle.removerEncomenda(nomeProduto);
                    }
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    reply = new Mensagem("Produto removido com sucesso!");
                    oos.writeObject(reply);
                    oos.close();
                    ois.close();
                }
            }
        }
    }
}