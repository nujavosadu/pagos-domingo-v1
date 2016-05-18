package com.example.koob.myapplication.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.koob.myapplication.R;
import com.example.koob.myapplication.model.Carro;
import com.example.koob.myapplication.model.Cliente;

import java.util.List;
import java.util.ResourceBundle;

public class AdapterCarros extends ArrayAdapter<Carro> {

    LayoutInflater mInflater;
    public AdapterCarros(Context context, List<Carro> clientes) {
        super(context, R.layout.item_cliente);
        addAll(clientes);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_cliente, null);
            ((ImageView)convertView.findViewById(R.id.imageView)).setImageResource(R.drawable.auto);
            holder = new ViewHolder();
            holder.nombre = (TextView)convertView.findViewById(R.id.nombre);
            holder.calle = (TextView)convertView.findViewById(R.id.calle);
            ((TextView)convertView.findViewById(R.id.label_calle)).setText("precio: ");
            holder.colonia= (TextView)convertView.findViewById(R.id.colonia);
            ((ViewGroup)holder.colonia.getParent()).setVisibility(View.GONE);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Carro carro = getItem(position);

        holder.nombre.setText(carro.getNombre());
        holder.calle.setText(String.format("$%,.2f", carro.getPrecio()));

        return convertView;
    }


    public static class ViewHolder {
        public TextView nombre,colonia,calle;
    }

}

