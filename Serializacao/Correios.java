package Serializacao;

import java.io.Serializable;
public class Correios implements Serializable {
    String nome;
    String destinatario;
    String remetente;
    int tipo;
    public Correios(String nome, String destinatario, String remetente, int tipo) {
        this.nome = nome;
        this.destinatario = destinatario;
        this.remetente = remetente;
        this.tipo = tipo;
    }
    @Override
    public String toString() {
        return
        "Nome : " + nome + "/n" +
        "De : " + remetente + "/n" +
        "Para : " + destinatario + "/n"; 
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
        "Nome : " + nome + "/n" +
        "Assunto : " + assunto + "/n" +
        "De : " + remetente + "/n" +
        "Para : " + destinatario + "/n"; 
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
        "Nome : " + nome + "/n" +
        "Encomenda : " + loja + "/n" +
        "De : " + remetente + "/n" +
        "Para : " + destinatario + "/n"; 
    }
}
class Controle {
    private Correios[] correios;
    public Controle() {
        correios = new Correios[1];
    }
    public void incriseSize() {
        Correios[] correiosTemp = new Correios[correios.length + 2];
        for (int i = 0; i < correios.length; i++) {
            correiosTemp[i] = correios[i];
        }
        correios = correiosTemp;
    }
    public boolean isEmpty() {
        return correios[0] == null;
    }
    public boolean isFull() {
        return correios[correios.length - 1] != null;
    }
    public Correios[] getCorreios() {
        if (isEmpty()) {
            System.out.println("Não há produtos cadastrados");
            return null;
        }
        return correios;
    }
    public void addCartas(String nome, String destinatario, String remetente, String assunto){
        if (isEmpty()) {
            correios[0] = new Cartas(nome, destinatario, remetente, assunto);
        } else if (isFull()) {
            incriseSize();
            for (int i = 0; i < correios.length; i++) {
                if (correios[i] == null) {
                    correios[i] = new Cartas(nome, destinatario, remetente, assunto);
                    break;
                }
            }
        } else {
            for (int i = 0; i < correios.length; i++) {
                if (correios[i] == null) {
                    correios[i] = new Cartas(nome, destinatario, remetente, assunto);
                    break;
                }
            }
        }
    }
    public void addEncomendas(String nome, String destinatario, String remetente, String loja) {
        if (isEmpty()) {
            correios[0] = new Encomendas(nome, destinatario, remetente, loja);
        } else if (isFull()) {
            incriseSize();
            for (int i = 0; i < correios.length; i++) {
                if (correios[i] == null) {
                    correios[i] = new Encomendas(nome, destinatario, remetente, loja);
                    break;
                }
            }
        } else {
            for (int i = 0; i < correios.length; i++) {
                if (correios[i] == null) {
                    correios[i] = new Encomendas(nome, destinatario, remetente, loja);
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
}