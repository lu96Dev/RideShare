package com.example.rideshare;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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

    private String calcularTiempoRelativo(String fechaStr, String horaStr) {
        try {
            LocalDate fecha = LocalDate.parse(fechaStr); // Formato esperado "2026-04-18"
            LocalTime hora = LocalTime.parse(horaStr);   // Formato esperado "17:31:00"

            LocalDateTime fechaViaje = LocalDateTime.of(fecha, hora);
            LocalDateTime ahora = LocalDateTime.now();

            // Calculamos la diferencia
            long dias = ChronoUnit.DAYS.between(fechaViaje, ahora);
            long horas = ChronoUnit.HOURS.between(fechaViaje, ahora);
            long minutos = ChronoUnit.MINUTES.between(fechaViaje, ahora);

            if (dias > 0) return dias + "d";
            if (horas > 0) return horas + "h";
            if (minutos > 0) return minutos + "m";
            return "Ahora";

        } catch (Exception e) {
            // Si hay algún error en el formato, devolvemos cadena vacía
            return "";
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViajeViewHolder holder, int position) {
        Trips viajeActual = listaViajes.get(position);

        // 1. NOMBRE Y APELLIDO
        String nombre = viajeActual.getNombre();
        String apellido = viajeActual.getApellidos(); // Usando getApellidos() como en tu código

        String nombreCompleto = (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
        String resultadoFinal = nombreCompleto.trim();

        if (resultadoFinal.isEmpty()) {
            holder.tvNombre.setText("Anónimo");
        } else {
            holder.tvNombre.setText(resultadoFinal);
        }

        // 2. TIEMPO RELATIVO (Calculado)
        String tiempoRelativo = calcularTiempoRelativo(viajeActual.getFecha(), viajeActual.getHora());
        if (!tiempoRelativo.isEmpty()) {
            holder.tvTiempo.setText(tiempoRelativo);
            holder.tvTiempo.setVisibility(View.VISIBLE);
        } else {
            holder.tvTiempo.setVisibility(View.GONE);
        }

        // 3. DESCRIPCIÓN
        String desc = viajeActual.getDescripcion();
        if (desc == null || desc.trim().isEmpty() || desc.equalsIgnoreCase("null")) {
            holder.tvDescripcion.setVisibility(View.GONE);
        } else {
            holder.tvDescripcion.setVisibility(View.VISIBLE);
            holder.tvDescripcion.setText(desc);
        }

        // 4. HORA Y DISTANCIA
        holder.tvHora.setText("Salida a las " + viajeActual.getHora());

        if (viajeActual.getDistancia() != null) {
            holder.tvDistancia.setText("A " + viajeActual.getDistancia() + " km de ti");
        } else {
            holder.tvDistancia.setText("Distancia no disponible");
        }

        // 5. EVENTO CLICK CHAT
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