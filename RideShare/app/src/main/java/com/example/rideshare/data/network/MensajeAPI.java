package com.example.rideshare.data.network;

import com.example.rideshare.model.Mensaje;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface MensajeAPI {

    @GET("api/mensajes/{chatId}")
    Call<List<Mensaje>> obtenerMensajes(@Path("chatId") int chatId,
                                        @Query("ultimoId") Integer ultimoId);

    @POST("api/mensajes")
    Call<Mensaje> enviarMensaje(@Body Mensaje mensaje);

    @PUT("api/mensajes/{chatId}/leidos/{usuarioId}")
    Call<Void> marcarLeidos(@Path("chatId") int chatId,
                            @Path("usuarioId") int usuarioId);
}