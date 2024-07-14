package Logica;

import org.json.JSONException;

import java.util.Collection;
import java.util.List;

import Dominio.MateriaPrima;
import Dominio.Producto;

public interface SistemaFacade {
    List<Producto> getListaProducto();
    List<MateriaPrima> getListaMateriaPrima();
    boolean editarProducto(Producto producto);
    void ingresarProducto(Producto producto);
    int busquedaLinealProductos(int id);
    boolean eliminarProducto(Producto producto);
    void ingresarMateriaPrima(MateriaPrima materiaPrima);
    boolean editarMateriaPrima(MateriaPrima materiaPrima);
    boolean eliminarMateriaPrima(MateriaPrima materiaPrima);
    int busquedaMateriasPrimas(int idMateriaPrima);
    void guardarEnFirestore();
    void obtenerProductos();
}
