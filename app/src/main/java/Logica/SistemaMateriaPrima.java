package Logica;

import org.json.JSONException;

import java.util.Collection;
import java.util.List;

import Dominio.MateriaPrima;

public interface SistemaMateriaPrima {

    void ingresarMateriaPrima(MateriaPrima materiaPrima);
    List<MateriaPrima> getListaMateriaPrima();
    boolean editarMateriaPrima(MateriaPrima materiaPrima);
    boolean eliminarMateriaPrima(MateriaPrima materiaPrima);
    int busquedaLineal(int id);
    void guardarProductosEnFirestore();
}
