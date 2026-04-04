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

        rvViajes = view.findViewById(R.id.rvViajes);
        rvViajes.setLayoutManager(new LinearLayoutManager(getContext()));

        // Datos de prueba con el nuevo campo de distancia (km)
        List<Trips> listaPrueba = new ArrayList<>();

        // Formato: Nombre, Tiempo, Descripción, Hora, Distancia (KM)
        listaPrueba.add(new Trips("starryskies23", "1d", "No puedo desviarme.", "7:00 AM", "2.5"));
        listaPrueba.add(new Trips("emberecho", "1d", "Solo los miércoles y jueves.", "7:30 AM", "1.2"));
        listaPrueba.add(new Trips("Bruno", "4d", "Pongo la música fuerte.", "16:00", "0.5"));

        // Relleno automático con distancias aleatorias o progresivas
        for (int i = 1; i <= 10; i++) {
            listaPrueba.add(new Trips(
                    "Usuario Prueba " + i,
                    i + "d",
                    "Aquí va una descripción " + i,
                    (8 + i) + ":00",
                    (i + 0.5) + "" // Esto genera distancias como 1.5, 2.5, etc.
            ));
        }

        adaptador = new TripsAdapter(listaPrueba);
        rvViajes.setAdapter(adaptador);

        return view;
    }
}