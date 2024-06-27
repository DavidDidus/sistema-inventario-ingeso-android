package Logica;

import java.util.List;

import Dominio.MateriaPrima;

public interface SistemaMateriaPrima {

    void ingresarMateriaPrima(MateriaPrima materiaPrima);

    List<MateriaPrima> getListaMateriaPrima();
}
