package org.hugo.dein.proyectodein;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;


import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * La clase pantallaBiblioteca gestiona la inicialización y el lanzamiento de la ventana principal
 * de la biblioteca, cargando el archivo FXML correspondiente y mostrando la interfaz.
 */
public class AppBiblioteca extends Application {

    static Stage stage;  // La ventana principal de la aplicación

    /**
     * Metodo que se llama al iniciar la aplicación.
     * Carga el archivo FXML para la interfaz de la biblioteca y muestra la ventana principal.
     *
     * @param s El escenario principal que se utiliza para mostrar la interfaz.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage s) throws IOException {
        // Cargar la configuración de la conexión (si es necesario)
        Properties connConfig = ConexionBBDD.loadProperties();

        // Establecer el escenario principal
        stage = s;

        // Cargar las propiedades del idioma
        Properties properties = ConexionBBDD.cargarIdioma();
        String lang = properties.getProperty("language");

        // Verificar si la propiedad 'language' está correctamente cargada
        if (lang == null || lang.isEmpty()) {
            // Si no se ha encontrado, podemos asignar un valor por defecto
            lang = "es"; // Por ejemplo, español
        }

        // Crear el objeto Locale basado en el idioma cargado
        Locale locale = new Locale(lang);

        // Intentar cargar el ResourceBundle, capturando excepciones si no se encuentra el archivo
        ResourceBundle bundle = null;
        try {
            bundle = ResourceBundle.getBundle("languages.lang", locale);
        } catch (MissingResourceException e) {
            System.out.println("Error al cargar el archivo de idioma: " + e.getMessage());
            // Como fallback, podemos cargar el idioma en español si no se encuentra el archivo
            bundle = ResourceBundle.getBundle("languages.lang", new Locale("es"));
        }

        // Cargar el archivo FXML para la interfaz de la biblioteca
        FXMLLoader fxmlLoader = new FXMLLoader(AppBiblioteca.class.getResource("/fxml/biblioGeneral.fxml"));
        // Establecer el ResourceBundle en el FXMLLoader
        fxmlLoader.setResources(bundle);

        // Crear la escena con el archivo FXML cargado
        Scene scene = new Scene(fxmlLoader.load());

        // Configurar la ventana
        stage.setTitle(bundle.getString("tituloapp"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Devuelve el escenario principal de la aplicación.
     *
     * @return El escenario principal de la aplicación.
     */
    public static Stage getStage() {
        return stage;  // Retorna el escenario principal
    }

    /**
     * Metodo principal que se utiliza para iniciar la aplicación.
     *
     * @param args Argumentos de línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        launch();  // Lanza la aplicación JavaFX
    }
}