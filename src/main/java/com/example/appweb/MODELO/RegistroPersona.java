package com.example.appweb.MODELO;

import java.sql.Date;

public class RegistroPersona {
    private String rut;
    private String nombre;
    private Date fechaHora;
    private String tipoRegistro;

    public RegistroPersona() {}

    // Constructor completo
    public RegistroPersona(String rut, String nombre, Date fechaHora, String tipoRegistro) {
        this.rut = rut;
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.tipoRegistro = tipoRegistro;
    }

    // Getters y setters
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}