package com.example.rideshare.network;

import com.example.rideshare.dto.SolicitudActualizacion;
import com.example.rideshare.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioAPI {
    // Apunta al @PutMapping("/{id}") de tu backend
    @PUT("api/usuarios/{id}")
    Call<Usuario> actualizarPerfil(@Path("id") Integer id, @Body SolicitudActualizacion solicitud);
}