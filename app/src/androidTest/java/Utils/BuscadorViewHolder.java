package Utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BuscadorViewHolder extends RecyclerView.ViewHolder {
    TextView nombreProducto;
    // Aquí puedes declarar otros elementos de la vista

    public BuscadorViewHolder(@NonNull View itemView) {
        super(itemView);
        nombreProducto = itemView.findViewById(R.id.nom);
        // Aquí puedes inicializar otros elementos de la vista
    }
}
