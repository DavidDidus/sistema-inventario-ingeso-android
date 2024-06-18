package Logica;

import java.util.ArrayList;
import java.util.List;

import Dominio.*;

public interface SistemaProductos {

    public List<Producto> getListaProducto();
    public boolean editarProducto(Producto producto);

    public void ingresarMateriaPrima(MateriaPrima materiaPrima);
    public void ingresarProducto(Producto producto);
    public int busquedaBinariaProductos(int id);
    public void eliminarMateriaPrima(MateriaPrima materiaPrima);
    public boolean eliminarProducto(Producto producto);

}
