package org.hugo.dein.proyectodein.Modelos;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa el histórico de un préstamo de un libro.
 */
public class ModeloHistoricoPrestamo {

    private int id_prestamo;
    private ModeloAlumno alumno;
    private ModeloLibro libro;
    private LocalDateTime fecha_prestamo;
    private LocalDateTime fecha_devolucion;

    /**
     * Constructor que inicializa un histórico de préstamo con los valores proporcionados.
     *
     * @param id_prestamo el ID del préstamo.
     * @param alumno el alumno que ha realizado el préstamo.
     * @param libro el libro que ha sido prestado.
     * @param fecha_prestamo la fecha y hora del préstamo.
     * @param fecha_devolucion la fecha y hora de la devolución.
     */
    public ModeloHistoricoPrestamo(int id_prestamo, ModeloAlumno alumno, ModeloLibro libro, LocalDateTime fecha_prestamo, LocalDateTime fecha_devolucion) {
        this.id_prestamo = id_prestamo;
        this.alumno = alumno;
        this.libro = libro;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    /**
     * Constructor vacío para crear un histórico de préstamo sin inicializar los datos.
     */
    public ModeloHistoricoPrestamo() {}

    /**
     * Obtiene el ID del préstamo.
     *
     * @return el ID del préstamo.
     */
    public int getId_prestamo() {
        return id_prestamo;
    }

    /**
     * Establece el ID del préstamo.
     *
     * @param id_prestamo el ID del préstamo.
     */
    public void setId_prestamo(int id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    /**
     * Obtiene el alumno que ha realizado el préstamo.
     *
     * @return el alumno que ha realizado el préstamo.
     */
    public ModeloAlumno getAlumno() {
        return alumno;
    }

    /**
     * Establece el alumno que ha realizado el préstamo.
     *
     * @param alumno el alumno que ha realizado el préstamo.
     */
    public void setAlumno(ModeloAlumno alumno) {
        this.alumno = alumno;
    }

    /**
     * Obtiene el libro que ha sido prestado.
     *
     * @return el libro que ha sido prestado.
     */
    public ModeloLibro getLibro() {
        return libro;
    }

    /**
     * Establece el libro que fue prestado.
     *
     * @param libro el libro que fue prestado.
     */
    public void setLibro(ModeloLibro libro) {
        this.libro = libro;
    }

    /**
     * Obtiene la fecha y hora en que se realizó el préstamo.
     *
     * @return la fecha y hora del préstamo.
     */
    public LocalDateTime getFecha_prestamo() {
        return fecha_prestamo;
    }

    /**
     * Establece la fecha y hora del préstamo.
     *
     * @param fecha_prestamo la fecha y hora del préstamo.
     */
    public void setFecha_prestamo(LocalDateTime fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    /**
     * Obtiene la fecha y hora de la devolución del libro.
     *
     * @return la fecha y hora de la devolución.
     */
    public LocalDateTime getFecha_devolucion() {
        return fecha_devolucion;
    }

    /**
     * Establece la fecha y hora de la devolución del libro.
     *
     * @param fecha_devolucion la fecha y hora de la devolución.
     */
    public void setFecha_devolucion(LocalDateTime fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    /**
     * Compara este histórico de préstamo con otro objeto para ver si son iguales.
     * Dos históricos de préstamo son iguales si tienen el mismo ID de préstamo.
     *
     * @param o el objeto con el que comparar.
     * @return true si los históricos de préstamo son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ModeloHistoricoPrestamo that = (ModeloHistoricoPrestamo) o;
        return id_prestamo == that.id_prestamo;
    }

    /**
     * Devuelve el valor hash del objeto.
     *
     * @return el valor hash del histórico de préstamo.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id_prestamo);
    }
}
