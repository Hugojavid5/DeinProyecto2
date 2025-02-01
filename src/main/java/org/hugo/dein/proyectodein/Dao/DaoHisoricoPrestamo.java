package org.hugo.dein.proyectodein.Dao;

import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import org.hugo.dein.proyectodein.Modelos.ModeloHistoricoPrestamo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DaoHisoricoPrestamo {

    private static Connection conn;

    static {
        conn = ConexionBBDD.getConnection();
    }

    /**
     * Obtiene un historial de préstamo específico a partir de su ID.
     *
     * @param idPrestamo El ID del préstamo a buscar.
     * @return El historial de préstamo correspondiente al ID, o null si no se encuentra.
     */
    public static ModeloHistoricoPrestamo getHistorialPrestamo(int idPrestamo) {
        String sql = "SELECT * FROM Historico_prestamo WHERE id_prestamo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idPrestamo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new ModeloHistoricoPrestamo(
                        rs.getInt("id_prestamo"),
                        DaoAlumno.getAlumno(rs.getString("dni_alumno")),
                        DaoLibro.getLibro(rs.getInt("codigo_libro")),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime(),
                        rs.getTimestamp("fecha_devolucion").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene todos los historiales de préstamo almacenados.
     *
     * @return Una lista con todos los historiales de préstamo.
     */
    public static List<ModeloHistoricoPrestamo> getTodosHistorialPrestamo() {
        List<ModeloHistoricoPrestamo> listaHistorial = new ArrayList<>();
        String sql = "SELECT * FROM Historico_prestamo";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listaHistorial.add(new ModeloHistoricoPrestamo(
                        rs.getInt("id_prestamo"),
                        DaoAlumno.getAlumno(rs.getString("dni_alumno")),
                        DaoLibro.getLibro(rs.getInt("codigo_libro")),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime(),
                        rs.getTimestamp("fecha_devolucion").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaHistorial;
    }

    /**
     * Inserta un nuevo historial de préstamo en la base de datos.
     *
     * @param prestamo El objeto de historial de préstamo a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public static boolean insertHistorialPrestamo(ModeloHistoricoPrestamo prestamo) {
        String sql = "INSERT INTO Historico_prestamo (dni_alumno, codigo_libro, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, prestamo.getAlumno().getDni());
            pstmt.setInt(2, prestamo.getLibro().getCodigo());
            pstmt.setTimestamp(3, Timestamp.valueOf(prestamo.getFecha_prestamo()));
            pstmt.setTimestamp(4, Timestamp.valueOf(prestamo.getFecha_devolucion()));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Obtiene el historial de préstamo de un alumno específico por su DNI.
     *
     * @param dniAlumno El DNI del alumno.
     * @return Una lista de historiales de préstamo para el alumno indicado.
     */
    public static List<ModeloHistoricoPrestamo> getHistorialPrestamoByAlumno(String dniAlumno) {
        List<ModeloHistoricoPrestamo> listaHistorial = new ArrayList<>();
        String sql = "SELECT * FROM Historico_prestamo WHERE dni_alumno = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dniAlumno);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                listaHistorial.add(new ModeloHistoricoPrestamo(
                        rs.getInt("id_prestamo"),
                        DaoAlumno.getAlumno(dniAlumno),
                        DaoLibro.getLibro(rs.getInt("codigo_libro")),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime(),
                        rs.getTimestamp("fecha_devolucion").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaHistorial;
    }

    /**
     * Obtiene el historial de préstamo de un libro específico por su código.
     *
     * @param codigoLibro El código del libro.
     * @return Una lista de historiales de préstamo para el libro indicado.
     */
    public static List<ModeloHistoricoPrestamo> getHistorialPrestamoByLibro(int codigoLibro) {
        List<ModeloHistoricoPrestamo> listaHistorial = new ArrayList<>();
        String sql = "SELECT * FROM Historico_prestamo WHERE codigo_libro = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codigoLibro);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                listaHistorial.add(new ModeloHistoricoPrestamo(
                        rs.getInt("id_prestamo"),
                        DaoAlumno.getAlumno(rs.getString("dni_alumno")),
                        DaoLibro.getLibro(codigoLibro),
                        rs.getTimestamp("fecha_prestamo").toLocalDateTime(),
                        rs.getTimestamp("fecha_devolucion").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaHistorial;
    }
}
