package com.example.koob.myapplication;

import android.app.Application;

import com.example.koob.myapplication.model.Cotizacion;
import com.example.koob.myapplication.model.Carro;
import com.example.koob.myapplication.model.Cliente;
import com.example.koob.myapplication.model.Vendedor;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by oscar on 4/24/16.
 */
public class MyApplication extends Application {

    public long primaryKeyCarro = 0,primaryKeyCliente = 0,primaryKeyVendedor = 0,primaryKeyCotizacion = 0;

    static MyApplication instance;
    public static MyApplication instance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm realm = null;
        try {
            realm = Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                realm = Realm.getInstance(realmConfiguration);
            } catch (Exception ex){
                throw ex;
                //No Realm file to remove.
            }
        }

        try {
            primaryKeyCarro = realm.where(Carro.class).max("id").longValue();
        }catch (Exception ex){

        }
        try {
            primaryKeyCliente= realm.where(Cliente.class).max("id").longValue();
        }catch (Exception ex){

        }
        try {
            primaryKeyVendedor = realm.where(Vendedor.class).max("id").longValue();
        }catch (Exception ex){

        }
        try {
            primaryKeyCotizacion = realm.where(Cotizacion.class).max("id").longValue();
        }catch (Exception ex){

        }


    }
}

