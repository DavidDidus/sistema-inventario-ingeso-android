package Actividades;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

import Dominio.MateriaPrima;
import Dominio.Producto;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;

public class NuevaMateriaPrimaActivity extends AppCompatActivity {
    private Spinner spinner;
    private EditText etMateriaPrimaNombre,etMateriaPrimaCant;
    private SistemaFacade sistema;
    private Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_materia_prima);

        sistema = SistemaFacadeImpl.getInstancia(getApplicationContext());

        etMateriaPrimaNombre = findViewById(R.id.etMateriaPrimaNombre);
        etMateriaPrimaCant = findViewById(R.id.etMateriaPrimacant);
        spinner = findViewById(R.id.spinner);
        boton = findViewById(R.id.button);
        boton.setBackgroundColor(Color.rgb(102, 187, 106));

        setupSpinner();


    }

    private void setupSpinner() {
        ArrayList<String> units = new ArrayList<>();
        units.add("kilos (kg)");
        units.add("gramos (g)");
        units.add("litros (l)");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }
    public void pressRegistro(View v){
        if (areAllFieldsFilled()) {
            String nombre = etMateriaPrimaNombre.getText().toString();
            double cantidad = Double.parseDouble(etMateriaPrimaCant.getText().toString());
            String unidad = spinner.getSelectedItem().toString();

            // Verificar si el nombre del producto ya existe
            if (isProductNameUnique(nombre)) {
                MateriaPrima materiaPrimaNueva = new MateriaPrima(sistema.getListaMateriaPrima().size(),nombre,cantidad,unidad);
                sistema.ingresarMateriaPrima(materiaPrimaNueva);
                Toast.makeText(NuevaMateriaPrimaActivity.this, "Materia prima " + nombre + " ingresado.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ListaMateriasPrimasActivity.class);
                startActivity(intent);// Cierra la actividad después de la edición exitosa
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(NuevaMateriaPrimaActivity.this, "El nombre del producto ya existe. Por favor ingresa un nombre único.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Mostrar un mensaje de error si no se completan todos los campos
            Toast.makeText(NuevaMateriaPrimaActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
        }


    }
    private boolean isProductNameUnique(String nombre) {
        for (MateriaPrima materiaPrima : sistema.getListaMateriaPrima()) {
            if (materiaPrima.getNombre().equalsIgnoreCase(nombre)) {
                return false; // El nombre del producto ya existe
            }
        }
        return true; // El nombre del producto es único
    }
    private boolean areAllFieldsFilled() {
        return !etMateriaPrimaNombre.getText().toString().trim().isEmpty()
                && !etMateriaPrimaCant.getText().toString().trim().isEmpty();
    }

}