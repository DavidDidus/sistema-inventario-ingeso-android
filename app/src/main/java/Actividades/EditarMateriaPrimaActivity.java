package Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_inventario_ingeso.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Dominio.MateriaPrima;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;

public class EditarMateriaPrimaActivity extends AppCompatActivity {

    private EditText etNombreMateriaPrima, etCantMateriaPrima, etUnidadMateriaPrima;
    private FloatingActionButton fabEdit, fabDelete;
    private MateriaPrima materiaPrima;
    private SistemaFacade sistema;
    private Button guardarButton;
    private Spinner unidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_materia_prima);

        ArrayList<String> units = new ArrayList<>();
        units.add("kilos (kg)");
        units.add("gramos (g)");
        units.add("litros (l)");

        unidades = findViewById(R.id.spinnerUnits);


        unidades.setVisibility(View.VISIBLE);

        sistema = SistemaFacadeImpl.getInstancia();

        fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setVisibility(View.INVISIBLE);

        fabDelete = findViewById(R.id.fabDelete);
        fabDelete.setVisibility(View.INVISIBLE);

        guardarButton = findViewById(R.id.EditButton);
        guardarButton.setVisibility(View.VISIBLE);

        etNombreMateriaPrima = findViewById(R.id.etMateriaPrimanombre);
        etCantMateriaPrima = findViewById(R.id.etMateriaPrimaCant);
        etUnidadMateriaPrima = findViewById(R.id.etMateriaPrimaUnidad);
        etUnidadMateriaPrima.setVisibility(View.INVISIBLE);




        obtenerMateriaPrima();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        unidades.setAdapter(adapter);


        if (materiaPrima != null) {
            etNombreMateriaPrima.setHint(materiaPrima.getNombre());
            etCantMateriaPrima.setHint(String.valueOf(materiaPrima.getCantidad()));


        } else {
            Toast.makeText(this, "Materia prima no encontrada", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public int obtenerId() {
        return getIntent().getIntExtra("id", -1);
    }

    public void obtenerMateriaPrima() {
        int idMateriaPrima = obtenerId();

        if (idMateriaPrima != -1) {
            int posMateriaPrima = sistema.busquedaMateriasPrimas(idMateriaPrima);

            if (posMateriaPrima >= 0 && posMateriaPrima < sistema.getListaMateriaPrima().size()) {
                materiaPrima = sistema.getListaMateriaPrima().get(posMateriaPrima);
            } else {
                materiaPrima = null;
            }
        } else {
            materiaPrima = null;
        }
    }

    public void pressEdit(View v) {
        if (materiaPrima == null) {
            Toast.makeText(this, "Materia prima no encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        String nuevoNombre = etNombreMateriaPrima.getText().toString();
        String nuevaCategoria = unidades.getSelectedItem().toString();
        double nuevaCantidad;

        try {
            nuevaCantidad = Integer.parseInt(etCantMateriaPrima.getText().toString().trim());
        } catch (NumberFormatException e) {
            nuevaCantidad = materiaPrima.getCantidad();
        }

        if (nuevoNombre.isEmpty()) {
            nuevoNombre = materiaPrima.getNombre();
        }


        materiaPrima.setNombre(nuevoNombre);
        materiaPrima.setUnidad(nuevaCategoria);
        materiaPrima.setCantidad(nuevaCantidad);

        boolean resultado = sistema.editarMateriaPrima(materiaPrima);

        if (resultado) {
            Toast.makeText(this, "Materia prima actualizada correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ListaMateriasPrimasActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar la materia prima", Toast.LENGTH_SHORT).show();
        }
    }
}
