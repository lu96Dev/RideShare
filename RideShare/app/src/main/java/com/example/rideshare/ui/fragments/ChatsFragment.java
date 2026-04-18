package com.example.rideshare.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.data.network.MensajeAPI;
import com.example.rideshare.data.network.RetrofitCliente;
import com.example.rideshare.model.ChatPreview;
import com.example.rideshare.ui.adapters.ChatListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsFragment extends Fragment {

    private RecyclerView recyclerChats;
    private TextView txtVacio;

    private List<ChatPreview> listaChats;
    private ChatListAdapter adapter;

    private int usuarioId;
    private MensajeAPI api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerChats = view.findViewById(R.id.recyclerChats);
        txtVacio = view.findViewById(R.id.txtVacio);

        SharedPreferences prefs = getActivity()
                .getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);

        usuarioId = prefs.getInt("id_usuario", -1);

        api = RetrofitCliente.getClient().create(MensajeAPI.class);

        listaChats = new ArrayList<>();
        adapter = new ChatListAdapter(listaChats, usuarioId, getContext());

        recyclerChats.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerChats.setAdapter(adapter);

        cargarChats();

        return view;
    }

    private void cargarChats() {

        api.obtenerChats(usuarioId).enqueue(new Callback<List<ChatPreview>>() {
            @Override
            public void onResponse(Call<List<ChatPreview>> call, Response<List<ChatPreview>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    listaChats.clear();
                    listaChats.addAll(response.body());

                    adapter.notifyDataSetChanged();

                    txtVacio.setVisibility(listaChats.isEmpty() ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ChatPreview>> call, Throwable t) {
                txtVacio.setVisibility(View.VISIBLE);
                txtVacio.setText("Error cargando chats");
            }
        });
    }
}