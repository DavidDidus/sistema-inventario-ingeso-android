package Utils;

import Dominio.Producto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistema_inventario_ingeso.R;

import java.util.ArrayList;


public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ProductoViewHolder> {
    ArrayList<Producto> listaProductos;
    ArrayList<Producto> listaOriginal;

    public AdaptadorProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaProductos);
    }


    @NonNull
    @Override
    public AdaptadorProductos.ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ver_productos,null,false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        holder.viewNombre.setText(listaProductos.get(position).getNombre());
        holder.viewCantidad.setText(String.valueOf(listaProductos.get(position).getCantidad()) + " unidades");
        holder.viewCategoria.setText(listaProductos.get(position).getCategoria());
        holder.viewValor.setText("$" + String.valueOf(listaProductos.get(position).getValor()));

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre,viewCantidad,viewCategoria,viewUnidad,viewValor;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewCantidad = itemView.findViewById(R.id.viewCantidad);
            viewCategoria = itemView.findViewById(R.id.viewCategoria);
            viewValor = itemView.findViewById(R.id.viewValor);

        }
    }

}
