package com.example.appweb.MODELO;

import java.sql.Date;
import java.time.LocalDate;

public class Usuario {
    private int idUsuario;
    private String NombreCompletoUser;
    private String correoUser;
    private int numeroUser;
    private String nombreUser;
    private String contrasena;
    private LocalDate fechaCreacion;
    private String rol; // Campo para el rol del usuario

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreCompletoUser() {
        return NombreCompletoUser;
    }

    public void setNombreCompletoUser(String nombreCompletoUser) {
        NombreCompletoUser = nombreCompletoUser;
    }

    public String getCorreoUser() {
        return correoUser;
    }

    public void setCorreoUser(String correoUser) {
        this.correoUser = correoUser;
    }

    public int getNumeroUser() {
        return numeroUser;
    }

    public void setNumeroUser(int numeroUser) {
        this.numeroUser = numeroUser;
    }
    
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
