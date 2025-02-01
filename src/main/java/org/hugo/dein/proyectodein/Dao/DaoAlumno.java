package org.hugo.dein.proyectodein.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;

import java.sql.*;

/**
 * Clase DAO para gestionar las operaciones relacionadas con los alumnos en la base de datos.
 * Esta clase permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los datos de los alumnos.
 */
public class DaoAlumno {

    private static Connection conn;

    static {
        conn = ConexionBBDD.getConnection();
    }

    /**
     * Constructor por defecto de la clase DaoAlumno.
     * Establece la conexión con la base de datos.
     *
     * @throws SQLException Si ocurre un error al establecer la conexión a la base de datos.
     */
    public DaoAlumno() throws SQLException {
    }

    /**
     * Obtiene un alumno de la base de datos utilizando su DNI.
     *
     * @param dni El DNI del alumno que se desea obtener.
     * @return Un objeto ModeloAlumno con los datos del alumno, o null si no se encuentra.
     */
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

    /**
     * Obtiene todos los alumnos registrados en la base de datos.
     *
     * @return Una lista observable de todos los alumnos.
     */
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

    /**
     * Inserta un nuevo alumno en la base de datos.
     *
     * @param alumno El objeto ModeloAlumno que se desea insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
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

    /**
     * Actualiza los datos de un alumno en la base de datos.
     *
     * @param alumno El objeto ModeloAlumno con los nuevos datos del alumno.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
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

    /**
     * Elimina un alumno de la base de datos utilizando su DNI.
     *
     * @param dni El DNI del alumno que se desea eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
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

    /**
     * Verifica si un alumno existe en la base de datos utilizando su DNI.
     *
     * @param dni El DNI del alumno que se desea verificar.
     * @return true si el alumno existe, false en caso contrario.
     */
    public static boolean comprobarSiExiste(String dni) {
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
