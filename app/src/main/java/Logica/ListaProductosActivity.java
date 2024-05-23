package Logica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.sistema_inventario_ingeso.R;

public class ListaProductosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    Sistema sistema;
    SearchView buscadorVista;
    RecyclerView listaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        sistema = (Sistema) getIntent().getSerializableExtra("sistema");

        listaProductos = findViewById(R.id.productos);
        listaProductos.setLayoutManager(new LinearLayoutManager(this));

        buscadorVista = findViewById(R.id.search);
        buscadorVista.setOnQueryTextListener(this);


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