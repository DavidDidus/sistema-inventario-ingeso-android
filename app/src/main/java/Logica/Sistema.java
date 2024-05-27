package Logica;

import java.util.ArrayList;
import java.util.List;

import Dominio.*;

public interface Sistema {
    public List<Producto> getListaProducto();
    public void setListaProducto(ArrayList<Producto> productos);
    public List<MateriaPrima> getListaMateriaPrima();
    public void ingresarMateriaPrima(MateriaPrima materiaPrima);
    public void ingresarProducto(Producto producto);
    public int busquedaBinariaMateriasPrimas(int id);
    public int busquedaBinariaProductos(int id);
    public void eliminarMateriaPrima(MateriaPrima materiaPrima);
    public void eliminarProducto(Producto producto);


}
