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
import com.example.koob.myapplication.model.Carro;
import com.example.koob.myapplication.model.Vendedor;

import io.realm.Realm;
import io.realm.RealmQuery;


/**
 * A simple {@link Fragment} subclass.
 */
public class CapturarCarro extends DialogFragment {

    EditText ID, nombre, precio;
    Button guardar, cancelar;

    public CapturarCarro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.producto, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {

        ID = (EditText) view.findViewById(R.id.id);
        ID.setText(getCount()+"");
        nombre = (EditText) view.findViewById(R.id.nombre);
        precio = (EditText) view.findViewById(R.id.precio);

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

    private boolean validar() {

        if (nombre.getText().toString().trim().length() == 0) {
            nombre.setError("Ingrese un nombre");
            nombre.requestFocus();
            return false;
        }

        if (precio.getText().toString().trim().length() == 0) {
            precio.setError("Ingrese un precio");
            precio.requestFocus();
            return false;
        } else {
            try {
                Double x = Double.parseDouble(precio.getText().toString());
            } catch (Exception ex) {
                precio.setError("Ingrese un precio correcto");
                precio.requestFocus();
                return false;
            }

        }

        return true;
    }

    private void guardar() {

        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        Carro realCarro = realm.createObject(Carro.class);
        realCarro.setId(getCount());
        realCarro.setNombre(nombre.getText().toString());
        realCarro.setPrecio(Double.parseDouble(precio.getText().toString()));
        realm.commitTransaction();

        if (getActivity() instanceof ListaActivity) {
            ((ListaActivity) getActivity()).updateList();
        }

        MyApplication.instance().primaryKeyCarro++;

        dismiss();
    }


    public int getCount() {

        return (int) MyApplication.instance().primaryKeyCarro+1;
    }

}
