package Logica;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import Dominio.*;

public interface SistemaProductos {

    List<Producto> getListaProducto();
    boolean editarProducto(Producto producto);
    void ingresarProducto(Producto producto);
    int busquedaLinealProductos(int id);
    boolean eliminarProducto(Producto producto);
    void guardarProductosEnFirestore();
    void obtenerProductos();
}
