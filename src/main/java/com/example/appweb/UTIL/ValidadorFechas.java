package com.example.appweb.UTIL;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidadorFechas {
    public static void validarFechaNoFutura(String fechaStr) throws IllegalArgumentException {
        try {
            LocalDate fechaIngresada = LocalDate.parse(fechaStr);
            if (fechaIngresada.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("La fecha no puede ser futura");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use YYYY-MM-DD");
        }
    }
}
