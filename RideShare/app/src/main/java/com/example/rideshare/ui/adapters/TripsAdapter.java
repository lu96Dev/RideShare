package com.example.rideshare.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.data.network.ChatAPI;
import com.example.rideshare.data.network.RetrofitCliente;
import com.example.rideshare.model.Chat;
import com.example.rideshare.model.ChatRequest;
import com.example.rideshare.model.Trip;
import com.example.rideshare.ui.activities.ChatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        String nombreCompleto = (
                (viajeActual.getNombre() != null ? viajeActual.getNombre() : "") + " " +
                        (viajeActual.getApellidos() != null ? viajeActual.getApellidos() : "")
        ).trim();

        holder.tvNombre.setText(nombreCompleto.isEmpty() ? "Anónimo" : nombreCompleto);

        String desc = viajeActual.getDescripcion();
        holder.tvDescripcion.setVisibility(
                (desc == null || desc.trim().isEmpty() || desc.equalsIgnoreCase("null"))
                        ? View.GONE
                        : View.VISIBLE
        );
        holder.tvDescripcion.setText(desc != null ? desc : "");

        holder.tvTiempo.setText(viajeActual.getTiempo() != null ? viajeActual.getTiempo() : "");

        holder.tvHora.setText(
                viajeActual.getHora() != null
                        ? "Salida a las " + viajeActual.getHora()
                        : ""
        );

        holder.tvDistancia.setText(
                viajeActual.getDistancia() != null
                        ? "A " + viajeActual.getDistancia() + " km de ti"
                        : ""
        );

        holder.ivChat.setOnClickListener(v -> {

            Context context = v.getContext();

            int usuarioActualId = context
                    .getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE)
                    .getInt("id_usuario", -1);

            int otroUsuarioId = viajeActual.getConductorId();

            if (usuarioActualId == -1 || otroUsuarioId == -1) {
                Toast.makeText(context, "Error de usuario", Toast.LENGTH_SHORT).show();
                return;
            }

            ChatAPI api = RetrofitCliente.getClient().create(ChatAPI.class);

            ChatRequest request = new ChatRequest();
            request.setTrayectoId(viajeActual.getId());
            request.setUsuario1Id(usuarioActualId);
            request.setUsuario2Id(otroUsuarioId);

            // 🔥 IMPORTANTE: evitar clicks múltiples (bug típico)
            holder.ivChat.setEnabled(false);

            api.crearOCrearChat(request).enqueue(new Callback<Chat>() {

                @Override
                public void onResponse(Call<Chat> call, Response<Chat> response) {

                    holder.ivChat.setEnabled(true);

                    if (response.isSuccessful() && response.body() != null) {

                        Chat chat = response.body();

                        Intent intent = new Intent(context, ChatActivity.class);
                        intent.putExtra("chatId", chat.getId());
                        intent.putExtra("otroUsuarioId", otroUsuarioId);

                        context.startActivity(intent);

                    } else {
                        Toast.makeText(context,
                                "Error creando chat (" + response.code() + ")",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Chat> call, Throwable t) {

                    holder.ivChat.setEnabled(true);

                    Toast.makeText(context,
                            "Error de red creando chat",
                            Toast.LENGTH_SHORT).show();
                }
            });
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