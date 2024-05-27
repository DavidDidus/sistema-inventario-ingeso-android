package Logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Dominio.MateriaPrima;
import Dominio.Producto;

public class SistemaImpl implements Sistema, Serializable {
    private final List<MateriaPrima> listaMateriaPrima;
    private List<Producto> listaProducto;


    public SistemaImpl(){
        listaMateriaPrima = new ArrayList<>();
        listaProducto = new ArrayList<>();
        obtenerMateriasPrimas();
        obtenerProductos();
    }

    @Override
    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    @Override
    public void setListaProducto(ArrayList<Producto> productos) {
        listaProducto = productos;
    }

    @Override
    public List<MateriaPrima> getListaMateriaPrima() {
        return listaMateriaPrima;
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
    public int busquedaBinariaMateriasPrimas(int id) {
        int posIzq = 0;
        int posDer = listaMateriaPrima.size() - 1;

        while (posIzq <= posDer) {
            int posMid = posIzq + (posDer - posIzq) / 2;

            if (id == listaMateriaPrima.get(posMid).getId()) {
                return posMid;
            } else if (id < listaMateriaPrima.get(posMid).getId()) {
                posDer = posMid - 1;
            } else {
                posIzq = posMid + 1;
            }
        }
        return -1;
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
    public void eliminarProducto(Producto producto) {
        listaProducto.remove(producto);
    }
    private void obtenerProductos(){
        ingresarProducto(new Producto("perfume"," 1", 10, "kg", 15.99));
        ingresarProducto(new Producto("perfume","Producto 1", 10, "kg", 15.99));
        ingresarProducto(new Producto("perfume","Producto 1", 10, "kg", 15.99));
        ingresarProducto(new Producto("perfume","Producto 1", 10, "kg", 15.99));
        ingresarProducto(new Producto("perfume","Producto 1", 10, "kg", 15.99));
        ingresarProducto(new Producto("perfume","Producto 1", 10, "kg", 15.99));
        ingresarProducto(new Producto("perfume","Producto 1", 10, "kg", 15.99));
        ingresarProducto(new Producto("perfume","Producto 1", 10, "kg", 15.99));
        ingresarProducto(new Producto("perfume","Producto 1", 10, "kg", 15.99));

    }

    private void obtenerMateriasPrimas(){

    }
}
