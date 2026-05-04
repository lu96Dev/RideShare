package com.example.rideshare.data.network;

import com.example.rideshare.model.Chat;
import com.example.rideshare.model.ChatRequest;
import com.example.rideshare.model.ChatPreview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ChatAPI {

    @POST("api/chats/crear")
    Call<Chat> crearOCrearChat(@Body ChatRequest dto);

    @GET("api/chats/{usuarioId}")
    Call<List<ChatPreview>> obtenerChats(@Path("usuarioId") int usuarioId);
}