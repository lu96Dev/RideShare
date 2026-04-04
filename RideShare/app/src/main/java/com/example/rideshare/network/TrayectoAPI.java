package com.example.rideshare.network;

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
}