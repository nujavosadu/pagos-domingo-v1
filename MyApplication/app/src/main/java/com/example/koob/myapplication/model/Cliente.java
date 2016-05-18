package com.example.koob.myapplication.model;


import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by oscar on 4/24/16.
 */
public class Cliente extends RealmObject {

    @Index
    private int id;
    private String nombre;
    private String colonia;
    private String calle;

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }
}
