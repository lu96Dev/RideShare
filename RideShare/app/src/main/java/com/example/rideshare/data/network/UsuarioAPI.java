package com.example.rideshare.data.network;

import com.example.rideshare.data.dto.RespuestaInicio;
import com.example.rideshare.data.dto.SolicitudActualizacion;
import com.example.rideshare.data.dto.SolicitudCambioPassword;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioAPI {
    // Apunta al @PutMapping("/{id}") de tu backend

    @GET("api/usuarios/{id}")
    Call<RespuestaInicio> obtenerUsuario(@Path("id") Integer id);
    @PUT("api/usuarios/{id}")
    Call<RespuestaInicio> actualizarPerfil(@Path("id") Integer id, @Body SolicitudActualizacion solicitud);

    @PUT("api/usuarios/{id}/password")
    Call<RespuestaInicio> cambiarPassword(@Path("id") Integer id, @Body SolicitudCambioPassword solicitud);


}