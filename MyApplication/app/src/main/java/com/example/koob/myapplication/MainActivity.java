package com.example.koob.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.koob.myapplication.fragments.Productos;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }


    public void showClientes(View v){
        ListaActivity.ELEMENTS = ListaActivity.CLIENTES;
        startActivity(new Intent(this, ListaActivity.class));
    }

    public void showCarros(View v){
        ListaActivity.ELEMENTS = ListaActivity.CARROS;
        startActivity(new Intent(this, ListaActivity.class));
    }

    public void showVendedores(View v){
        ListaActivity.ELEMENTS = ListaActivity.VENDEDORES;
        startActivity(new Intent(this, ListaActivity.class));
    }

    public void nuevaCotizacion(View view) {
        ListaActivity.ELEMENTS = ListaActivity.COTIZACIONES;
        startActivity(new Intent(this, ListaActivity.class));
    }
}