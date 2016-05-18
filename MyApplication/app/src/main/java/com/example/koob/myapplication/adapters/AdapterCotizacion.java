package com.example.koob.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.koob.myapplication.R;
import com.example.koob.myapplication.model.Cotizacion;

import java.text.DateFormat;
import java.util.List;

public class AdapterCotizacion extends ArrayAdapter<Cotizacion> {

    LayoutInflater mInflater;
    public AdapterCotizacion(Context context, List<Cotizacion> cotizaciones) {
        super(context, R.layout.item_lista_cotizacion);
        addAll(cotizaciones);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lista_cotizacion, null);
            holder = new ViewHolder();
            holder.cliente = (TextView)convertView.findViewById(R.id.cliente);
            holder.vendedor = (TextView)convertView.findViewById(R.id.vendedor);
            holder.monto = (TextView)convertView.findViewById(R.id.fecha);
            holder.fecha = (TextView)convertView.findViewById(R.id.monto);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        Cotizacion cotizacion = getItem(position);

        holder.cliente.setText(cotizacion.getCliente().getNombre());
        holder.vendedor.setText(cotizacion.getVendedor().getNombre());
        holder.fecha.setText(DateFormat.getDateTimeInstance().format(cotizacion.getFecha()));

        double total = cotizacion.getSeguro()+(cotizacion.getCarro().getPrecio() - (cotizacion.getCarro().getPrecio() * (cotizacion.getEnganche()/100.0)));

        holder.monto.setText(String.format("$%,.2f", total));

        return convertView;
    }


    public static class ViewHolder {
        public TextView cliente,vendedor,fecha,monto;
    }

}

