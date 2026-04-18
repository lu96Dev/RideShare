package com.example.rideshare.network;

import com.example.rideshare.Trips; // Asegúrate de importar tu clase Trips
import com.example.rideshare.dto.RespuestaTrayecto;
import com.example.rideshare.dto.SolicitudTrayecto;
import com.example.rideshare.dto.SolicitudBusquedaTrayecto;

import com.example.rideshare.dto.RespuestaTrayecto;
import com.example.rideshare.dto.SolicitudTrayecto;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TrayectoAPI {

    @POST("api/trayectos/{usuarioId}")
    Call<RespuestaTrayecto> crearTrayecto(@Path("usuarioId") Integer usuarioId, @Body SolicitudTrayecto solicitud);

    @GET("api/trayectos")
    Call<List<RespuestaTrayecto>> listarTrayectos();

    // CORRECCIÓN: Ahora espera una LISTA directa de Trips
    @POST("api/trayectos/buscar")
    Call<List<Trips>> buscarTrayectosCercanos(@Body SolicitudBusquedaTrayecto solicitud);
}