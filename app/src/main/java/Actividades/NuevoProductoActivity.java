package Actividades;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sistema_inventario_ingeso.R;

import java.util.ArrayList;
import java.util.HashMap;

import Dominio.MateriaPrima;
import Dominio.Producto;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;

public class NuevoProductoActivity extends AppCompatActivity {

    private EditText etProductName, etProductCategory, etProductAmount;
    private SistemaFacade sistema;
    private static final int REQUEST_CODE_SELECCIONAR_MATERIAS_PRIMAS = 1;
    private static Producto productoNuevo;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_producto);

        sistema = SistemaFacadeImpl.getInstancia(getApplicationContext());

        etProductName = findViewById(R.id.etProductName);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductAmount = findViewById(R.id.etProductAmount);
        boton = findViewById(R.id.AddProducto);
        boton.setBackgroundColor(Color.rgb(102, 187, 106));
    }

    public void pressRegistro(View v) {
        if (areAllFieldsFilled()) {
            String categoria = etProductCategory.getText().toString();
            String nombre = etProductName.getText().toString();
            int cantidad = Integer.parseInt(etProductAmount.getText().toString());

            if (isProductNameUnique(nombre)) {
                productoNuevo = new Producto(sistema.getListaProducto().size(),categoria, nombre, cantidad);

                // Lanzar la actividad para seleccionar materias primas
                Intent intent = new Intent(this, SeleccionarMateriasPrimasActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECCIONAR_MATERIAS_PRIMAS);
            } else {
                Toast.makeText(NuevoProductoActivity.this, "El nombre del producto ya existe. Por favor ingresa un nombre único.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(NuevoProductoActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECCIONAR_MATERIAS_PRIMAS && resultCode == RESULT_OK && data != null) {
            if (data.getSerializableExtra("materiasPrimasSeleccionadas") instanceof ArrayList<?>) {
                ArrayList<MateriaPrima> materiasPrimasSeleccionadas = (ArrayList<MateriaPrima>) data.getSerializableExtra("materiasPrimasSeleccionadas");

                HashMap<String, MateriaPrima> mapaMateriasPrimas = new HashMap<>();
                for (MateriaPrima mp : sistema.getListaMateriaPrima()) {
                    mapaMateriasPrimas.put(mp.getNombre(), mp);
                }

                for (MateriaPrima materiaPrimaSeleccionada : materiasPrimasSeleccionadas) {
                    MateriaPrima materiaPrimaOriginal = mapaMateriasPrimas.get(materiaPrimaSeleccionada.getNombre());
                    if (materiaPrimaOriginal != null) {
                        double nuevaCantidad = materiaPrimaOriginal.getCantidad() - materiaPrimaSeleccionada.getCantidad();
                        materiaPrimaOriginal.setCantidad(nuevaCantidad);
                    }
                }

                Producto productoNuevo = new Producto(sistema.getListaProducto().size(),
                        etProductCategory.getText().toString(),
                        etProductName.getText().toString(),
                        Integer.parseInt(etProductAmount.getText().toString())
                );
                sistema.ingresarProducto(productoNuevo);

                Toast.makeText(NuevoProductoActivity.this, "Producto " + productoNuevo.getNombre() + " ingresado.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, ListaProductosActivity.class);
                startActivity(intent);
            }
        }
    }



    private boolean isProductNameUnique(String nombre) {
        for (Producto producto : sistema.getListaProducto()) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return false;
            }
        }
        return true;
    }

    private boolean areAllFieldsFilled() {
        return !etProductName.getText().toString().trim().isEmpty()
                && !etProductCategory.getText().toString().trim().isEmpty()
                && !etProductAmount.getText().toString().trim().isEmpty();
    }
}
