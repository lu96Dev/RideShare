package com.example.rideshare.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private MensajeAdapter adapter;
    private ArrayList<Mensaje> mensajes = new ArrayList<>();

    private MensajeAPI api;
    private int usuarioId;
    private int trayectoId;
    private int otroUsuarioId;
    private String nombre;

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

        trayectoId = getIntent().getIntExtra("trayectoId", -1);
        otroUsuarioId = getIntent().getIntExtra("otroUsuarioId", -1);
        nombre = getIntent().getStringExtra("nombre");

        titulo.setText(nombre);

        api = RetrofitCliente.getClient().create(MensajeAPI.class);

        adapter = new MensajeAdapter(getLayoutInflater(), mensajes, usuarioId);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);

        recycler.setLayoutManager(lm);
        recycler.setAdapter(adapter);

        cargarMensajes();

        btnSend.setOnClickListener(v -> enviarMensaje());
    }

    private void cargarMensajes() {
        api.obtenerMensajes(trayectoId, usuarioId).enqueue(new Callback<List<Mensaje>>() {
            @Override
            public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mensajes.clear();
                    mensajes.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    recycler.scrollToPosition(mensajes.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error cargando mensajes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviarMensaje() {
        String texto = input.getText().toString().trim();
        if (texto.isEmpty()) return;

        Mensaje m = new Mensaje();
        m.setContenido(texto);
        m.setRemitenteId(usuarioId);
        m.setDestinatarioId(otroUsuarioId);
        m.setTrayectoId(trayectoId);

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
                Toast.makeText(ChatActivity.this, "Error enviando mensaje", Toast.LENGTH_SHORT).show();
            }
        });
    }
}