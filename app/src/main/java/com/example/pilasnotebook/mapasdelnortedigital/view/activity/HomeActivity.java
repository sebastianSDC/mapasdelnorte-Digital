package com.example.pilasnotebook.mapasdelnortedigital.view.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.pilasnotebook.mapasdelnortedigital.R;


public class HomeActivity extends AppCompatActivity {

    private Button btnComercios,
            btnCuponera,
            btnCartelera,
            btnReservas,
            btnDelivery,
            btnActivityInApp;
    private ViewPager vpPublicidad;

    private BottomNavigationView botomBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnCartelera=(Button)findViewById(R.id.cARTELERA);
        btnCuponera=(Button)findViewById(R.id.cUPONERA);
        btnComercios=(Button)findViewById(R.id.cOMERCIOS);
        btnReservas=(Button)findViewById(R.id.rESERVAS);
        btnDelivery=(Button)findViewById(R.id.dELIVERY);
        btnActivityInApp=(Button)findViewById(R.id.aCTIVITY_IN_APP);


    }


}
