package com.example.koob.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.koob.myapplication.R;
import com.example.koob.myapplication.model.Cliente;
import com.example.koob.myapplication.model.Vendedor;

import java.util.List;

public class AdapterVendedor extends ArrayAdapter<Vendedor> {

    LayoutInflater mInflater;
    public AdapterVendedor(Context context, List<Vendedor> vendedores) {
        super(context, R.layout.item_cliente);
        addAll(vendedores);
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
            ((ViewGroup)holder.calle.getParent()).setVisibility(View.GONE);
            holder.colonia= (TextView)convertView.findViewById(R.id.colonia);
            ((ViewGroup)holder.colonia.getParent()).setVisibility(View.GONE);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Vendedor cliente = getItem(position);

        holder.nombre.setText(cliente.getNombre());

        return convertView;
    }


    public static class ViewHolder {
        public TextView nombre,colonia,calle;
    }

}

