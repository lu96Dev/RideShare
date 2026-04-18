package com.example.rideshare.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.model.Trip;
import com.example.rideshare.ui.activities.ContainerActivity;

import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViajeViewHolder> {

    private List<Trip> listaViajes;

    public TripsAdapter(List<Trip> listaViajes) {
        this.listaViajes = listaViajes;
    }

    @NonNull
    @Override
    public ViajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resultado_viaje, parent, false);
        return new ViajeViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViajeViewHolder holder, int position) {
        Trip viajeActual = listaViajes.get(position);

        // 1. CONCATENACIÓN DE NOMBRE Y APELLIDO
        String nombre = viajeActual.getNombre();
        String apellido = viajeActual.getApellidos();

        // Creamos el nombre completo. Si ambos existen, se verán "Juan Pérez"
        String nombreCompleto = (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");

        // .trim() elimina espacios sobrantes si uno de los dos falta
        String resultadoFinal = nombreCompleto.trim();

        if (resultadoFinal.isEmpty()) {
            holder.tvNombre.setText("Anónimo");
        } else {
            holder.tvNombre.setText(resultadoFinal);
        }

        // 2. DESCRIPCIÓN (Ocultar si está vacía para evitar el hueco)
        String desc = viajeActual.getDescripcion();
        if (desc == null || desc.trim().isEmpty() || desc.equalsIgnoreCase("null")) {
            holder.tvDescripcion.setVisibility(View.GONE);
        } else {
            holder.tvDescripcion.setVisibility(View.VISIBLE);
            holder.tvDescripcion.setText(desc);
        }

        // 3. RESTO DE CAMPOS (Tiempo, Hora, Distancia)
        holder.tvTiempo.setText(viajeActual.getTiempo());
        holder.tvHora.setText("Salida a las " + viajeActual.getHora());

        if (viajeActual.getDistancia() != null) {
            holder.tvDistancia.setText("A " + viajeActual.getDistancia() + " km de ti");
        }

        // 4. EVENTO CLICK CHAT
        holder.ivChat.setOnClickListener(v -> {
            if (v.getContext() instanceof ContainerActivity) {
                ((ContainerActivity) v.getContext()).irAlChat();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaViajes != null ? listaViajes.size() : 0;
    }

    public static class ViajeViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTiempo, tvDescripcion, tvHora, tvDistancia;
        ImageView ivPerfil, ivChat;

        public ViajeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreBusqueda);
            tvTiempo = itemView.findViewById(R.id.tvTiempo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionViaje);
            tvHora = itemView.findViewById(R.id.tvHoraSalida2);
            tvDistancia = itemView.findViewById(R.id.tvDistanciaKm);
            ivPerfil = itemView.findViewById(R.id.ivPerfilBusqueda);
            ivChat = itemView.findViewById(R.id.ivIconoChat);
        }
    }
}