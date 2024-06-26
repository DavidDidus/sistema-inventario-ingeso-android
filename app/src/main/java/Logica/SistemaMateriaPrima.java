package Logica;

import java.util.List;

import Dominio.MateriaPrima;

public interface SistemaMateriaPrima {

    void ingresarMateriaPrima(MateriaPrima materiaPrima);

    List<MateriaPrima> getListaMateriaPrima();
    String busquedaLinealMateriaPrima(String materiaPrima);

    int busquedaBinaria(int idMateriaPrima);

    boolean editarMateriaPrima(MateriaPrima materiaPrima);

    boolean eliminarMateriaPrima(MateriaPrima materiaPrima);
    int busquedaLineal(int id);
}
