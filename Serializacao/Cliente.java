package Serializacao;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final int PORTA = 1234;
        final String IP = "localhost";
        Socket socket = new Socket(IP, PORTA);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String opcao;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Mensagem reply = null;
        Mensagem mensagem = null;
        try {
            do {
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Listar");
                System.out.println("2 - Trocar");
                System.out.println("3 - Cadastrar");
                System.out.println("4 - Enviar");
                System.out.println("5 - Sair");
                System.out.print("Opção: ");
                opcao = br.readLine();
                switch (opcao) {
                    case "1":
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        mensagem = new Mensagem("listagem");
                        oos.writeObject(mensagem);
                        ois = new ObjectInputStream(socket.getInputStream());
                        reply = (Mensagem) ois.readObject();
                        for (Correios produto : reply.getArrayProdutos()) {
                            System.out.println(produto);
                        }
                        break;
                    case "2":
                        System.out.println("Deseja trocar uma Carta, uma Encomenda ou um Telegrama ?");
                        System.out.println("1 - Carta");
                        System.out.println("2 - Encomenda");
                        System.out.println("3 - Telegrama");
                        System.out.print("Opção: ");
                        String tipoProduto = br.readLine();
                        switch (tipoProduto) {
                            case "1":
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("troca");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Digite o novo nome da carta : ");
                                String nomeCartaNova = br.readLine();
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(nomeCartaNova);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                break;
                            case "2":
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("troca");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Digite o nome da nova encomenda : ");
                                String nomeEncomendaNova = br.readLine();
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(nomeEncomendaNova);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                break;
                            case "3":
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("troca");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Digite o nome do novo telegrama : ");
                                String nomeTelegramaNova = br.readLine();
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(nomeTelegramaNova);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                break;
                            default:
                                break;
                        }
                        break;
                    case "3":
                        System.out.println("Deseja Cadastrar uma Carta, Encomenda ou Telegrama ?");
                        System.out.println("1 - Carta");
                        System.out.println("2 - Encomenda");
                        System.out.println("3 - Telegrama");
                        System.out.print("Opção: ");
                        tipoProduto = br.readLine();
                        switch (tipoProduto) {
                            case "1":
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("cadastro");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Digite o nome da Carta : ");
                                String nomeCarta = br.readLine();
                                System.out.println("Digite o Remetente : ");
                                String remetenteCarta = br.readLine();
                                System.out.println("Digite o Destinatario : ");
                                String destinatarioCarta = br.readLine();
                                System.out.println("Digite o Assunto : ");
                                String assuntoCarta = br.readLine();
                                Correios correios = new Cartas(nomeCarta, destinatarioCarta, remetenteCarta, assuntoCarta);
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(correios);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                break;
                            case "2":
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("cadastro");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Digite o nome da Encomenda : ");
                                String nomeEncomenda = br.readLine();
                                System.out.println("Digite o remetente da Encomenda : ");
                                String remetenteEncomenda = br.readLine();
                                System.out.println("Digite o destinatario da Encomenda : ");
                                String destinatarioEncomenda = br.readLine();
                                System.out.println("Digite a loja da Encomenda : ");
                                String lojaEncomenda = br.readLine();
                                correios = new Encomendas(nomeEncomenda, destinatarioEncomenda, remetenteEncomenda, lojaEncomenda);
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(correios);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                break;
                            case "3":
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("cadastro");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Digite o nome do Telegrama");
                                String nomeTelegrama = br.readLine();
                                System.out.println("Digite o remetente do Telegrama");
                                String remetenteTelegrama = br.readLine();
                                System.out.println("Digite o destinatario do Telegrama : ");
                                String destinatarioTelegrama = br.readLine();
                                System.out.println("Digite o ano do Telegrama : ");
                                int anoTelegrama = Integer.parseInt(br.readLine());
                                correios = new Telegrama(nomeTelegrama, destinatarioTelegrama, remetenteTelegrama, anoTelegrama);
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(correios);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                break;
                        }
                        break;
                    case "4":
                        System.out.println("Qual item deseja enviar ?");
                        System.out.println("1 - Carta");
                        System.out.println("2 - Encomenda");
                        System.out.println("3 - Telegrama");
                        System.out.print("Opção: ");
                        tipoProduto = br.readLine();
                        switch (tipoProduto) {
                            case "1":
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("remocao");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Qual o nome da Carta que deseja remover?");
                                String nomeProduto = br.readLine();
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(tipoProduto + ", " + nomeProduto);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                break;
                            case "2":
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("remocao");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Qual o nome da Encomenda que deseja remover?");
                                nomeProduto = br.readLine();
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(tipoProduto + ", " + nomeProduto);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                break;
                            case "3":
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem("remocao");
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                System.out.println("Qual o nome do Telegrama que deseja remover?");
                                nomeProduto = br.readLine();
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                mensagem = new Mensagem(tipoProduto + ", " + nomeProduto);
                                oos.writeObject(mensagem);
                                ois = new ObjectInputStream(socket.getInputStream());
                                reply = (Mensagem) ois.readObject();
                                System.out.println(reply.getConteudo());
                                break;
                            default:
                                break;
                        }
                        break;
                    case "5":
                        System.out.println("Tchau !");
                        socket.close();
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            } while (!opcao.equals("5"));
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (Throwable e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            socket.close();
            oos.close();
            ois.close();
        }
    }
}