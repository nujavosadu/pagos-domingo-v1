package com.example.koob.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.koob.myapplication.adapters.AdapterCarros;
import com.example.koob.myapplication.adapters.AdapterClientes;
import com.example.koob.myapplication.adapters.AdapterCotizacion;
import com.example.koob.myapplication.adapters.AdapterVendedor;
import com.example.koob.myapplication.fragments.CapturaVendedor;
import com.example.koob.myapplication.fragments.CapturarCarro;
import com.example.koob.myapplication.fragments.CapturarCliente;
import com.example.koob.myapplication.model.Cotizacion;
import com.example.koob.myapplication.model.Carro;
import com.example.koob.myapplication.model.Cliente;
import com.example.koob.myapplication.model.Vendedor;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class ListaActivity extends AppCompatActivity {

    public static int ELEMENTS = -1;
    public static final int CLIENTES = 1;
    public static final int VENDEDORES = 2;
    public static final int CARROS = 3;
    public static final int COTIZACIONES = 4;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        initViews();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (ELEMENTS) {
                    case CLIENTES:
                        CapturarCliente capturarCliente = new CapturarCliente();
                        capturarCliente.show(getSupportFragmentManager(), "");
                        break;
                    case VENDEDORES:
                        CapturaVendedor capturaVendedor = new CapturaVendedor();
                        capturaVendedor.show(getSupportFragmentManager(), "");
                        break;
                    case CARROS:
                        CapturarCarro capturaCarro = new CapturarCarro();
                        capturaCarro.show(getSupportFragmentManager(), "");
                        break;
                    case COTIZACIONES:
                        startActivity(new Intent(ListaActivity.this,NuevaCotizacion.class));
                        break;
                }
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (ELEMENTS) {
                    case CLIENTES:
                        deleteCliente(position);
                        break;
                    case VENDEDORES:
                        deleteVendedor(position);
                        break;
                    case CARROS:
                        deleteCarro(position);
                        break;
                    case COTIZACIONES:
                        showCotizacion((Cotizacion) adapterView.getItemAtPosition(position));
                        break;
                }
            }
        });

       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
               switch (ELEMENTS) {
                   case CLIENTES:
                       deleteCliente(position);
                       break;
                   case VENDEDORES:
                       deleteVendedor(position);
                       break;
                   case CARROS:
                       deleteCarro(position);
                       break;
                   case COTIZACIONES:
                       deleteCotizacion(position);
                       break;
               }
               return false;
           }
       });

        updateList();


    }

    private void showCotizacion(Cotizacion cotizacion) {
        Intent i = new Intent(this,VerCotizacion.class);
        i.putExtra(VerCotizacion.ID_COT,cotizacion.getId());
        startActivity(i);
    }

    private void deleteCarro(final int position) {

        final Carro carro;

        if (listView.getAdapter() instanceof AdapterCarros) {
            carro = (Carro) listView.getAdapter().getItem(position);
        } else {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Desea eliminar al carro " + carro.getNombre()) .setTitle("Eliminar carro");

        builder.setPositiveButton("si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                Realm instance = Realm.getInstance(ListaActivity.this);
                instance.beginTransaction();
                Carro c = instance.where(Carro.class).equalTo("id", carro.getId()).findFirst();
                c.removeFromRealm();
                instance.commitTransaction();

                updateList();
            }
        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void deleteVendedor(int position) {

        final Vendedor vendedor;

        if (listView.getAdapter() instanceof AdapterVendedor) {
            vendedor = (Vendedor) listView.getAdapter().getItem(position);
        } else {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("¿Desea eliminar al vendedor " + vendedor.getNombre())
                .setTitle("Eliminar vendedor");

        builder.setPositiveButton("si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                Realm instance = Realm.getInstance(ListaActivity.this);
                instance.beginTransaction();
                Vendedor c = instance.where(Vendedor.class).equalTo("id", vendedor.getId()).findFirst();
                c.removeFromRealm();
                instance.commitTransaction();

                updateList();
            }
        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void deleteCliente(int position) {

        final Cliente cliente;

        if (listView.getAdapter() instanceof AdapterClientes) {
            cliente = (Cliente) listView.getAdapter().getItem(position);
        } else {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea eliminar al cliente " + cliente.getNombre()).setTitle("Eliminar cliente");

        builder.setPositiveButton("si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Realm instance = Realm.getInstance(ListaActivity.this);
                instance.beginTransaction();
                Cliente c = instance.where(Cliente.class).equalTo("id", cliente.getId()).findFirst();
                c.removeFromRealm();
                instance.commitTransaction();

                updateList();
            }
        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteCotizacion(int position) {

        final Cotizacion cotizacion;

        if (listView.getAdapter() instanceof AdapterCotizacion) {
            cotizacion = (Cotizacion) listView.getAdapter().getItem(position);
        } else {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea eliminar la cotizacion del " + DateFormat.getDateTimeInstance().format(cotizacion.getFecha())).setTitle("Eliminar cotizacion");

        builder.setPositiveButton("si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Realm instance = Realm.getInstance(ListaActivity.this);
                instance.beginTransaction();
                Cotizacion c = instance.where(Cotizacion.class).equalTo("id", cotizacion.getId()).findFirst();
                c.removeFromRealm();
                instance.commitTransaction();

                updateList();
            }
        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    public void updateList() {
        switch (ELEMENTS) {
            case CLIENTES:
                setAdapterClientes();
                break;
            case VENDEDORES:
                setAdapterVendedores();
                break;
            case CARROS:
                setAdapterCarros();
                break;
            case COTIZACIONES:
                setAdapterCotizaciones();
                break;
        }
    }

    private void setAdapterCarros() {
        Realm realm = Realm.getInstance(this);
        RealmResults<Carro> results = realm.where(Carro.class).findAllSorted("id");

        AdapterCarros adapterCarros = new AdapterCarros(this, results);
        listView.setAdapter(adapterCarros);
    }

    private void setAdapterVendedores() {
        Realm realm = Realm.getInstance(this);
        RealmResults<Vendedor> results = realm.where(Vendedor.class).findAllSorted("id");

        AdapterVendedor adapterVendedor = new AdapterVendedor(this, results);
        listView.setAdapter(adapterVendedor);
    }

    private void setAdapterClientes() {
        Realm realm = Realm.getInstance(this);
        RealmResults<Cliente> results = realm.where(Cliente.class).findAllSorted("id");

        AdapterClientes adapterClientes = new AdapterClientes(this, results);
        listView.setAdapter(adapterClientes);
    }

    private void setAdapterCotizaciones() {
        Realm realm = Realm.getInstance(this);
        RealmResults<Cotizacion> results = realm.where(Cotizacion.class).findAllSorted("id");
        AdapterCotizacion adapterCotizacion = new AdapterCotizacion(this, results);
        listView.setAdapter(adapterCotizacion);
    }

}
