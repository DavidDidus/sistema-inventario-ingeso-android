package Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
            String categoria = etProductCategory.getText().toString();
            String nombre = etProductName.getText().toString();
            int cantidad = Integer.parseInt(etProductAmount.getText().toString());

            // Verificar si el nombre del producto ya existe
            if (isProductNameUnique(nombre)) {
                Producto productoNuevo = new Producto(categoria, nombre, cantidad);
                sistema.ingresarProducto(productoNuevo);
                Toast.makeText(NuevoProductoActivity.this, "Producto " + nombre + " ingresado.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ListaProductosActivity.class);
                startActivity(intent);// Cierra la actividad después de la edición exitosa
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(NuevoProductoActivity.this, "El nombre del producto ya existe. Por favor ingresa un nombre único.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Mostrar un mensaje de error si no se completan todos los campos
            Toast.makeText(NuevoProductoActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
        }


    }
    private boolean isProductNameUnique(String nombre) {
        for (Producto producto : sistema.getListaProducto()) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return false; // El nombre del producto ya existe
            }
        }
        return true; // El nombre del producto es único
    }
    private boolean areAllFieldsFilled() {
        return !etProductName.getText().toString().trim().isEmpty()
                && !etProductCategory.getText().toString().trim().isEmpty()
                && !etProductAmount.getText().toString().trim().isEmpty();
    }

}