package com.example.dan.mapsformacorrecta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.dan.mapsformacorrecta.Fragment1.adapter;
import static com.example.dan.mapsformacorrecta.Fragment1.lista_regiones;
import static com.example.dan.mapsformacorrecta.Fragment1.llenaRegiones;


public class Fragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment3, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerFrag3);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        lista_regiones = new ArrayList<>();
        llenaRegiones();
        adapter = new Adapter(lista_regiones, getContext());

        recyclerView.setAdapter(adapter);
        /*root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "click aqui! ", Toast.LENGTH_LONG).show();
            }
        });*/

        return root;
    }
}
