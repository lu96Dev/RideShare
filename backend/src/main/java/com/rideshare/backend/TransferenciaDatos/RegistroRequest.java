package com.rideshare.backend.TransferenciaDatos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroRequest {
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
}

