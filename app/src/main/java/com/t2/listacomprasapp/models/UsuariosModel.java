package com.t2.listacomprasapp.models;

public class UsuariosModel {

    private String email;
    private String UIDusuario;

    public UsuariosModel() {}

    public UsuariosModel(String email, String UIDusuario) {
        this.email = email;
        this.UIDusuario = UIDusuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUIDusuario() {
        return UIDusuario;
    }

    public void setUIDusuario(String UIDusuario) {
        this.UIDusuario = UIDusuario;
    }
}
