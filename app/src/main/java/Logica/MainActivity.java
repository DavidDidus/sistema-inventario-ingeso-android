package Logica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sistema_inventario_ingeso.R;

import java.io.Serializable;

import Actividades.ListaMateriasPrimas;
import Actividades.ListaProductosActivity;


public class MainActivity extends AppCompatActivity implements Serializable {

    Sistema sistema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sistema = new SistemaImpl();
        Toast.makeText(this, "sis", Toast.LENGTH_SHORT).show();


    }
    public void pressProductButton(View v){

        Intent intent = new Intent(this, ListaProductosActivity.class);
        intent.putExtra("sistema", (Serializable) sistema);
        startActivity(intent);


    }
    public void pressMateriasButton(View v){
        Intent intent = new Intent(this, ListaMateriasPrimas.class);
        intent.putExtra("sistema", (Serializable) sistema);
        startActivity(intent);
    }

}