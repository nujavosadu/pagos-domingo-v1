package com.example.koob.myapplication.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.CharacterPickerDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.koob.myapplication.ListaActivity;
import com.example.koob.myapplication.MyApplication;
import com.example.koob.myapplication.R;
import com.example.koob.myapplication.model.Cliente;

import io.realm.Realm;
import io.realm.RealmQuery;


public class CapturarCliente extends android.support.v4.app.DialogFragment{

    EditText ID,nombre,calle,colonia;
    Button guardar,cancelar;
    private int count;

    public CapturarCliente() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cliente, container, false);

        initViews(view);


        return view;
    }

    private void initViews(View view) {
        ID = (EditText) view.findViewById(R.id.id);

        ID.setText(getCount()+"");

        nombre = (EditText) view.findViewById(R.id.nombre);
        calle = (EditText) view.findViewById(R.id.calle);
        colonia = (EditText) view.findViewById(R.id.colonia);

        guardar = (Button) view.findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()){
                    guardar();
                }
            }
        });
        cancelar= (Button) view.findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private boolean validar() {

        if(nombre.getText().toString().trim().length() == 0){
            nombre.setError("Ingrese un nombre");
            nombre.requestFocus();
            return false;
        }

        if(calle.getText().toString().trim().length() == 0){
            calle.setError("Ingrese una calle");
            calle.requestFocus();
            return false;
        }

        if(colonia.getText().toString().trim().length() == 0){
            colonia.setError("Ingrese una colonia");
            colonia.requestFocus();
            return false;
        }

        return true;
    }

    private void guardar() {

        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        Cliente realmCliente = realm.createObject(Cliente.class);
        realmCliente.setId(getCount());
        realmCliente.setNombre(nombre.getText().toString());
        realmCliente.setColonia(colonia.getText().toString());
        realmCliente.setCalle(calle.getText().toString());
        realm.commitTransaction();

        if(getActivity() instanceof ListaActivity){
            ((ListaActivity) getActivity()).updateList();
        }

        MyApplication.instance().primaryKeyCliente++;

        dismiss();
    }


    public int getCount() {

        return (int) MyApplication.instance().primaryKeyCliente+1;
    }
}
