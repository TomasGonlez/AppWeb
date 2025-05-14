package com.example.appweb.MODELO;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class RegistroPersona {
    private String rut;
    private String nombre;
    private Date fecha;
    private String tipoRegistro;
    private String hora;

    public RegistroPersona() {}

    // Constructor completo
    public RegistroPersona(String rut, String nombre, Date fecha, String tipoRegistro, String hora) {
        this.rut = rut;
        this.nombre = nombre;
        this.fecha = fecha;
        this.tipoRegistro = tipoRegistro;
        this.hora = hora;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}