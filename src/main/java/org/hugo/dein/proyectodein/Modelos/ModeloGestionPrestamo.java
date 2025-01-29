package org.hugo.dein.proyectodein.Modelos;

import java.time.LocalDate;

public class ModeloGestionPrestamo {
    private int idPrestamo; // Corresponde al campo id_prestamo
    private String dniAlumno; // Corresponde al campo dni_alumno
    private int codigoLibro; // Corresponde al campo codigo_libro
    private LocalDate fechaPrestamo; // Corresponde al campo fecha_prestamo
    private LocalDate fechaDevolucion; // Corresponde al campo fecha_devolucion

    // Constructor completo
    public ModeloGestionPrestamo(int idPrestamo, String dniAlumno, int codigoLibro, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.idPrestamo = idPrestamo;
        this.dniAlumno = dniAlumno;
        this.codigoLibro = codigoLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Constructor para nuevos préstamos (sin ID)
    public ModeloGestionPrestamo(String dniAlumno, int codigoLibro, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.dniAlumno = dniAlumno;
        this.codigoLibro = codigoLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters y setters
    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getDniAlumno() {
        return dniAlumno;
    }

    public void setDniAlumno(String dniAlumno) {
        this.dniAlumno = dniAlumno;
    }

    public int getCodigoLibro() {
        return codigoLibro;
    }

    public void setCodigoLibro(int codigoLibro) {
        this.codigoLibro = codigoLibro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        return "ModeloGestionPrestamo{" +
                "idPrestamo=" + idPrestamo +
                ", dniAlumno='" + dniAlumno + '\'' +
                ", codigoLibro=" + codigoLibro +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion +
                '}';
    }
}
