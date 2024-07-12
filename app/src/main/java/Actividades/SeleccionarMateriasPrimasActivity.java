package Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sistema_inventario_ingeso.R;

import java.util.ArrayList;
import java.util.List;

import Dominio.MateriaPrima;
import Dominio.Producto;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;
import Utils.MateriasPrimasAdapter;

public class SeleccionarMateriasPrimasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MateriasPrimasAdapter adapter;
    private SistemaFacade sistema;
    private List<MateriaPrima> listaMateriasPrimas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_materias_primas);

        sistema = SistemaFacadeImpl.getInstancia(getApplicationContext());
        listaMateriasPrimas = sistema.getListaMateriaPrima();

        recyclerView = findViewById(R.id.recyclerViewMateriasPrimas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MateriasPrimasAdapter((ArrayList<MateriaPrima>) listaMateriasPrimas);
        recyclerView.setAdapter(adapter);

        Button btnConfirmar = findViewById(R.id.btnConfirmarMateriasPrimas);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarSeleccion();
            }
        });

    }

    private void confirmarSeleccion() {
        ArrayList<MateriaPrima> materiasPrimasSeleccionadas = adapter.getMateriasPrimasSeleccionadas();
        if (confirmarCantidad(materiasPrimasSeleccionadas)) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("materiasPrimasSeleccionadas", materiasPrimasSeleccionadas);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Cantidad no valida", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean confirmarCantidad(ArrayList<MateriaPrima> materiasPrimasSeleccionadas) {
        for (MateriaPrima materiaPrimaSeleccionada : materiasPrimasSeleccionadas) {
            MateriaPrima materiaPrimaAlmacenada = sistema.getListaMateriaPrima().stream()
                    .filter(materiaPrima -> materiaPrima.getId() == materiaPrimaSeleccionada.getId())
                    .findFirst()
                    .orElse(null);

            if (materiaPrimaAlmacenada == null || materiaPrimaSeleccionada.getCantidad() > materiaPrimaAlmacenada.getCantidad()) {
                return false;
            }
        }
        return true;
    }

}
