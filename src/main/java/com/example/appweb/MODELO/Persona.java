package com.example.appweb.MODELO;

public class Persona {
    private String Rut;
    private String Nombre;

    public Persona() {
    }

    public Persona(String rut, String nombre) {
        this.Rut = rut;
        this.Nombre = nombre;
    }

    public String getRut() {
        return Rut;
    }

    public void setRut(String rut) {
        Rut = rut;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
