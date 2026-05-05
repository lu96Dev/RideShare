package com.example.rideshare.data.network;

import com.example.rideshare.data.dto.RespuestaInicio;
import com.example.rideshare.data.dto.RespuestaRegistro;
import com.example.rideshare.data.dto.SolicitudInicio;
import com.example.rideshare.data.dto.SolicitudRegistro;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("api/auth/login")
    Call<RespuestaInicio> Inicio(@Body SolicitudInicio solicitudInicio);

    @POST("api/auth/registro")
    Call<RespuestaRegistro> Registro(@Body SolicitudRegistro solicitudRegistro);
}
