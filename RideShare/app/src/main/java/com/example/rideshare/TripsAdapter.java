package com.example.rideshare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViajeViewHolder> {

    private List<Trips> listaViajes;

    public TripsAdapter(List<Trips> listaViajes) {
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
        Trips viajeActual = listaViajes.get(position);

        // 1. Lógica de Nombre y Apellidos
        String nombre = viajeActual.getNombre();
        String apellido = viajeActual.getApellido();

        // Si el nombre es null, mostramos un aviso para saber que el objeto conductor no llegó
        if (nombre == null || nombre.isEmpty()) {
            holder.tvNombre.setText("Sin nombre (Error Datos)");
        } else {
            holder.tvNombre.setText((nombre + " " + (apellido != null ? apellido : "")).trim());
        }

        // 2. Tiempo y Hora
        holder.tvTiempo.setText(viajeActual.getTiempo());
        holder.tvHora.setText("Salida a las " + viajeActual.getHora());

        // 3. Descripción (FORZANDO ELIMINACIÓN DE HUECO)
        String descripcion = viajeActual.getDescripcion();

        // Comprobamos si es null, vacío o literalmente la palabra "null" (a veces pasa con APIs)
        if (descripcion == null || descripcion.trim().isEmpty() || descripcion.equalsIgnoreCase("null")) {
            holder.tvDescripcion.setVisibility(View.GONE);

            // TRUCO EXTRA: Re-anclamos la distancia directamente al nombre para que suba
            // Esto solo es necesario si el ConstraintLayout se resiste
        } else {
            holder.tvDescripcion.setVisibility(View.VISIBLE);
            holder.tvDescripcion.setText(descripcion);
        }

        // 4. Distancia
        if (viajeActual.getDistancia() != null && !viajeActual.getDistancia().equals("0.0")) {
            holder.tvDistancia.setText("A " + viajeActual.getDistancia() + " km de ti");
        } else {
            holder.tvDistancia.setText("Calculando...");
        }

        // 5. Chat
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