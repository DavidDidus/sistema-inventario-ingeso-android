package Logica;

import java.util.ArrayList;
import java.util.List;

import Dominio.MateriaPrima;
import Dominio.Producto;

public class SistemaFacadeImpl implements SistemaFacade{
    private static SistemaFacade instancia;
    private SistemaMateriaPrima materiasPrimas;
    private SistemaProductos productos;
    private SistemaFacadeImpl(){
        materiasPrimas = SistemaMateriaPrimaimpl.getInstance();
        productos = SistemaProductosImpl.getInstance();
    }
    public static SistemaFacade getInstancia() {
        if(instancia == null){
            instancia = new SistemaFacadeImpl();
        }
        return instancia;
    }


    @Override
    public List<Producto> getListaProducto() {
        return productos.getListaProducto();
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
}
