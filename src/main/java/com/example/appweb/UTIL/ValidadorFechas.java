package com.example.appweb.UTIL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ValidadorFechas {
    public static void validarFechaNoFutura(String fechaStr) throws IllegalArgumentException {
        try {
            LocalDate fechaIngresada = LocalDate.parse(fechaStr);
            //System.out.println("Fecha "+fechaIngresada);
            if (fechaIngresada.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("La fecha a ingresar no puede ser futura.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use YYYY-MM-DD.");
        }
    }
    public static void validarFechaPasada(String fechaStr) throws IllegalArgumentException {
        try {
            LocalDate fechaIngresada = LocalDate.parse(fechaStr);
            //System.out.println("Fecha "+fechaIngresada);
            if (fechaIngresada.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("La fecha a ingresar no puede ser pasada.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use YYYY-MM-DD.");
        }
    }
    public static void validarRangoFechas(String desdeStr, String hastaStr) throws IllegalArgumentException {
        try {
            LocalDate desde = LocalDate.parse(desdeStr);
            LocalDate hasta = LocalDate.parse(hastaStr);
            if (desde.isAfter(hasta)) {
                throw new IllegalArgumentException("La fecha DESDE no puede ser mayor que la fecha HASTA.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use YYYY-MM-DD.");
        }
    }
}
