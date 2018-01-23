package com.example.dan.mapsformacorrecta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;   SE NECESITA PARA MOSTRAR LA IMAGEN  ANTES AGREGAR EN EL GRADLE

/**
 * Created by Dell on 02/01/2018.
 */

public class CentralesDetalle extends Fragment {

    public static String ICON_KEY = "foto";
    public static String TEXT_KEY = "text";
    public static String SIGLA_KEY = "sigla";
    public static String DIREC_KEY = "direccio";


    public static CentralesDetalle newInstance(@Nullable Bundle extras) {
        CentralesDetalle fragment = new CentralesDetalle();

        if (extras != null) {
            fragment.setArguments(extras);
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_window, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView textView = (TextView) getActivity().findViewById(R.id.titulo);
        //ImageView imageView = (ImageView) getActivity().findViewById(R.id.G_imagen_detalle);
        TextView textView2 = (TextView) getActivity().findViewById(R.id.sigla);
        TextView textView3 = (TextView) getActivity().findViewById(R.id.direccion);


        Bundle extras = getArguments();

        if (extras != null) {
           // String foto = (String) extras.get(CentralDetalle.ICON_KEY);
            String titulo = (String) extras.get(CentralesDetalle.TEXT_KEY);
            String sigla = (String) extras.get(CentralesDetalle.SIGLA_KEY);
            String direccion = (String) extras.get(CentralesDetalle.DIREC_KEY);


            textView.setText(titulo); //Nombre
            //Picasso.with(getContext()).load(foto).into(imageView); //aqui cargamos la imagen
            textView2.setText(sigla);
            textView3.setText(direccion);

        }
    }
}
