package com.t2.listacomprasapp.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class listasModel {

    private String emailUsuario;
    private List<MercadoriasModel> mercadorias;
    @ServerTimestamp
    private Date data;

    public listasModel() {}

    public listasModel(String emailUsuario, List<MercadoriasModel> mercadorias, Date data) {
        this.emailUsuario = emailUsuario;
        this.mercadorias = mercadorias;
        this.data = data;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public List<MercadoriasModel> getMercadorias() {
        return mercadorias;
    }

    public void setMercadorias(List<MercadoriasModel> mercadorias) {
        this.mercadorias = mercadorias;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
