package Utils;

import android.view.ViewGroup;
import Dominio.Producto;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Buscador extends RecyclerView.Adapter<Buscador.BuscadorViewHolder> {
    ArrayList<Producto> listaProductos;
    ArrayList<Producto> listaOriginal;

    public Buscador(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaProductos);
    }
    @NonNull
    @Override
    public BuscadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BuscadorViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
