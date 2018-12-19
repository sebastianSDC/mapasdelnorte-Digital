package com.example.pilasnotebook.mapasdelnortedigital.model.POJO;

import java.io.Serializable;

public class DatosAdicionales implements Serializable {

    private Reservas reserva;
    private Delivery delivery;
    private DatosDeFacturacion datosDeFacturacion;

    public DatosAdicionales(){
    }

    public DatosAdicionales(Reservas reserva, Delivery delivery, DatosDeFacturacion datosDeFacturacion) {
        this.reserva = reserva;
        this.delivery = delivery;
        this.datosDeFacturacion = datosDeFacturacion;
    }

    public Reservas getReserva() {
        return reserva;
    }

    public void setReserva(Reservas reserva) {
        this.reserva = reserva;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public DatosDeFacturacion getDatosDeFacturacion() {
        return datosDeFacturacion;
    }

    public void setDatosDeFacturacion(DatosDeFacturacion datosDeFacturacion) {
        this.datosDeFacturacion = datosDeFacturacion;
    }
}
