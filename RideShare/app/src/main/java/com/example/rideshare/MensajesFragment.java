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

public class MensajesFragment extends Fragment {

    private RecyclerView recyclerMensajes;
    private MensajePreviewAdapter adapter;
    private ArrayList<MensajePreview> listaMensajes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mensajes, container, false);

        recyclerMensajes = view.findViewById(R.id.recyclerConversaciones);

        listaMensajes = new ArrayList<>();
        /*
        listaMensajes.add(new MensajePreview("starryskies23","Cuando vuelves?","1d", R.drawable.usuario1,true));
        listaMensajes.add(new MensajePreview("nebulanomad","Interesante","1d", R.drawable.usuario2,true));
        listaMensajes.add(new MensajePreview("embereco","Estoy de baja","2d", R.drawable.usuario3,false));
        listaMensajes.add(new MensajePreview("lunavoyager","Donde te recojo?","3d", R.drawable.usuario4,false));
        listaMensajes.add(new MensajePreview("shadowlynx","Voy en septiembre. Y tú?","4d", R.drawable.usuario5,true));


         */

        adapter = new MensajePreviewAdapter(listaMensajes);

        recyclerMensajes.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMensajes.setAdapter(adapter);

        return view;
    }
}