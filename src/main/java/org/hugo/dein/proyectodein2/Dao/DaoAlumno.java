package org.hugo.dein.proyectodein2.Dao;

import org.hugo.dein.proyectodein2.Modelos.ModeloAlumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DaoAlumno {
    private Connection connection;

    // Constructor
    public DaoAlumno(Connection connection) {
        this.connection = connection;
    }

    // Alta de un alumno
    public boolean agregarAlumno(ModeloAlumno alumno) {
        String sql = "INSERT INTO Alumno (dni, nombre, apellido1, apellido2) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, alumno.getDni());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getApellido1());
            ps.setString(4, alumno.getApellido2());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar el alumno: " + e.getMessage());
        }
        return false;
    }

    // Modificación de un alumno
    public boolean actualizarAlumno(ModeloAlumno alumno) {
        String sql = "UPDATE Alumno SET nombre = ?, apellido1 = ?, apellido2 = ? WHERE dni = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido1());
            ps.setString(3, alumno.getApellido2());
            ps.setString(4, alumno.getDni());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el alumno: " + e.getMessage());
        }
        return false;
    }

    // Consulta de un alumno por DNI
    public ModeloAlumno buscarAlumnoPorDni(String dni) {
        String sql = "SELECT * FROM Alumno WHERE dni = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ModeloAlumno(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("apellido1"),
                            rs.getString("apellido2")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el alumno: " + e.getMessage());
        }
        return null;
    }

    // Consulta de todos los alumnos
    public List<ModeloAlumno> listarAlumnos() {
        List<ModeloAlumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM Alumno";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                alumnos.add(new ModeloAlumno(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los alumnos: " + e.getMessage());
        }
        return alumnos;
    }
}

