package com.example.pilasnotebook.mapasdelnortedigital.model.POJO;

import java.util.List;

public class DatosDeContacto {

    private String telefono;
    private String mail;
    private String  paginaWeb;
    private List<String> redes;

    public DatosDeContacto(){

    }

    public DatosDeContacto(String telefono, String mail, String paginaWeb, List<String> redes) {
        this.telefono = telefono;
        this.mail = mail;
        this.paginaWeb = paginaWeb;
        this.redes = redes;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public List<String> getRedes() {
        return redes;
    }

    public void setRedes(List<String> redes) {
        this.redes = redes;
    }
}
