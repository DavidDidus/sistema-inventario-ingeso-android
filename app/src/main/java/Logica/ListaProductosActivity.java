package Logica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sistema_inventario_ingeso.R;

public class ListaProductosActivity extends AppCompatActivity {
    Sistema sistema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        sistema = (Sistema) getIntent().getSerializableExtra("sistema");

    }
}