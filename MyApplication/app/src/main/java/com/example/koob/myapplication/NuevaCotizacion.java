package com.example.koob.myapplication;

import android.graphics.drawable.RippleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.koob.myapplication.model.Carro;
import com.example.koob.myapplication.model.Cotizacion;
import com.example.koob.myapplication.model.Cliente;
import com.example.koob.myapplication.model.Vendedor;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.microedition.khronos.egl.EGLDisplay;

import io.realm.Realm;
import io.realm.RealmResults;

public class NuevaCotizacion extends AppCompatActivity {

    ScrollView scrollView;

    TextView fecha,enganche,interes;
    SeekBar seekEnganche,seekInteres;

    EditText factura,totalEnganche,importe,seguro,saldo;
    Spinner spinnerClientes, spinnerVendedores, spinnerCarros;
    RealmResults<Cliente> rclientes;
    RealmResults<Carro> rcarros;
    RealmResults<Vendedor> rvendedores;

    NumberPicker pickerPagos;

    Button cancelar,guardar,reporte;

    Cotizacion cotizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_datos);

        cotizacion = new Cotizacion();
        cotizacion.setId((int) MyApplication.instance().primaryKeyCotizacion + 1);

        findViewById(R.id.container_rest).setVisibility(View.GONE);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        initViews();
    }

    private void initViews() {
        fecha = (TextView) findViewById(R.id.textViewFecha);
        fecha.setText(DateFormat.getDateTimeInstance().format(new Date()));

        factura = (EditText) findViewById(R.id.factura);
        factura.setText(MyApplication.instance().primaryKeyCotizacion + 1 + "");

        setSpinnerClientes();
        setSpinnerCarros();
        setSpinnerVendedor();

        importe = (EditText) findViewById(R.id.editTextImporte);

        setEnganche();

        seguro = (EditText) findViewById(R.id.editTextSeguro);
        seguro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateView();
            }
        });
        saldo = (EditText) findViewById(R.id.editTextSaldo);


        setInteres();

        pickerPagos = (NumberPicker) findViewById(R.id.numberPickerPagos);
        pickerPagos.setMinValue(0);
        pickerPagos.setMaxValue(48);
        pickerPagos.setWrapSelectorWheel(false);



        cancelar = (Button) findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        guardar = (Button) findViewById(R.id.guardar);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar())
                    guardar();
            }
        });


    }

    private void guardar() {

        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        Cotizacion realmCotizacion = realm.createObject(Cotizacion.class);
        realmCotizacion.setId(cotizacion.getId());
        realmCotizacion.setCliente(cotizacion.getCliente());
        realmCotizacion.setVendedor(cotizacion.getVendedor());
        realmCotizacion.setCarro(cotizacion.getCarro());
        realmCotizacion.setEnganche(cotizacion.getEnganche());
        realmCotizacion.setTotalEnganche(cotizacion.getTotalEnganche());
        realmCotizacion.setInteres(cotizacion.getInteres());
        realmCotizacion.setSeguro(cotizacion.getSeguro());
        realmCotizacion.setFecha(new Date());
        realmCotizacion.setMeses(cotizacion.getMeses());
        realmCotizacion.setSaldo(cotizacion.getSaldo());
        realm.commitTransaction();

        MyApplication.instance().primaryKeyCliente++;
finish();
    }

    private boolean validar() {

        if(cotizacion.getCarro() == null || cotizacion.getVendedor() == null || cotizacion.getCliente() == null)
            return false;

        cotizacion.setMeses(pickerPagos.getValue());

        if(seguro.getText().toString().trim().length() == 0){
            seguro.setError("Ingrese el monto del seguro");
            seguro.requestFocus();
        }

        double saldo = (cotizacion.getCarro().getPrecio()-cotizacion.getTotalEnganche())+cotizacion.getSeguro();
        cotizacion.setSaldo(saldo);
        this.saldo.setText(String.format("$%,.2f", saldo));


        return true;
    }

    private void setInteres() {
        interes = (TextView) findViewById(R.id.interes);
        seekInteres = (SeekBar) findViewById(R.id.seekBarInteres);
        seekInteres.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cotizacion.setInteres(i / 100.0);
                updateView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setEnganche() {
        enganche = (TextView) findViewById(R.id.textViewEnganche);
        totalEnganche = (EditText) findViewById(R.id.editTextMonto);
        seekEnganche = (SeekBar) findViewById(R.id.seekEnganche);
        seekEnganche.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cotizacion.setEnganche(i);
                updateView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void updateView() {

        double enganche = 0.0;
        if (cotizacion.getCarro() != null) {
            this.enganche.setText("Enganche: "+(seekEnganche.getProgress())+"%");
            enganche = cotizacion.getCarro().getPrecio() * (seekEnganche.getProgress() / 100.0);
            cotizacion.setTotalEnganche(enganche);
            totalEnganche.setText(String.format("$%,.2f", enganche));
        }

        double interes = (seekInteres.getProgress());
        cotizacion.setInteres(interes);
        this.interes.setText("Intereses: "+interes+"%");

        if(seguro.getText().toString().trim().length() != 0){
            try{
                double sseguro = Double.parseDouble(seguro.getText().toString());
                cotizacion.setSeguro(sseguro);

                double saldo = (cotizacion.getCarro().getPrecio()-cotizacion.getTotalEnganche())+sseguro;
                cotizacion.setSaldo(saldo);
                this.saldo.setText(String.format("$%,.2f", saldo));

            }catch (Exception ex){
                seguro.setText("");
                seguro.setError("Ingresa una cantidad valida");
                seguro.requestFocus();
            }
        }



    }

    private void setSpinnerVendedor() {
        spinnerVendedores = (Spinner) findViewById(R.id.spinnerVendedores);

        Realm realm = Realm.getInstance(this);
        rvendedores = realm.where(Vendedor.class).findAllSorted("nombre");
        ArrayList<String> vendedores = new ArrayList<>();
        for (Vendedor c : rvendedores) {
            vendedores.add(c.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vendedores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVendedores.setAdapter(adapter);

        spinnerVendedores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String vendedor = (String) adapterView.getSelectedItem();
                Vendedor v = rvendedores.get(i);
                cotizacion.setVendedor(v);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSpinnerCarros() {

        spinnerCarros = (Spinner) findViewById(R.id.spinnerCarro);

        Realm realm = Realm.getInstance(this);
        rcarros = realm.where(Carro.class).findAllSorted("nombre");
        ArrayList<String> carros = new ArrayList<>();
        for (Carro c : rcarros) {
            carros.add(c.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carros);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarros.setAdapter(adapter);

        spinnerCarros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String carro = (String) adapterView.getSelectedItem();
                Carro c = rcarros.get(i);
                importe.setText(String.format("$%,.2f", c.getPrecio()));
                cotizacion.setCarro(c);
                findViewById(R.id.container_rest).setVisibility(View.VISIBLE);

                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0,(int) totalEnganche.getY()+300);
                    }
                },400);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setSpinnerClientes() {

        spinnerClientes = (Spinner) findViewById(R.id.spinnerClientes);

        Realm realm = Realm.getInstance(this);
        rclientes = realm.where(Cliente.class).findAllSorted("nombre");
        ArrayList<String> clientes = new ArrayList<>();
        for (Cliente c : rclientes) {
            clientes.add(c.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clientes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClientes.setAdapter(adapter);

        spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cliente = (String) adapterView.getSelectedItem();
                Cliente c = rclientes.get(i);
                cotizacion.setCliente(c);
                seekEnganche.setProgress(0);
                updateView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
