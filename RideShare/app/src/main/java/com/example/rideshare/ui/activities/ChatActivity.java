package com.example.rideshare.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rideshare.R;
import com.example.rideshare.data.network.MensajeAPI;
import com.example.rideshare.data.network.RetrofitCliente;
import com.example.rideshare.model.Mensaje;
import com.example.rideshare.ui.adapters.MensajeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private EditText input;
    private Button btnSend;
    private TextView titulo;

    private MensajeAPI api;
    private MensajeAdapter adapter;
    private ArrayList<Mensaje> mensajes = new ArrayList<>();

    private int usuarioId;
    private int chatId;
    private int otroUsuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recycler = findViewById(R.id.recyclerMensajes);
        input = findViewById(R.id.editMensaje);
        btnSend = findViewById(R.id.btnEnviar);
        titulo = findViewById(R.id.nombreUsuario);

        SharedPreferences prefs = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("id_usuario", -1);

        chatId = getIntent().getIntExtra("chatId", -1);
        otroUsuarioId = getIntent().getIntExtra("otroUsuarioId", -1);

        if (chatId == -1) {
            Toast.makeText(this, "Chat inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        api = RetrofitCliente.getClient().create(MensajeAPI.class);

        adapter = new MensajeAdapter(getLayoutInflater(), mensajes, usuarioId);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);

        recycler.setLayoutManager(lm);
        recycler.setAdapter(adapter);

        cargarMensajes();
        marcarLeidos();

        btnSend.setOnClickListener(v -> enviarMensaje());
    }

    private void cargarMensajes() {

        api.obtenerMensajes(chatId, null).enqueue(new Callback<List<Mensaje>>() {
            @Override
            public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    mensajes.clear();
                    mensajes.addAll(response.body());

                    adapter.notifyDataSetChanged();

                    if (!mensajes.isEmpty()) {
                        recycler.scrollToPosition(mensajes.size() - 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable t) {
                Toast.makeText(ChatActivity.this,
                        "Error cargando mensajes",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarMensaje() {

        String texto = input.getText().toString().trim();
        if (texto.isEmpty()) return;

        Mensaje m = new Mensaje();
        m.setChatId(chatId);
        m.setRemitenteId(usuarioId);
        m.setContenido(texto);

        api.enviarMensaje(m).enqueue(new Callback<Mensaje>() {
            @Override
            public void onResponse(Call<Mensaje> call, Response<Mensaje> response) {

                if (response.isSuccessful() && response.body() != null) {

                    mensajes.add(response.body());
                    adapter.notifyItemInserted(mensajes.size() - 1);

                    recycler.scrollToPosition(mensajes.size() - 1);

                    input.setText("");
                }
            }

            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                Toast.makeText(ChatActivity.this,
                        "Error enviando mensaje",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void marcarLeidos() {

        api.marcarLeidos(chatId, usuarioId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }
}