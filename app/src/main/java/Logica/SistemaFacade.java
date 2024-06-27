package Logica;

import java.util.List;

import Dominio.MateriaPrima;
import Dominio.Producto;

public interface SistemaFacade {
    public List<Producto> getListaProducto();
    List<MateriaPrima> getListaMateriaPrima();
    public boolean editarProducto(Producto producto);
    public void ingresarProducto(Producto producto);
    public int busquedaBinariaProductos(int id);
    public int busquedaLinealProductos(String nombre);

    public boolean eliminarProducto(Producto producto);
}
