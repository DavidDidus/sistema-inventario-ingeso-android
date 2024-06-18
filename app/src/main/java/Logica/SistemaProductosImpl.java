package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Dominio.MateriaPrima;
import Dominio.Producto;

public class SistemaProductosImpl implements SistemaProductos, Serializable {
    private final List<MateriaPrima> listaMateriaPrima;
    private List<Producto> listaProducto;

    private static SistemaProductos instance;

    private SistemaProductosImpl() {
        listaMateriaPrima = new ArrayList<>();
        listaProducto = new ArrayList<>();
        obtenerMateriasPrimas();
        obtenerProductos();
    }
    // Método estático para obtener la única instancia de la clase
    public static synchronized SistemaProductosImpl getInstance() {
        if (instance == null) {
            instance = new SistemaProductosImpl();
        }
        return (SistemaProductosImpl) instance;
    }
    @Override
    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    @Override
    public boolean editarProducto(Producto producto) {
        int posProducto = busquedaBinariaProductos(producto.getId());
        if (posProducto != -1) {
            // Actualizar el producto en la lista
            listaProducto.set(posProducto, producto);
            return true;
        }
        return false;

    }

    @Override
    public void ingresarMateriaPrima(MateriaPrima materiaPrima) {
        materiaPrima.setId(listaMateriaPrima.size());
        listaMateriaPrima.add(materiaPrima);
    }

    @Override
    public void ingresarProducto(Producto producto) {
        producto.setId(listaProducto.size());
        listaProducto.add(producto);
    }

    @Override
    public int busquedaBinariaProductos(int id) {
        int posIzq = 0;
        int posDer = listaProducto.size() - 1;

        while (posIzq <= posDer) {
            int posMid = posIzq + (posDer - posIzq) / 2;
            if (id == listaProducto.get(posMid).getId()) {
                return posMid;
            } else if (id < listaProducto.get(posMid).getId()) {
                posDer = posMid - 1;
            } else {
                posIzq = posMid + 1;
            }
        }
        return -1;
    }

    @Override
    public void eliminarMateriaPrima(MateriaPrima materiaPrima) {
        listaMateriaPrima.remove(materiaPrima);
    }

    @Override
    public boolean eliminarProducto(Producto producto) {
        return listaProducto.remove(producto);
    }
    private void obtenerProductos(){
        ingresarProducto(new Producto("perfume"," 1", 10));
        ingresarProducto(new Producto("perfume","Producto 1", 10));
        ingresarProducto(new Producto("perfume","Producto 1", 10));
        ingresarProducto(new Producto("perfume","Producto 1", 10));
        ingresarProducto(new Producto("perfume","Producto 1", 10));
        ingresarProducto(new Producto("perfume","Producto 1", 10));
        ingresarProducto(new Producto("perfume","Producto 1", 10));
        ingresarProducto(new Producto("perfume","Producto 1", 10));
        ingresarProducto(new Producto("perfume","Producto 1", 10));

    }

    private void obtenerMateriasPrimas(){
        ingresarMateriaPrima(new MateriaPrima("azufre",2,"mg"));

    }

}
