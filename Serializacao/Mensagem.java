package Serializacao;

import java.io.Serializable;

class Mensagem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String conteudo;
    private Correios correios;
    private Correios[] arrayCorreios;
    public Mensagem(String conteudo) {
        this.conteudo = conteudo;
    }
    public Mensagem(Correios correios) {
        this.correios = correios;
    }
    public Mensagem(Correios[] arrayCorreios) {
        this.arrayCorreios = arrayCorreios;
    }
    public String getConteudo() {
        return conteudo;
    }
    public Correios getProduto() {
        return correios;
    }
    public Correios[] getArrayProdutos() {
        return arrayCorreios;
    }
    public void setConteudo(String mensagem) {
        this.conteudo = mensagem;
    }
}