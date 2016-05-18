package com.example.koob.myapplication.model;

import com.example.koob.myapplication.model.Carro;
import com.example.koob.myapplication.model.Cliente;
import com.example.koob.myapplication.model.Vendedor;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by oscar on 4/25/16.
 */
public class Cotizacion extends RealmObject {

    @Index
    private int id;
    private Vendedor vendedor;
    private Cliente cliente;
    private Carro carro;


    private int enganche; //de aqui sacar $ enganche, carro.precio * (  enganche / 100 )
    private double totalEnganche;
    private double seguro;
    private double interes;

    private int meses;

    private Date fecha;
    private double saldo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public int getEnganche() {
        return enganche;
    }

    public void setEnganche(int enganche) {
        this.enganche = enganche;
    }

    public double getSeguro() {
        return seguro;
    }

    public void setSeguro(double seguro) {
        this.seguro = seguro;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotalEnganche() {
        return totalEnganche;
    }

    public void setTotalEnganche(double totalEnganche) {
        this.totalEnganche = totalEnganche;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }
}
