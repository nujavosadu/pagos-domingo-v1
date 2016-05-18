package com.example.koob.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.koob.myapplication.model.Cotizacion;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;

import io.realm.Realm;

public class VerCotizacion extends AppCompatActivity {

    public static String ID_COT = "ID_COT";

    public Cotizacion cotizacion;

    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cotizacion);

        container = (LinearLayout) findViewById(R.id.container);


        int idC = getIntent().getExtras().getInt(ID_COT,1);


        Realm realm = Realm.getInstance(this);
        this.cotizacion = realm.where(Cotizacion.class).equalTo("id",idC).findFirst();

        if(cotizacion!=null){
            updateView();
        }



        Button cerrar = (Button) findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    LayoutInflater inflater;

    private void updateView() {

        inflater = LayoutInflater.from(this);

        DateTime dateTime = new DateTime(cotizacion.getFecha());

        double varCapital = 0,varIntereses = 0,varSaldoBanco = 0;

        for(int n = 0;n < cotizacion.getMeses(); n++ ){

            LinearLayout v = (LinearLayout)inflater.inflate(R.layout.item_grid, container, false);

            TextView noPago = (TextView) v.findViewById(R.id.pago);
            noPago.setText(n + 1 + "");

            TextView fechaPago = (TextView) v.findViewById(R.id.fecha);
            dateTime.plusMonths(1);
            DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
            fechaPago.setText(fmt.print(dateTime));

            TextView capital = (TextView) v.findViewById(R.id.capital);

            double saldo = (cotizacion.getCarro().getPrecio()-cotizacion.getTotalEnganche())+cotizacion.getSeguro();
            varCapital = (saldo / cotizacion.getMeses());

            capital.setText(String.format("$%,.2f", varCapital));

            TextView intereses = (TextView) v.findViewById(R.id.interes);
            if(n == 0){
                varIntereses = saldo * (cotizacion.getInteres()/100);
            }else{
                varIntereses = varSaldoBanco*(cotizacion.getInteres()/100);
            }
            intereses.setText(String.format("$%,.2f", varIntereses));

            TextView totalBanco = (TextView) v.findViewById(R.id.pago_banco);
            totalBanco.setText(String.format("$%,.2f", (varCapital + varIntereses)));

            TextView saldoBanco = (TextView) v.findViewById(R.id.saldo_banco);
            if(n == 0){
                varSaldoBanco = saldo - varCapital;
            }else{
                varSaldoBanco = varSaldoBanco - varCapital;
            }
            saldoBanco.setText(String.format("$%,.2f", varSaldoBanco));


            container.addView(v);
        }
    }
}
