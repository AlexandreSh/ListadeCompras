package com.t2.listacomprasapp.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class ListasModel {

    private String nomeListas;

    public ListasModel() {}

    public ListasModel(String nomeListas) {
        this.nomeListas = nomeListas;
    }

    public String getNomeListas() {
        return nomeListas;
    }

    public void setNomeListas(String nomeListas) {
        this.nomeListas = nomeListas;
    }
}
