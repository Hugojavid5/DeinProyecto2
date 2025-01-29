package org.hugo.dein.proyectodein.Controlers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.hugo.dein.proyectodein.Dao.DaoLibro;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;
import javafx.collections.ObservableList;
import java.io.File;
import java.nio.file.Files;
import java.sql.Blob;

public class ControlerLibros {
    private byte[] imagenBytes;
    @FXML
    private Button btt_borraImg;

    @FXML
    private Button btt_cancelar;

    @FXML
    private Button btt_guardar;

    @FXML
    private Button btt_imagen;

    @FXML
    private ComboBox<String> comboEstado;

    @FXML
    private Label lbl_imagen;

    @FXML
    private TextField txt_autor;

    @FXML
    private TextField txt_editorial;

    @FXML
    private ImageView img_portada;

    @FXML
    private TextField txt_libro;

    @FXML
    public void initialize() {
        ObservableList<String> estados = FXCollections.observableArrayList(
                "Nuevo",
                "Usado nuevo",
                "Usado seminuevo",
                "Usado estropeado",
                "Restaurado"
        );
        comboEstado.setItems(estados);
    }


    @FXML
    void borrarImagen(ActionEvent event) {
        // Restablecer la imagen por defecto
        Image imagenPorDefecto = new Image(getClass().getResourceAsStream("/imagenes/portada.jpg"));
        img_portada.setImage(imagenPorDefecto);
    }



    @FXML
    void cancelarCambios(ActionEvent event) {

    }
    @FXML
    void guardarCambios(ActionEvent event) {

    }

    @FXML
    void seleccionarImagen(ActionEvent event) {
        // Abrir un explorador de archivos para seleccionar una imagen
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        // Obtener el archivo seleccionado
        File file = fileChooser.showOpenDialog(txt_libro.getScene().getWindow());
        if (file != null) {
            try {
                // Establecer la imagen seleccionada en el ImageView
                Image image = new Image(file.toURI().toString());
                img_portada.setImage(image);

                // Convertir la imagen seleccionada a un array de bytes
                imagenBytes = Files.readAllBytes(file.toPath());
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo cargar la imagen: " + e.getMessage());
            }
        }
    }

    private ModeloLibro libro;
    public void setLibro(ModeloLibro libro) {
        this.libro=libro;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

