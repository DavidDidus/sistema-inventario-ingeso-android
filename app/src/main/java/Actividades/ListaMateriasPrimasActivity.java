package Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sistema_inventario_ingeso.R;

import java.util.ArrayList;

import Dominio.MateriaPrima;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;
import Logica.SistemaProductos;
import Utils.AdaptadorMateriaPrima;

public class ListaMateriasPrimasActivity extends AppCompatActivity {

    private SistemaFacade sistema;
    private AdaptadorMateriaPrima adapter;
    private RecyclerView listaMateriaPrima;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_materias_primas);

        sistema = SistemaFacadeImpl.getInstancia();

        listaMateriaPrima = findViewById(R.id.listaMateriasPrimas);
        listaMateriaPrima.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdaptadorMateriaPrima((ArrayList<MateriaPrima>) sistema.getListaMateriaPrima());
        listaMateriaPrima.setAdapter(adapter);
    }
}