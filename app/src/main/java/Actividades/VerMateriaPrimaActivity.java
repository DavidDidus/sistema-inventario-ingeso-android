package Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.sistema_inventario_ingeso.R;

import java.util.ArrayList;

public class VerMateriaPrimaActivity extends AppCompatActivity {
    private Spinner unidades;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_materia_prima);

        unidades = findViewById(R.id.spinnerUnits);
        ArrayList<String> units = new ArrayList<>();
        units.add("kilos (kg)");
        units.add("gramos (g)");
        units.add("litros (l)");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        unidades.setAdapter(adapter);

    }
}