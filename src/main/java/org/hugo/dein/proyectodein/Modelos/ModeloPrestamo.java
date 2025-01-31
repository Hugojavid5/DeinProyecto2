package org.hugo.dein.proyectodein.Modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ModeloPrestamo {


    private static final Logger logger = LoggerFactory.getLogger(ModeloPrestamo.class);


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    private int id_prestamo;


    private ModeloAlumno alumno;


    private ModeloLibro libro;


    private LocalDateTime fecha_prestamo;


    public ModeloPrestamo(int id_prestamo, ModeloAlumno alumno, ModeloLibro libro, LocalDateTime fecha_prestamo) {
        this.id_prestamo = id_prestamo;
        this.alumno = alumno;
        this.libro = libro;
        this.fecha_prestamo = fecha_prestamo;
    }


    public ModeloPrestamo() {}

    @Override
    public String toString() {
        String formattedFecha = FORMATTER.format(fecha_prestamo);
        logger.info("Fecha formateada a string: {}", formattedFecha);
        return id_prestamo + " - " + alumno + " - " + libro + " - " + formattedFecha;
    }


    public int getId_prestamo() {
        return id_prestamo;
    }

    public void setId_prestamo(int id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    public ModeloAlumno getAlumno() {
        return alumno;
    }

    public void setAlumno(ModeloAlumno alumno) {
        this.alumno = alumno;
    }

    public ModeloLibro getLibro() {
        return libro;
    }


    public void setLibro(ModeloLibro libro) {
        this.libro = libro;
    }


    public LocalDateTime getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(LocalDateTime fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }
}
