package com.example.rideshare.network;

import com.example.rideshare.dto.RespuestaInicio;
import com.example.rideshare.dto.RespuestaRegistro;
import com.example.rideshare.dto.SolicitudInicio;
import com.example.rideshare.dto.SolicitudRegistro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("api/auth/login")
    Call<RespuestaInicio> Inicio(@Body SolicitudInicio solicitudInicio);

    @POST("auth/registro")
    Call<RespuestaRegistro> Registro(@Body SolicitudRegistro solicitudRegistro);
}
