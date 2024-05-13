package Dominio;

public class MateriaPrima {
    private int id;
    private String nombre;
    private double cantidad;
    private String unidad;

    public MateriaPrima(String nombre, double cantidad, String unidad){
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
