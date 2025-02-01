package org.hugo.dein.proyectodein.Modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hugo.dein.proyectodein.utils.FechaFormatter;

/**
 * Clase que representa un préstamo de libro en el sistema.
 */
public class ModeloPrestamo {

    private static final Logger logger = LoggerFactory.getLogger(ModeloPrestamo.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
    private int id_prestamo;
    private ModeloAlumno alumno;
    private ModeloLibro libro;
    private LocalDateTime fecha_prestamo;

    /**
     * Constructor que inicializa un préstamo con los valores proporcionados.
     *
     * @param id_prestamo el identificador del préstamo.
     * @param alumno el alumno que realizó el préstamo.
     * @param libro el libro prestado.
     * @param fecha_prestamo la fecha en que se realizó el préstamo.
     */
    public ModeloPrestamo(int id_prestamo, ModeloAlumno alumno, ModeloLibro libro, LocalDateTime fecha_prestamo) {
        this.id_prestamo = id_prestamo;
        this.alumno = alumno;
        this.libro = libro;
        this.fecha_prestamo = fecha_prestamo;
    }

    /**
     * Constructor vacío para crear un préstamo sin inicializar los datos.
     */
    public ModeloPrestamo() {}

    /**
     * Devuelve una representación en cadena del préstamo con el formato adecuado.
     * La fecha de préstamo es formateada antes de ser incluida en la cadena.
     *
     * @return la representación en cadena del préstamo con fecha formateada.
     */
    @Override
    public String toString() {
        String formattedFecha = FORMATTER.format(fecha_prestamo);
        logger.info("Fecha formateada a string: {}", formattedFecha);
        return id_prestamo + " - " + alumno + " - " + libro + " - " + formattedFecha;
    }

    /**
     * Obtiene el identificador del préstamo.
     *
     * @return el identificador del préstamo.
     */
    public int getId_prestamo() {
        return id_prestamo;
    }

    /**
     * Establece el identificador del préstamo.
     *
     * @param id_prestamo el identificador del préstamo.
     */
    public void setId_prestamo(int id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    /**
     * Obtiene el alumno que realizó el préstamo.
     *
     * @return el alumno que realizó el préstamo.
     */
    public ModeloAlumno getAlumno() {
        return alumno;
    }

    /**
     * Establece el alumno que realizó el préstamo.
     *
     * @param alumno el alumno que realizó el préstamo.
     */
    public void setAlumno(ModeloAlumno alumno) {
        this.alumno = alumno;
    }

    /**
     * Obtiene el libro prestado en el préstamo.
     *
     * @return el libro prestado en el préstamo.
     */
    public ModeloLibro getLibro() {
        return libro;
    }

    /**
     * Establece el libro prestado en el préstamo.
     *
     * @param libro el libro prestado en el préstamo.
     */
    public void setLibro(ModeloLibro libro) {
        this.libro = libro;
    }

    /**
     * Obtiene la fecha en que se realizó el préstamo.
     *
     * @return la fecha del préstamo.
     */
    public LocalDateTime getFecha_prestamo() {
        return fecha_prestamo;
    }

    /**
     * Establece la fecha en que se realizó el préstamo.
     *
     * @param fecha_prestamo la fecha del préstamo.
     */
    public void setFecha_prestamo(LocalDateTime fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }
}
