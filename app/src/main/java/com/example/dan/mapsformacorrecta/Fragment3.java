package com.example.dan.mapsformacorrecta;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class Fragment3 extends Fragment {

    private CardView card7330, card7356, repetidores;
    static ArrayList<TBAS> lista_tbas;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment3, container, false);

        card7330 = root.findViewById(R.id.idCard7330);
        card7356 = root.findViewById(R.id.idCard7356);
        repetidores = root.findViewById(R.id.idCardRepetidores);

        lista_tbas = new ArrayList<>();
        llenaTbas();

        card7330.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("position_card", 0);
                intent.putExtra("clave", 10);
                startActivity(intent);
            }
        });

        card7356.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("position_card", 1);
                intent.putExtra("clave", 10);
                startActivity(intent);
            }
        });

        repetidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("position_card", 2);
                intent.putExtra("clave", 10);
                startActivity(intent);
            }
        });

        return root;
    }


    private void llenaTbas(){

        lista_tbas.add(new TBAS("alcatel7330", 0));
        lista_tbas.add(new TBAS("alcatel7356", 1));
        lista_tbas.add(new TBAS("repetidores", 2));
    }
}
