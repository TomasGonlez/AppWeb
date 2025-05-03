package com.example.appweb.MODELO;

public class RegistroPersona {
    private String rut;
    private String nombre;
    private String tipoRegistro;

    public RegistroPersona() {}

    public RegistroPersona(String rut, String nombre, String tipoRegistro) {
        this.rut = rut;
        this.nombre = nombre;
        this.tipoRegistro = tipoRegistro;
    }

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

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}
