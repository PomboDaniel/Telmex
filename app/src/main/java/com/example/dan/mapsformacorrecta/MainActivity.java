package com.example.dan.mapsformacorrecta;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
        ,OnMapReadyCallback{

    private GoogleMap mimapa;
    private SupportMapFragment fragmento;
    //Para poder usar firebase necesitamos crear una referencia
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference RegionReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        if (servicesDisponible()) {

            android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

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

        fragmento = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_telmex);
        fragmento.getMapAsync(this);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        android.support.v4.app.FragmentManager sfm = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_camera){

            if( fragmento.isHidden() ){
                sfm.beginTransaction().show(fragmento).commit();
            }

            fragmento.getMapAsync(this);
        }
        else if (id == R.id.nav_gallery) {


        }else if (id == R.id.nav_slideshow) {

        }
        else if (id == R.id.nav_manage) {

        }
        else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mimapa = googleMap;
        mimapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings ajustes = mimapa.getUiSettings();
        ajustes.setZoomControlsEnabled(true);

        LatLng Ver = new LatLng(19.151801, -96.110851);
                googleMap.addMarker(new MarkerOptions().position(Ver)
                                .title("Marker in Veracruz"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Ver, 13));

                retrievedata();
        LatLng  Catemaco= new LatLng(18.419201, -95.111934);
        googleMap.addMarker(new MarkerOptions().position(Catemaco)
                                .title("Marker in Catemaco"));



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

    private void  retrievedata(){
                Toast.makeText(this, "Entra al retri", Toast.LENGTH_LONG).show();
                //databaseReference = RegionReference.child("CENTRALES");
                databaseReference.child("CENTRALES").child("VERACRUZ").addListenerForSingleValueEvent(new ValueEventListener() {
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

                                        foo=entry.child("LUGAR");
                                        place.nombre=foo.getValue() != null ? foo.getValue().toString():"";

                                        foo=entry.child("DIRECCION");
                                        place.direccion =foo.getValue() != null ? foo.getValue().toString():"";

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
                        mimapa.addMarker(new MarkerOptions()
                                .position(coorde)
                                .title(marcadores.get(i).nombre)
                                .snippet(marcadores.get(i).siglas+ "," + marcadores.get(i).direccion));
                        mimapa.setInfoWindowAdapter(new CustominfoWindowAdapter(MainActivity.this));
                    //mMap.addMarker(new MarkerOptions().position(coorde).title(hola.get(i).nombre).snippet(hola.get(i).costo+","+hola.get(i).tipo+","+hola.get(i).imagen_url));
                    //mMap.setInfoWindowAdapter(new Custominfowindowadapter(MapsActivity.this));

                                    }
    }



}
