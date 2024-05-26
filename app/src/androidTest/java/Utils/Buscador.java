package Utils;

import Dominio.Producto;
import Utils.BuscadorViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new BuscadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Buscador.BuscadorViewHolder holder, int position) {

    }
    

    @Override
    public int getItemCount() {
        return 0;
    }


}
