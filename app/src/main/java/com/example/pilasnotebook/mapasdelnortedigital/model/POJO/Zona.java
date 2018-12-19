package com.example.pilasnotebook.mapasdelnortedigital.model.POJO;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Zona implements Serializable {

    private String direccion;
    private String localidad;
    private String provincia;
    private String pais;
    private LatLng latlang;

    public Zona() {
    }

    public Zona(String direccion, String localidad, String provincia, String pais) {
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public LatLng getLatlang() {
        return latlang;
    }

    public void setLatlang(LatLng latlang) {
        this.latlang = latlang;
    }
}
