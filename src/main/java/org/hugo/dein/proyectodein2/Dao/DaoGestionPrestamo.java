package org.hugo.dein.proyectodein2.Dao;

import org.hugo.dein.proyectodein2.Modelos.ModeloGestionPrestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoGestionPrestamo {
    private Connection connection;

    // Constructor
    public DaoGestionPrestamo(Connection connection) {
        this.connection = connection;
    }

    // Alta de un préstamo
    public boolean agregarPrestamo(ModeloGestionPrestamo prestamo) {
        String sql = "INSERT INTO Prestamo (codigo_libro, id_alumno, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, prestamo.getCodigoLibro());
            ps.setInt(2, prestamo.getIdAlumno());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar el préstamo: " + e.getMessage());
        }
        return false;
    }

    // Consulta de un préstamo por ID
    public ModeloGestionPrestamo buscarPrestamoPorId(int id) {
        String sql = "SELECT * FROM Prestamo WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ModeloGestionPrestamo(
                            rs.getInt("id"),
                            rs.getInt("codigo_libro"),
                            rs.getInt("id_alumno"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_devolucion").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el préstamo: " + e.getMessage());
        }
        return null;
    }

    // Listar todos los préstamos
    public List<ModeloGestionPrestamo> listarPrestamos() {
        List<ModeloGestionPrestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM Prestamo";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                prestamos.add(new ModeloGestionPrestamo(
                        rs.getInt("id"),
                        rs.getInt("codigo_libro"),
                        rs.getInt("id_alumno"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getDate("fecha_devolucion").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los préstamos: " + e.getMessage());
        }
        return prestamos;
    }

    // Listar préstamos por alumno
    public List<ModeloGestionPrestamo> listarPrestamosPorAlumno(int idAlumno) {
        List<ModeloGestionPrestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM Prestamo WHERE id_alumno = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prestamos.add(new ModeloGestionPrestamo(
                            rs.getInt("id"),
                            rs.getInt("codigo_libro"),
                            rs.getInt("id_alumno"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_devolucion").toLocalDate()
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los préstamos por alumno: " + e.getMessage());
        }
        return prestamos;
    }
}
