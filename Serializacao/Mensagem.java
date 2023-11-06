package Serializacao;

import java.io.Serializable;

public class Mensagem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String conteudo;
    public Correios correios;
    public Mensagem(String conteudo) {
        this.conteudo = conteudo;
    }
    public Mensagem(Correios correios) {
        this.correios = correios;
    }
    public String getConteudo() {
        return conteudo;
    }
    public Correios getCorreios() {
        return correios;
    }
    public void setConteudo(String mensagem) {
        this.conteudo = mensagem;
    }
}