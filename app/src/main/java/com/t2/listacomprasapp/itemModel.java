package com.t2.listacomprasapp;

public class itemModel {
    private String criador;
    private String ID;
    private String preco;

    private  float valor = Float.parseFloat(preco);

    public itemModel() {
    }

    public itemModel(String criador, String ID, String preco, float valor) {
        this.criador = criador;
        this.ID = ID;
        this.preco = preco;
        this.valor = valor;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getCriador() {
        return criador;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}

