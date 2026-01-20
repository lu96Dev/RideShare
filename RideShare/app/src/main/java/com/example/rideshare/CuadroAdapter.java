package com.example.rideshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CuadroAdapter extends RecyclerView.Adapter<CuadroAdapter.CuadroViewHolder> {

    private Context context;
    private List<Usuario> usuarios;

    public CuadroAdapter(Context context, List<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    public static class CuadroViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre, textViewDisponibilidad, textViewHoraSalida;
        ImageView fotoUsuario;

        public CuadroViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewDisponibilidad = itemView.findViewById(R.id.textViewDisponibilidad);
            textViewHoraSalida = itemView.findViewById(R.id.textViewHoraSalida);
            fotoUsuario = itemView.findViewById(R.id.fotoUsuario);
        }
    }

    @NonNull
    @Override
    public CuadroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_cuadro, parent, false);
        return new CuadroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuadroViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.textViewNombre.setText(usuario.getNombre());
        holder.textViewDisponibilidad.setText(usuario.getDisponibilidad());
        holder.textViewHoraSalida.setText(usuario.getHoraSalida());
        holder.fotoUsuario.setImageResource(usuario.getFotoResId());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}