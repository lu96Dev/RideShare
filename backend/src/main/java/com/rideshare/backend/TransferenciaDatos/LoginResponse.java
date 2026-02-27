package com.rideshare.backend.TransferenciaDatos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private Integer id;
    private String nombre;
    private String email;
    private String biografia;
    private boolean correcto;
    private String mensaje;
}