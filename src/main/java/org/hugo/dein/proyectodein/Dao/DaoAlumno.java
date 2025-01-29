package org.hugo.dein.proyectodein.Dao;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import java.sql.*;

public class DaoAlumno {

    private static Connection conn;

    static {
        conn = ConexionBBDD.getConnection();
    }

    public DaoAlumno() throws SQLException {
    }

    public static ModeloAlumno getAlumno(String dni) {
        String sql = "SELECT * FROM Alumno WHERE dni = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new ModeloAlumno(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ObservableList<ModeloAlumno> getTodosAlumnos() {
        ObservableList<ModeloAlumno> listaAlumnos = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Alumno";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listaAlumnos.add(new ModeloAlumno(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaAlumnos;
    }

    public static boolean insertAlumno(ModeloAlumno alumno) {
        String sql = "INSERT INTO Alumno (dni, nombre, apellido1, apellido2) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, alumno.getDni());
            pstmt.setString(2, alumno.getNombre());
            pstmt.setString(3, alumno.getApellido1());
            pstmt.setString(4, alumno.getApellido2());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateAlumno(ModeloAlumno alumno) {
        String sql = "UPDATE Alumno SET nombre = ?, apellido1 = ?, apellido2 = ? WHERE dni = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, alumno.getNombre());
            pstmt.setString(2, alumno.getApellido1());
            pstmt.setString(3, alumno.getApellido2());
            pstmt.setString(4, alumno.getDni());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteAlumno(String dni) {
        String sql = "DELETE FROM Alumno WHERE dni = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean existeAlumno(String dni) {
        String sql = "SELECT dni FROM Alumno WHERE dni = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
