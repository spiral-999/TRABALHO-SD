package SocketsStreams.output;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import SocketsStreams.Pessoa;

public class PessoasOutputStream extends OutputStream {
    private OutputStream op;
    private Pessoa[] pessoas;
    public PessoasOutputStream() {
    }
    public PessoasOutputStream(Pessoa[] pessoas, OutputStream os) {
        this.pessoas = pessoas;
        this.op = os;
    }
    public void writeSystem() {
        PrintStream opTemp = new PrintStream(this.op);
        int quantidade = this.pessoas.length;
        opTemp.println("Numero de Pessoas : " + quantidade);
        for (Pessoa pessoa : this.pessoas) {
            if (pessoa != null) {
                int caracteres = pessoa.getNome().getBytes().length;
                String nome = pessoa.getNome();
                String cpf = pessoa.getCpf();
                int idade = pessoa.getIdade();
                opTemp.println(
                "Quantidade Caracteres : " + caracteres + "\n" + 
                "Nome : " + nome + "\n" + 
                "CPF : " + cpf + "\n" + 
                "Idade : " + idade);
            }
        }
    }
    public void writeFile(FileOutputStream fout) {
        try {
            System.out.println("Arquivo Criado");
            BufferedOutputStream buffer = new BufferedOutputStream(fout);
            int quantidade = pessoas.length;
            buffer.write((quantidade + "\n").getBytes());
            for (Pessoa pessoa : pessoas) {
                if (pessoa != null) {
                    int caracteres = pessoa.getNome().getBytes().length;
                    String nome = pessoa.getNome();
                    String cpf = pessoa.getCpf();
                    int idade = pessoa.getIdade();
                    buffer.write(
                    (caracteres + "\n" +
                    nome + "\n" +
                    cpf + "\n" +
                    idade + "\n").getBytes());
                }
            }
            buffer.close();
            System.out.println("Arquivo Escrito");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
    public void writeTCP() {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(this.op);
            int numeroDePessoas = pessoas.length;
            dataOutputStream.writeInt(numeroDePessoas);
            for (Pessoa pessoa : pessoas) {
                int tamanhoNome = pessoa.getNome().getBytes("UTF-8").length;
                dataOutputStream.writeInt(tamanhoNome);
                dataOutputStream.write(pessoa.getNome().getBytes());
                dataOutputStream.writeUTF(pessoa.getCpf());
                dataOutputStream.writeInt(pessoa.getIdade());
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
    public static void main(String args[]) {
        try {
            Pessoa pessoa1 = new Pessoa("Mateus Lima Rodrigues", "03766032321", 20);
            Pessoa pessoa2 = new Pessoa("Miguel Lima Rodrigues", "12345678910", 13);
            Pessoa pessoa3 = new Pessoa("Maria Beatriz", "10987654321", 20);
            Pessoa[] pessoas = { pessoa1 , pessoa2 , pessoa3};
            PessoasOutputStream pessoaOutputStreamSys = new PessoasOutputStream(pessoas, System.out);
            pessoaOutputStreamSys.writeSystem();
            pessoaOutputStreamSys.close();
            File arquivo = new File("arquivo_pessoas.dat");
            FileOutputStream fileOutputStream = new FileOutputStream(arquivo);
            PessoasOutputStream pessoasOutputStreamFile = new PessoasOutputStream(pessoas, fileOutputStream);
            pessoasOutputStreamFile.writeFile(fileOutputStream);
            pessoasOutputStreamFile.close();
            String servidorRemoto = "172.25.250.237";
            int porta = 12345;
            Socket socket = new Socket(servidorRemoto, porta);
            PessoasOutputStream pessoasOutputStream = new PessoasOutputStream(pessoas, socket.getOutputStream());
            pessoasOutputStream.writeTCP();
            socket.close();
            pessoasOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void write(int b) throws IOException {
        // TODO Auto-generated method stub
    }
}