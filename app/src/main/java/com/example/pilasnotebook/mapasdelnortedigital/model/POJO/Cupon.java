package com.example.pilasnotebook.mapasdelnortedigital.model.POJO;


import java.io.Serializable;

public class Cupon implements Serializable {

    private String tipo;
    private String fecha;
    private String titulo;
    private String descripcion;
    private String foto;
    private Cliente clienteCupon;


    public Cupon() {

    }

    public Cupon(String tipo, String fecha, String titulo, String descripcion, Cliente clienteCupon) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.clienteCupon = clienteCupon;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Cliente getClienteCupon() {
        return clienteCupon;
    }

    public void setClienteCupon(Cliente clienteCupon) {
        this.clienteCupon = clienteCupon;
    }
}
