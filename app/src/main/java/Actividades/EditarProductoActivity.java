package Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_inventario_ingeso.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Dominio.Producto;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;

public class EditarProductoActivity extends AppCompatActivity{

    private EditText etProductName, etProductCategory, etProductAmount;
    private FloatingActionButton fabEdit;
    private Producto producto;
    private SistemaFacade sistema;
    private Button guardarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);

        etProductName = findViewById(R.id.etProductName);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductAmount = findViewById(R.id.etProductAmount);

        sistema = SistemaFacadeImpl.getInstancia();

        fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setVisibility(View.INVISIBLE);

        guardarButton = findViewById(R.id.EditButton);
        guardarButton.setVisibility(View.VISIBLE);

        obtenerProducto();

        if (producto != null) {
            etProductName.setHint(producto.getNombre());
            etProductAmount.setHint(String.valueOf(producto.getCantidad()));
            etProductCategory.setHint(producto.getCategoria());
        } else {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si el producto no se encuentra
        }
    }

    public int obtenerId() {
        return getIntent().getIntExtra("id", -1);
    }

    public void obtenerProducto() {
        int idProducto = obtenerId();

        if (idProducto != -1) {
            int posProducto = sistema.busquedaBinariaProductos(idProducto);

            if (posProducto >= 0 && posProducto < sistema.getListaProducto().size()) {
                producto = sistema.getListaProducto().get(posProducto);
            } else {
                producto = null;
            }
        } else {
            producto = null;
        }
    }

    public void pressEdit(View v) {
        if (producto == null) {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        String nuevoNombre = etProductName.getText().toString();
        String nuevaCategoria = etProductCategory.getText().toString();
        int nuevaCantidad;

        try {
            nuevaCantidad = Integer.parseInt(etProductAmount.getText().toString().trim());
        } catch (NumberFormatException e) {
            nuevaCantidad = producto.getCantidad();
        }

        if (nuevoNombre.isEmpty()) {
            nuevoNombre = producto.getNombre();
        }
        if (nuevaCategoria.isEmpty()) {
            nuevaCategoria = producto.getCategoria();
        }

        producto.setNombre(nuevoNombre);
        producto.setCategoria(nuevaCategoria);
        producto.setCantidad(nuevaCantidad);

        boolean resultado = sistema.editarProducto(producto);

        if (resultado) {
            Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ListaProductosActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
        }
    }
}
