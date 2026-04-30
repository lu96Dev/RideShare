package com.example.rideshare.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.model.Mensaje;

import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int YO   = 0;
    private static final int OTRO = 1;

    private final LayoutInflater inflater;
    private final List<Mensaje> mensajes;
    private final int usuarioActualId;

    public MensajeAdapter(LayoutInflater inflater, List<Mensaje> mensajes, int usuarioActualId) {
        this.inflater = inflater;
        this.mensajes = mensajes;
        this.usuarioActualId = usuarioActualId;
    }

    @Override
    public int getItemViewType(int position) {
        Mensaje m = mensajes.get(position);
        if (m.getRemitenteId() != null && m.getRemitenteId() == usuarioActualId) {
            return YO;
        }
        return OTRO;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == YO) {
            View v = inflater.inflate(R.layout.item_mensaje_yo, parent, false);
            return new YoViewHolder(v);
        } else {
            View v = inflater.inflate(R.layout.item_mensaje_otro, parent, false);
            return new OtroViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Mensaje mensaje = mensajes.get(position);
        String hora = formatearHora(mensaje.getFechaEnvio());

        if (holder instanceof YoViewHolder) {
            YoViewHolder h = (YoViewHolder) holder;
            h.tvMensaje.setText(mensaje.getContenido());
            h.tvHora.setText(hora);
        } else {
            OtroViewHolder h = (OtroViewHolder) holder;
            h.tvMensaje.setText(mensaje.getContenido());
            h.tvHora.setText(hora);
        }
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    // "2024-01-15T14:32:00" → "14:32"
    private String formatearHora(String fechaEnvio) {
        if (fechaEnvio == null || fechaEnvio.length() < 16) return "";
        try {
            String timePart = fechaEnvio.contains("T")
                    ? fechaEnvio.split("T")[1]
                    : fechaEnvio;
            return timePart.substring(0, 5);
        } catch (Exception e) {
            return "";
        }
    }

    static class YoViewHolder extends RecyclerView.ViewHolder {
        TextView tvMensaje, tvHora;
        YoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvMensajeYo);
            tvHora    = itemView.findViewById(R.id.tvHoraYo);
        }
    }

    static class OtroViewHolder extends RecyclerView.ViewHolder {
        TextView tvMensaje, tvHora;
        OtroViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvMensajeOtro);
            tvHora    = itemView.findViewById(R.id.tvHoraOtro);
        }
    }
}