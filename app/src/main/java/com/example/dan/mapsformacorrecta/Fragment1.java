package com.example.dan.mapsformacorrecta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import java.util.ArrayList;

public class Fragment1 extends Fragment {

    public static ArrayList<Regiones> lista_regiones;
    public static Adapter adapter;
    public static ImageButton btnCos, btnVer, btnSax;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment1, container, false);

        btnCos = root.findViewById(R.id.idCos);
        btnVer = root.findViewById(R.id.idVer);
        btnSax = root.findViewById(R.id.idSax);

        lista_regiones = new ArrayList<>();
        llenaRegiones();

        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("position_card", 0);
                startActivity(intent);
            }
        });

        btnCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("position_card", 2);
                startActivity(intent);
            }
        });

        btnSax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("position_card", 1);
                startActivity(intent);
            }
        });


        //************EN CASO QUE SE QUIERA USAR UN RECYLCER**********

        /*RecyclerView recyclerView = root.findViewById(R.id.recyclerFrag1);

        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getDrawable(R.drawable.line2), false, false));
        recyclerView.addItemDecoration(new SpaceItemDecoration(getActivity(), R.dimen.list_space, true, true));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new Adapter(lista_regiones, getContext());

        recyclerView.setAdapter(adapter);*/

        /*root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "click en centrales! ", Toast.LENGTH_LONG).show();
            }
        });*/

        return root;
    }


    public static void llenaRegiones(){

        lista_regiones.add(new Regiones("Veracruz", 0));
        lista_regiones.add(new Regiones("San andres tuxtla", 1));
        lista_regiones.add(new Regiones("Cosamaloapan", 2));
    }
}
