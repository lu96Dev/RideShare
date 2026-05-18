package com.example.rideshare.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.data.network.ChatAPI;
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
    private ChatAPI api;

    private ChatListAdapter.OnChatDeleteListener deleteListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerChats = view.findViewById(R.id.recyclerChats);
        txtVacio = view.findViewById(R.id.txtVacio);

        SharedPreferences prefs = getActivity()
                .getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);

        usuarioId = prefs.getInt("id_usuario", -1);

        api = RetrofitCliente.getClient().create(ChatAPI.class);

        listaChats = new ArrayList<>();

        deleteListener = (chat, position) -> {
            new androidx.appcompat.app.AlertDialog.Builder(getContext())
                    .setTitle(R.string.eliminar_chat)
                    .setMessage(R.string.eliminar_paraambos_usuarios)
                    .setPositiveButton(R.string.eliminar, (dialog, which) -> borrarChat(chat.getChatId(), position))
                    .setNegativeButton(R.string.cancelar, (dialog, which) -> adapter.notifyItemChanged(position))
                    .show();
        };

        adapter = new ChatListAdapter(listaChats, usuarioId, getContext(), (chat, position) -> {
            new androidx.appcompat.app.AlertDialog.Builder(getContext())
                    .setTitle(R.string.eliminar_chat)
                    .setMessage(R.string.eliminar_paraambos_usuarios)
                    .setPositiveButton(R.string.eliminar, (dialog, which) -> {
                        borrarChat(chat.getChatId(), position);
                    })
                    .setNegativeButton(R.string.cancelar, (dialog, which) -> {
                        // Restaurar la card visualmente si cancela
                        adapter.notifyItemChanged(position);
                    })
                    .show();
        });

        recyclerChats.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerChats.setAdapter(adapter);

        new androidx.recyclerview.widget.ItemTouchHelper(
                new androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(
                        0, // sin drag
                        androidx.recyclerview.widget.ItemTouchHelper.LEFT | androidx.recyclerview.widget.ItemTouchHelper.RIGHT
                ) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView r, @NonNull RecyclerView.ViewHolder v,
                                          @NonNull RecyclerView.ViewHolder t) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        ChatPreview chat = listaChats.get(position);
                        // Dispara el listener → muestra el diálogo
                        deleteListener.onDelete(chat, position);  // ← esto lo necesitamos guardar
                    }
                }
        ).attachToRecyclerView(recyclerChats);

        cargarChats();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarChats();
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
                txtVacio.setText(R.string.error_cargarchat);
            }
        });
    }

    private void borrarChat(int chatId, int position) {
        api.eliminarChat(chatId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    adapter.notifyDelete(position);
                    if (listaChats.isEmpty()) {
                        txtVacio.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(getContext(), R.string.error_eliminar, Toast.LENGTH_SHORT).show();
                    adapter.notifyItemChanged(position); // restaura la card
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), R.string.sin_conexion, Toast.LENGTH_SHORT).show();
                adapter.notifyItemChanged(position);
            }
        });
    }
}