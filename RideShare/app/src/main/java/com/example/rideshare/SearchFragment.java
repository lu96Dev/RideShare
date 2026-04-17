package com.example.rideshare;
import android.app.Activity;
import android.content.Intent;
import android.location.Location; // Clase para calcular distancias
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rideshare.dto.SolicitudBusquedaTrayecto;
import com.example.rideshare.network.RetrofitCliente;
import com.example.rideshare.network.TrayectoAPI;

import java.util.List;
import java.util.Locale; // Para formatear los decimales de los KM

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private TextView tvSalidaMap;
    private Button btnContinuar;

    private String strOrigen = "";
    private Double origenLat = null;
    private Double origenLng = null;

    private final Double RADIO_BUSQUEDA_KM = 50.0;

    private final ActivityResultLauncher<Intent> abrirMapaPantallaCompleta = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    origenLat = data.getDoubleExtra("LATITUD", 0);
                    origenLng = data.getDoubleExtra("LONGITUD", 0);
                    strOrigen = data.getStringExtra("CALLE");
                    tvSalidaMap.setText(strOrigen);
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        tvSalidaMap = view.findViewById(R.id.tvSalidaMap);
        btnContinuar = view.findViewById(R.id.btnContinuar);

        tvSalidaMap.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MapaPantallaCompletaActivity.class);
            abrirMapaPantallaCompleta.launch(intent);
        });

        btnContinuar.setOnClickListener(v -> buscarViajesEnRadio());
        return view;
    }

    private void buscarViajesEnRadio() {
        if (origenLat == null || origenLng == null) {
            Toast.makeText(getContext(), "Selecciona un punto en el mapa", Toast.LENGTH_SHORT).show();
            return;
        }

        SolicitudBusquedaTrayecto solicitud = new SolicitudBusquedaTrayecto(origenLat, origenLng, RADIO_BUSQUEDA_KM);
        TrayectoAPI api = RetrofitCliente.getTrayectoAPI();

        api.buscarTrayectosCercanos(solicitud).enqueue(new Callback<List<Trips>>() {
            @Override
            public void onResponse(Call<List<Trips>> call, Response<List<Trips>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Trips> resultados = response.body();

                    // Lógica para inyectar la distancia calculada
                    for (Trips viaje : resultados) {
                        if (viaje.getLatitud() != null && viaje.getLongitud() != null) {
                            Location miUbicacion = new Location("PuntoPulsado");
                            miUbicacion.setLatitude(origenLat);
                            miUbicacion.setLongitude(origenLng);

                            Location ubicacionViaje = new Location("PuntoViaje");
                            ubicacionViaje.setLatitude(viaje.getLatitud());
                            ubicacionViaje.setLongitude(viaje.getLongitud());

                            float metros = miUbicacion.distanceTo(ubicacionViaje);
                            float km = metros / 1000;

                            // Guardamos el cálculo en el objeto para que el Adapter lo lea
                            viaje.setDistancia(String.format(Locale.getDefault(), "%.1f", km));
                        }
                    }

                    if (getActivity() instanceof ContainerActivity) {
                        ((ContainerActivity) getActivity()).mostrarResultados(resultados);
                    }
                } else {
                    Toast.makeText(getContext(), "No se encontraron viajes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Trips>> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(getContext(), "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}