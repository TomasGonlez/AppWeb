package com.example.appweb.MODELO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Registro {
    private int idRegistro;
    private int Rut;
    private int idUsuario;
    private LocalDateTime fechaHora;
    private String tipoRegistro;

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getRut() {
        return Rut;
    }

    public void setRut(int rut) {
        Rut = rut;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}
