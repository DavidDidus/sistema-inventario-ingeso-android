package Actividades;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_inventario_ingeso.R;

import Dominio.Producto;

public class VerActivity extends AppCompatActivity {

    private Producto producto;
    private EditText etProductName,etProductCategory,etProductUnidad,
            etProductPrice,etProductAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);

        etProductName = findViewById(R.id.etProductName);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductAmount = findViewById(R.id.etProductAmount);
        etProductPrice = findViewById(R.id.etProductPrice);

        producto = (Producto) getIntent().getSerializableExtra("datos");

        Toast.makeText(this, producto.getNombre() + " a", Toast.LENGTH_SHORT).show();

        etProductName.setText(producto.getNombre());
        etProductCategory.setText(producto.getCategoria());
        etProductAmount.setText(String.valueOf(producto.getCantidad()));
        etProductPrice.setText(String.valueOf(producto.getValor()));

        etProductName.setEnabled(false);
        etProductCategory.setEnabled(false);
        etProductAmount.setEnabled(false);
        etProductPrice.setEnabled(false);
    }
}
