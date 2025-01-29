package org.hugo.dein.proyectodein.Dao;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;


import java.sql.*;


public class DaoLibro {

    private static Connection conn;

    static {
        conn = ConexionBBDD.getConnection();
    }

    public DaoLibro() throws SQLException {
    }


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
}