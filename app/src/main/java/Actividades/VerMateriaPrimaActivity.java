package Actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sistema_inventario_ingeso.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Dominio.MateriaPrima;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;

public class VerMateriaPrimaActivity extends AppCompatActivity {
    private Spinner unidades;
    private MateriaPrima materiaPrima;
    private SistemaFacade sistema;
    private FloatingActionButton fabEdit, fabDelete;
    private EditText etNombreMateriaPrima, etUnidadMateriaPrima, etCantMateriaPrima;
    private Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_materia_prima);

        initViews();
        setupSistemaFacade();
        setupSpinner();
        setupFabButtons();
        populateFieldsIfMateriaPrimaExists();
    }

    private void initViews() {
        unidades = findViewById(R.id.spinnerUnits);
        etNombreMateriaPrima = findViewById(R.id.etMateriaPrimanombre);
        etCantMateriaPrima = findViewById(R.id.etMateriaPrimaCant);
        etUnidadMateriaPrima = findViewById(R.id.etMateriaPrimaUnidad);
        fabEdit = findViewById(R.id.fabEdit);
        fabDelete = findViewById(R.id.fabDelete);

    }

    private void setupSistemaFacade() {
        sistema = SistemaFacadeImpl.getInstancia(getApplicationContext());
    }

    private void setupSpinner() {
        ArrayList<String> units = new ArrayList<>();
        units.add("kilos (kg)");
        units.add("gramos (g)");
        units.add("litros (l)");

        unidades.setVisibility(View.INVISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        unidades.setAdapter(adapter);
    }

    private void setupFabButtons() {
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materiaPrima != null) {
                    Intent intent = new Intent(VerMateriaPrimaActivity.this, EditarMateriaPrimaActivity.class);
                    intent.putExtra("id", materiaPrima.getId()); // Asumiendo que el producto tiene un método getId()
                    startActivity(intent);
                } else {
                    Toast.makeText(VerMateriaPrimaActivity.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
    }

    private void populateFieldsIfMateriaPrimaExists() {
        materiaPrima = (MateriaPrima) getIntent().getSerializableExtra("datos");
        if (materiaPrima != null) {
            etNombreMateriaPrima.setText(materiaPrima.getNombre());
            etCantMateriaPrima.setText(String.valueOf(materiaPrima.getCantidad()));
            etUnidadMateriaPrima.setText(materiaPrima.getUnidad());

            etNombreMateriaPrima.setEnabled(false);
            etCantMateriaPrima.setEnabled(false);
            etUnidadMateriaPrima.setEnabled(false);

            etNombreMateriaPrima.setTextColor(Color.rgb(0, 0, 0));
            etCantMateriaPrima.setTextColor(Color.rgb(0, 0, 0));
            etUnidadMateriaPrima.setTextColor(Color.rgb(0, 0, 0));
        }
    }

    public void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar eliminación");
        builder.setMessage("¿Estás seguro de que deseas eliminar este producto?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lógica para eliminar el producto
                boolean resultado = sistema.eliminarMateriaPrima(materiaPrima);
                if (resultado) {
                    Toast.makeText(VerMateriaPrimaActivity.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerMateriaPrimaActivity.this, ListaMateriasPrimasActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(VerMateriaPrimaActivity.this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
