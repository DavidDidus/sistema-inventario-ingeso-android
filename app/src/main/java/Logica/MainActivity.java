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
import com.google.firebase.FirebaseApp;

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
        FirebaseApp.initializeApp(this);

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
    protected void onStart() {
        super.onStart();
        sistema = SistemaFacadeImpl.getInstancia(getApplicationContext());
        sistema.obtenerProductos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sistema = SistemaFacadeImpl.getInstancia(getApplicationContext());

    }
}