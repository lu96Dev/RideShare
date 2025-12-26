package com.rideshare.backend.Entidades;

public class Usuario {
    int id;
    String nombre;
    String email;
    String password;
    String telefono;

    public Usuario(){

    }


    public Usuario(int id, String telefono, String password, String email, String nombre) {
        this.id = id;
        this.telefono = telefono;
        this.password = password;
        this.email = email;
        this.nombre = nombre;
    }

    public void registrarse(){

    }

    public void publicarTrayecto(){

    }

    public void participarChat(){

    }

    public void borrarUsuario(){

    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
