package com.example.rideshare.data.network;

import com.example.rideshare.model.Trip; // Asegúrate de importar tu clase Trips
import com.example.rideshare.data.dto.RespuestaTrayecto;
import com.example.rideshare.data.dto.SolicitudTrayecto;
import com.example.rideshare.data.dto.SolicitudBusquedaTrayecto;

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

    @POST("api/trayectos/buscar")
    Call<List<Trip>> buscarTrayectosCercanos(@Body SolicitudBusquedaTrayecto solicitud);
}