package Logica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Dominio.MateriaPrima;
import Dominio.Producto;

public class SistemaProductosImpl implements SistemaProductos, Serializable {
    private List<Producto> listaProducto;

    private static SistemaProductos instance;

    private SistemaProductosImpl() {
        listaProducto = new ArrayList<>();
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
    public int busquedaLinealProductos(String nombre) {
        for (int i = 0; i < listaProducto.size(); i++) {
            if (nombre.equalsIgnoreCase(listaProducto.get(i).getNombre())) {
                return i; // Se ha encontrado el producto
            }
        }
        return -1; // El producto no se encontró
    }
    @Override
    public boolean eliminarProducto(Producto producto) {
        return listaProducto.remove(producto);
    }
    private void obtenerProductos() {
        try {
            // Obtener la ruta del archivo JSON como un recurso
            InputStream inputStream = getClass().getResourceAsStream("/productos.json");

            // Verificar que el archivo se haya cargado correctamente
            if (inputStream != null) {
                // Leer el contenido del archivo JSON
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();

                // Convertir el contenido a un objeto JSON
                JSONArray jsonArray = new JSONArray(content.toString());

                // Iterar sobre cada objeto JSON en el array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Obtener los datos del producto
                    String nombre = jsonObject.getString("nombre");
                    String codigo = jsonObject.getString("categoria");
                    int cantidad = jsonObject.getInt("cantidad");

                    // Crear un nuevo objeto Producto y agregarlo a la lista
                    Producto producto = new Producto(codigo,nombre, cantidad);
                    ingresarProducto(producto);
                }
            } else {
                System.err.println("No se pudo cargar el archivo JSON de productos.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
