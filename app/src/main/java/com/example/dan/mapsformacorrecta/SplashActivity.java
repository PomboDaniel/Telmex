package com.example.dan.mapsformacorrecta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /*Intent intent = new Intent (this, Inicio_sesion.class);
       startActivity(intent);
       finish();*/

        Intent intent = new Intent (this, ViewPagerActivity.class);
        startActivity(intent);
        finish();
    }
}
