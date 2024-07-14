package Actividades;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sistema_inventario_ingeso.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Dominio.Producto;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;
import Utils.AdaptadorProductos;

public class ListaProductosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private SistemaFacade sistema;
    private SearchView buscadorVista;
    private RecyclerView listaProductos;
    private FloatingActionButton fabAdd;
    private ListaProductosActivity instancia = this;
    private AdaptadorProductos adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        sistema = SistemaFacadeImpl.getInstancia(getApplicationContext());

        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdaptadorProductos((ArrayList<Producto>) sistema.getListaProducto());
        listaProductos.setAdapter(adapter);

        buscadorVista = findViewById(R.id.buscador);
        buscadorVista.setOnQueryTextListener(this);

        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(instancia, NuevoProductoActivity.class);
                startActivity(intent);

            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if (adapter != null) {
            adapter.getProductos().clear();
            adapter.setProductos((ArrayList<Producto>) sistema.getListaProducto());
            adapter.notifyDataSetChanged();

        } else {
            // Log de advertencia o mensaje de depuración para identificar el problema
            Log.e("ListaProductosActivity", "El adaptador es nulo en onResume()");
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