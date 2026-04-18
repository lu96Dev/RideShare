package com.example.rideshare.model;

public class Mensaje {

    private Integer id;
    private Integer remitenteId;
    private Integer destinatarioId;
    private Integer trayectoId;

    private String contenido;
    private String fechaEnvio;
    private Boolean leido;

    // ===== constructor vacío =====
    public Mensaje() {}

    // ===== getters & setters =====
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getRemitenteId() { return remitenteId; }
    public void setRemitenteId(Integer remitenteId) { this.remitenteId = remitenteId; }

    public Integer getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(Integer destinatarioId) { this.destinatarioId = destinatarioId; }

    public Integer getTrayectoId() { return trayectoId; }
    public void setTrayectoId(Integer trayectoId) { this.trayectoId = trayectoId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(String fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }
}
