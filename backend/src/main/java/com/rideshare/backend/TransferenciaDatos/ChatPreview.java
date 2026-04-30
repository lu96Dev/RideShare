package com.rideshare.backend.TransferenciaDatos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatPreview {

    private Integer chatId;
    private Integer trayectoId;
    private Integer otroUsuarioId;
    private String nombre;
    private String ultimoMensaje;
    private Long unreadCount;
    private String fotoPerfil;
}