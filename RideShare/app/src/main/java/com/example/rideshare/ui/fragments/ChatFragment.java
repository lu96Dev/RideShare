package com.example.rideshare.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.model.Mensaje;
import com.example.rideshare.ui.adapters.MensajeAdapter;
import com.example.rideshare.R;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private ImageView imgUsuario;
    private TextView nombreUsuario;
    private RecyclerView recyclerMensajes;
    private EditText editMensaje;
    private Button btnEnviar;

    private ArrayList<Mensaje> listaMensajes;
    private MensajeAdapter adapter;

    private String usuarioActual = "Carlos";
    private String receptor = "Ana";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        imgUsuario = view.findViewById(R.id.imgUsuario);
        nombreUsuario = view.findViewById(R.id.nombreUsuario);
        recyclerMensajes = view.findViewById(R.id.recyclerMensajes);
        editMensaje = view.findViewById(R.id.editMensaje);
        btnEnviar = view.findViewById(R.id.btnEnviar);

        nombreUsuario.setText(receptor);

        listaMensajes = new ArrayList<>();
        adapter = new MensajeAdapter(inflater, listaMensajes, usuarioActual);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setStackFromEnd(true);
        recyclerMensajes.setLayoutManager(manager);
        recyclerMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(v -> {
            String texto = editMensaje.getText() != null ? editMensaje.getText().toString().trim() : "";
            if (!texto.isEmpty()) {
                Mensaje m = new Mensaje(texto, usuarioActual, receptor);
                listaMensajes.add(m);
                adapter.notifyItemInserted(listaMensajes.size() - 1);
                recyclerMensajes.smoothScrollToPosition(listaMensajes.size() - 1);
                editMensaje.setText("");
            }
        });

        return view;
    }
}