package com.example.dan.mapsformacorrecta;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    public static String DIREC_KEY = "direccion";
    public static String url_key = "algo";
    Button btn;
    Intent intent;


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
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView textView = (TextView) getActivity().findViewById(R.id.titulo_D);
        TextView textView2 = (TextView) getActivity().findViewById(R.id.sigla_D);
        TextView textView3 = (TextView) getActivity().findViewById(R.id.direccion_D);
        btn = getActivity().findViewById(R.id.idbtn);

        Bundle extras = getArguments();

        if (extras != null) {
            String titulo = (String) extras.get(CentralesDetalle.TEXT_KEY);
            String sigla = (String) extras.get(CentralesDetalle.SIGLA_KEY);
            String direccion = (String) extras.get(CentralesDetalle.DIREC_KEY);
            final String linked = (String) extras.get(CentralesDetalle.url_key);

            textView.setText(titulo); //Nombre
            textView2.setText(sigla);
            textView3.setText(direccion);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linked));
                    startActivity(intent);
                }
            });
        }
    }
}
