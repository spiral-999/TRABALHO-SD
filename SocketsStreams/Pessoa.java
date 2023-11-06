package SocketsStreams;
// classe pessoa que representa as informações
public class Pessoa {
    public String nome; //nome
    public String cpf; //cpf
    public int idade; //idade
    public Pessoa(String nome, String cpf, int idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }
    public String getNome() {
        return this.nome;
    }
    void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return this.cpf;
    }
    void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public int getIdade() {
        return this.idade;
    }
    void setIdade(int idade) {
        this.idade = idade;
    }
    public String toString(){
        String 
        out = "Nome : " + this.getNome();
        out += "\n";
        out += "CPF : " + this.getCpf();
        out += "\n";
        out += "Idade : " + this.getIdade();
        out += "\n";
        return out;
    }
}