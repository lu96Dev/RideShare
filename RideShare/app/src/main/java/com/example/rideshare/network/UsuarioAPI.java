package com.example.rideshare.network;

import com.example.rideshare.dto.RespuestaInicio;
import com.example.rideshare.dto.SolicitudActualizacion;
import com.example.rideshare.Usuario;
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
}