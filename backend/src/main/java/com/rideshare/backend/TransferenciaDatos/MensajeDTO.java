package com.rideshare.backend.TransferenciaDatos;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MensajeDTO {
    private Integer id;
    private Integer chatId;
    private Integer remitenteId;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private Boolean leido;
}