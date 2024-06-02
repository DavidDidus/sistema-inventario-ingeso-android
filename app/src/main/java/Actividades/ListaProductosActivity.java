package Actividades;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.sistema_inventario_ingeso.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Dominio.Producto;
import Logica.Sistema;
import Logica.SistemaImpl;
import Utils.AdaptadorProductos;

public class ListaProductosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private Sistema sistema;
    private SearchView buscadorVista;
    private RecyclerView listaProductos;
    private FloatingActionButton fabAdd;
    private ListaProductosActivity instancia = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        sistema = SistemaImpl.getInstance();

        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        AdaptadorProductos adapter = new AdaptadorProductos((ArrayList<Producto>) sistema.getListaProducto());
        listaProductos.setAdapter(adapter);

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
    public boolean onQueryTextSubmit(String query) {


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}