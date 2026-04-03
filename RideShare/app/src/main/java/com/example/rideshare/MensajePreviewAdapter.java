package com.example.rideshare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MensajePreviewAdapter extends RecyclerView.Adapter<MensajePreviewAdapter.ViewHolder> {

    private List<MensajePreview> mensajes;

    public MensajePreviewAdapter(List<MensajePreview> mensajes) {
        this.mensajes = mensajes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MensajePreview m = mensajes.get(position);
        holder.tvUsuario.setText(m.getUsuario());
        holder.tvMensajeUltimo.setText(m.getMensajeUltimo());
        holder.tvTiempo.setText(m.getTiempo());
        holder.imgUsuario.setImageResource(m.getFotoResId());
        holder.viewNoLeido.setVisibility(m.isNoLeido() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUsuario;
        TextView tvUsuario, tvMensajeUltimo, tvTiempo;
        View viewNoLeido;

        ViewHolder(View itemView) {
            super(itemView);
            imgUsuario = itemView.findViewById(R.id.imgUsuario);
            tvUsuario = itemView.findViewById(R.id.tvUsuario);
            tvMensajeUltimo = itemView.findViewById(R.id.tvMensajeUltimo);
            tvTiempo = itemView.findViewById(R.id.tvTiempo);
            viewNoLeido = itemView.findViewById(R.id.viewNoLeido);
        }
    }
}