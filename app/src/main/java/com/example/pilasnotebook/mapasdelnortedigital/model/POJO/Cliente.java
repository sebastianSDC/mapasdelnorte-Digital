package com.example.pilasnotebook.mapasdelnortedigital.model.POJO;


import java.io.Serializable;

public class Cliente implements Serializable {

    private String id; // lo agrego del auth.
    private String nombreComercio;
    private String categoria;
    private Zona zona;
    private DatosDeContacto datosDeContacto;
    private DatosAdicionales datosAdicionales;
    private String descripcionComercio; //lo agrego por set
    private String foto;                //lo agrego por set

    private String horarioDeAtencion;   //lo agrego por set

    //TODO: falta agregarle los list de cuponera y cartelera...



    public Cliente() {

    }

    public Cliente(String nombre, String categoria, Zona zona, DatosDeContacto datosDeContacto, DatosAdicionales datosAdicionales) {
        this.nombreComercio = nombre;
        this.categoria = categoria;
        this.zona = zona;
        this.datosDeContacto = datosDeContacto;


        this.datosAdicionales = datosAdicionales;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombre) {
        this.nombreComercio = nombre;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }


    public String getHorarioDeAtencion() {
        return horarioDeAtencion;
    }

    public void setHorarioDeAtencion(String horarioDeAtencion) {
        this.horarioDeAtencion = horarioDeAtencion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcionComercio() {
        return descripcionComercio;
    }

    public void setDescripcionComercio(String descripcionComercio) {
        this.descripcionComercio = descripcionComercio;
    }

    public DatosDeContacto getDatosDeContacto() {
        return datosDeContacto;
    }

    public void setDatosDeContacto(DatosDeContacto datosDeContacto) {
        this.datosDeContacto = datosDeContacto;
    }

    public DatosAdicionales getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(DatosAdicionales datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }
}