package org.hugo.dein.proyectodein2.Modelos;
import java.time.LocalDate;

public class ModeloGestionPrestamo {
    private int id;
    private int codigoLibro;
    private int idAlumno;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    // Constructor completo
    public ModeloGestionPrestamo(int id, int codigoLibro, int idAlumno, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.id = id;
        this.codigoLibro = codigoLibro;
        this.idAlumno = idAlumno;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Constructor para nuevos préstamos (sin ID)
    public ModeloGestionPrestamo(int codigoLibro, int idAlumno, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.codigoLibro = codigoLibro;
        this.idAlumno = idAlumno;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoLibro() {
        return codigoLibro;
    }

    public void setCodigoLibro(int codigoLibro) {
        this.codigoLibro = codigoLibro;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
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
        return "Prestamo{" +
                "id=" + id +
                ", codigoLibro=" + codigoLibro +
                ", idAlumno=" + idAlumno +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion +
                '}';
    }
}

