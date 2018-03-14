package com.example.dan.mapsformacorrecta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class Fragment1 extends Fragment {

    public static ArrayList<Regiones> lista_regiones;
    //public static Adapter adapter;
    public static CardView cardCos, cardVer, cardSax;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
