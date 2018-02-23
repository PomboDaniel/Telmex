package com.example.dan.mapsformacorrecta;

/**
 * Created by Dell on 15/01/2018.
 */

public class markers_maps {

    public String nombre;
    public String direccion;
    public String siglas;
    public Double latitud;
    public Double longitud;
    public String referencia;
    public String link;
    public String distrito;

    public String getDistrito() { return distrito; }

    public String getReferencia() { return referencia; }

    public Double getLatitud(){
        return latitud;
    }

    public Double getLongitud(){
        return longitud;
    }

    public String getNombre(){
        return nombre;
    }

    public  String getDireccion(){
        return direccion;
    }

    public  String getSiglas(){
        return siglas;
    }

    public String getLink(){
        return link;
    }
}
