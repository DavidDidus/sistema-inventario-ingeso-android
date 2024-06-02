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
    public void pressEdit(View v){
        String nuevoNombre = etProductName.getText().toString().trim();
        String nuevaCategoria = etProductCategory.getText().toString().trim();
        int nuevaCantidad;

        try {
            nuevaCantidad = Integer.parseInt(etProductAmount.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Cantidad inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!nuevoNombre.isEmpty()) {
            producto.setNombre(nuevoNombre);
        }
        if (!nuevaCategoria.isEmpty()) {
            producto.setCategoria(nuevaCategoria);
        }
        producto.setCantidad(nuevaCantidad);

        boolean resultado = sistema.editarProducto(producto);

        if (resultado) {
            Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
            etProductName.setText(producto.getNombre());
            etProductCategory.setText(producto.getCategoria());
            etProductAmount.setText(producto.getCantidad());
            finish(); // Cierra la actividad después de la edición exitosa
        } else {
            Toast.makeText(this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
        }
    }


}
