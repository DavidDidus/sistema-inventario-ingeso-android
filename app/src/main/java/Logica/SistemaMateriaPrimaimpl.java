package Logica;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Dominio.MateriaPrima;

public class SistemaMateriaPrimaimpl implements SistemaMateriaPrima {
    private static SistemaMateriaPrima  instance;
    private final List<MateriaPrima> listaMateriaPrima;

    private SistemaMateriaPrimaimpl(){
        listaMateriaPrima = new ArrayList<>();
        obtenerMateriasPrimas();

    }
    public static SistemaMateriaPrima getInstance(){
        if(instance == null){
            instance = new SistemaMateriaPrimaimpl();
        }
        return instance;
    }
    private void obtenerMateriasPrimas(){
        try {
            // Obtener la ruta del archivo JSON como un recurso
            InputStream inputStream = getClass().getResourceAsStream("/materias_primas.json");

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
                    String unidad = jsonObject.getString("unidad");
                    double cantidad = jsonObject.getInt("cantidad");

                    // Crear un nuevo objeto Producto y agregarlo a la lista
                    MateriaPrima nuevo = new MateriaPrima(nombre,cantidad,unidad);
                    ingresarMateriaPrima(nuevo);
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
    @Override
    public void ingresarMateriaPrima(MateriaPrima materiaPrima) {
        materiaPrima.setId(listaMateriaPrima.size());
        listaMateriaPrima.add(materiaPrima);
    }

    @Override
    public List<MateriaPrima> getListaMateriaPrima() {
        return listaMateriaPrima;
    }

}



