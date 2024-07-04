package Dominio;

import java.io.Serializable;

public class MateriaPrima implements Serializable {
    private int id;
    private String nombre;
    private double cantidad;
    private String unidad;

    public MateriaPrima(int id,String nombre, double cantidad, String unidad){
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public double getCantidad() {
        return cantidad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
