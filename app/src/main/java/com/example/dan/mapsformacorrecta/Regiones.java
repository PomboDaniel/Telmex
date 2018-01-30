package com.example.dan.mapsformacorrecta;

public class Regiones{

    private String titulo;
    private int indice;

    public Regiones(String titulo, int indice){

        this.titulo = titulo;
        this.indice = indice;
    }


    public String getTitulo(){
        return titulo;
    }


    public int getIndice(){
        return indice;
    }
}
