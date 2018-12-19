package com.example.pilasnotebook.mapasdelnortedigital.model.POJO;

public class Reservas {

    private String telefonoDeReservas;
    private String tolerancia;
    private String seña;

    public Reservas(){
    }

    public Reservas(String telefonoDeReservas, String tolerancia, String seña) {
        this.telefonoDeReservas = telefonoDeReservas;
        this.tolerancia = tolerancia;
        this.seña = seña;
    }

    public String getSeña() {
        return seña;
    }

    public void setSeña(String seña) {
        this.seña = seña;
    }

    public String getTolerancia() {
        return tolerancia;
    }

    public void setTolerancia(String tolerancia) {
        this.tolerancia = tolerancia;
    }

    public String getTelefonoDeReservas() {
        return telefonoDeReservas;
    }

    public void setTelefonoDeReservas(String telefonoDeReservas) {
        this.telefonoDeReservas = telefonoDeReservas;
    }
}
