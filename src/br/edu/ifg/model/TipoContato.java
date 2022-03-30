package br.edu.ifg.model;

public class TipoContato {

    private String descricao;

    public TipoContato() {
    }

    public TipoContato(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}