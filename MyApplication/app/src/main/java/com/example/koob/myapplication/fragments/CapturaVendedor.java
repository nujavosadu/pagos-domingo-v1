package com.example.koob.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.koob.myapplication.ListaActivity;
import com.example.koob.myapplication.MyApplication;
import com.example.koob.myapplication.R;
import com.example.koob.myapplication.model.Cliente;
import com.example.koob.myapplication.model.Vendedor;

import io.realm.Realm;
import io.realm.RealmQuery;

public class CapturaVendedor extends DialogFragment {

    EditText ID, nombre;
    Button cancelar, guardar;

    public CapturaVendedor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vendedor, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        ID = (EditText) view.findViewById(R.id.id);
        ID.setText(getCount() + "");
        nombre = (EditText) view.findViewById(R.id.nombre);

        cancelar = (Button) view.findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        guardar = (Button) view.findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar())
                    guardar();
            }
        });
    }

    private void guardar() {

        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        Vendedor realmVendedor = realm.createObject(Vendedor.class);
        realmVendedor.setId(getCount());
        realmVendedor.setNombre(nombre.getText().toString());
        realm.commitTransaction();

        if (getActivity() instanceof ListaActivity) {
            ((ListaActivity) getActivity()).updateList();
        }

        MyApplication.instance().primaryKeyVendedor++;

        dismiss();
    }


    public int getCount() {

        return (int) MyApplication.instance().primaryKeyVendedor+1;
    }

    private boolean validar() {

        if (nombre.getText().toString().trim().length() == 0) {
            nombre.setError("ingrese un nombre");
            nombre.requestFocus();
            return false;
        }

        return true;
    }

}
