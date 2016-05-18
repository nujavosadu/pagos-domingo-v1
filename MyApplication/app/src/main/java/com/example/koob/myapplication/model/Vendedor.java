package com.example.koob.myapplication.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by oscar on 4/25/16.
 */
public class Vendedor extends RealmObject {

    @Index
    private int id;
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
