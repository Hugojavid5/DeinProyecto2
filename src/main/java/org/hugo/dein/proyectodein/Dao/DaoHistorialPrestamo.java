package org.hugo.dein.proyectodein.Dao;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hugo.dein.proyectodein.Modelos.ModeloHistoricoPrestamo;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DaoHistorialPrestamo {
    private static final Logger logger = LoggerFactory.getLogger(DaoHistorialPrestamo.class);


    public static ModeloHistoricoPrestamo getHistorialPrestamo(String id_prestamo) {
        ConexionBBDD connection;
        ModeloHistoricoPrestamo prestamo = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_prestamo,dni_alumno,codigo_libro,fecha_prestamo,fecha_devolucion FROM Historico_prestamo WHERE id_prestamo = ?";
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
                LocalDateTime fecha_devolucion = rs.getTimestamp("fecha_devolucion").toLocalDateTime();
                prestamo = new ModeloHistoricoPrestamo(id_prestamo_db, alumno, libro, fecha_prestamo, fecha_devolucion);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return prestamo;
    }

    public static ObservableList<ModeloHistoricoPrestamo> historialDeAlumno(ModeloAlumno alumno) {
        ConexionBBDD connection;
        ObservableList<ModeloHistoricoPrestamo> prestamos = FXCollections.observableArrayList();
        try{
            connection = new ConexionBBDD();
            String consulta = "SELECT id_prestamo,dni_alumno,codigo_libro,fecha_prestamo,fecha_devolucion FROM Historico_prestamo WHERE dni_alumno = ?";
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
                LocalDateTime fecha_devolucion = rs.getTimestamp("fecha_devolucion").toLocalDateTime();
                ModeloHistoricoPrestamo prestamo = new ModeloHistoricoPrestamo(id_prestamo_db, alumno_db, libro, fecha_prestamo, fecha_devolucion);
                prestamos.add(prestamo);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return prestamos;
    }

    public static ObservableList<ModeloHistoricoPrestamo> cargarListado() {
        ConexionBBDD connection;
        ObservableList<ModeloHistoricoPrestamo> prestamos = FXCollections.observableArrayList();
        try{
            connection = new ConexionBBDD();
            String consulta = "SELECT id_prestamo,dni_alumno,codigo_libro,fecha_prestamo,fecha_devolucion FROM Historico_prestamo";
            PreparedStatement ps = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_prestamo_db = rs.getInt("id_prestamo");
                String dni_alumno = rs.getString("dni_alumno");
                ModeloAlumno alumno = DaoAlumno.getAlumno(dni_alumno);
                int codigo_libro = rs.getInt("codigo_libro");
                ModeloLibro libro = DaoLibro.getLibro(codigo_libro);
                LocalDateTime fecha_prestamo = rs.getTimestamp("fecha_prestamo").toLocalDateTime();
                LocalDateTime fecha_devolucion = rs.getTimestamp("fecha_devolucion").toLocalDateTime();
                ModeloHistoricoPrestamo prestamo = new ModeloHistoricoPrestamo(id_prestamo_db, alumno, libro, fecha_prestamo, fecha_devolucion);
                prestamos.add(prestamo);
            }
            rs.close();
            connection.closeConnection();
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return prestamos;
    }

    public static boolean modificar(ModeloHistoricoPrestamo prestamo) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Historico_prestamo SET dni_alumno = ?,codigo_libro = ?,fecha_prestamo = ?,fecha_devolucion = ? WHERE id_prestamo = ?";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, prestamo.getAlumno().getDni());
            ps.setInt(2, prestamo.getLibro().getCodigo());
            ps.setTimestamp(3, Timestamp.valueOf(prestamo.getFecha_prestamo()));
            ps.setTimestamp(4, Timestamp.valueOf(prestamo.getFecha_devolucion()));
            ps.setInt(5, prestamo.getId_prestamo());
            int filasAfectadas = ps.executeUpdate();
            ps.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public  static boolean insertar(ModeloHistoricoPrestamo prestamo) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Historico_prestamo (id_prestamo,dni_alumno,codigo_libro,fecha_prestamo,fecha_devolucion) VALUES (?,?,?,?,?) ";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setInt(1, prestamo.getId_prestamo());
            ps.setString(2, prestamo.getAlumno().getDni());
            ps.setInt(3, prestamo.getLibro().getCodigo());
            ps.setTimestamp(4, Timestamp.valueOf(prestamo.getFecha_prestamo()));
            ps.setTimestamp(5, Timestamp.valueOf(prestamo.getFecha_devolucion()));
            int filasAfectadas = ps.executeUpdate();
            ps.close();
            connection.closeConnection();
            return (filasAfectadas > 0);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public static boolean eliminar(ModeloHistoricoPrestamo prestamo) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Historico_prestamo WHERE id_prestamo = ?";
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
