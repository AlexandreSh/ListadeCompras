package com.t2.listacomprasapp.models;

public class MercadoriasModel {
    private String nome;
    private String preco;

    public MercadoriasModel() {}

    public MercadoriasModel(String nome, String preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}
