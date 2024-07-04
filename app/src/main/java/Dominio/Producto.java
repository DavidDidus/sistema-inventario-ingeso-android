package Dominio;

import java.io.Serializable;

public class Producto implements Serializable {
    private int id;
    private String categoria, nombre;
    private int cantidad;

    public Producto(int id,String categoria, String nombre,int cantidad) {
        this.id = id;
        this.categoria = categoria;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
