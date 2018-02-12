package com.example.dan.mapsformacorrecta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static com.example.dan.mapsformacorrecta.Fragment1.btnCos;
import static com.example.dan.mapsformacorrecta.Fragment1.btnSax;
import static com.example.dan.mapsformacorrecta.Fragment1.btnVer;



public class Fragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment1, container, false);

        btnCos = root.findViewById(R.id.idCos);
        btnVer = root.findViewById(R.id.idVer);
        btnSax = root.findViewById(R.id.idSax);

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

        return root;
    }
}
