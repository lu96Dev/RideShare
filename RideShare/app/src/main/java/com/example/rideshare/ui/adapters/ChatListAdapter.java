package com.example.rideshare.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.model.ChatPreview;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private List<ChatPreview> lista;
    private int usuarioId;
    private Context context;

    public ChatListAdapter(List<ChatPreview> lista, int usuarioId, Context context) {
        this.lista = lista;
        this.usuarioId = usuarioId;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChatPreview chat = lista.get(position);

        holder.nombre.setText(chat.getNombre());
        holder.mensaje.setText(chat.getUltimoMensaje());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, com.example.rideshare.ui.activities.ChatActivity.class);
            intent.putExtra("trayectoId", chat.getTrayectoId());
            intent.putExtra("otroUsuarioId", chat.getOtroUsuarioId());
            intent.putExtra("nombre", chat.getNombre());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, mensaje;

        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreChat);
            mensaje = itemView.findViewById(R.id.mensajeChat);
        }
    }
}