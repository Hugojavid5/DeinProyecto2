package org.hugo.dein.proyectodein.Modelos;

import java.sql.Blob;
import java.util.Objects;

/**
 * Clase que representa un libro en el sistema.
 */
public class ModeloLibro {

    private int codigo;
    private String titulo;
    private String autor;
    private String editorial;
    private String estado;
    private int baja;
    private Blob imagen;

    /**
     * Constructor que inicializa un libro con los valores proporcionados.
     *
     * @param codigo el código del libro.
     * @param titulo el título del libro.
     * @param autor el autor del libro.
     * @param editorial la editorial del libro.
     * @param estado el estado del libro (ej. disponible, prestado, etc.).
     * @param baja indica si el libro ha sido dado de baja (1: dado de baja, 0: no dado de baja).
     * @param imagen la imagen del libro almacenada como un Blob.
     */
    public ModeloLibro(int codigo, String titulo, String autor, String editorial, String estado, int baja, Blob imagen) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.estado = estado;
        this.baja = baja;
        this.imagen = imagen;
    }

    /**
     * Constructor vacío para crear un libro sin inicializar los datos.
     */
    public ModeloLibro() {
    }

    /**
     * Devuelve una representación en cadena del libro (solo el título).
     *
     * @return el título del libro.
     */
    @Override
    public String toString() {
        return titulo;
    }

    /**
     * Obtiene el código del libro.
     *
     * @return el código del libro.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del libro.
     *
     * @param codigo el código del libro.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el título del libro.
     *
     * @return el título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro.
     *
     * @param titulo el título del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el autor del libro.
     *
     * @return el autor del libro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Establece el autor del libro.
     *
     * @param autor el autor del libro.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Obtiene la editorial del libro.
     *
     * @return la editorial del libro.
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * Establece la editorial del libro.
     *
     * @param editorial la editorial del libro.
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * Obtiene el estado del libro (ej. disponible, prestado, etc.).
     *
     * @return el estado del libro.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del libro.
     *
     * @param estado el estado del libro.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el estado de baja del libro (1 si está dado de baja, 0 si no).
     *
     * @return el estado de baja del libro.
     */
    public int getBaja() {
        return baja;
    }

    /**
     * Establece el estado de baja del libro.
     *
     * @param baja el estado de baja del libro (1 para dado de baja, 0 para no dado de baja).
     */
    public void setBaja(int baja) {
        this.baja = baja;
    }

    /**
     * Obtiene la imagen del libro.
     *
     * @return la imagen del libro almacenada como un Blob.
     */
    public Blob getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen del libro.
     *
     * @param imagen la imagen del libro almacenada como un Blob.
     */
    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    /**
     * Compara este libro con otro objeto para ver si son iguales.
     * Dos libros son iguales si tienen el mismo código, título, autor, editorial, estado y baja.
     * La imagen no se compara.
     *
     * @param o el objeto con el que comparar.
     * @return true si los libros son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ModeloLibro libroModel = (ModeloLibro) o;

        // Compara todos los campos excepto la imagen
        return codigo == libroModel.codigo &&
                Objects.equals(titulo, libroModel.titulo) &&
                Objects.equals(autor, libroModel.autor) &&
                Objects.equals(editorial, libroModel.editorial) &&
                Objects.equals(estado, libroModel.estado) &&
                baja == libroModel.baja;
    }

    /**
     * Devuelve el valor hash del libro.
     *
     * @return el valor hash del libro.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }
}
