package br.edu.ifsc.fln.maonamassa3.domain;

public class Servico {
    private int id;
    private String descricao;
    private double valor;
    private static int pontos;
    private ECategoria categoria;

    public Servico(int id, String descricao, double valor, ECategoria categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        pontos = 20; // Pontuação para qualquer tipo de serviço.
    }
    // Lógica do valor do serviço referente a categoria do veiculo.
    public double calculaValorPelaCategoria(ECategoria categoria) {
        // Criando essa variavel temporaria para não alterar o valor do serviço, no atributo da classe.
        double precoFinal = this.valor;

        switch (categoria) {
            case MOTO:
                precoFinal = this.valor * 0.90;
                break;
            case PEQUENO:
                precoFinal = this.valor * 0.95;
                break;
            case MEDIO:
                precoFinal = this.valor * 1.10;
                break;
            case GRANDE:
                precoFinal = this.valor * 1.20;
                break;
            default:
                precoFinal = this.valor; // Valor para veiculo padrão.
        }
        return precoFinal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public static int getPontos() {
        return pontos;
    }

    public static void setPontos(int pontos) {
        Servico.pontos = pontos;
    }
    public ECategoria getCategoria() {
        return categoria;
    }
    public void setCategoria(ECategoria categoria) {
        this.categoria = categoria;
    }

}
