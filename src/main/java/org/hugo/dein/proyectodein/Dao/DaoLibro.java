package org.hugo.dein.proyectodein.Dao;

import org.hugo.dein.proyectodein.Modelos.ModeloLibro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoLibro {
    private Connection connection;

    // Constructor
    public DaoLibro(Connection connection) {
        this.connection = connection;
    }

    // Registrar un nuevo libro
    public boolean registrarLibro(ModeloLibro libro) {
        String sql = "INSERT INTO Libro (titulo, autor, editorial, estado, baja, imagen) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setString(4, libro.getEstado());
            ps.setBoolean(5, libro.isBaja());
            ps.setBytes(6, libro.getImagenPortada());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar libro: " + e.getMessage());
        }
        return false;
    }

    // Actualizar información de un libro
    public boolean actualizarLibro(ModeloLibro libro) {
        String sql = "UPDATE Libro SET titulo = ?, autor = ?, editorial = ?, estado = ?, baja = ?, imagen = ? WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setString(4, libro.getEstado());
            ps.setBoolean(5, libro.isBaja());
            ps.setBytes(6, libro.getImagenPortada());
            ps.setInt(7, libro.getCodigo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
        }
        return false;
    }

    // Marcar un libro como dado de baja
    public boolean darDeBajaLibro(int codigoLibro) {
        String sql = "UPDATE Libro SET baja = true WHERE codigo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, codigoLibro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al dar de baja el libro: " + e.getMessage());
        }
        return false;
    }

    // Consultar libros (excluyendo los dados de baja)
    public List<ModeloLibro> listarLibrosActivos() {
        String sql = "SELECT * FROM Libro WHERE baja = false";
        List<ModeloLibro> libros = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                libros.add(new ModeloLibro(
                        rs.getInt("codigo"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editorial"),
                        rs.getString("estado"),
                        rs.getBoolean("baja"),
                        rs.getBytes("imagen")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros activos: " + e.getMessage());
        }
        return libros;
    }

    // Consultar libros dados de baja
    public List<ModeloLibro> listarLibrosDadosDeBaja() {
        String sql = "SELECT * FROM Libro WHERE baja = true";
        List<ModeloLibro> libros = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                libros.add(new ModeloLibro(
                        rs.getInt("codigo"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editorial"),
                        rs.getString("estado"),
                        rs.getBoolean("baja"),
                        rs.getBytes("imagen")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros dados de baja: " + e.getMessage());
        }
        return libros;
    }
}