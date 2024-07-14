package Actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_inventario_ingeso.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Dominio.Producto;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;


public class VerActivity extends AppCompatActivity {

    private Producto producto;
    private EditText etProductName, etProductCategory, etProductAmount;
    private VerActivity instancia = this;
    private FloatingActionButton fabEdit,fabDelete;
    private Button guardarButton;
    private SistemaFacade sistema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);
        sistema = SistemaFacadeImpl.getInstancia(getApplicationContext());
        producto = (Producto) getIntent().getSerializableExtra("datos");
        initViews();
        setupInitialData();
        setupListeners();

    }

    private void initViews() {
        etProductName = findViewById(R.id.etProductName);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductAmount = findViewById(R.id.etProductAmount);
        fabEdit = findViewById(R.id.fabEdit);
        fabDelete = findViewById(R.id.fabDelete);
        guardarButton = findViewById(R.id.EditButton);
        guardarButton.setBackgroundColor(Color.rgb(102, 187, 106));
    }

    private void setupInitialData() {

        if (producto != null) {
            String productoNombre = producto.getNombre();

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

        }else{
            Toast.makeText(this, "Producto nulo", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si el producto es nulo
        }
    }

    private void setupListeners() {
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(producto.getNombre());
                if (producto != null) {
                    Intent intent = new Intent(VerActivity.this, EditarProductoActivity.class);
                    intent.putExtra("id", producto.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(VerActivity.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
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
    public void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar eliminación");
        builder.setMessage("¿Estás seguro de que deseas eliminar este producto?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lógica para eliminar el producto
                boolean resultado = sistema.eliminarProducto(producto);
                if (resultado) {
                    Toast.makeText(VerActivity.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerActivity.this, ListaProductosActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(VerActivity.this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
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
