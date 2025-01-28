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
        // Verificar si el libro ya está prestado
        if (libroYaPrestado(prestamo.getCodigoLibro())) {
            System.err.println("Error: El libro ya está prestado.");
            return false;  // No se puede agregar el préstamo
        }

        String sql = "INSERT INTO Prestamo (dni_alumno, codigo_libro, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, prestamo.getDniAlumno()); // Asignar el DNI del alumno
            ps.setInt(2, prestamo.getCodigoLibro());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo())); // Convertir LocalDate a Date
            ps.setDate(4, prestamo.getFechaDevolucion() != null ? Date.valueOf(prestamo.getFechaDevolucion()) : null); // Si la fecha de devolución es nula, asignar null
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar el préstamo: " + e.getMessage());
        }
        return false;
    }

    // Metodo para comprobar si el libro ya está prestado
    private boolean libroYaPrestado(int codigoLibro) {
        String sql = "SELECT COUNT(*) FROM Prestamo WHERE codigo_libro = ? AND fecha_devolucion IS NULL";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, codigoLibro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Si el conteo es mayor a 0, el libro está prestado
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al comprobar si el libro está prestado: " + e.getMessage());
        }
        return false;
    }

    // Consulta de un préstamo por ID
    public ModeloGestionPrestamo buscarPrestamoPorId(int id) {
        String sql = "SELECT * FROM Prestamo WHERE id_prestamo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ModeloGestionPrestamo(
                            rs.getInt("id_prestamo"),
                            rs.getString("dni_alumno"), // Leer el DNI del alumno
                            rs.getInt("codigo_libro"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null // Manejo de fechaDevolucion nula
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
                        rs.getInt("id_prestamo"),
                        rs.getString("dni_alumno"), // Leer el DNI del alumno
                        rs.getInt("codigo_libro"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null // Manejo de fechaDevolucion nula
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los préstamos: " + e.getMessage());
        }
        return prestamos;
    }

    // Listar préstamos por alumno (por DNI)
    public List<ModeloGestionPrestamo> listarPrestamosPorAlumno(String dniAlumno) {
        List<ModeloGestionPrestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM Prestamo WHERE dni_alumno = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dniAlumno);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prestamos.add(new ModeloGestionPrestamo(
                            rs.getInt("id_prestamo"),
                            rs.getString("dni_alumno"), // Leer el DNI del alumno
                            rs.getInt("codigo_libro"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null // Manejo de fechaDevolucion nula
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los préstamos por alumno: " + e.getMessage());
        }
        return prestamos;
    }
}
