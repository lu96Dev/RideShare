package com.example.rideshare.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.model.ChatPreview;
import com.example.rideshare.ui.activities.ChatActivity;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private static final int[] AVATAR_COLORS = {
            0xFF1565C0, 0xFF2E7D32, 0xFF6A1B9A,
            0xFFAD1457, 0xFF00838F, 0xFFE65100
    };

    private final List<ChatPreview> lista;
    private final Context context;

    public ChatListAdapter(List<ChatPreview> lista, int usuarioId, Context context) {
        this.lista = lista;
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

        // Nombre
        String nombre = chat.getNombre();
        if (nombre == null || nombre.isEmpty()) {
            nombre = "Usuario " + chat.getOtroUsuarioId();
        }
        holder.tvNombre.setText(nombre);

        // Último mensaje
        String ultimo = chat.getUltimoMensaje();
        holder.tvUltimo.setText((ultimo != null && !ultimo.isEmpty()) ? ultimo : "Chat iniciado");

        // Avatar: foto o iniciales
        String foto = chat.getFotoPerfil();
        if (foto != null && !foto.isEmpty()) {
            Bitmap bmp = base64ToBitmap(foto);
            if (bmp != null) {
                holder.ivAvatar.setImageBitmap(bmp);
                holder.ivAvatar.setVisibility(View.VISIBLE);
                holder.tvIniciales.setVisibility(View.GONE);
            } else {
                mostrarIniciales(holder, nombre, position);
            }
        } else {
            mostrarIniciales(holder, nombre, position);
        }

        // Badge no leídos
        Long unread = chat.getUnreadCount();
        if (unread != null && unread > 0) {
            holder.tvBadge.setVisibility(View.VISIBLE);
            holder.tvBadge.setText(unread > 99 ? "99+" : String.valueOf(unread));
            holder.tvUltimo.setTextColor(0xFF1A1A2E);
            holder.tvUltimo.setTypeface(null, Typeface.BOLD);
        } else {
            holder.tvBadge.setVisibility(View.GONE);
            holder.tvUltimo.setTextColor(0xFF888888);
            holder.tvUltimo.setTypeface(null, Typeface.NORMAL);
        }

        // Click — pasamos nombre y foto al ChatActivity
        final String nombreFinal = nombre;
        final String fotoFinal = foto;
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ChatActivity.class);
            i.putExtra("chatId", chat.getChatId());
            i.putExtra("otroUsuarioId", chat.getOtroUsuarioId());
            i.putExtra("nombre", nombreFinal);
            i.putExtra("fotoPerfil", fotoFinal);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    private void mostrarIniciales(ViewHolder holder, String nombre, int position) {
        holder.ivAvatar.setVisibility(View.GONE);
        holder.tvIniciales.setVisibility(View.VISIBLE);
        holder.tvIniciales.setText(obtenerIniciales(nombre));
        holder.tvIniciales.getBackground().setTint(AVATAR_COLORS[position % AVATAR_COLORS.length]);
    }

    private String obtenerIniciales(String nombre) {
        if (nombre == null || nombre.isEmpty()) return "?";
        String[] partes = nombre.trim().split(" ");
        if (partes.length >= 2) {
            return String.valueOf(partes[0].charAt(0)).toUpperCase()
                    + String.valueOf(partes[1].charAt(0)).toUpperCase();
        }
        return String.valueOf(partes[0].charAt(0)).toUpperCase();
    }

    private Bitmap base64ToBitmap(String base64) {
        try {
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {
            return null;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvIniciales, tvNombre, tvUltimo, tvBadge;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar    = itemView.findViewById(R.id.ivAvatar);
            tvIniciales = itemView.findViewById(R.id.tvIniciales);
            tvNombre    = itemView.findViewById(R.id.tvNombreChat);
            tvUltimo    = itemView.findViewById(R.id.tvUltimoMensaje);
            tvBadge     = itemView.findViewById(R.id.tvBadge);
        }
    }
}