package com.example.pilasnotebook.mapasdelnortedigital.model.POJO;

import java.io.Serializable;

public class DatosDeFacturacion implements Serializable {

    private String nombreBanco;
    private String nombretitular;
    private String numeroDeCuenta;
    private String tipo;
    private String cbu;
    private String cuit;

    public DatosDeFacturacion(){
    }

    public DatosDeFacturacion(String nombreBanco, String nombretitular, String numeroDeCuenta, String tipo, String cbu, String cuit) {
        this.nombreBanco = nombreBanco;
        this.nombretitular = nombretitular;
        this.numeroDeCuenta = numeroDeCuenta;
        this.tipo = tipo;
        this.cbu = cbu;
        this.cuit = cuit;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getNombretitular() {
        return nombretitular;
    }

    public void setNombretitular(String nombretitular) {
        this.nombretitular = nombretitular;
    }

    public String getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public void setNumeroDeCuenta(String numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
}
