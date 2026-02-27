package com.example.rideshare;

import android.app.AlertDialog;
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
    private TextView ResultadoBiblio;

    // TODO: Recuperar el ID real del login. Si este ID no existe en tu DB, el servidor dará error 404.
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
            actualizarBiografiaEnServidor(nuevoTexto);
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void actualizarBiografiaEnServidor(String nuevaBio) {
        SolicitudActualizacion solicitud = new SolicitudActualizacion(nuevaBio);

        RetrofitCliente.getUsuarioAPI().actualizarPerfil(idUsuarioLogueado, solicitud)
                .enqueue(new Callback<RespuestaInicio>() {
                    @Override
                    public void onResponse(Call<RespuestaInicio> call, Response<RespuestaInicio> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // ÉXITO: El servidor guardó los datos
                            actualizarInterfaz(nuevaBio);
                            Toast.makeText(getContext(), response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                        } else {
                            // ERROR DE LÓGICA: El servidor respondió pero con un error (404, 500, etc.)
                            String errorMsg = "Error del servidor (Código " + response.code() + ")";

                            // Intentamos ver si el servidor nos mandó un JSON con el mensaje de error
                            try {
                                if (response.errorBody() != null) {
                                    Log.e("API_ERROR", "Detalle: " + response.errorBody().string());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Log.e("API_ERROR", errorMsg);
                            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaInicio> call, Throwable t) {
                        // ERROR DE RED: No se pudo llegar al servidor
                        Log.e("NETWORK_ERROR", "Causa: " + t.getMessage());
                        Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarInterfaz(String texto) {
        if (texto == null || texto.isEmpty()) {
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