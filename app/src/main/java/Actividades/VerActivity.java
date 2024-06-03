package Actividades;

import android.content.Intent;
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
import Logica.Sistema;
import Logica.SistemaImpl;

public class VerActivity extends AppCompatActivity {

    private Producto producto;
    private EditText etProductName, etProductCategory, etProductAmount;
    private VerActivity instancia = this;
    private FloatingActionButton fabEdit;
    private Button guardarButton;
    private Sistema sistema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);
        sistema = SistemaImpl.getInstance();
        initViews();
        setupInitialData();
        setupListeners();

    }

    private void initViews() {
        etProductName = findViewById(R.id.etProductName);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductAmount = findViewById(R.id.etProductAmount);
        fabEdit = findViewById(R.id.fabEdit);
        guardarButton = findViewById(R.id.EditButton);
    }

    private void setupInitialData() {
        producto = (Producto) getIntent().getSerializableExtra("datos");
        int productoId = producto.getId();
        if (productoId != -1) {
            producto = sistema.getListaProducto().get(sistema.busquedaBinariaProductos(productoId));
            if (producto != null) {
                guardarButton.setVisibility(View.INVISIBLE);

                etProductName.setText(producto.getNombre());
                etProductCategory.setText(producto.getCategoria());
                etProductAmount.setText(String.valueOf(producto.getCantidad()));

                etProductName.setEnabled(false);
                etProductCategory.setEnabled(false);
                etProductAmount.setEnabled(false);

                etProductName.setTextColor(Color.rgb(0, 0, 0));
                etProductCategory.setTextColor(Color.rgb(0, 0, 0));
                etProductAmount.setTextColor(Color.rgb(0, 0, 0));
            } else {
                Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                finish(); // Cierra la actividad si el producto no se encuentra
            }
        } else {
            Toast.makeText(this, "ID de producto inválido", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si el ID es inválido
        }
    }

    private void setupListeners() {
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(instancia, EditarProductoActivity.class);
                intent.putExtra("id", producto.getId());
                startActivity(intent);
            }
        });
    }
}
