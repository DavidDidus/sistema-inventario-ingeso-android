package Logica;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.util.Collection;
import java.util.List;

import Dominio.MateriaPrima;
import Dominio.Producto;

public class SistemaFacadeImpl implements SistemaFacade{
    private static SistemaFacade instancia;
    private SistemaMateriaPrima materiasPrimas;
    private SistemaProductos productos;
    private Context context;

    private SistemaFacadeImpl(Context context){
        materiasPrimas = SistemaMateriaPrimaImpl.getInstance(context);
        productos = SistemaProductosImpl.getInstance(context);
    }
    public static SistemaFacade getInstancia(Context context) {
        if(instancia == null){
            instancia = new SistemaFacadeImpl(context);
        }
        return instancia;
    }


    @Override
    public List<Producto> getListaProducto() {
        return productos.getListaProducto();
    }

    @Override
    public List<MateriaPrima> getListaMateriaPrima() {
        return materiasPrimas.getListaMateriaPrima();
    }

    @Override
    public boolean editarProducto(Producto producto) {
        return productos.editarProducto(producto);
    }

    @Override
    public void ingresarProducto(Producto producto) {
        productos.ingresarProducto(producto);
    }

    @Override
    public int busquedaBinariaProductos(int id) {
        return productos.busquedaBinariaProductos(id);
    }

    @Override
    public int busquedaLinealProductos(String nombre) {
        return productos.busquedaLinealProductos(nombre);
    }


    @Override
    public boolean eliminarProducto(Producto producto) {
        return productos.eliminarProducto(producto);
    }

    @Override
    public void ingresarMateriaPrima(MateriaPrima materiaPrima) {
        materiasPrimas.ingresarMateriaPrima(materiaPrima);
    }

    @Override
    public int busquedaBinariaMateriasPrimas(int idMateriaPrima) {
        return materiasPrimas.busquedaBinaria(idMateriaPrima);
    }

    @Override
    public boolean editarMateriaPrima(MateriaPrima materiaPrima) {


        return materiasPrimas.editarMateriaPrima(materiaPrima);
    }

    @Override
    public boolean eliminarMateriaPrima(MateriaPrima materiaPrima) {
        return materiasPrimas.eliminarMateriaPrima(materiaPrima);
    }

    @Override
    public int busquedaMateriasPrimas(int idMateriaPrima) {
        return materiasPrimas.busquedaLineal(idMateriaPrima);
    }
    @Override
    public void actualizarMateriasPrimas(Collection<MateriaPrima> materiasPrimasActualizadas) {
        materiasPrimas.actualizarMateriasPrimas(materiasPrimasActualizadas);
    }



    @Override
    public void guardarEnFirestore() {
        productos.guardarProductosEnFirestore();
        materiasPrimas.guardarProductosEnFirestore();

    }
    public void obtenerProductos(){
        productos.obtenerProductos();
    }
}
