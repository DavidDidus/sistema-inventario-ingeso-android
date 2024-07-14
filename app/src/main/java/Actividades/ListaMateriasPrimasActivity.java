package Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.util.Log;

import com.example.sistema_inventario_ingeso.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Dominio.MateriaPrima;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;
import Utils.AdaptadorMateriaPrima;

public class ListaMateriasPrimasActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SistemaFacade sistema;
    private SearchView buscadorVista;
    private RecyclerView listaMateriaPrima;
    private FloatingActionButton fabAdd;
    private ListaMateriasPrimasActivity instancia = this;
    private AdaptadorMateriaPrima adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_materias_primas);

        sistema = SistemaFacadeImpl.getInstancia(getApplicationContext());

        listaMateriaPrima = findViewById(R.id.listaMateriasPrimas);
        listaMateriaPrima.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdaptadorMateriaPrima((ArrayList<MateriaPrima>) sistema.getListaMateriaPrima());
        listaMateriaPrima.setAdapter(adapter);

        buscadorVista = findViewById(R.id.buscador);
        buscadorVista.setOnQueryTextListener(this);

        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(instancia, NuevaMateriaPrimaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (adapter != null) {
            adapter.listaMateriaPrima.clear();
            adapter.setMateriaPrima((ArrayList<MateriaPrima>) sistema.getListaMateriaPrima());
            adapter.notifyDataSetChanged();
        } else {
            Log.e("ListaMateriasPrimasActivity", "El adaptador es nulo en onRestart()");
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrar(newText);
        return false;
    }
}
