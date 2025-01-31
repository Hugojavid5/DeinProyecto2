package org.hugo.dein.proyectodein.Dao;

import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;
import org.hugo.dein.proyectodein.Modelos.ModeloPrestamo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class DaoPrestamo {

    private static final Logger logger = LoggerFactory.getLogger(DaoPrestamo.class);


    public static ModeloPrestamo getPrestamo(String id_prestamo) {
        ConexionBBDD connection;
        ModeloPrestamo prestamo = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_prestamo,dni_alumno,codigo_libro,fecha_prestamo FROM Prestamo WHERE id_prestamo = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, id_prestamo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id_prestamo_db = rs.getInt("id_prestamo");
                String dni_alumno = rs.getString("dni_alumno");
                ModeloAlumno alumno = DaoAlumno.getAlumno(dni_alumno);
                int codigo_libro = rs.getInt("codigo_libro");
                ModeloLibro libro = DaoLibro.getLibro(codigo_libro);
                LocalDateTime fecha_prestamo = rs.getTimestamp("fecha_prestamo").toLocalDateTime();
                prestamo = new ModeloPrestamo(id_prestamo_db, alumno, libro, fecha_prestamo);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return prestamo;
    }

    public static ObservableList<ModeloPrestamo> prestamosDeAlumno(ModeloAlumno alumno) {
        ConexionBBDD connection;
        ObservableList<ModeloPrestamo> prestamos = FXCollections.observableArrayList();
        try{
            connection = new ConexionBBDD();
            String consulta = "SELECT id_prestamo,dni_alumno,codigo_libro,fecha_prestamo FROM Prestamo WHERE dni_alumno = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, alumno.getDni());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_prestamo_db = rs.getInt("id_prestamo");
                String dni_alumno = rs.getString("dni_alumno");
                ModeloAlumno alumno_db = DaoAlumno.getAlumno(dni_alumno);
                int codigo_libro = rs.getInt("codigo_libro");
                ModeloLibro libro = DaoLibro.getLibro(codigo_libro);
                LocalDateTime fecha_prestamo = rs.getTimestamp("fecha_prestamo").toLocalDateTime();
                ModeloPrestamo prestamo = new ModeloPrestamo(id_prestamo_db, alumno_db, libro, fecha_prestamo);
                prestamos.add(prestamo);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return prestamos;
    }

    public static ObservableList<ModeloPrestamo> cargarListado() {
        ConexionBBDD connection;
        ObservableList<ModeloPrestamo> prestamos = FXCollections.observableArrayList();
        try{
            connection = new ConexionBBDD();
            String consulta = "SELECT id_prestamo,dni_alumno,codigo_libro,fecha_prestamo FROM Prestamo";
            PreparedStatement ps = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prestamos.add(new ModeloPrestamo(
                        rs.getInt("id_prestamo"),
                        DaoAlumno.getAlumno(rs.getString("dni_alumno")),
                        DaoLibro.getLibro(rs.getInt("codigo_libro")),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime()
                ));
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return prestamos;
    }

    public static boolean modificar(ModeloPrestamo prestamo) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Prestamo SET dni_alumno = ?,codigo_libro = ?,fecha_prestamo = ? WHERE id_prestamo = ?";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, prestamo.getAlumno().getDni());
            ps.setInt(2, prestamo.getLibro().getCodigo());
            ps.setTimestamp(3, Timestamp.valueOf(prestamo.getFecha_prestamo()));
            ps.setInt(4, prestamo.getId_prestamo());
            int filasAfectadas = ps.executeUpdate();
            ps.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public  static int insertar(ModeloPrestamo prestamo) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Prestamo (dni_alumno,codigo_libro,fecha_prestamo) VALUES (?,?,?) ";
            ps = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, prestamo.getAlumno().getDni());
            ps.setInt(2, prestamo.getLibro().getCodigo());
            ps.setTimestamp(3, Timestamp.valueOf(prestamo.getFecha_prestamo()));
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    ps.close();
                    connection.closeConnection();
                    return id;
                }
            }
            ps.close();
            connection.closeConnection();
            return -1;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return -1;
        }
    }

    public static boolean eliminar(ModeloPrestamo prestamo) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Prestamo WHERE id_prestamo = ?";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setInt(1, prestamo.getId_prestamo());
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
