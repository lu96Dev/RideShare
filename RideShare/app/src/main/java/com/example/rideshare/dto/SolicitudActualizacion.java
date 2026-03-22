package com.example.rideshare.dto;

public class SolicitudActualizacion {
    private String nombre;
    private String apellidos;
    private String telefono;
    private String biografia;
    private String preferenciasViaje;
    private String fotoPerfil;

    // Constructor vacío
    public SolicitudActualizacion() {}

    // Constructor solo biografía (el que usas ahora)
    public SolicitudActualizacion(String biografia) {
        this.biografia = biografia;
    }

    // Constructor completo para futuro uso
    public SolicitudActualizacion(String nombre, String apellidos, String telefono,
                                  String biografia, String preferenciasViaje, String fotoPerfil) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.biografia = biografia;
        this.preferenciasViaje = preferenciasViaje;
        this.fotoPerfil = fotoPerfil;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getTelefono() { return telefono; }
    public String getBiografia() { return biografia; }
    public String getPreferenciasViaje() { return preferenciasViaje; }
    public String getFotoPerfil() { return fotoPerfil; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
    public void setPreferenciasViaje(String preferenciasViaje) { this.preferenciasViaje = preferenciasViaje; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }
}


/*
package com.example.rideshare.dto;

public class SolicitudActualizacion {
    private String biografia;

    public SolicitudActualizacion(String biografia) {
        this.biografia = biografia;
    }

    public String getBiografia() { return biografia; }
}
 */
