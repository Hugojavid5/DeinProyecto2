package org.hugo.dein.proyectodein;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AppBiblioteca extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML con la ruta correcta
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/biblioGeneral.fxml"));

        // Si está en el mismo paquete, la ruta sería algo como "/nombreDelArchivo.fxml"
        AnchorPane root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Biblioteca");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
