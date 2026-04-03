package com.example.rideshare;

public class MensajePreview {

    private String usuario;
    private String mensajeUltimo;
    private String tiempo;
    private int fotoResId;
    private boolean noLeido;

    public MensajePreview() { }

    public MensajePreview(String usuario, String mensajeUltimo, String tiempo, int fotoResId, boolean noLeido) {
        this.usuario = usuario;
        this.mensajeUltimo = mensajeUltimo;
        this.tiempo = tiempo;
        this.fotoResId = fotoResId;
        this.noLeido = noLeido;
    }

    public String getUsuario() { return usuario; }
    public String getMensajeUltimo() { return mensajeUltimo; }
    public String getTiempo() { return tiempo; }
    public int getFotoResId() { return fotoResId; }
    public boolean isNoLeido() { return noLeido; }

    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setMensajeUltimo(String mensajeUltimo) { this.mensajeUltimo = mensajeUltimo; }
    public void setTiempo(String tiempo) { this.tiempo = tiempo; }
    public void setFotoResId(int fotoResId) { this.fotoResId = fotoResId; }
    public void setNoLeido(boolean noLeido) { this.noLeido = noLeido; }
}