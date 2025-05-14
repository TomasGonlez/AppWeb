package com.example.appweb.MODELO;

import java.sql.Date;

public class RegistroPersona {
    private String rut;
    private String nombre;
    private Date fecha;
    private String tipoRegistro;

    public RegistroPersona() {}

    // Constructor completo
    public RegistroPersona(String rut, String nombre, Date fecha, String tipoRegistro) {
        this.rut = rut;
        this.nombre = nombre;
        this.fecha = fecha;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}