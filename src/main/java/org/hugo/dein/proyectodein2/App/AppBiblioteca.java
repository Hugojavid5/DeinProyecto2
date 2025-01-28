package org.hugo.dein.proyectodein2.App;

import org.hugo.dein.proyectodein2.BBDD.ConexionBBDD;
import java.sql.Connection;
import java.sql.SQLException;
public class AppBiblioteca {
        public static void main(String[] args) {
            try {
                // Intentamos establecer la conexión
                ConexionBBDD conexionBBDD = new ConexionBBDD();
                Connection connection = conexionBBDD.getConnection();

                // Verificamos si la conexión es válida
                if (connection != null && !connection.isClosed()) {
                    System.out.println("Conexión exitosa a la base de datos.");
                } else {
                    System.out.println("Error en la conexión a la base de datos.");
                }

                // Cerramos la conexión después de verificar
                conexionBBDD.closeConnection();
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            }
        }
}
