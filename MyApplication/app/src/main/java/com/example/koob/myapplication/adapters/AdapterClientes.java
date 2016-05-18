package com.example.koob.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.koob.myapplication.R;
import com.example.koob.myapplication.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class AdapterClientes extends ArrayAdapter<Cliente> {

    LayoutInflater mInflater;
    public AdapterClientes(Context context,List<Cliente> clientes) {
        super(context, R.layout.item_cliente);
        addAll(clientes);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_cliente, null);
            holder = new ViewHolder();
            holder.nombre = (TextView)convertView.findViewById(R.id.nombre);
            holder.calle = (TextView)convertView.findViewById(R.id.calle);
            holder.colonia= (TextView)convertView.findViewById(R.id.colonia);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Cliente cliente = getItem(position);

        holder.nombre.setText(cliente.getNombre());
        holder.calle.setText(cliente.getCalle());
        holder.colonia.setText(cliente.getColonia());

        return convertView;
    }


    public static class ViewHolder {
        public TextView nombre,colonia,calle;
    }

}

