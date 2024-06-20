package Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sistema_inventario_ingeso.R;

import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;
import Logica.SistemaProductos;

public class ListaMateriasPrimasActivity extends AppCompatActivity {

    private SistemaFacade sistema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_materias_primas);
        sistema = SistemaFacadeImpl.getInstancia();
    }
}