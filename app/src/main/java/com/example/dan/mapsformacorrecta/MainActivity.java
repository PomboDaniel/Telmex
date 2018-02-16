package com.example.dan.mapsformacorrecta;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.dan.mapsformacorrecta.Interfaces.IComunicaFragments;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
import static com.example.dan.mapsformacorrecta.Fragment1.lista_regiones;
import static com.example.dan.mapsformacorrecta.Fragment3.lista_tbas;
import static com.example.dan.mapsformacorrecta.ViewPagerActivity.mViewPager;



public class MainActivity extends AppCompatActivity implements
        GoogleMap.OnInfoWindowClickListener,
        OnMapReadyCallback,
        IComunicaFragments{

    private double lat = 0, lon = 0;
    private int posicionCard, idPagina, clave;
    private String name, siglas, direccion, referencia;
    private GoogleMap mimapa;
    private SupportMapFragment fragmento_mapa;
    private final int LOCATION_REQUEST = 500;
    private LocationManager locationManager;
    private AlertDialog alert = null;
    private AutoCompleteTextView auto;
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(); //Para poder usar firebase necesitamos crear una referencia
    private ImageButton busca;
    private ArrayList<markers_maps> marker_list;
    private markers_maps place;
    private ArrayAdapter<String> adaptador_dropdownList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        busca = findViewById(R.id.idBuscar);

        adaptador_dropdownList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);

        if (servicesDisponible()) {

            iniciaMapa();

            /*locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                AlertNoGps();
            }*/

            auto = findViewById(R.id.idauto);

            auto.setThreshold(1);
            auto.setAdapter(adaptador_dropdownList);

            busca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int aux = 0;
                    String texto = auto.getText().toString();
                    if(texto.isEmpty()) Toast.makeText(getApplicationContext(), "Debes completar el campo de busqueda", Toast.LENGTH_LONG).show();
                    else{

                        for(markers_maps markers: marker_list){

                            if(clave == 10){

                                if(texto.equalsIgnoreCase(markers.getSiglas())){
                                    lat = markers.getLatitud();
                                    lon = markers.getLongitud();
                                    name = markers.getNombre();
                                    siglas = markers.getSiglas();
                                    referencia = markers.getReferencia();
                                    direccion = markers.getDireccion();
                                    aux = 1;
                                }
                            }
                            else{

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

        mimapa = googleMap;
        mimapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings ajustes = mimapa.getUiSettings();
        ajustes.setZoomControlsEnabled(true);

        try{

            Bundle datos = this.getIntent().getExtras();              //Bundle para recibir datos
            posicionCard = datos.getInt("position_card");   //Recibo el dato
            clave = datos.getInt("clave");

            if(clave == 20){
                ponZoom();
            }
            else{
                    //Son tbas y ponemos el zoom en Veracruz.
                LatLng Ver = new LatLng(19.151801, -96.110851);
                mimapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Ver, 13));
            }


        }catch (Exception e){

            ponZoom();          //Entra aqui cuando se regresa del infoWindow porque viene vacio el Bundle
        }

        idPagina = mViewPager.getCurrentItem();  //Recibimos la pagina en la que estamos

        if(idPagina == 0) retrievedata("CENTRALES", posicionCard);
        else if(idPagina == 1) retrievedata("RADIOBASES", posicionCard);
        else retrievedata("TBAS", posicionCard);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);

            return;
        }
        mimapa.setMyLocationEnabled(true);
    }


    public void ponZoom(){

        if(posicionCard == 0){

            LatLng Ver = new LatLng(19.151801, -96.110851);
            mimapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Ver, 13));
        }
        else if(posicionCard == 1){

            LatLng sax = new LatLng(18.28073, -95.21427);
            mimapa.moveCamera(CameraUpdateFactory.newLatLngZoom(sax, 10));
        }
        else{

            LatLng cos = new LatLng(18.34569, -95.97950);
            mimapa.moveCamera(CameraUpdateFactory.newLatLngZoom(cos, 10));
        }
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

        try{

            if(clave == 10){        //Se eligieron TBAS

                for(TBAS tba: lista_tbas){

                    if(posicionCardview == tba.getIndice()){

                        databaseReference.child(hijo).child(tba.getTitulo().toUpperCase()).addListenerForSingleValueEvent(new ValueEventListener() {

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

                                    foo = entry.child("NOMBRE");
                                    place.nombre= foo.getValue() != null ? foo.getValue().toString(): "";

                                    foo = entry.child("DISTRITO");
                                    place.siglas = foo.getValue() != null ? foo.getValue().toString() : "";

                                    adaptador_dropdownList.add(place.siglas);

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
            else{

                Toast.makeText(getApplicationContext(), "no es tba", Toast.LENGTH_LONG).show();
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

                                            adaptador_dropdownList.add(place.nombre);

                                            foo = entry.child("SIGLAS");
                                            place.siglas = foo.getValue() != null ? foo.getValue().toString() : "";
                                        }
                                        else if(hijo.equals("RADIOBASES")){

                                            foo = entry.child("RB");
                                            place.nombre= foo.getValue() != null ? foo.getValue().toString(): "";

                                            adaptador_dropdownList.add(place.nombre);

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
                        else{

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

                                            adaptador_dropdownList.add(place.nombre);

                                            foo = entry.child("SIGLAS");
                                            place.siglas = foo.getValue() != null ? foo.getValue().toString() : "";
                                        }
                                        else if(hijo.equals("RADIOBASES")){

                                            foo = entry.child("RB");
                                            place.nombre= foo.getValue() != null ? foo.getValue().toString(): "";

                                            adaptador_dropdownList.add(place.nombre);

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
            }

        }catch (Exception e){

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
                    .snippet(marcadores.get(i).siglas+ "|" + marcadores.get(i).direccion+ "|" + marcadores.get(i).referencia)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_centrales)));

            mimapa.setInfoWindowAdapter(new CustominfoWindowAdapter(MainActivity.this));
            mimapa.setOnInfoWindowClickListener(this);
        }
    }



    /*public void jalaCentralesFirebasee(final ArrayAdapter<String> adaptador_dropdownList){

        databaseReference.child("CENTRALES").child("VERACRUZ").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot lugaresSnapShot: dataSnapshot.getChildren()){

                    String place = lugaresSnapShot.child("LUGAR").getValue(String.class);
                    adaptador_dropdownList.add(place);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/



    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
        String Nombre = marker.getTitle();
        String snippet = marker.getSnippet();
        String[] info = snippet.split("\\|");


        IComunicaFragments listener;
        listener = (IComunicaFragments) this;
        listener.enviarCentrales(Nombre, info[0], info[1]);

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

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

                getWindow().setExitTransition(new Explode());
                startActivity(intent,
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
            }
            else {
                startActivity(intent);
            }
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


    public void onPause(){
                                    //Se ejecuta automaticamente cuando pasas a otro activity, minimizas la app o se pone en
                                    //segundo plano
        super.onPause();

        SharedPreferences dato = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mieditor = dato.edit();

        mieditor.putInt("variable", posicionCard);  //se guarda el valor para que cuando se recargue el mapa no se pierda
        mieditor.putInt("variable2", clave);
        mieditor.apply();
    }


    public void onResume(){

        super.onResume();

        SharedPreferences dato = PreferenceManager.getDefaultSharedPreferences(this);

        posicionCard = dato.getInt("variable", 0);          //Recogemos el valor guardado del onPause
        clave = dato.getInt("variable2", 0);
    }



    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }
}
