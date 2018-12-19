package com.example.pilasnotebook.mapasdelnortedigital.view.activity;


/**
 * Creada por Sebastián Suárez Da Costa y Pablo Herrera.
 */


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.pilasnotebook.mapasdelnortedigital.R;
import com.example.pilasnotebook.mapasdelnortedigital.view.fragment.CarteleraClienteFragment;
import com.example.pilasnotebook.mapasdelnortedigital.view.fragment.CatalogoOnLineClienteFragment;
import com.example.pilasnotebook.mapasdelnortedigital.view.fragment.CuponeraClienteFragment;
import com.example.pilasnotebook.mapasdelnortedigital.view.fragment.DatosClienteFragment;
import com.example.pilasnotebook.mapasdelnortedigital.view.fragment.FotosClienteFragment;

public class ViewPagerAdminActivity extends AppCompatActivity implements CarteleraClienteFragment.OnFragmentInteractionListener, CuponeraClienteFragment.OnFragmentInteractionListener, FotosClienteFragment.OnFragmentInteractionListener, DatosClienteFragment.OnFragmentInteractionListener, CatalogoOnLineClienteFragment.OnFragmentInteractionListener {

    private PlaceholderFragment.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new PlaceholderFragment.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static Fragment newInstance(int sectionNumber) {
            Fragment fragment = null;

            switch (sectionNumber) {

                case 1:
                    fragment = new DatosClienteFragment();
                    break;
                case 2:
                    fragment = new CuponeraClienteFragment();
                    break;
                case 3:
                    fragment = new CarteleraClienteFragment();
                    break;
                case 4:
                    fragment = new FotosClienteFragment();
                    break;
                case 5:
                    fragment = new CatalogoOnLineClienteFragment();
                    break;
            }
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View view = inflater.inflate(R.layout.activity_view_pager_admin, container, false);
            return view;
        }

        public static class SectionsPagerAdapter extends FragmentPagerAdapter {
            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return PlaceholderFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Datos";
                    case 1:
                        return "Cuponera";
                    case 2:
                        return "Cartelera";
                    case 3:
                        return "Fotos";
                    case 4:
                        return "Catalogo On-Line";
                }
                return null;
            }
        }
    }
}
