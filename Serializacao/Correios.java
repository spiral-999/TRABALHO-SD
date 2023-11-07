package Serializacao;

import java.io.Serializable;

public class Correios implements Serializable {
    String nome;
    String remetente;
    String destinatario;
    int tipo; 
    public Correios(String nome, String remetente, String destinatario, int tipo) {
        this.nome = nome;
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.tipo = tipo;
    }
    @Override
    public String toString() {
        return 
        "Nome : " + nome + "-" +
        "De : " + remetente + "-" +
        "Para : " + destinatario;
    }
    public static void main(String[] args) {
    }
}
class Cartas extends Correios {
    String assunto;
    public Cartas(String nome, String destinatario, String remetente, String assunto) {
        super(nome, destinatario,remetente, 0);
        this.assunto = assunto;
    }
    @Override
    public String toString() {
        return
        "Nome : " + nome + "-" +
        "Assunto : " + assunto + "-" +
        "De : " + remetente + "-" +
        "Para : " + destinatario; 
    }
}
class Encomendas extends Correios {
    String loja;
    public Encomendas(String nome, String destinatario, String remetente, String loja) {
        super(nome, destinatario, remetente, 1);
        this.loja = loja;
    }
    @Override
    public String toString() {
        return 
        "Nome : " + nome + "-" +
        "Encomenda : " + loja + "-" +
        "De : " + remetente + "-" +
        "Para : " + destinatario; 
    }
}
class Telegrama extends Correios {
    int ano;
    public Telegrama(String nome, String destinatario, String remetente, int ano) {
        super(nome, destinatario, remetente, 3);
        this.ano = ano;
    }
    @Override
    public String toString() {
        return 
        "Nome : " + nome + "-" +
        "Ano : " + ano + "-" +
        "De : " + remetente + "-" +
        "Para : " + destinatario; 
    }
}
class Controle {
    private Correios[] correios;
    public Controle() {
        correios = new Correios[3];
        correios[0] = new Cartas("Carta 1", "Joao", "Pedro", "Assunto");
        correios[1] = new Encomendas("Encomenda 1", "Paulo", "Henrique", "Amazon");
        correios[2] = new Telegrama("Telegrama 1", "Mateus", "Miguel", 2003);
    }
    public void incriseSize() {
        Correios[] produtosTemp = new Correios[correios.length + 2];
        for (int i = 0; i < correios.length; i++) {
            produtosTemp[i] = correios[i];
        }
        correios = produtosTemp;
    }
    public boolean isEmpty() {
        return correios[0] == null;
    }
    public boolean isFull() {
        return correios[correios.length - 1] != null;
    }
    public Correios[] getCorreios() {
        if (isEmpty()) {
            System.out.println("Nenhum registro nos correios encontrado");
            return null;
        }
        Correios[] produtosTemp = new Correios[correios.length];
        int j = 0;
        for (int i = 0; i < correios.length; i++) {
            if (correios[i] instanceof Cartas) {
                produtosTemp[j] = correios[i];
                j++;
            }
        }
        for (int i = 0; i < correios.length; i++) {
            if (correios[i] instanceof Encomendas) {
                produtosTemp[j] = correios[i];
                j++;
            }
        }
        for (int i = 0; i < correios.length; i++) {
            if (correios[i] instanceof Telegrama) {
                produtosTemp[j] = correios[i];
                j++;
            }
        }
        return produtosTemp;
    }
    public void addCartas(String nome, String destinatario, String rementente, String assunto) {
        if (isEmpty()) {
            correios[0] = new Cartas(nome, destinatario, rementente, assunto);
        } else if (isFull()) {
            incriseSize();
            for (int i = 0; i < correios.length; i++) {
                if (correios[i] == null) {
                    correios[i] = new Cartas(nome, destinatario, rementente, assunto);
                    break;
                }
            }
        } else {
            for (int i = 0; i < correios.length; i++) {
                if (correios[i] == null) {
                    correios[i] = new Cartas(nome, destinatario, rementente, assunto);
                    break;
                }
            }
        }
    }
    public void addEncomendas(String nome, String destinatario, String rementente, String loja) {
        if (isEmpty()) {
            correios[0] = new Encomendas(nome, destinatario, rementente, loja);
        } else if (isFull()) {
            incriseSize();
            for (int i = 0; i < correios.length; i++) {
                if (correios[i] == null) {
                    correios[i] = new Encomendas(nome, destinatario, rementente, loja);
                    break;
                }
            }
        } else {
            for (int i = 0; i < correios.length; i++) {
                if (correios[i] == null) {
                    correios[i] = new Encomendas(nome, destinatario, rementente, loja);
                    break;
                }
            }
        }
    }
    public void addTelegrama(String nome, String destinatario, String rementente, int ano) {
        if (isEmpty()) {
            correios[0] = new Telegrama(nome, destinatario, rementente, ano);
        } else if (isFull()) {
            incriseSize();
            for (int i = 0; i < correios.length; i++) {
                if (correios[i] == null) {
                    correios[i] = new Telegrama(nome, destinatario, rementente, ano);
                    break;
                }
            }
        } else {
            for (int i = 0; i < correios.length; i++) {
                if (correios[i] == null) {
                    correios[i] = new Telegrama(nome, destinatario, rementente, ano);
                    break;
                }
            }
        }
    }
    public void removerCarta(String nome) {
        for (int i = 0; i < correios.length; i++) {
            if (correios[i] instanceof Cartas && correios[i].nome.equals(nome)) {
                for (int j = i; j < correios.length - 1; j++) {
                    correios[j] = correios[j + 1];
                }
                correios[correios.length - 1] = null;
                break;
            }
        }
    }
    public void removerEncomenda(String nome) {
        for (int i = 0; i < correios.length; i++) {
            if (correios[i] instanceof Encomendas && correios[i].nome.equals(nome)) {
                for (int j = i; j < correios.length - 1; j++) {
                    correios[j] = correios[j + 1];
                }
                correios[correios.length - 1] = null;
                break;
            }
        }
    }
    public void removerTelegrama(String nome) {
        for (int i = 0; i < correios.length; i++) {
            if (correios[i] instanceof Telegrama && correios[i].nome.equals(nome)) {
                for (int j = i; j < correios.length - 1; j++) {
                    correios[j] = correios[j + 1];
                }
                correios[correios.length - 1] = null;
                break;
            }
        }
    }
    public void trocarCarta(String nomeAntigo, String nomeNovo, String destinatario, String remetente, String assunto ) {
        for (int i = 0; i < correios.length; i++) {
            if (correios[i] instanceof Cartas && correios[i].nome.equals(nomeAntigo)) {
                correios[i] = new Cartas(nomeNovo, remetente, destinatario, assunto);
                break;
            } else if (correios[i] == null) {
                System.out.println("Produto não encontrado");
                break;
            }
        }
    }
    public void trocarEncomenda(String nomeAntigo, String nomeNovo, String destinatario, String remetente, String loja ) {
        for (int i = 0; i < correios.length; i++) {
            if (correios[i] instanceof Cartas && correios[i].nome.equals(nomeAntigo)) {
                correios[i] = new Cartas(nomeNovo, remetente, destinatario, loja);
                break;
            } else if (correios[i] == null) {
                System.out.println("Produto não encontrado");
                break;
            }
        }
    }
    public void trocarTelegrama(String nomeAntigo, String nomeNovo, String destinatario, String remetente, String ano ) {
        for (int i = 0; i < correios.length; i++) {
            if (correios[i] instanceof Cartas && correios[i].nome.equals(nomeAntigo)) {
                correios[i] = new Cartas(nomeNovo, remetente, destinatario, ano);
                break;
            } else if (correios[i] == null) {
                System.out.println("Produto não encontrado");
                break;
            }
        }
    }
}