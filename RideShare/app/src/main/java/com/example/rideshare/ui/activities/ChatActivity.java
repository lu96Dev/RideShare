package com.example.rideshare.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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

    private static final String TAG = "ChatActivity";

    private RecyclerView recycler;
    private EditText input;
    private ImageButton btnEnviar;

    // Header
    private TextView tvHeaderNombre;
    private TextView tvHeaderIniciales;
    private ImageView ivHeaderAvatar;

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

        // Vistas
        recycler         = findViewById(R.id.recyclerMensajes);
        input            = findViewById(R.id.editMensaje);
        btnEnviar        = findViewById(R.id.btnEnviar);
        tvHeaderNombre   = findViewById(R.id.tvHeaderNombre);
        tvHeaderIniciales = findViewById(R.id.tvHeaderIniciales);
        ivHeaderAvatar   = findViewById(R.id.ivHeaderAvatar);
        ImageButton btnBack = findViewById(R.id.btnBack);

        // Sesión
        SharedPreferences prefs = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
        usuarioId = prefs.getInt("id_usuario", -1);

        // Intent extras
        chatId        = getIntent().getIntExtra("chatId", -1);
        otroUsuarioId = getIntent().getIntExtra("otroUsuarioId", -1);
        String nombre    = getIntent().getStringExtra("nombre");
        String fotoPerfil = getIntent().getStringExtra("fotoPerfil");

        Log.d(TAG, "chatId: " + chatId + " | usuarioId: " + usuarioId);

        if (chatId == -1 || usuarioId == -1) {
            Toast.makeText(this, "Error: sesión o chat inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // — Header —
        btnBack.setOnClickListener(v -> finish());

        if (nombre == null || nombre.isEmpty()) {
            nombre = "Usuario " + otroUsuarioId;
        }
        tvHeaderNombre.setText(nombre);

        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            Bitmap bmp = base64ToBitmap(fotoPerfil);
            if (bmp != null) {
                ivHeaderAvatar.setImageBitmap(bmp);
                ivHeaderAvatar.setVisibility(View.VISIBLE);
                tvHeaderIniciales.setVisibility(View.GONE);
            } else {
                tvHeaderIniciales.setText(obtenerIniciales(nombre));
            }
        } else {
            tvHeaderIniciales.setText(obtenerIniciales(nombre));
        }

        // — RecyclerView —
        api = RetrofitCliente.getClient().create(MensajeAPI.class);
        adapter = new MensajeAdapter(getLayoutInflater(), mensajes, usuarioId);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        recycler.setLayoutManager(lm);
        recycler.setAdapter(adapter);

        cargarMensajes();
        marcarLeidos();

        // — Enviar —
        btnEnviar.setOnClickListener(v -> enviarMensaje());

        // Enviar también con el botón "Send" del teclado
        input.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEND
                    || actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                enviarMensaje();
                return true;
            }
            return false;
        });
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
                } else {
                    Log.e(TAG, "Error HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable t) {
                Log.e(TAG, "Error de red: " + t.getMessage());
                Toast.makeText(ChatActivity.this, "Error cargando mensajes", Toast.LENGTH_SHORT).show();
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
                } else {
                    Log.e(TAG, "Error al enviar: " + response.code());
                    Toast.makeText(ChatActivity.this, "Error al enviar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Mensaje> call, Throwable t) {
                Log.e(TAG, "Error de red al enviar: " + t.getMessage());
                Toast.makeText(ChatActivity.this, "Sin conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void marcarLeidos() {
        api.marcarLeidos(chatId, usuarioId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "Mensajes marcados como leídos");
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "Error marcando leídos: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarMensajes();
    }

    // — Helpers —

    private Bitmap base64ToBitmap(String base64) {
        try {
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {
            return null;
        }
    }

    private String obtenerIniciales(String nombre) {
        if (nombre == null || nombre.isEmpty()) return "?";
        String[] partes = nombre.trim().split(" ");
        if (partes.length >= 2) {
            return String.valueOf(partes[0].charAt(0)).toUpperCase()
                    + String.valueOf(partes[1].charAt(0)).toUpperCase();
        }
        return String.valueOf(partes[0].charAt(0)).toUpperCase();
    }
}