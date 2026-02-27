package com.rideshare.backend.repositorio;

import com.rideshare.backend.Entidades.Usuario;
import com.rideshare.backend.Repositorio.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("default")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void guardarYBuscarPorEmail() {
        Usuario usuario = Usuario.builder()
                .nombre("SOUAKINA")
                .apellidos("ABOUSAID")
                .email("12938@gmail.com")
                .password("1664")
                .build();

        usuarioRepository.save(usuario);

        assertTrue(usuarioRepository.findByEmail("test@test.com").isPresent());
    }
}
