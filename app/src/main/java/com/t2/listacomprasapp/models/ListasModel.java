package com.t2.listacomprasapp.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ListasModel implements Serializable {

    private String nomeListas, idLista, criador;
    private Date date;

    public ListasModel() {}

    public ListasModel(String nomeListas) {
        this.nomeListas = nomeListas;
    }

    public String getNomeListas() {
        return nomeListas;
    }

    public String getIdLista() {
        return idLista;
    }

    public void setIdLista(String idLista) {  this.idLista = idLista;}

    public String getCriador() {return criador; }

    public void setCriador(String criador) {  this.criador = criador;}

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public void setNomeListas(String nomeListas) {
        this.nomeListas = nomeListas;
    }
}
