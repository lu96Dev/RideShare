package com.example.rideshare.ui.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rideshare.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapaPantallaCompletaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView tvCalleSeleccionada;
    private Button btnConfirmarUbicacion;

    private Double latitudElegida = null;
    private Double longitudElegida = null;
    private String calleElegida = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_pantalla_completa);

        tvCalleSeleccionada = findViewById(R.id.tvCalleSeleccionada);
        btnConfirmarUbicacion = findViewById(R.id.btnConfirmarUbicacion);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapaGigante);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btnConfirmarUbicacion.setOnClickListener(v -> {
            if (latitudElegida != null && longitudElegida != null) {
                // Preparamos los datos para enviarlos de vuelta a la pantalla de Publicar
                Intent resultado = new Intent();
                resultado.putExtra("LATITUD", latitudElegida);
                resultado.putExtra("LONGITUD", longitudElegida);
                resultado.putExtra("CALLE", calleElegida);
                setResult(RESULT_OK, resultado);
                finish(); // Cerramos el mapa y volvemos
            } else {
                Toast.makeText(this, "Por favor, toca el mapa primero", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // ACTIVAR LOS BOTONES DE ZOOM (+ y -) EN LA PANTALLA
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // --- SOLUCIÓN: MOVER LOS CONTROLES DEL MAPA HACIA ARRIBA ---
        // Convertimos 90dp (lo que ocupa tu botón + margen) a píxeles de la pantalla
        int paddingBottom = (int) (90 * getResources().getDisplayMetrics().density);
        // Le aplicamos el relleno al mapa (Izquierda, Arriba, Derecha, Abajo)
        mMap.setPadding(0, 0, 0, paddingBottom);
        // -----------------------------------------------------------

        // Centrar en Sevilla
        LatLng sevilla = new LatLng(37.3891, -5.9845);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sevilla, 13f));

        mMap.setOnMapClickListener(latLng -> {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Salida"));
            latitudElegida = latLng.latitude;
            longitudElegida = latLng.longitude;

            obtenerNombreCalle(latLng);
        });
    }

    private void obtenerNombreCalle(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> direcciones = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (direcciones != null && !direcciones.isEmpty()) {
                calleElegida = direcciones.get(0).getAddressLine(0);
                tvCalleSeleccionada.setText(calleElegida);
            } else {
                calleElegida = "Ubicación seleccionada";
                tvCalleSeleccionada.setText("Coordenadas guardadas");
            }
        } catch (Exception e) {
            calleElegida = "Ubicación seleccionada";
            tvCalleSeleccionada.setText("Ubicación guardada");
        }
    }
}