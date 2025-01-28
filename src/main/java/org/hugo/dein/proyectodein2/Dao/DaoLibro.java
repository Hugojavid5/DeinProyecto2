package org.hugo.dein.proyectodein2.Dao;

import org.hugo.dein.proyectodein2.Modelos.ModeloLibro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoLibro {
    private Connection connection;

    // Constructor
    public DaoLibro(Connection connection) {
        this.connection = connection;
    }

    // Alta de un libro
    public boolean agregarLibro(ModeloLibro libro) {
        String sql = "INSERT INTO Libro (titulo, autor, editorial, estado, baja) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setString(4, libro.getEstado());
            ps.setBoolean(5, libro.isBaja());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar el libro: " + e.getMessage());
        }
        return false;
    }

    // Baja de un libro (marcar como dado de baja)
    public boolean darDeBajaLibro(int codigo) {
        String sql = "UPDATE Libro SET baja = 1 WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al dar de baja el libro: " + e.getMessage());
        }
        return false;
    }

    // Modificación de un libro
    public boolean actualizarLibro(ModeloLibro libro) {
        String sql = "UPDATE Libro SET titulo = ?, autor = ?, editorial = ?, estado = ?, baja = ? WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setString(4, libro.getEstado());
            ps.setBoolean(5, libro.isBaja());
            ps.setInt(6, libro.getCodigo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el libro: " + e.getMessage());
        }
        return false;
    }

    // Consulta de un libro por código
    public ModeloLibro buscarLibroPorCodigo(int codigo) {
        String sql = "SELECT * FROM Libro WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ModeloLibro(
                            rs.getInt("codigo"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("editorial"),
                            rs.getString("estado"),
                            rs.getBoolean("baja")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el libro: " + e.getMessage());
        }
        return null;
    }

    // Listar todos los libros
    public List<ModeloLibro> listarLibros() {
        List<ModeloLibro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libro";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                libros.add(new ModeloLibro(
                        rs.getInt("codigo"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editorial"),
                        rs.getString("estado"),
                        rs.getBoolean("baja")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los libros: " + e.getMessage());
        }
        return libros;
    }
}
