package com.example.dan.mapsformacorrecta;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mimapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (servicesDisponible()) {
            Toast.makeText(this, "Perfecto", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_main);
            iniciaMapa();
        }
    }


    boolean servicesDisponible() {

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int disponible = api.isGooglePlayServicesAvailable(this);

        if (disponible == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(disponible)) {

            Dialog error = api.getErrorDialog(this, disponible, 0);
            error.show();
        } else {
            Toast.makeText(this, "No se puede conectar a play services", Toast.LENGTH_LONG).show();
        }

        return false;
    }


    private void iniciaMapa() {

        MapFragment fragmento = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        fragmento.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        Toast.makeText(this, "EN MAPrEADY", Toast.LENGTH_LONG).show();
        mimapa = googleMap;
        mimapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings ajustes = mimapa.getUiSettings();
        ajustes.setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mimapa.setMyLocationEnabled(true);
    }
}
