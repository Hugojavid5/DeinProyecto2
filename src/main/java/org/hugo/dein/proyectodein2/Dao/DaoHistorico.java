package org.hugo.dein.proyectodein2.Dao;

import org.hugo.dein.proyectodein2.Modelos.ModeloGestionPrestamo;
import org.hugo.dein.proyectodein2.Modelos.ModeloHistorico;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DaoHistorico {
    private Connection connection;

    // Constructor
    public DaoHistorico(Connection connection) {
        this.connection = connection;
    }

    // Realizar la devolución de un libro
    public boolean devolverLibro(int idPrestamo, String estadoLibro) {
        // Obtener el préstamo que se va a devolver
        ModeloGestionPrestamo prestamo = obtenerPrestamoPorId(idPrestamo);
        if (prestamo == null) {
            System.err.println("El préstamo no existe.");
            return false;
        }

        // Mover el préstamo a la tabla de históricos
        String sqlHistorico = "INSERT INTO PrestamoHistorico (id_prestamo, dni_alumno, codigo_libro, fecha_prestamo, fecha_devolucion, estado_libro) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psHistorico = connection.prepareStatement(sqlHistorico)) {
            psHistorico.setInt(1, prestamo.getIdPrestamo());
            psHistorico.setString(2, prestamo.getDniAlumno());
            psHistorico.setInt(3, prestamo.getCodigoLibro());
            psHistorico.setDate(4, Date.valueOf(prestamo.getFechaPrestamo()));
            psHistorico.setDate(5, Date.valueOf(prestamo.getFechaDevolucion()));
            psHistorico.setString(6, estadoLibro);  // Guardar el estado del libro al devolverlo
            psHistorico.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar en histórico: " + e.getMessage());
            return false;
        }

        // Eliminar el préstamo de la tabla Prestamo
        String sqlEliminar = "DELETE FROM Prestamo WHERE id_prestamo = ?";
        try (PreparedStatement psEliminar = connection.prepareStatement(sqlEliminar)) {
            psEliminar.setInt(1, idPrestamo);
            psEliminar.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el préstamo: " + e.getMessage());
        }

        return false;
    }

    // Obtener un préstamo por su ID
    public ModeloGestionPrestamo obtenerPrestamoPorId(int idPrestamo) {
        String sql = "SELECT * FROM Prestamo WHERE id_prestamo = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ModeloGestionPrestamo(
                            rs.getInt("id_prestamo"),
                            rs.getString("dni_alumno"),
                            rs.getInt("codigo_libro"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el préstamo: " + e.getMessage());
        }
        return null;
    }

    // Consultar los históricos de préstamos con varios filtros
    public List<ModeloHistorico> consultarHistoricoPrestamos(String dniAlumno, LocalDate fechaInicio, LocalDate fechaFin, String estadoLibro) {
        List<ModeloHistorico> historicos = new ArrayList<>();
        String sql = "SELECT * FROM PrestamoHistorico WHERE dni_alumno = ? AND fecha_prestamo BETWEEN ? AND ? AND estado_libro = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dniAlumno);
            ps.setDate(2, Date.valueOf(fechaInicio));
            ps.setDate(3, Date.valueOf(fechaFin));
            ps.setString(4, estadoLibro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    historicos.add(new ModeloHistorico(
                            rs.getInt("id_prestamo"),
                            rs.getString("dni_alumno"),
                            rs.getInt("codigo_libro"),
                            rs.getDate("fecha_prestamo").toLocalDate(),
                            rs.getDate("fecha_devolucion").toLocalDate(),
                            rs.getString("estado_libro")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar histórico de préstamos: " + e.getMessage());
        }
        return historicos;
    }
}
