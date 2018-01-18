package com.example.dan.mapsformacorrecta;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.support.v4.view.MenuItemCompat.getActionView;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mimapa;
    private SupportMapFragment fragmento;
    private final int LOCATION_REQUEST = 500;
    private AutoCompleteTextView auto;
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(); //Para poder usar firebase necesitamos crear una referencia


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auto = (AutoCompleteTextView)findViewById(R.id.idauto);


        if (servicesDisponible()) {

            Toast.makeText(this, "Perfecto", Toast.LENGTH_LONG).show();
            iniciaMapa();
        }
    }


    boolean servicesDisponible() {

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int disponible = api.isGooglePlayServicesAvailable(this);

        if (disponible == ConnectionResult.SUCCESS) {
            return true;
        }
        else if (api.isUserResolvableError(disponible)) {

            Dialog error = api.getErrorDialog(this, disponible, 0);
            error.show();
        }
        else {
            Toast.makeText(this, "No se puede conectar a play services", Toast.LENGTH_LONG).show();
        }

        return false;
    }



    private void iniciaMapa() {

        fragmento = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        fragmento.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        Bundle datos = this.getIntent().getExtras();
        int posicionPagina = datos.getInt("position_page");

        //Toast.makeText(this, "posicion: " + posicionPagina, Toast.LENGTH_LONG).show();

        mimapa = googleMap;
        mimapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings ajustes = mimapa.getUiSettings();
        ajustes.setZoomControlsEnabled(true);

        LatLng Ver = new LatLng(19.151801, -96.110851);
        googleMap.addMarker(new MarkerOptions().position(Ver).title("Marker in Veracruz"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Ver, 13));

        retrievedata();

        LatLng  Catemaco= new LatLng(18.419201, -95.111934);
        googleMap.addMarker(new MarkerOptions().position(Catemaco).title("Marker in Catemaco"));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);

            return;
        }

        mimapa.setMyLocationEnabled(true);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case LOCATION_REQUEST:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mimapa.setMyLocationEnabled(true);
                }
                break;
        }
    }



    private void  retrievedata(){

                Toast.makeText(this, "Entra al retrieve", Toast.LENGTH_LONG).show();
                databaseReference.child("CENTRALES").addListenerForSingleValueEvent(new ValueEventListener() {


                      @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                                ArrayList<markers_maps> marker_list = new ArrayList<markers_maps>();
                                for(DataSnapshot entry: dataSnapshot.getChildren()){
                                        markers_maps place = new markers_maps();

                                                DataSnapshot foo=entry.child("SIGLAS");
                                        place.siglas= foo.getValue() != null ? foo.getValue().toString(): "";

                                                foo =entry.child("LATITUD");
                                        place.latitud = foo.getValue() != null ? Double.parseDouble(foo.getValue().toString()): 10;

                                                foo=entry.child("LONGITUD");
                                        place.longitud = foo.getValue() != null ? Double.parseDouble(foo.getValue().toString()) : 10 ;



                                                               marker_list.add(place);

                                            }
                                ponemoslosmarker(marker_list);



                                                    }

                     @Override
             public void onCancelled(DatabaseError databaseError) { }
         });
            }

             private void ponemoslosmarker(ArrayList<markers_maps>  marcadores){

                ///SE GUARDA INFORMACION EN EL SNIPET QUE SE USARA PARA PONERLA EN LA VENTANA DE INFORMACION

                LatLng coorde;
                for (int i =0; i<marcadores.size();i++){
                        coorde= new LatLng(marcadores.get(i).latitud, marcadores.get(i).longitud);
                        mimapa.addMarker(new MarkerOptions().position(coorde).title(marcadores.get(i).siglas));
                        //mimapa.setInfoWindowAdapter(new Custominfowindowadapter(MapsActivity.this));
                }
    }

}
