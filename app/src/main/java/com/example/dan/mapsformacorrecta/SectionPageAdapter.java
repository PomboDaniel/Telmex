package com.example.dan.mapsformacorrecta;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> listaFragments = new ArrayList<>();
    private final List<String> tituloTab = new ArrayList<>();

    public void agregaFragment(Fragment frag, String titutlo){

        listaFragments.add(frag);
        tituloTab.add(titutlo);
    }

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloTab.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return listaFragments.get(position);
    }

    @Override
    public int getCount() {
        return listaFragments.size();
    }
}
