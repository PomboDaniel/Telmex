package com.example.dan.mapsformacorrecta;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.dan.mapsformacorrecta.Interfaces.IComunicaFragments;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import static android.support.v4.view.MenuItemCompat.getActionView;
import static com.example.dan.mapsformacorrecta.Fragment1.adapter;
import static com.example.dan.mapsformacorrecta.Fragment1.lista_regiones;
import static com.example.dan.mapsformacorrecta.ViewPagerActivity.mViewPager;



public class MainActivity extends AppCompatActivity implements
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback,
        IComunicaFragments{

    private double lat = 0, lon = 0;
    private int posicionCard;
    private String name, siglas, direccion, referencia;
    private GoogleMap mimapa;
    private SupportMapFragment fragmento_mapa;
    private final int LOCATION_REQUEST = 500;
    private AutoCompleteTextView auto;
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(); //Para poder usar firebase necesitamos crear una referencia
    private ImageButton busca;
    private ArrayList<markers_maps> marker_list;
    private markers_maps place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        busca = findViewById(R.id.idBuscar);

        ArrayAdapter<String> centrales = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);

        if (servicesDisponible()) {

            iniciaMapa();

            jalaCentralesFirebasee(centrales);

            auto = findViewById(R.id.idauto);

            auto.setThreshold(1);
            auto.setAdapter(centrales);

            busca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int aux = 0;
                    String texto = auto.getText().toString();
                    if(texto.isEmpty()) Toast.makeText(getApplicationContext(), "Debes completar el campo de busqueda", Toast.LENGTH_LONG).show();
                    else{

                        for(markers_maps markers: marker_list){

                            if(texto.equalsIgnoreCase(markers.getNombre())){
                                lat = markers.getLatitud();
                                lon = markers.getLongitud();
                                name = markers.getNombre();
                                siglas = markers.getSiglas();
                                referencia = markers.getReferencia();
                                direccion = markers.getDireccion();
                                aux = 1;
                            }
                        }

                        if(aux == 0) Toast.makeText(getApplicationContext(), "Lugar no encontrado", Toast.LENGTH_LONG).show();
                        else{

                            mimapa.clear();
                            LatLng coord = new LatLng(lat, lon);
                            CameraUpdate miLocalizacion = CameraUpdateFactory.newLatLngZoom(coord, 16);

                            mimapa.addMarker(new MarkerOptions().position(coord).title(name).snippet(siglas + "|" + direccion + "|" + referencia));
                            mimapa.setInfoWindowAdapter(new CustominfoWindowAdapter(MainActivity.this));
                            //pin = mimapa.addMarker(new MarkerOptions().position(coord).title(name));
                            mimapa.animateCamera(miLocalizacion);

                            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputMethodManager.hideSoftInputFromWindow(auto.getWindowToken(), 0);

                            auto.setText("");
                            auto.clearFocus();
                        }
                    }
                }
            });
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

        fragmento_mapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        fragmento_mapa.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        try{

            Bundle datos = this.getIntent().getExtras();              //Bundle para recibir datos
            posicionCard = datos.getInt("position_card");   //Recibo el/los datos

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Entra al catch", Toast.LENGTH_LONG).show();
        }

        int idPagina = mViewPager.getCurrentItem();  //Recibimos la pagina en la que estamos

        mimapa = googleMap;
        mimapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings ajustes = mimapa.getUiSettings();
        ajustes.setZoomControlsEnabled(true);
        LatLng Ver = new LatLng(19.151801, -96.110851);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Ver, 13));

        if(idPagina == 0) retrievedata("CENTRALES", posicionCard);
        else if(idPagina == 1) retrievedata("RADIOBASES", posicionCard);
        else retrievedata("TBAS", posicionCard);

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


    private void  retrievedata(final String hijo, int posicionCardview){

        for(Regiones region: lista_regiones){

            if(posicionCardview == region.getIndice()){

                if(region.getTitulo().equalsIgnoreCase("san andres tuxtla")){

                    databaseReference.child(hijo).child("SAN ANDRES").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            marker_list = new ArrayList<markers_maps>();

                            for(DataSnapshot entry: dataSnapshot.getChildren()){

                                place = new markers_maps();
                                DataSnapshot foo;

                                foo =entry.child("LATITUD");
                                place.latitud = foo.getValue() != null ? Double.parseDouble(foo.getValue().toString()): 10;

                                foo=entry.child("LONGITUD");
                                place.longitud = foo.getValue() != null ? Double.parseDouble(foo.getValue().toString()) : 10 ;

                                foo=entry.child("DIRECCION");
                                place.direccion =foo.getValue() != null ? foo.getValue().toString():"";

                                if(hijo.equals("CENTRALES")) {

                                    foo = entry.child("LUGAR");
                                    place.nombre= foo.getValue() != null ? foo.getValue().toString(): "";

                                    foo = entry.child("SIGLAS");
                                    place.siglas = foo.getValue() != null ? foo.getValue().toString() : "";
                                }
                                else if(hijo.equals("RADIOBASES")){

                                    foo = entry.child("RB");
                                    place.nombre= foo.getValue() != null ? foo.getValue().toString(): "";

                                    foo = entry.child("REF SISA");
                                    place.referencia = foo.getValue() != null ? foo.getValue().toString() : "";
                                }
                                else Toast.makeText(getApplicationContext(), "Por el momento no hay tbas", Toast.LENGTH_LONG).show();

                                marker_list.add(place);
                            }

                            ponemoslosmarker(marker_list);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }



                databaseReference.child(hijo).child(region.getTitulo().toUpperCase()).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        marker_list = new ArrayList<markers_maps>();

                        for(DataSnapshot entry: dataSnapshot.getChildren()){

                            place = new markers_maps();
                            DataSnapshot foo;

                            foo =entry.child("LATITUD");
                            place.latitud = foo.getValue() != null ? Double.parseDouble(foo.getValue().toString()): 10;

                            foo=entry.child("LONGITUD");
                            place.longitud = foo.getValue() != null ? Double.parseDouble(foo.getValue().toString()) : 10 ;

                            foo=entry.child("DIRECCION");
                            place.direccion =foo.getValue() != null ? foo.getValue().toString():"";

                            if(hijo.equals("CENTRALES")) {

                                foo = entry.child("LUGAR");
                                place.nombre= foo.getValue() != null ? foo.getValue().toString(): "";

                                foo = entry.child("SIGLAS");
                                place.siglas = foo.getValue() != null ? foo.getValue().toString() : "";
                            }
                            else if(hijo.equals("RADIOBASES")){

                                foo = entry.child("RB");
                                place.nombre= foo.getValue() != null ? foo.getValue().toString(): "";

                                foo = entry.child("REF SISA");
                                place.referencia = foo.getValue() != null ? foo.getValue().toString() : "";
                            }
                            else Toast.makeText(getApplicationContext(), "Por el momento no hay tbas", Toast.LENGTH_LONG).show();

                            marker_list.add(place);
                        }

                        ponemoslosmarker(marker_list);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        }
    }



    private void ponemoslosmarker(ArrayList<markers_maps>  marcadores){

        ///SE GUARDA INFORMACION EN EL SNIPPET QUE SE USARA PARA PONERLA EN LA VENTANA DE INFORMACION
        LatLng coorde;

        for (int i =0; i<marcadores.size();i++){

            coorde= new LatLng(marcadores.get(i).latitud, marcadores.get(i).longitud);

            mimapa.addMarker(new MarkerOptions()
                    .position(coorde)
                    .title(marcadores.get(i).nombre)
                    .snippet(marcadores.get(i).siglas+ "|" + marcadores.get(i).direccion+ "|" + marcadores.get(i).referencia));

            mimapa.setInfoWindowAdapter(new CustominfoWindowAdapter(MainActivity.this));
            mimapa.setOnInfoWindowClickListener(this);
        }
    }



    public void jalaCentralesFirebasee(final ArrayAdapter<String> centrales){

        databaseReference.child("CENTRALES").child("VERACRUZ").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot lugaresSnapShot: dataSnapshot.getChildren()){

                    String place = lugaresSnapShot.child("LUGAR").getValue(String.class);
                    centrales.add(place);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.map_fragment, new DetalleFragment()).commit();
        String Nombre = marker.getTitle();
        String Siglas = marker.getSnippet();
        String Direccion = marker.getSnippet();

        IComunicaFragments listener;
        listener = (IComunicaFragments) this;
        listener.enviarCentrales(Nombre, Siglas, Direccion);

    }



    @Override
    public void enviarCentrales(String titulo, String siglas, String direccion) {
        View v = findViewById(R.id.General_container);

        if (v == null) {
            Intent intent = new Intent(this, DetalleActivity.class);

            //intent.putExtra(CentralesDetalle.ICON_KEY, imagen); //
            intent.putExtra(CentralesDetalle.TEXT_KEY, titulo);
            intent.putExtra(CentralesDetalle.SIGLA_KEY, siglas );
            intent.putExtra(CentralesDetalle.DIREC_KEY, direccion); //

            startActivity(intent);

        } else {

            Bundle bundle = new Bundle ();

           // bundle.putString(CentralesDetalle.ICON_KEY, imagen); //
            bundle.putString(CentralesDetalle.TEXT_KEY, titulo);
            bundle.putString(CentralesDetalle.DIREC_KEY, direccion); //
            bundle.putString(CentralesDetalle.SIGLA_KEY, siglas );


            CentralesDetalle detailsFragment = CentralesDetalle.newInstance(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.General_container, detailsFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();

        }
    }

}
