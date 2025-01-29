package org.hugo.dein.proyectodein.Modelos;

public class ModeloLibro {
    private int codigo;
    private String titulo;
    private String autor;
    private String editorial;
    private String estado; // Puede ser Nuevo, Usado nuevo, Usado seminuevo, Usado estropeado, Restaurado
    private boolean baja;
    private byte[] imagenPortada; // Imagen en formato BLOB

    // Constructor completo
    public ModeloLibro(int codigo, String titulo, String autor, String editorial, String estado, boolean baja, byte[] imagenPortada) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.estado = estado;
        this.baja = baja;
        this.imagenPortada = imagenPortada;
    }

    // Constructor sin código (para nuevos libros)
    public ModeloLibro(String titulo, String autor, String editorial, String estado, boolean baja, byte[] imagenPortada) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.estado = estado;
        this.baja = baja;
        this.imagenPortada = imagenPortada;
    }

    // Getters y setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isBaja() {
        return baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }

    public byte[] getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenPortada(byte[] imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "codigo=" + codigo +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", estado='" + estado + '\'' +
                ", baja=" + baja +
                '}';
    }
}
