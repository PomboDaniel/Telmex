package com.example.dan.mapsformacorrecta;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class TBAS{

    private String titulo;
    private int indice;

    public TBAS(String titulo, int indice){

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