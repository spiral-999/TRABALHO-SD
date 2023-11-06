package Serializacao;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final int PORTA = 1234;
        final String IP = "localhost";
        try (Socket socket = new Socket(IP, PORTA)) {
            do {
                Controle controle = new Controle();
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Listar Itens dos Correios");
                System.out.println("2 - Cadastrar nos Correios");
                System.out.println("3 - Remover Item dos Correios");
                System.out.println("4 - Sair");
                System.out.print("Opção: ");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String opcao = br.readLine();
                switch (opcao) {
                    case "1":
                        System.out.println("Itens :");
                        for (Correios correios : controle.getCorreios()) {
                            System.out.println(correios);
                        }
                        break;
                    case "2":
                        System.out.println("Qual o tipo do produto a ser cadastrado?");
                        System.out.println("1 - Carta");
                        System.out.println("2 - Encomenda");
                        System.out.print("Opção: ");
                        String tipo = br.readLine();
                        switch (tipo) {
                            case "1":
                                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                Mensagem mensagem = new Mensagem("cadastro");
                                oos.writeObject(mensagem);
                                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                                Mensagem reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Informe o nome da Carta : ");
                                String nomeCarta = br.readLine();
                                System.out.println("Informe o remetente da Carta : ");
                                String remetenteCarta = br.readLine();
                                System.out.println("Informe o destinatario da Carta : ");
                                String destinatarioCarta = br.readLine();
                                System.out.println("Informe o assunto da Carta : ");
                                String assuntoCarta = br.readLine();
                                Correios correios = new Cartas(nomeCarta, remetenteCarta, destinatarioCarta, assuntoCarta);
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(correios);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                ois.close();
                                oos.close();
                                break;
                            case "2":
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("cadastro");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Informe o nome da Encomenda : ");
                                String nomeEncomenda = br.readLine();
                                System.out.println("Informe o remetente da Encomenda : ");
                                String remetenteEncomenda = br.readLine();
                                System.out.println("Informe o destinatario da Encomenda : ");
                                String destinatarioEncomenda = br.readLine();
                                System.out.println("Informe a loja da Encomenda : ");
                                String lojaEncomenda = br.readLine();
                                correios = new Encomendas(nomeEncomenda, remetenteEncomenda, destinatarioEncomenda, lojaEncomenda);
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(correios);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                ois.close();
                                oos.close();
                                socket.close();
                                break;
                        }
                        break;
                    case "3":
                        System.out.println("Deseja remover uma carta ou uma encomenda ?");
                        System.out.println("1 - Carta");
                        System.out.println("2 - Encomenda");
                        System.out.print("Opção: ");
                        tipo = br.readLine();
                        switch (tipo) {
                            case "1":
                                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                Mensagem mensagem = new Mensagem("remocao");
                                oos.writeObject(mensagem);
                                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                                Mensagem reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Qual o nome do objeto a ser removido :");
                                String nome = br.readLine();
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(tipo + ", " + nome);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                ois.close();
                                oos.close();
                                socket.close();
                                break;
                            case "2":
                                System.out.println("Encomendas :");
                                for (Correios encomenda : controle.getCorreios()) {
                                    if (encomenda instanceof Encomendas) {
                                        System.out.println(encomenda);
                                    }
                                }
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("remocao");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Qual o nome do objeto a ser removido :");
                                nome = br.readLine();
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(tipo + ", " + nome);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                ois.close();
                                oos.close();
                                socket.close();
                                break;
                        }
                        break;
                    case "4":
                        System.out.println("Tchau !");
                        break;
                    default:
                        System.out.println("Opção Inválida");
                        break;
                }
                socket.close();
                break;
            } while (true);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Throwable e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            System.out.println("Fim do programa");
        }
    }
}