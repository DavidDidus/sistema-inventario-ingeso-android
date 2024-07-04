package Logica;

import org.json.JSONException;

import java.util.Collection;
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
    public void ingresarMateriaPrima(MateriaPrima materiaPrima);

    int busquedaBinariaMateriasPrimas(int idMateriaPrima);

    boolean editarMateriaPrima(MateriaPrima materiaPrima);
    boolean eliminarMateriaPrima(MateriaPrima materiaPrima);

    int busquedaMateriasPrimas(int idMateriaPrima);

    void actualizarMateriasPrimas(Collection<MateriaPrima> materiasPrimasActualizadas);
    void guardarCambios() throws JSONException;
}
