package Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistema_inventario_ingeso.R;

import Dominio.Producto;
import Logica.Sistema;
import Logica.SistemaImpl;

public class NuevoProductoActivity extends AppCompatActivity {

    private EditText etProductName,etProductCategory,etProductUnidad,
                     etProductPrice,etProductAmount;


    Sistema sistema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_producto);

        sistema = SistemaImpl.getInstance();

        etProductName = findViewById(R.id.etProductName);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductAmount = findViewById(R.id.etProductAmount);

    }
    public void pressRegistro(View v){
        if (areAllFieldsFilled()) {
            Toast.makeText(NuevoProductoActivity.this, "categoria", Toast.LENGTH_SHORT).show();
            String categoria = etProductCategory.getText().toString();
            Toast.makeText(NuevoProductoActivity.this, "nombre", Toast.LENGTH_SHORT).show();
            String nombre= etProductName.getText().toString();
            Toast.makeText(NuevoProductoActivity.this, "cantidad", Toast.LENGTH_SHORT).show();
            int cantidad = Integer.parseInt(etProductAmount.getText().toString());

            Producto productoNuevo = new Producto(categoria,nombre,cantidad," ",0);
            sistema.ingresarProducto(productoNuevo);
            Toast.makeText(NuevoProductoActivity.this, "Producto " + nombre + " ingresado.", Toast.LENGTH_SHORT).show();
        } else {
            // Mostrar un mensaje de error
            Toast.makeText(NuevoProductoActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
        }


    }
    private boolean areAllFieldsFilled() {
        return !etProductName.getText().toString().trim().isEmpty()
                && !etProductCategory.getText().toString().trim().isEmpty()
                && !etProductAmount.getText().toString().trim().isEmpty();
    }

}