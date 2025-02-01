package org.hugo.dein.proyectodein.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoLibro {

    private static Connection conn;

    static {
        conn = ConexionBBDD.getConnection();
    }

    public DaoLibro() throws SQLException {
    }

    /**
     * Obtiene un libro de la base de datos por su código.
     *
     * @param codigo El código del libro a buscar.
     * @return El objeto {@link ModeloLibro} correspondiente al código, o null si no se encuentra.
     */
    public static ModeloLibro getLibro(int codigo) {
        String sql = "SELECT * FROM Libro WHERE codigo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codigo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new ModeloLibro(
                        rs.getInt("codigo"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editorial"),
                        rs.getString("estado"),
                        rs.getInt("baja"),
                        rs.getBlob("imagen")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene todos los libros de la base de datos.
     *
     * @return Una lista observable de todos los libros.
     */
    public static ObservableList<ModeloLibro> getTodosLibros() {
        ObservableList<ModeloLibro> listaLibros = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Libro";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listaLibros.add(new ModeloLibro(
                        rs.getInt("codigo"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editorial"),
                        rs.getString("estado"),
                        rs.getInt("baja"),
                        rs.getBlob("imagen")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaLibros;
    }

    /**
     * Obtiene todos los libros que no han sido dados de baja (baja = 0).
     *
     * @return Una lista observable de los libros no dados de baja.
     */
    public static ObservableList<ModeloLibro> getTodosLibrosConBajaA0() {
        ObservableList<ModeloLibro> listaLibros = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Libro WHERE baja = 0";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listaLibros.add(new ModeloLibro(
                        rs.getInt("codigo"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editorial"),
                        rs.getString("estado"),
                        rs.getInt("baja"),
                        rs.getBlob("imagen")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaLibros;
    }

    /**
     * Convierte un arreglo de bytes en un objeto Blob.
     *
     * @param data El arreglo de bytes a convertir.
     * @return El objeto {@link Blob} correspondiente.
     */
    public static Blob convertirABlob(byte[] data) {
        try {
            Blob blob = conn.createBlob();
            blob.setBytes(1, data);
            return blob;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Inserta un nuevo libro en la base de datos.
     *
     * @param libro El objeto {@link ModeloLibro} a insertar.
     * @return true si el libro se insertó correctamente, false si ocurrió un error.
     */
    public static boolean insertLibro(ModeloLibro libro) {
        String sql = "INSERT INTO Libro (titulo, autor, editorial, estado, baja, imagen) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getEditorial());
            pstmt.setString(4, libro.getEstado());
            pstmt.setInt(5, libro.getBaja());
            pstmt.setBlob(6, libro.getImagen());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Obtiene los libros que están disponibles para ser prestados (es decir, aquellos que no están en la tabla de préstamos).
     *
     * @return Una lista de los libros disponibles.
     */
    public static List<ModeloLibro> getLibrosDisponibles() {
        List<ModeloLibro> listaLibros = new ArrayList<>();
        String sql = "SELECT * FROM Libro WHERE codigo NOT IN (SELECT codigo_libro FROM Prestamo)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listaLibros.add(new ModeloLibro(
                        rs.getInt("codigo"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editorial"),
                        rs.getString("estado"),
                        rs.getInt("baja"),
                        rs.getBlob("imagen")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaLibros;
    }

    /**
     * Actualiza la información de un libro en la base de datos.
     *
     * @param libro El objeto {@link ModeloLibro} con la información actualizada.
     * @return true si la actualización fue exitosa, false si ocurrió un error.
     */
    public static boolean updateLibro(ModeloLibro libro) {
        String sql = "UPDATE Libro SET titulo = ?, autor = ?, editorial = ?, estado = ?, baja = ?, imagen = ? WHERE codigo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getEditorial());
            pstmt.setString(4, libro.getEstado());
            pstmt.setInt(5, libro.getBaja());
            pstmt.setBlob(6, libro.getImagen());
            pstmt.setInt(7, libro.getCodigo());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Da de baja un libro, marcándolo como no disponible para préstamos.
     *
     * @param codigo El código del libro a dar de baja.
     * @return true si la baja fue exitosa, false si ocurrió un error.
     */
    public static boolean bajaDelLibro(int codigo) {
        String sql = "UPDATE Libro SET baja = 1 WHERE codigo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codigo);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Actualiza el estado de un libro en la base de datos.
     *
     * @param idLibro El código del libro cuyo estado se actualizará.
     * @param nuevoEstado El nuevo estado del libro.
     * @return true si la actualización fue exitosa, false si ocurrió un error.
     */
    public static boolean updateLibroEstado(int idLibro, String nuevoEstado) {
        String sql = "UPDATE Libro SET estado = ? WHERE codigo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idLibro);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
