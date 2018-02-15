package com.example.dan.mapsformacorrecta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import static com.example.dan.mapsformacorrecta.Fragment1.lista_regiones;
import static com.example.dan.mapsformacorrecta.Fragment1.llenaRegiones;

public class Fragment2 extends Fragment {

    public static CardView cardCos, cardVer, cardSax;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment2, container, false);

        cardCos = root.findViewById(R.id.idCardCos);
        cardVer = root.findViewById(R.id.idCardVer);
        cardSax = root.findViewById(R.id.idCardSax);

        lista_regiones = new ArrayList<>();
        llenaRegiones();

        cardVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("position_card", 0);
                intent.putExtra("clave", 20);
                startActivity(intent);
            }
        });

        cardCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("position_card", 2);
                intent.putExtra("clave", 20);
                startActivity(intent);
            }
        });

        cardSax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("position_card", 1);
                intent.putExtra("clave", 20);
                startActivity(intent);
            }
        });

        return root;
    }
}
