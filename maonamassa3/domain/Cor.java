package br.edu.ifsc.fln.maonamassa3.domain;

public class Cor {
    private long id;
    private String nome;

    // Construtor vazio, pois posso adcionar os atributos dele com o Método SET.
    public Cor() {
    }

    public Cor(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Informações da Cor - " +
                " | Identificação: " + id +
                " | Nome da Cor: " + nome;
    }
}