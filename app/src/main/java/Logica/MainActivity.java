package Logica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.sistema_inventario_ingeso.R;
import java.io.Serializable;

import Actividades.ListaMateriasPrimasActivity;
import Actividades.ListaProductosActivity;


public class MainActivity extends AppCompatActivity implements Serializable {

    SistemaProductos sistemaProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sistemaProductos = SistemaProductosImpl.getInstance();
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


}