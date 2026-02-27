package com.example.rideshare;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.util.Log; // Añadido para debugging
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rideshare.dto.SolicitudActualizacion;
import com.example.rideshare.network.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private ImageView avatar;
    private LinearLayout btnBiblio;
    private TextView ResultadoBiblio;

    // El ID del usuario logueado.
    // TODO: Recuperar el ID real guardado durante el login (ej: de SharedPreferences)
    private Integer idUsuarioLogueado = 1;

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

        avatar = view.findViewById(R.id.imagenPerfil);
        btnBiblio = view.findViewById(R.id.btnPublicarBiblio);
        ResultadoBiblio = view.findViewById(R.id.ResultadoBiblio);

        avatar.setOnClickListener(v -> getContent.launch("image/*"));
        btnBiblio.setOnClickListener(v -> abrirDialogoTexto());
        ResultadoBiblio.setOnClickListener(v -> abrirDialogoTexto());

        return view;
    }

    private void abrirDialogoTexto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Editar Biografía");

        final EditText ventanaTexto = new EditText(getContext());
        String textoActual = ResultadoBiblio.getText().toString();

        if (!textoActual.isEmpty()) {
            ventanaTexto.setText(textoActual);
            ventanaTexto.setSelection(ventanaTexto.getText().length());
        }

        builder.setView(ventanaTexto);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nuevoTexto = ventanaTexto.getText().toString().trim();

            // Llamamos a la función que sincroniza con el Backend
            actualizarBiografiaEnServidor(nuevoTexto);
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void actualizarBiografiaEnServidor(String nuevaBio) {
        // Creamos el DTO con la información
        SolicitudActualizacion solicitud = new SolicitudActualizacion(nuevaBio);

        // Realizamos la petición usando tu RetrofitCliente
        RetrofitCliente.getUsuarioAPI().actualizarPerfil(idUsuarioLogueado, solicitud)
                .enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful()) {
                            // Si el servidor responde OK, actualizamos la UI
                            actualizarInterfaz(nuevaBio);
                            Toast.makeText(getContext(), "Perfil actualizado en la nube", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("API_ERROR", "Código de error: " + response.code());
                            Toast.makeText(getContext(), "Error al guardar en el servidor", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Log.e("NETWORK_ERROR", t.getMessage());
                        Toast.makeText(getContext(), "Sin conexión con el servidor", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarInterfaz(String texto) {
        if (texto.isEmpty()) {
            ResultadoBiblio.setText("");
            ResultadoBiblio.setVisibility(View.GONE);
            btnBiblio.setVisibility(View.VISIBLE);
        } else {
            ResultadoBiblio.setText(texto);
            ResultadoBiblio.setVisibility(View.VISIBLE);
            btnBiblio.setVisibility(View.GONE);
        }
    }
}