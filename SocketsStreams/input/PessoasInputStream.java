package SocketsStreams.input;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import SocketsStreams.Pessoa;

public class PessoasInputStream extends InputStream {
    private InputStream inputStream;
    public PessoasInputStream(InputStream sourceInputStream) throws IOException {
        this.inputStream = sourceInputStream;
    }
    public void readFIle() throws IOException, ClassNotFoundException {
        Path filePath = Paths.get("./arquivo_pessoas.dat");
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(filePath, charset);
            int quantidade = Integer.parseInt(lines.get(0));
            System.out.println("Quantidade de pessoas : " + quantidade);
            for (int i = 1; i < lines.size(); i += 1) {
                System.out.println("Quantidade de Caracteres do Nome : " + lines.get(i));
                i = i + 1;
                System.out.println("Nome : " + lines.get(i));
                i = i + 1;
                System.out.println("CPF : " + lines.get(i));
                i = i + 1;
                System.out.println("Idade : " + lines.get(i));
            }
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
    }
    public void readSystem() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(this.inputStream);
        List<Pessoa> pessoas = new ArrayList<>();
        System.out.println("Insira a Quantidade de Pessoas");
        int quantidade = sc.nextInt();
        for (int i = 0; i < quantidade; i += 1) {
            System.out.println("Insira o Nome");
            String nome = sc.next();
            System.out.println("Insira o CPF");
            String cpf = sc.next();
            System.out.println("Insira a Idade");
            int idade = sc.nextInt();
            Pessoa pessoa = new Pessoa(nome, cpf, idade);
            pessoas.add(pessoa);
            System.out.print("\n");
        }
        sc.close();
        for (Pessoa p : pessoas) {
            System.out.println(p.toString());
        }
    }
    public void readTCP() throws IOException, ClassNotFoundException {
        DataInputStream data = new DataInputStream(this.inputStream);
        int quantidade = data.readInt();
        System.out.println("Quantidade de pessoas : " + quantidade);
        for (int i = 0; i < quantidade; i += 1) {
            int tamanhoNome = data.readInt();
            byte[] nomeBytes = data.readNBytes(tamanhoNome);
            String nome = new String(nomeBytes);
            String cpf = data.readUTF();
            int idade = data.readInt();
            Pessoa pessoa = new Pessoa(nome, cpf, idade);
            System.out.println(pessoa.toString());
        }
    }
    public void close() throws IOException {
        inputStream.close();
    }
    public static void main(String[] args) {
        try {
            PessoasInputStream pessoasInputStreamSys = new PessoasInputStream(System.in);
            pessoasInputStreamSys.readSystem();
            pessoasInputStreamSys.close();
            InputStream fileInputStream = new FileInputStream("arquivo_pessoas.dat");
            PessoasInputStream pessoasInputStreamFile = new PessoasInputStream(fileInputStream);
            pessoasInputStreamFile.readFIle();
            pessoasInputStreamFile.close();
            Socket cliente = new Socket("localhost", 12345);
            PessoasInputStream pessoasInputStreamTCP = new PessoasInputStream(cliente.getInputStream());
            pessoasInputStreamTCP.readTCP();
            cliente.close();
            pessoasInputStreamTCP.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int read() throws IOException {
        return 0;
    }
}
