package com.example.rideshare.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.model.Mensaje;
import com.example.rideshare.R;

import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int YO = 0;
    private static final int OTRO = 1;

    private LayoutInflater inflater;
    private List<Mensaje> mensajes;
    private String usuarioActual;

    public MensajeAdapter(LayoutInflater inflater, List<Mensaje> mensajes, String usuarioActual) {
        this.inflater = inflater;
        this.mensajes = mensajes;
        this.usuarioActual = usuarioActual;
    }

    @Override
    public int getItemViewType(int position) {
        Mensaje m = mensajes.get(position);
        if (m.getUsuario().equals(usuarioActual)) {
            return YO;
        } else {
            return OTRO;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == YO) {
            View view = inflater.inflate(R.layout.item_mensaje_yo, parent, false);
            return new YoViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_mensaje_otro, parent, false);
            return new OtroViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Mensaje mensaje = mensajes.get(position);
        if (holder instanceof YoViewHolder) {
            ((YoViewHolder) holder).tvMensajeYo.setText(mensaje.getTexto());
        } else {
            ((OtroViewHolder) holder).tvMensajeOtro.setText(mensaje.getTexto());
        }
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    static class YoViewHolder extends RecyclerView.ViewHolder {
        TextView tvMensajeYo;
        YoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensajeYo = itemView.findViewById(R.id.tvMensajeYo);
        }
    }

    static class OtroViewHolder extends RecyclerView.ViewHolder {
        TextView tvMensajeOtro;
        OtroViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensajeOtro = itemView.findViewById(R.id.tvMensajeOtro);
        }
    }
}