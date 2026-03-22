package com.example.rideshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rideshare.dto.RespuestaInicio;
import com.example.rideshare.dto.SolicitudActualizacion;
import com.example.rideshare.network.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ImageView avatar;
    private LinearLayout btnBiblio;
    private TextView resultadoBiblio;

    private TextView nombreUsuarioPerfil;
    private Integer idUsuarioLogueado; // Ahora es dinámico

    private final ActivityResultLauncher<String> getContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    avatar.setImageURI(uri);
                    avatar.setPadding(0, 0, 0, 0);
                }
            });

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // --- CARGAR ID REAL ---
        SharedPreferences preferences = getActivity().getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
        idUsuarioLogueado = preferences.getInt("id_usuario", -1);
        // ----------------------

        Log.d("PROFILE_DEBUG", "ID cargado de SharedPreferences: " + idUsuarioLogueado);

        avatar = view.findViewById(R.id.imagenPerfil);
        btnBiblio = view.findViewById(R.id.btnPublicarBiblio);
        resultadoBiblio = view.findViewById(R.id.ResultadoBiblio);
        nombreUsuarioPerfil = view.findViewById(R.id.nombreUsuario);

        avatar.setOnClickListener(v -> getContent.launch("image/*"));
        btnBiblio.setOnClickListener(v -> abrirDialogoTexto());
        resultadoBiblio.setOnClickListener(v -> abrirDialogoTexto());

        cargarDatosUsuario();

        return view;
    }

    private void cargarDatosUsuario() {
        if (idUsuarioLogueado == -1) {
            return;
        }

        RetrofitCliente.getUsuarioAPI().obtenerUsuario(idUsuarioLogueado)
                .enqueue(new Callback<RespuestaInicio>() {
                    @Override
                    public void onResponse(Call<RespuestaInicio> call, Response<RespuestaInicio> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            RespuestaInicio usuario = response.body();

                            // Mostrar biografía si existe
                            actualizarInterfaz(usuario.getBiografia());
                            nombreUsuarioPerfil.setText(usuario.getNombre());

                            // Aquí puedes cargar más campos en el futuro:
                            // nombreTextView.setText(usuario.getNombre());
                            // etc.
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaInicio> call, Throwable t) {
                        Log.e("PROFILE_ERROR", "Error al cargar perfil: " + t.getMessage());
                    }
                });
    }

    private void abrirDialogoTexto() {
        if (idUsuarioLogueado == -1) {
            Toast.makeText(getContext(), "Error: Sesión no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Editar Biografía");

        final EditText ventanaTexto = new EditText(getContext());
        String textoActual = resultadoBiblio.getText().toString();

        if (!textoActual.isEmpty()) {
            ventanaTexto.setText(textoActual);
            ventanaTexto.setSelection(ventanaTexto.getText().length());
        }

        builder.setView(ventanaTexto);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nuevoTexto = ventanaTexto.getText().toString().trim();
            actualizarBiografiaEnServidor(nuevoTexto);
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void actualizarBiografiaEnServidor(String nuevaBio) {
        SolicitudActualizacion solicitud = new SolicitudActualizacion(nuevaBio);

        Log.d("API_DEBUG", "Enviando a ID: " + idUsuarioLogueado);
        Log.d("API_DEBUG", "Biografía: " + nuevaBio);

        RetrofitCliente.getUsuarioAPI().actualizarPerfil(idUsuarioLogueado, solicitud)
                .enqueue(new Callback<RespuestaInicio>() {
                    @Override
                    public void onResponse(Call<RespuestaInicio> call, Response<RespuestaInicio> response) {
                        Log.d("API_DEBUG", "onResponse llamado");
                        Log.d("API_DEBUG", "Código respuesta: " + response.code());

                        if (response.isSuccessful() && response.body() != null) {
                            actualizarInterfaz(nuevaBio);
                            Toast.makeText(getContext(), response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("API_ERROR", "Código: " + response.code());
                            Toast.makeText(getContext(), "Error al guardar: El ID " + idUsuarioLogueado + " no existe", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaInicio> call, Throwable t) {
                        Log.e("NETWORK_ERROR", "Causa: " + t.getMessage());
                        Toast.makeText(getContext(), "Sin conexión", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarInterfaz(String texto) {
        if (texto == null || texto.isEmpty()) {
            resultadoBiblio.setText("");
            resultadoBiblio.setVisibility(View.GONE);
            btnBiblio.setVisibility(View.VISIBLE);
        } else {
            resultadoBiblio.setText(texto);
            resultadoBiblio.setVisibility(View.VISIBLE);
            btnBiblio.setVisibility(View.GONE);
        }
    }
}