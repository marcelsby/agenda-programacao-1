package br.edu.ifg.model;

public class TipoEndereco {

    private String descricao;

    public TipoEndereco() {
    }

    public TipoEndereco(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}