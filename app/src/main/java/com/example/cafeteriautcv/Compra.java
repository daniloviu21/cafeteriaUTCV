package com.example.cafeteriautcv;

import java.io.Serializable;

public class Compra implements Serializable {
    private String producto;
    private int cantidad;
    private double precio;
    private String estado;

    public Compra(String producto, int cantidad, double precio, String estado){
        this.producto =  producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.estado = estado;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
