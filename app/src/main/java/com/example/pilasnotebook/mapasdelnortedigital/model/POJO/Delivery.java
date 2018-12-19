package com.example.pilasnotebook.mapasdelnortedigital.model.POJO;

import java.io.Serializable;

public class Delivery implements Serializable {
    
    private String numeroTelefonoDelivery;
    private String radioDeEntrega;

    public Delivery(){
    }
    
    public Delivery(String numeroTelefonoDelivery, String radioDeEntrega) {
        this.numeroTelefonoDelivery = numeroTelefonoDelivery;
        this.radioDeEntrega = radioDeEntrega;
    }

    public String getNumeroTelefonoDelivery() {
        return numeroTelefonoDelivery;
    }

    public void setNumeroTelefonoDelivery(String numeroTelefonoDelivery) {
        this.numeroTelefonoDelivery = numeroTelefonoDelivery;
    }

    public String getRadioDeEntrega() {
        return radioDeEntrega;
    }

    public void setRadioDeEntrega(String radioDeEntrega) {
        this.radioDeEntrega = radioDeEntrega;
    }
}
