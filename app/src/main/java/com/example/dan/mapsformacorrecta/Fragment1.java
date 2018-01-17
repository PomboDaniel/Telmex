package com.example.dan.mapsformacorrecta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class Fragment1 extends Fragment {

    private ArrayList<Regiones> lista_regiones;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment1, container, false);

        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recyclerFrag1);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        lista_regiones = new ArrayList<>();
        llenaRegiones();
        adapter = new Adapter(lista_regiones, getContext());

        recyclerView.setAdapter(adapter);

        /*root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "click en centrales! ", Toast.LENGTH_LONG).show();
            }
        });*/

        return root;
    }


    public void llenaRegiones(){

        lista_regiones.add(new Regiones("Todas las regiones"));
        lista_regiones.add(new Regiones("Veracruz"));
        lista_regiones.add(new Regiones("San andres tuxtla"));
        lista_regiones.add(new Regiones("Cosamaloapan"));
    }
}
