package Logica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.sistema_inventario_ingeso.R;

import org.json.JSONException;

import java.io.Serializable;

import Actividades.ListaMateriasPrimasActivity;
import Actividades.ListaProductosActivity;


public class MainActivity extends AppCompatActivity implements Serializable {

    SistemaFacade sistema;
    Button productos,materiaPrima;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sistema = SistemaFacadeImpl.getInstancia();
        productos = findViewById(R.id.productButton);
        materiaPrima = findViewById(R.id.Elotrobutton);

        productos.setBackgroundColor(Color.rgb(102, 187, 106));
        materiaPrima.setBackgroundColor(Color.rgb(102, 187, 106));
    }
    public void pressProductButton(View v){

        Intent intent = new Intent(this, ListaProductosActivity.class);
        startActivity(intent);


    }
    public void pressMateriasButton(View v){
        Intent intent = new Intent(this, ListaMateriasPrimasActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal,menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            sistema.guardarCambios();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}