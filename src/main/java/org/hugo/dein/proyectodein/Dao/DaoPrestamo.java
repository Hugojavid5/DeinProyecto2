package org.hugo.dein.proyectodein.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;
import org.hugo.dein.proyectodein.Modelos.ModeloPrestamo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DaoPrestamo {

    private static Connection conn;

    static {
        conn = ConexionBBDD.getConnection();
    }

    public DaoPrestamo() throws SQLException {
    }


    public static ModeloPrestamo getPrestamo(int idPrestamo) {
        String sql = "SELECT * FROM Prestamo WHERE id_prestamo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPrestamo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new ModeloPrestamo(
                        rs.getInt("id_prestamo"),
                        DaoAlumno.getAlumno(rs.getString("dni_alumno")),
                        DaoLibro.getLibro(rs.getInt("codigo_libro")),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<ModeloPrestamo> getTodosPrestamo() {
        ObservableList<ModeloPrestamo> listaPrestamos = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Prestamo";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listaPrestamos.add(new ModeloPrestamo(
                        rs.getInt("id_prestamo"),
                        DaoAlumno.getAlumno(rs.getString("dni_alumno")),
                        DaoLibro.getLibro(rs.getInt("codigo_libro")),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaPrestamos;
    }

    public static boolean insertPrestamo(ModeloPrestamo prestamo) {
        if (!comprobarSiLibroSePuedePrestar(prestamo.getLibro().getCodigo())) {
            return false;
        }

        String sql = "INSERT INTO Prestamo (dni_alumno, codigo_libro, fecha_prestamo) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Añadido Statement.RETURN_GENERATED_KEYS
            pstmt.setString(1, prestamo.getAlumno().getDni());
            pstmt.setInt(2, prestamo.getLibro().getCodigo());
            pstmt.setTimestamp(3, Timestamp.valueOf(prestamo.getFecha_prestamo()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Recuperar el ID generado
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        prestamo.setId_prestamo(generatedKeys.getInt(1)); // Asignar el ID generado
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<ModeloPrestamo> getPrestamosDeAlumno(ModeloAlumno alumno) {
        List<ModeloPrestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM Prestamo WHERE dni_alumno = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, alumno.getDni());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                prestamos.add(new ModeloPrestamo(
                        rs.getInt("id_prestamo"),
                        alumno,
                        DaoLibro.getLibro(rs.getInt("codigo_libro")),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

    public static List<ModeloPrestamo> getPrestamosDeLibro(ModeloLibro libro) {
        List<ModeloPrestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM Prestamo WHERE codigo_libro = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, libro.getCodigo());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                prestamos.add(new ModeloPrestamo(
                        rs.getInt("id_prestamo"),
                        DaoAlumno.getAlumno(rs.getString("dni_alumno")),
                        libro,
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }


    public static boolean comprobarSiLibroSePuedePrestar(int codigoLibro) {
        String sql = "SELECT COUNT(*) FROM Prestamo WHERE codigo_libro = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codigoLibro);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Hay un préstamo activo para este libro.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean deletePrestamo(int idPrestamo) {
        String sql = "DELETE FROM Prestamo WHERE id_prestamo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPrestamo);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
