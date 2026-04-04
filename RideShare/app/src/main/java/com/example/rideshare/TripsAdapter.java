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
        // Inflamos el layout de la tarjeta corregida
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resultado_viaje, parent, false);
        return new ViajeViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViajeViewHolder holder, int position) {
        Trips viajeActual = listaViajes.get(position);

        // Seteamos los datos en los componentes
        holder.tvNombre.setText(viajeActual.getNombre());
        holder.tvTiempo.setText(viajeActual.getTiempo());
        holder.tvDescripcion.setText(viajeActual.getDescripcion());
        holder.tvHora.setText("Salida a las " + viajeActual.getHora());
        holder.tvDistancia.setText("A " + viajeActual.getDistancia() + " km de ti");
        // Evento de clic para ir al chat
        holder.ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getContext() instanceof ContainerActivity) {
                    // Llamamos al metodo de la actividad para cambiar de pestaña
                    ((ContainerActivity) v.getContext()).irAlChat();
                }
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

            // Enlazamos con los IDs exactos del XML corregido
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