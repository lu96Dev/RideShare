package com.example.rideshare.network;

import com.example.rideshare.dto.SolicitudInicio;
import com.example.rideshare.dto.SolicitudRegistro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("auth/inicio")
    Call<Void> Inicio(@Body SolicitudInicio solicitudInicio);

    @POST("auth/registro")
    Call<Void> Registro(@Body SolicitudRegistro solicitudRegistro);
}
