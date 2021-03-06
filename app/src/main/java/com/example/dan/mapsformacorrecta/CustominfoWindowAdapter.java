package com.example.dan.mapsformacorrecta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Dell on 17/01/2018.
 */

public class CustominfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private   View v;
    private Context mcontext;

    public CustominfoWindowAdapter(Context context) {
        mcontext = context;
        v= LayoutInflater.from(context).inflate(R.layout.info_window,null);
    }

    @Override
    public View getInfoWindow(Marker marker) {

        String titulo = marker.getTitle();
        TextView tvtitulo = (TextView) v.findViewById(R.id.titulo);
        if (!titulo.equals("")){
            tvtitulo.setText(titulo);
        }

        String infosnip = marker.getSnippet();
        TextView etiqueta = (TextView) v.findViewById(R.id.etiqueta_sigla);
        TextView siglas = (TextView) v.findViewById(R.id.sigla);
        TextView direccion = (TextView) v.findViewById(R.id.direccion);
        if(infosnip!= null) {
            String[] info = infosnip.split("\\|");

            if (info[0].equals("null")) {
                if (info[2].equals("null")) {
                    if(info[3].equals("null")){
                        siglas.setText("");    //EN caso de no contar con siglas
                        etiqueta.setText("");
                    } else {
                        siglas.setText(info[3]); //Distrito
                        etiqueta.setText("Distrito:");
                    }
                } else {
                    siglas.setText(info[2]); //Referencia SISA
                }
            } else {
                siglas.setText(info[0]);   //Siglas de la central
                etiqueta.setText("SIGLAS:");
            }
            if (info[1].equals("null")) {
                direccion.setText("Direccion no especificada");
            } else {
                direccion.setText(info[1]);
            }

        }
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
       return null;
    }
}
