package org.hugo.dein.proyectodein.Modelos;

import java.util.Objects;

/**
 * Clase que representa a un alumno con sus datos personales.
 */
public class ModeloAlumno {
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;

    /**
     * Constructor que inicializa un alumno con los valores proporcionados.
     *
     * @param dni el DNI del alumno.
     * @param nombre el nombre del alumno.
     * @param apellido1 el primer apellido del alumno.
     * @param apellido2 el segundo apellido del alumno.
     */
    public ModeloAlumno(String dni, String nombre, String apellido1, String apellido2) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    /**
     * Constructor vacío para crear un alumno sin inicializar los datos.
     */
    public ModeloAlumno() {
    }

    /**
     * Obtiene el DNI del alumno.
     *
     * @return el DNI del alumno.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del alumno.
     *
     * @param dni el DNI del alumno.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre del alumno.
     *
     * @return el nombre del alumno.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del alumno.
     *
     * @param nombre el nombre del alumno.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el primer apellido del alumno.
     *
     * @return el primer apellido del alumno.
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Establece el primer apellido del alumno.
     *
     * @param apellido1 el primer apellido del alumno.
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * Obtiene el segundo apellido del alumno.
     *
     * @return el segundo apellido del alumno.
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Establece el segundo apellido del alumno.
     *
     * @param apellido2 el segundo apellido del alumno.
     */
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    /**
     * Devuelve una representación en cadena del alumno, mostrando solo su nombre.
     *
     * @return una cadena con el nombre del alumno.
     */
    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Compara este alumno con otro objeto para ver si son iguales.
     * Dos alumnos son iguales si tienen el mismo DNI, nombre, primer apellido y segundo apellido.
     *
     * @param o el objeto con el que comparar.
     * @return true si los alumnos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModeloAlumno alumno = (ModeloAlumno) o;

        return Objects.equals(dni, alumno.dni) &&
                Objects.equals(nombre, alumno.nombre) &&
                Objects.equals(apellido1, alumno.apellido1) &&
                Objects.equals(apellido2, alumno.apellido2);
    }

    /**
     * Devuelve el valor hash del objeto.
     *
     * @return el valor hash del alumno.
     */
    @Override
    public int hashCode() {
        return Objects.hash(dni, nombre, apellido1, apellido2);
    }
}
