package org.hugo.dein.proyectodein.Dao;

import org.hugo.dein.proyectodein.Modelos.ModeloLibro;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class DaoLibro {

    private static final Logger logger = LoggerFactory.getLogger(DaoLibro.class);

    public static ModeloLibro getLibro(int codigo) {
        ConexionBBDD connection;
        ModeloLibro libro = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT codigo,titulo,autor,editorial,estado,baja,imagen FROM Libro WHERE codigo = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(consulta);
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int codigo_db = rs.getInt("codigo");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                String estado = rs.getString("estado");
                int baja = rs.getInt("baja");
                Blob portada = rs.getBlob("imagen");
                libro = new ModeloLibro(codigo_db, titulo, autor, editorial, estado, baja, portada);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return libro;
    }
    public static ObservableList<ModeloLibro> todosLibrosActivos() {
        ConexionBBDD connection;
        ObservableList<ModeloLibro> libros = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT codigo,titulo,autor,editorial,estado,baja,imagen FROM Libro WHERE baja=0";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt(1);
                String titulo = rs.getString(2);
                String autor = rs.getString(3);
                String editorial = rs.getString(4);
                String estado = rs.getString(5);
                int baja = rs.getInt(6);
                Blob foto = rs.getBlob(7);
                ModeloLibro l=new ModeloLibro(codigo,titulo,autor,editorial,estado,baja,foto);
                libros.add(l);
            }
            rs.close();
            connection.closeConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return libros;
    }


    public static Blob convertBytesToBlob(byte[] bytes) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            try (Connection conn = connection.getConnection()) {
                Blob blob = conn.createBlob();
                blob.setBytes(1, bytes);
                return blob;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    public static boolean esEliminable(ModeloLibro libro) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            // Prestamos
            String consulta = "SELECT count(*) as cont FROM Prestamo WHERE codigo_libro = ?";
            PreparedStatement ps = connection.getConnection().prepareStatement(consulta);
            ps.setInt(1, libro.getCodigo());
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
            consulta = "SELECT count(*) as cont FROM Historico_prestamo WHERE codigo_libro = ?";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setInt(1, libro.getCodigo());
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

    public static boolean modificar(ModeloLibro libro) {
        ConexionBBDD connection;
        PreparedStatement ps;
        System.out.println(libro.getTitulo());
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Libro SET titulo = ?,autor = ?,editorial = ?,estado = ?,baja = ?,portada = ? WHERE codigo = ?";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setString(4, libro.getEstado());
            ps.setInt(5, libro.getBaja());
            ps.setBlob(6, libro.getPortada());
            ps.setInt(7, libro.getCodigo());
            int filasAfectadas = ps.executeUpdate();
            ps.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public static boolean darDeBaja(ModeloLibro libro) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Libro SET baja = 1 WHERE codigo = ?";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setInt(1, libro.getCodigo());
            int filasAfectadas = ps.executeUpdate();
            ps.close();
            connection.closeConnection();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public  static int insertar(ModeloLibro libro) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Libro (titulo,autor,editorial,estado,baja,portada) VALUES (?,?,?,?,?,?) ";
            ps = connection.getConnection().prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setString(4, libro.getEstado());
            ps.setInt(5, libro.getBaja());
            ps.setBlob(6, libro.getPortada());
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


    public static boolean eliminar(ModeloLibro libro) {
        ConexionBBDD connection;
        PreparedStatement ps;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Libro WHERE codigo = ?";
            ps = connection.getConnection().prepareStatement(consulta);
            ps.setInt(1, libro.getCodigo());
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
