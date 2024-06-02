package Actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_inventario_ingeso.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Dominio.Producto;

public class VerActivity extends AppCompatActivity {

    private Producto producto;
    private EditText etProductName,etProductCategory,etProductAmount;
    private VerActivity instancia = this;
    private FloatingActionButton fabEdit;
    private Button guardarButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);
        etProductName = findViewById(R.id.etProductName);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductAmount = findViewById(R.id.etProductAmount);

        producto = (Producto) getIntent().getSerializableExtra("datos");

        fabEdit = findViewById(R.id.fabEdit);

        guardarButton= findViewById(R.id.EditButton);
        guardarButton.setVisibility(View.INVISIBLE);

        etProductName.setText(producto.getNombre());
        etProductCategory.setText(producto.getCategoria());
        etProductAmount.setText(String.valueOf(producto.getCantidad()));

        etProductName.setEnabled(false);
        etProductCategory.setEnabled(false);
        etProductAmount.setEnabled(false);

        etProductName.setTextColor(Color.rgb(0,0,0));
        etProductCategory.setTextColor(Color.rgb(0,0,0));
        etProductAmount.setTextColor(Color.rgb(0,0,0));
        Toast.makeText(instancia, "b", Toast.LENGTH_SHORT).show();
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(instancia, EditarProductoActivity.class);
                intent.putExtra("id",producto.getId());
                startActivity(intent);

            }
        });
    }
}
