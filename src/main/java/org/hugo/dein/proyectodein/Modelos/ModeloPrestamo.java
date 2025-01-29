package org.hugo.dein.proyectodein.Modelos;

import org.hugo.dein.proyectodein.utils.FechaFormatter;

import java.time.LocalDateTime;

public class ModeloPrestamo {
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
        return id_prestamo + " - " + alumno + " - " + libro + " - " + FechaFormatter.formatearFecha(fecha_prestamo);
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
