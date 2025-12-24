package com.rideshare.backend.Controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testEndpoint")
public class Testeo {
    @GetMapping
    public String testeo() {
        return "Funciona";
    }
}
