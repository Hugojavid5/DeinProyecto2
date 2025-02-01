package org.hugo.dein.proyectodein.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FechaFormatter {


    private static final Logger logger = LoggerFactory.getLogger(FechaFormatter.class);


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    public static String formatearFecha(LocalDateTime fecha) {
        String string = FORMATTER.format(fecha);
        logger.info("Fecha formateada a string: {}", string);
        return string;
    }

    public static LocalDateTime formatearString(String string) {
        LocalDateTime fecha = LocalDateTime.parse(string, FORMATTER);
        logger.info("String formateada a fecha: {}", fecha.toString());
        return fecha;
    }
}
