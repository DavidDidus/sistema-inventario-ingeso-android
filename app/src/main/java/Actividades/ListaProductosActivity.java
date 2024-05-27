package Actividades;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.sistema_inventario_ingeso.R;

import java.util.ArrayList;

import Dominio.Producto;
import Logica.Sistema;
import Logica.SistemaImpl;
import Utils.AdaptadorProductos;

public class ListaProductosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    Sistema sistema;
    SearchView buscadorVista;
    RecyclerView listaProductos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        sistema = (SistemaImpl) getIntent().getSerializableExtra("sistema");

        listaProductos = findViewById(R.id.listaProductos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        AdaptadorProductos adapter = new AdaptadorProductos((ArrayList<Producto>) sistema.getListaProducto());
        listaProductos.setAdapter(adapter);

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