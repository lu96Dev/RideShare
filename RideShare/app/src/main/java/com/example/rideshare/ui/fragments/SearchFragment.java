package com.example.rideshare.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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

import com.example.rideshare.R;
import com.example.rideshare.model.Trip;
import com.example.rideshare.data.dto.SolicitudBusquedaTrayecto;
import com.example.rideshare.data.network.RetrofitCliente;
import com.example.rideshare.data.network.TrayectoAPI;
import com.example.rideshare.ui.activities.MapaPantallaCompletaActivity;
import com.example.rideshare.ui.activities.TripsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    private final ActivityResultLauncher<Intent> abrirMapaPantallaCompleta =
            registerForActivityResult(
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
            Toast.makeText(getContext(), R.string.selecciona_punto, Toast.LENGTH_SHORT).show();
            return;
        }

        SolicitudBusquedaTrayecto solicitud =
                new SolicitudBusquedaTrayecto(origenLat, origenLng, RADIO_BUSQUEDA_KM);

        TrayectoAPI api = RetrofitCliente.getTrayectoAPI();

        api.buscarTrayectosCercanos(solicitud).enqueue(new Callback<List<Trip>>() {
            @Override
            public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Trip> resultados = response.body();
                    List<Trip> filtrados = new ArrayList<>();

                    for (Trip viaje : resultados) {
                        if (viaje.getLatitud() != null && viaje.getLongitud() != null) {

                            Location miUbicacion = new Location("origen");
                            miUbicacion.setLatitude(origenLat);
                            miUbicacion.setLongitude(origenLng);

                            Location ubicacionViaje = new Location("viaje");
                            ubicacionViaje.setLatitude(viaje.getLatitud());
                            ubicacionViaje.setLongitude(viaje.getLongitud());

                            float km = miUbicacion.distanceTo(ubicacionViaje) / 1000;

                            if (km <= RADIO_BUSQUEDA_KM) {
                                viaje.setDistancia(
                                        String.format(Locale.getDefault(), "%.1f", km)
                                );
                                filtrados.add(viaje);
                            }
                        }
                    }

                    if (filtrados.isEmpty()) {
                        Toast.makeText(getContext(), R.string.no_viajes_radio, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(getContext(), TripsActivity.class);
                    intent.putExtra("TRIPS", new ArrayList<>(filtrados));
                    startActivity(intent);

                } else {
                    Toast.makeText(getContext(), R.string.no_viajes, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Trip>> call, Throwable t) {
                Log.e("API_ERROR", t != null ? t.getMessage() : "Error desconocido");
                Toast.makeText(getContext(), R.string.error_red, Toast.LENGTH_SHORT).show();
            }
        });
    }
}