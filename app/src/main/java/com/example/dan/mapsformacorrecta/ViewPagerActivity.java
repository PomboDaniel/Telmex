package com.example.dan.mapsformacorrecta;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class ViewPagerActivity extends AppCompatActivity {

    public static ViewPager mViewPager;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mViewPager = findViewById(R.id.container);
        tab = findViewById(R.id.pestanas);

        configuraPageAdapter(mViewPager);
        tab.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager, menu);
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
        if (id == R.id.action_logout) {
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            startActivity(new Intent(ViewPagerActivity.this,LoginActivity.class));
            this.finish();


        }

        return super.onOptionsItemSelected(item);
    }


    /*@Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("EXIT")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        ViewPagerActivity.super.onBackPressed();
                    }
                }).create().show();
    }*/


    private void configuraPageAdapter(ViewPager viewPager){

        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

        adapter.agregaFragment(new Fragment1(), "Centrales");
        adapter.agregaFragment(new Fragment2(), "Radio bases");
        adapter.agregaFragment(new Fragment3(), "Tbas");

        viewPager.setAdapter(adapter);
    }
}
