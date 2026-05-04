/*package com.example.rideshare.ui.fragments;

import android.os.Bundle;
import android.util.Log; // Importado para depuración
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.model.Trip;
import com.example.rideshare.ui.adapters.TripsAdapter;
import com.example.rideshare.ui.activities.ContainerActivity;

import java.util.ArrayList;
import java.util.List;

public class TripsFragment extends Fragment {

    private RecyclerView rvViajes;
    private TripsAdapter adaptador;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        // Inicializamos el RecyclerView
        rvViajes = view.findViewById(R.id.rvViajes);
        rvViajes.setLayoutManager(new LinearLayoutManager(getContext()));

        // --- LÓGICA DE DATOS REALES ---
        List<Trip> listaReal = new ArrayList<>();

        // Verificamos que la actividad sea ContainerActivity para pedirle los datos
        if (getActivity() instanceof ContainerActivity) {
            ContainerActivity activity = (ContainerActivity) getActivity();
            listaReal = activity.getListaViajesResultados();

            Log.d("TRIPS_DEBUG", "Viajes recibidos en Fragment: " + (listaReal != null ? listaReal.size() : 0));
        }

        // Si por algún motivo la lista llegara nula, inicializamos para evitar crash
        if (listaReal == null) {
            listaReal = new ArrayList<>();
        }

        // Configuramos el adaptador con la información de la base de datos
        adaptador = new TripsAdapter(listaReal);
        rvViajes.setAdapter(adaptador);

        return view;
    }
}*/