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

        holder.tvNombre.setText(viajeActual.getNombre());
        holder.tvTiempo.setText(viajeActual.getTiempo());
        holder.tvDescripcion.setText(viajeActual.getDescripcion());
        holder.tvHora.setText("Salida a las " + viajeActual.getHora());

        // CONFIGURACIÓN DEL CLIC EN EL ICONO DE CHAT
        holder.ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Como estamos en un Fragmento dentro de ContainerActivity:
                if (v.getContext() instanceof ContainerActivity) {
                    ContainerActivity activity = (ContainerActivity) v.getContext();

                    // Opción A: Si tienes un metodo en la Activity para cambiar al chat
                    activity.irAlChat();

                    // Opción B: Si quieres que el TabLayout cambie automáticamente a la pestaña 3 (Chat)
                    // activity.getViewPager().setCurrentItem(2);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaViajes != null ? listaViajes.size() : 0;
    }

    public static class ViajeViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTiempo, tvDescripcion, tvHora;
        ImageView ivPerfil, ivChat;

        public ViajeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreBusqueda);
            tvTiempo = itemView.findViewById(R.id.tvTiempo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionViaje);
            tvHora = itemView.findViewById(R.id.tvHoraSalida);
            ivPerfil = itemView.findViewById(R.id.ivPerfilBusqueda);
            ivChat = itemView.findViewById(R.id.ivIconoChat);
        }

    }

}