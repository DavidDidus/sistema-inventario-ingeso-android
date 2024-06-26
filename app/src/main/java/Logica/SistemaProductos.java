package Logica;

import java.util.ArrayList;
import java.util.List;

import Dominio.*;

public interface SistemaProductos {

    public List<Producto> getListaProducto();
    public boolean editarProducto(Producto producto);
    public void ingresarProducto(Producto producto);
    public int busquedaBinariaProductos(int id);
    public int busquedaLinealProductos(String nombre);
    public boolean eliminarProducto(Producto producto);

}
