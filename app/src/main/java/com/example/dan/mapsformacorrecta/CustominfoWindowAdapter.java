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
        TextView siglas = (TextView) v.findViewById(R.id.sigla);
        TextView direccion = (TextView) v.findViewById(R.id.direccion);
        if(!infosnip.equals("")){
            String[] info= infosnip.split(",");
            siglas.setText("Siglas de la central: "+ info[0]);
            direccion.setText(info[1]);
        }

        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}