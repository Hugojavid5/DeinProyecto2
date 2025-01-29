package org.hugo.dein.proyectodein.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DaoAlumno {

    private static final Logger logger = LoggerFactory.getLogger(DaoAlumno.class);

    public static ModeloAlumno getAlumno(String dni) {
        ConexionBBDD connection;
        ModeloAlumno alumno = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT dni,nombre,apellido1,apellido2 FROM Alumno WHERE dni = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String dni_db = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                alumno = new ModeloAlumno(dni_db, nombre, apellido1, apellido2);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return alumno;
    }

    public static ObservableList<ModeloAlumno> cargarListado() {
        ConexionBBDD connection;
        ObservableList<ModeloAlumno> alumnos = FXCollections.observableArrayList();
        try{
            connection = new ConexionBBDD();
            String consulta = "SELECT dni,nombre,apellido1,apellido2 FROM Alumno";
            PreparedStatement ps = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                ModeloAlumno alumno = new ModeloAlumno(dni, nombre, apellido1, apellido2);
                alumnos.add(alumno);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return alumnos;
    }

    public static boolean esEliminable(ModeloAlumno alumno) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            // Prestamos
            String consulta = "SELECT count(*) as cont FROM Prestamo WHERE dni_alumno = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, alumno.getDni());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int cont = rs.getInt("cont");
                if (cont != 0) {
                    rs.close();
                    connection.closeConnection();
                    return false;
                }
            }
            rs.close();
            ps.close();
            // Historial_prestamos
            consulta = "SELECT count(*) as cont FROM Historico_prestamo WHERE dni_alumno = ?";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, alumno.getDni());
            rs = ps.executeQuery();
            if (rs.next()) {
                int cont = rs.getInt("cont");
                rs.close();
                connection.closeConnection();
                return (cont == 0);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public static boolean modificar(ModeloAlumno alumno) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Alumno SET nombre = ?,apellido1 = ?,apellido2 = ? WHERE dni = ?";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido1());
            ps.setString(3, alumno.getApellido2());
            ps.setString(4, alumno.getDni());
            int filasAfectadas = ps.executeUpdate();
            ps.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }


    public  static boolean insertar(ModeloAlumno alumno) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Alumno (dni,nombre,apellido1,apellido2) VALUES (?,?,?,?) ";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, alumno.getDni());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getApellido1());
            ps.setString(4, alumno.getApellido2());
            int filasAfectadas = ps.executeUpdate();
            connection.closeConnection();
            return (filasAfectadas > 0);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public static boolean eliminar(ModeloAlumno alumno) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Alumno WHERE dni = ?";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, alumno.getDni());
            int filasAfectadas = ps.executeUpdate();
            ps.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
