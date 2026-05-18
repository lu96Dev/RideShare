package com.example.rideshare.ui.fragments;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rideshare.ui.activities.MapaPantallaCompletaActivity;
import com.example.rideshare.R;
import com.example.rideshare.data.dto.RespuestaTrayecto;
import com.example.rideshare.data.dto.SolicitudTrayecto;
import com.example.rideshare.data.network.RetrofitCliente;
import com.example.rideshare.data.network.TrayectoAPI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicationFragment extends Fragment {

    private TextView tvDireccion;
    private EditText etHora, etComentario;
    private Button btnPublicar;

    private String strOrigen = "";
    private Double origenLat = null;
    private Double origenLng = null;

    // Recibe los datos de vuelta cuando el usuario cierra el mapa en pantalla completa
    private final ActivityResultLauncher<Intent> abrirMapaPantallaCompleta = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    origenLat = data.getDoubleExtra("LATITUD", 0);
                    origenLng = data.getDoubleExtra("LONGITUD", 0);
                    strOrigen = data.getStringExtra("CALLE");

                    // Mostramos la calle elegida en el cuadradito
                    tvDireccion.setText(strOrigen);
                }
            });

    public PublicationFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publication, container, false);

        tvDireccion = view.findViewById(R.id.tvDireccion);
        etHora = view.findViewById(R.id.hora);
        etComentario = view.findViewById(R.id.comentario);
        btnPublicar = view.findViewById(R.id.btnContinuar);

        // Al tocar la dirección, abrimos el mapa en grande
        tvDireccion.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MapaPantallaCompletaActivity.class);
            abrirMapaPantallaCompleta.launch(intent);
        });

        // Reloj
        etHora.setFocusable(false);
        etHora.setOnClickListener(v -> abrirSelectorDeHora());

        // Botón Publicar
        btnPublicar.setOnClickListener(v -> publicarViaje());

        return view;
    }

    private void abrirSelectorDeHora() {
        final Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view, hourOfDay, minute) -> {
                    String horaElegida = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                    etHora.setText(horaElegida);
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void publicarViaje() {
        String strHora = etHora.getText().toString().trim();
        String strComentario = etComentario.getText().toString().trim();

        if (strOrigen.isEmpty() || origenLat == null || origenLng == null) {
            Toast.makeText(getContext(), R.string.mapa_salida, Toast.LENGTH_SHORT).show();
            return;
        }
        if (strHora.isEmpty()) {
            Toast.makeText(getContext(), R.string.elige_hora, Toast.LENGTH_SHORT).show();
            return;
        }

        if (strHora.length() == 4 && strHora.charAt(1) == ':') strHora = "0" + strHora;
        if (strHora.length() == 5) strHora += ":00";

        String destinoFijo = "Centro Escolar";
        String fechaHoy = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        SolicitudTrayecto solicitud = new SolicitudTrayecto(
                strOrigen, origenLat, origenLng, destinoFijo, fechaHoy, strHora, strComentario
        );

        // OBTENER ID
        SharedPreferences preferences = requireActivity().getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
        int usuarioId = preferences.getInt("id_usuario", -1);

        if (usuarioId == -1) {
            Toast.makeText(getContext(), R.string.error_usuario, Toast.LENGTH_LONG).show();
            return;
        }

        TrayectoAPI api = RetrofitCliente.getTrayectoAPI();
        api.crearTrayecto(usuarioId, solicitud).enqueue(new Callback<RespuestaTrayecto>() {
            @Override
            public void onResponse(Call<RespuestaTrayecto> call, Response<RespuestaTrayecto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.publicar_viaje_ok, Toast.LENGTH_SHORT).show();
                    tvDireccion.setText(R.string.tocar_abrir_mapa);
                    etHora.setText("");
                    etComentario.setText("");
                    origenLat = null; origenLng = null; strOrigen = "";
                } else {
                    Toast.makeText(getContext(), R.string.error+ response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RespuestaTrayecto> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_red + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}