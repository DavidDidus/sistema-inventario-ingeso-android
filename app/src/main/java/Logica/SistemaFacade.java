package Logica;

import java.util.List;
import Dominio.Producto;

public interface SistemaFacade {
    public List<Producto> getListaProducto();
    public boolean editarProducto(Producto producto);
    public void ingresarProducto(Producto producto);
    public int busquedaBinariaProductos(int id);

    public boolean eliminarProducto(Producto producto);
}
