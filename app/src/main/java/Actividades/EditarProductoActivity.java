package Actividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sistema_inventario_ingeso.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Dominio.MateriaPrima;
import Dominio.Producto;
import Logica.SistemaFacade;
import Logica.SistemaFacadeImpl;

public class EditarProductoActivity extends AppCompatActivity{

    private EditText etProductName, etProductCategory, etProductAmount;
    private FloatingActionButton fabEdit,fabDelete;
    private static Producto producto;
    private SistemaFacade sistema;
    private Button guardarButton;
    private Boolean cantedit;
    private static final int REQUEST_CODE_SELECCIONAR_MATERIAS_PRIMAS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);

        etProductName = findViewById(R.id.etProductName);
        etProductCategory = findViewById(R.id.etProductCategory);
        etProductAmount = findViewById(R.id.etProductAmount);

        sistema = SistemaFacadeImpl.getInstancia(getApplicationContext());

        fabEdit = findViewById(R.id.fabEdit);
        fabEdit.setVisibility(View.INVISIBLE);

        fabDelete = findViewById(R.id.fabDelete);
        fabDelete.setVisibility(View.INVISIBLE);

        guardarButton = findViewById(R.id.EditButton);
        guardarButton.setVisibility(View.VISIBLE);
        guardarButton.setBackgroundColor(Color.rgb(102, 187, 106));

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

        if(!etProductAmount.getText().toString().equals("")){
            nuevaCantidad = Integer.parseInt(etProductAmount.getText().toString().trim());
            cantedit = true;
        }else{
            nuevaCantidad = producto.getCantidad();
            cantedit = false;
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
        if(cantedit){
            Intent intent = new Intent(this, SeleccionarMateriasPrimasActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SELECCIONAR_MATERIAS_PRIMAS);

        }else{
            editar();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECCIONAR_MATERIAS_PRIMAS && resultCode == RESULT_OK && data != null) {
            ArrayList<MateriaPrima> materiasPrimasSeleccionadas = (ArrayList<MateriaPrima>) data.getSerializableExtra("materiasPrimasSeleccionadas");
            // Descontar las materias primas seleccionadas
            for (MateriaPrima materiaPrimaSeleccionada : materiasPrimasSeleccionadas) {
                for (MateriaPrima materiaPrima : sistema.getListaMateriaPrima()) {
                    if (materiaPrima.getNombre().equals(materiaPrimaSeleccionada.getNombre())) {
                        double nuevaCantidad = materiaPrima.getCantidad() - materiaPrimaSeleccionada.getCantidad();
                        materiaPrima.setCantidad(nuevaCantidad);
                    }
                }
            }

            editar();
        }
    }
    private void editar(){
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
