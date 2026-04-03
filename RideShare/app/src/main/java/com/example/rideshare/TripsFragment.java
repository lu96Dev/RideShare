package com.example.rideshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TripsFragment extends Fragment {

    private RecyclerView rvViajes;
    private TripsAdapter adaptador;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips, container, false);

        // CONFIGURACIÓN CRÍTICA: Buscar el RecyclerView en el layout
        rvViajes = view.findViewById(R.id.rvViajes);
        rvViajes.setLayoutManager(new LinearLayoutManager(getContext()));

        // Datos de prueba
        List<Trips> listaPrueba = new ArrayList<>();
        listaPrueba.add(new Trips("starryskies23", "1d", "No puedo desviarme.", "7:00 AM"));
        listaPrueba.add(new Trips("emberecho", "1d", "Solo los miercoles y jueves.", "7:30 AM"));
        listaPrueba.add(new Trips("Bruno", "4d", "Pongo la música fuerte.", "16:00"));

        // Relleno automático para forzar el scroll (10 viajes extra)
        for (int i = 1; i <= 10; i++) {
            listaPrueba.add(new Trips("Usuario Prueba " + i, i + "d",
                    "Aqui va una una descripción  " + i,
                    (8 + i) + ":00"));
        }

        adaptador = new TripsAdapter(listaPrueba);
        rvViajes.setAdapter(adaptador);

        return view;
    }
}