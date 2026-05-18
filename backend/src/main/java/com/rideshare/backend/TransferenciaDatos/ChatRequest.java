package com.rideshare.backend.TransferenciaDatos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    private Integer trayectoId;
    private Integer usuario1Id;
    private Integer usuario2Id;
}
