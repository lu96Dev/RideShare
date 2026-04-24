package com.example.rideshare.model;

public class Mensaje {

    private Integer id;
    private Integer chatId;
    private Integer remitenteId;

    private String contenido;
    private String fechaEnvio;
    private Boolean leido;

    public Mensaje() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getChatId() { return chatId; }
    public void setChatId(Integer chatId) { this.chatId = chatId; }

    public Integer getRemitenteId() { return remitenteId; }
    public void setRemitenteId(Integer remitenteId) { this.remitenteId = remitenteId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(String fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public Boolean getLeido() { return leido; }
    public void setLeido(Boolean leido) { this.leido = leido; }
}