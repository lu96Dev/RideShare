package com.example.rideshare.model;

public class ChatPreview {

    private Integer chatId;
    private Integer trayectoId;
    private Integer otroUsuarioId;
    private String nombre;
    private String ultimoMensaje;

    // 🔥 NUEVO: mensajes no leídos
    private Long unread;

    public ChatPreview() {}

    public Integer getChatId() { return chatId; }
    public void setChatId(Integer chatId) { this.chatId = chatId; }

    public Integer getTrayectoId() { return trayectoId; }
    public void setTrayectoId(Integer trayectoId) { this.trayectoId = trayectoId; }

    public Integer getOtroUsuarioId() { return otroUsuarioId; }
    public void setOtroUsuarioId(Integer otroUsuarioId) { this.otroUsuarioId = otroUsuarioId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUltimoMensaje() { return ultimoMensaje; }
    public void setUltimoMensaje(String ultimoMensaje) { this.ultimoMensaje = ultimoMensaje; }

    // 🔥 UNREAD
    public Long getUnread() { return unread; }
    public void setUnread(Long unread) { this.unread = unread; }
}