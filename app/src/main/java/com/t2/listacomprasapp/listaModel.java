package com.t2.listacomprasapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class listaModel {
    private String criador;
    private String criadaEm;
    private String modificadaEm;
    private String[][] itens;
    private String id;

    public listaModel() {
    }

    public listaModel(String criador, String criadaEm, String modificadaEm, String[][] itens) {
        this.id = id; //id unico da lista
        this.criador = criador;  //autor da lista
        this.criadaEm = criadaEm;  //data de criacao
        this.modificadaEm = modificadaEm; //ultima modificacao
        this.itens = itens; //matriz com os ids e qtdade de cada item
        //valor é recebido do servidor para cada item,  e totais são calculados no cliente
        //luxo: requisição e aquisição de preços num único vetor
    }

    public String getCriador() {
        return criador;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    public String getCriadaEm() {
        return criadaEm;
    }

    public void setCriadaEm(String criadaEm) {
        this.criadaEm = criadaEm;
    }

    public String getModificadaEm() {
        return modificadaEm;
    }

    public void setModificadaEm(String modificadaEm) {
        this.modificadaEm = modificadaEm;
    }

    public String[][] getItens() {
        return itens;
    }

    public void setItens(String[][] itens) {
        this.itens = itens;
    }

    public void salvaLista(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child(getId()).setValue(this);
    }

}
