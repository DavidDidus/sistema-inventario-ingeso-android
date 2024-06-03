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
import Logica.Sistema;
import Logica.SistemaImpl;

public class EditarProductoActivity extends AppCompatActivity{

    private EditText etProductName, etProductCategory,etProductAmount;
    private FloatingActionButton fabEdit;
    private Producto producto;
    private Sistema sistema;
    private Button guardarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);

        etProductName = findViewById(R.id.etProductName);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductAmount = findViewById(R.id.etProductAmount);

        sistema = SistemaImpl.getInstance();

        fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setVisibility(View.INVISIBLE);

        guardarButton= findViewById(R.id.EditButton);
        guardarButton.setVisibility(View.VISIBLE);

        obtenerProducto();

        etProductName.setHint(producto.getNombre());
        etProductAmount.setHint(String.valueOf(producto.getCantidad()));
        etProductCategory.setHint(producto.getCategoria());


    }
    public int obtenerId(){
        if (getIntent().hasExtra("id")) {
            return  getIntent().getIntExtra("id", -1);

        }else{
            return -1;
        }
    }
    public void obtenerProducto(){

        int posProducto = sistema.busquedaBinariaProductos(obtenerId());
        producto = sistema.getListaProducto().get(posProducto);


    }
    public void pressEdit(View v) {
        String nuevoNombre = etProductName.getText().toString();
        String nuevaCategoria = etProductCategory.getText().toString();
        int nuevaCantidad ;

        try {
            nuevaCantidad = Integer.parseInt(etProductAmount.getText().toString().trim());
        } catch (NumberFormatException e) {
            nuevaCantidad = producto.getCantidad();
        }

        // Verificar si el nuevo nombre está vacío
        if (nuevoNombre.isEmpty()) {
            // Si el nuevo nombre está vacío, se asigna el nombre original del producto
            nuevoNombre = producto.getNombre();
        }
        // Verificar si la nueva categoría está vacía
        if (nuevaCategoria.isEmpty()) {
            // Si la nueva categoría está vacía, se asigna la categoría original del producto
            nuevaCategoria = producto.getCategoria();
        }

        // Actualizar los datos del producto
        producto.setNombre(nuevoNombre);
        producto.setCategoria(nuevaCategoria);
        producto.setCantidad(nuevaCantidad);

        // Intentar editar el producto en el sistema
        boolean resultado = sistema.editarProducto(producto);

        if (resultado) {
            // Mostrar un mensaje de éxito
            Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
            // Regresar a la actividad ListaProductosActivity
            Intent intent = new Intent(this, ListaProductosActivity.class);
            startActivity(intent);
            // Finalizar esta actividad
            finish();
        } else {
            // Mostrar un mensaje de error si no se pudo editar el producto
            Toast.makeText(this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
        }
    }


}
