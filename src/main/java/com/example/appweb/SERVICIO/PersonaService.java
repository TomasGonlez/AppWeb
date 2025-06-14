package com.example.appweb.SERVICIO;

import com.example.appweb.DAO.PersonaDAO;
import com.example.appweb.MODELO.Persona;

public class PersonaService {

    private final PersonaDAO personaDAO;

    public PersonaService(PersonaDAO personaDAO) {this.personaDAO = personaDAO;}

    public boolean validarRut(String rut) {
        return personaDAO.buscarRut(rut);
    }

    public boolean validarNombre(String nombre, String rut) {
        return personaDAO.buscarNombre(nombre, rut);
    }

    public Persona crearPersona(String rut, String nombre) {
        Persona persona = new Persona();
        persona.setRut(rut);
        persona.setNombre(nombre);
        return persona;
    }

    public void registrarPersona(Persona persona) {
        personaDAO.registrar(persona);
    }


}
