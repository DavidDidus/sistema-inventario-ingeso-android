package Utils;

import Actividades.VerActivity;
import Dominio.Producto;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistema_inventario_ingeso.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ProductoViewHolder> {
    private ArrayList<Producto> listaProductos;
    private ArrayList<Producto> listaOriginal;

    public AdaptadorProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = new ArrayList<>(listaProductos);
        this.listaOriginal = new ArrayList<>(listaProductos);
    }

    public ArrayList<Producto> getProductos() {
        return listaProductos;
    }

    public void filtrar(String buscado) {
        int longitud = buscado.length();
        if (longitud == 0) {
            listaProductos.clear();
            listaProductos.addAll(listaOriginal);
        } else {
            List<Producto> coleccion;
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                coleccion = listaOriginal.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(buscado.toLowerCase()))
                        .collect(Collectors.toList());
            } else {
                coleccion = new ArrayList<>();
                for (Producto producto : listaOriginal) {
                    if (producto.getNombre().toLowerCase().contains(buscado.toLowerCase())) {
                        coleccion.add(producto);
                    }
                }
            }
            listaProductos.clear();
            listaProductos.addAll(coleccion);
        }
        notifyDataSetChanged();

    }

    // Implementa el método setProductos
    public void setProductos(ArrayList<Producto> productos) {
        this.listaProductos = productos;
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


    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre,viewCantidad,viewCategoria;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewCantidad = itemView.findViewById(R.id.viewCantidad);
            viewCategoria = itemView.findViewById(R.id.viewCategoria);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    System.out.println(getAdapterPosition());
                    intent.putExtra("datos", listaProductos.get(getAdapterPosition()));
                    System.out.println(listaProductos.get(getAdapterPosition()).getNombre());
                    context.startActivity(intent);
                }
            });
        }
    }

}
