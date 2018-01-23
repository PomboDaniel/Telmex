package com.example.dan.mapsformacorrecta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class DetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_detalle);

        Bundle extras = getIntent().getExtras ();

        CentralesDetalle detailsFragment = extras != null ?  CentralesDetalle.newInstance (extras) :  CentralesDetalle.newInstance (null);

         getSupportFragmentManager()
                .beginTransaction().replace(R.id.General_container,detailsFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();

    }
}