package Utils;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistema_inventario_ingeso.R;

import java.util.ArrayList;

import Dominio.MateriaPrima;

public class MateriasPrimasAdapter extends RecyclerView.Adapter<MateriasPrimasAdapter.MateriaPrimaViewHolder> {

    private ArrayList<MateriaPrima> listaMateriasPrimas;
    private ArrayList<MateriaPrimaViewHolder> holders = new ArrayList<>();

    public MateriasPrimasAdapter(ArrayList<MateriaPrima> listaMateriasPrimas) {
        this.listaMateriasPrimas = listaMateriasPrimas;
    }

    @NonNull
    @Override
    public MateriaPrimaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materia_prima, parent, false);
        return new MateriaPrimaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MateriaPrimaViewHolder holder, int position) {
        MateriaPrima materiaPrima = listaMateriasPrimas.get(position);
        holder.tvNombreMateriaPrima.setText(materiaPrima.getNombre());
        holder.tvCant.setText(String.valueOf(materiaPrima.getCantidad()) + " " + materiaPrima.getUnidad());
        holder.etCantidadMateriaPrima.setHint("0"); // Se puede cambiar para mostrar la cantidad actual

        // Asegurarse de que el holder se agregue a la lista de holders
        if (!holders.contains(holder)) {
            holders.add(holder);
        }
    }

    @Override
    public int getItemCount() {
        return listaMateriasPrimas.size();
    }

    public ArrayList<MateriaPrima> getMateriasPrimasSeleccionadas() {
        ArrayList<MateriaPrima> materiasPrimasSeleccionadas = new ArrayList<>();
        for (MateriaPrimaViewHolder holder : holders) {
            String cantidadStr = holder.etCantidadMateriaPrima.getText().toString().trim();
            if (!cantidadStr.isEmpty()) {
                int cantidadSeleccionada = Integer.parseInt(cantidadStr);
                if (cantidadSeleccionada > 0) {
                    MateriaPrima materiaPrima = listaMateriasPrimas.get(holder.getAdapterPosition());
                    MateriaPrima materiaPrimaSeleccionada = new MateriaPrima(0,materiaPrima.getNombre(), cantidadSeleccionada, materiaPrima.getUnidad());
                    materiasPrimasSeleccionadas.add(materiaPrimaSeleccionada);
                }
            }
        }
        return materiasPrimasSeleccionadas;
    }

    public class MateriaPrimaViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreMateriaPrima, tvCant;
        EditText etCantidadMateriaPrima;


        public MateriaPrimaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreMateriaPrima = itemView.findViewById(R.id.tvNombreMateriaPrima);
            etCantidadMateriaPrima = itemView.findViewById(R.id.etCantidadMateriaPrima);
            tvCant = itemView.findViewById(R.id.tvCant);

        }
    }
}
