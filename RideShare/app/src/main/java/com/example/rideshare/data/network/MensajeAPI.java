package com.example.rideshare.data.network;

import com.example.rideshare.model.ChatPreview;
import com.example.rideshare.model.Mensaje;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface MensajeAPI {

    // LISTA DE CHATS
    @GET("api/mensajes/chats/{usuarioId}")
    Call<List<ChatPreview>> obtenerChats(@Path("usuarioId") int usuarioId);

    // MENSAJES DE UN CHAT
    @GET("api/mensajes/{trayectoId}/{usuarioId}")
    Call<List<Mensaje>> obtenerMensajes(
            @Path("trayectoId") int trayectoId,
            @Path("usuarioId") int usuarioId
    );

    // ENVIAR MENSAJE
    @POST("api/mensajes")
    Call<Mensaje> enviarMensaje(@Body Mensaje mensaje);
}