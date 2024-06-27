package Utils;

import Actividades.VerActivity;
import Actividades.VerMateriaPrimaActivity;
import Dominio.MateriaPrima;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sistema_inventario_ingeso.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorMateriaPrima extends RecyclerView.Adapter<AdaptadorMateriaPrima.MateriaPrimaViewHolder> {
    public ArrayList<MateriaPrima> listaMateriaPrima;
    ArrayList<MateriaPrima> listaOriginal;

    public AdaptadorMateriaPrima(ArrayList<MateriaPrima> listaMateriaPrima) {
        this.listaMateriaPrima = new ArrayList<>(listaMateriaPrima);
        this.listaOriginal = new ArrayList<>(listaMateriaPrima);
    }

    public void filtrar(String buscado) {
        int longitud = buscado.length();
        if (longitud == 0) {
            listaMateriaPrima.clear();
            listaMateriaPrima.addAll(listaOriginal);
        } else {
            List<MateriaPrima> coleccion;
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                coleccion = listaOriginal.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(buscado.toLowerCase()))
                        .collect(Collectors.toList());
            } else {
                coleccion = new ArrayList<>();
                for (MateriaPrima materiaPrima : listaOriginal) {
                    if (materiaPrima.getNombre().toLowerCase().contains(buscado.toLowerCase())) {
                        coleccion.add(materiaPrima);
                    }
                }
            }
            listaMateriaPrima.clear();
            listaMateriaPrima.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    public void setMateriaPrima(ArrayList<MateriaPrima> materiaPrima) {
        this.listaMateriaPrima = materiaPrima;
    }

    @NonNull
    @Override
    public AdaptadorMateriaPrima.MateriaPrimaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ver_materias_primas, null, false);
        return new MateriaPrimaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaPrimaViewHolder holder, int position) {
        holder.viewNombre.setText(listaMateriaPrima.get(position).getNombre());
        holder.viewCantidad.setText(String.valueOf(listaMateriaPrima.get(position).getCantidad()) + " " + listaMateriaPrima.get(position).getUnidad());

    }

    @Override
    public int getItemCount() {
        return listaMateriaPrima.size();
    }

    public class MateriaPrimaViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewCantidad,viewUnidad;

        public MateriaPrimaViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewCantidad = itemView.findViewById(R.id.viewCantidad);
            viewUnidad = itemView.findViewById(R.id.viewUnidad);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, VerMateriaPrimaActivity.class);
                    intent.putExtra("datos", listaMateriaPrima.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
