package Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistema_inventario_ingeso.R;

import Dominio.Producto;
import Logica.Sistema;

public class IngresarProductoActivity extends AppCompatActivity {

    private EditText etProductName,etProductCategory,etProductUnidad,
                     etProductPrice,etProductAmount;


    Sistema sistema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sistema =  (Sistema) getIntent().getSerializableExtra("sistema");

        setContentView(R.layout.activity_ingresar_producto);
        etProductName = findViewById(R.id.etProductName);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductAmount = findViewById(R.id.etProductAmount);
        etProductPrice = findViewById(R.id.etProductPrice);


    }
    public void pressRegistro(View v){
        if (areAllFieldsFilled()) {
            String categoria = etProductCategory.getText().toString();
            String nombre= etProductName.getText().toString();
            int cantidad = Integer.parseInt( etProductAmount.getText().toString());
            String unidad = etProductUnidad.getText().toString();
            double valor = Double.parseDouble( etProductPrice.getText().toString());;
            Producto productoNuevo = new Producto(categoria,nombre,cantidad,unidad,valor);
            sistema.ingresarProducto(productoNuevo);
            Toast.makeText(IngresarProductoActivity.this, "Producto " + nombre + " ingresado.", Toast.LENGTH_SHORT).show();
        } else {
            // Mostrar un mensaje de error
            Toast.makeText(IngresarProductoActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
        }


    }
    private boolean areAllFieldsFilled() {
        return !etProductName.getText().toString().trim().isEmpty()
                && !etProductCategory.getText().toString().trim().isEmpty()
                && !etProductAmount.getText().toString().trim().isEmpty()
                && !etProductPrice.getText().toString().trim().isEmpty()
                && !etProductUnidad.getText().toString().trim().isEmpty();
    }

}