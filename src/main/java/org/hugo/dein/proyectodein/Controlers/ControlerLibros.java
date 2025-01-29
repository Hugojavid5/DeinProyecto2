package org.hugo.dein.proyectodein.Controlers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.hugo.dein.proyectodein.Dao.DaoLibro;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;
import javafx.collections.ObservableList;
import java.io.File;
import java.nio.file.Files;

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
    private Runnable onCloseCallback;
    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

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
        // Ejecutar el callback si está configurado
        if (onCloseCallback != null) {
            onCloseCallback.run();
        }

        // Cerrar la ventana
        Stage stage = (Stage) txt_libro.getScene().getWindow();
        stage.close();
    }

    @FXML
    void guardarCambios(ActionEvent event) {
        StringBuilder errores = new StringBuilder();

        // Validar los campos
        String titulo = txt_libro.getText();
        String autor = txt_autor.getText();
        String editorial = txt_editorial.getText();
        String estado = comboEstado.getValue(); // Estado seleccionado

        if (titulo == null || titulo.isEmpty()) {
            errores.append("- El título no puede estar vacío.\n");
        }

        if (autor == null || autor.isEmpty()) {
            errores.append("- El autor no puede estar vacío.\n");
        }

        if (editorial == null || editorial.isEmpty()) {
            errores.append("- La editorial no puede estar vacía.\n");
        }

        if (estado == null || estado.isEmpty()) {
            errores.append("- Debes seleccionar un estado para el libro.\n");
        }

        if (imagenBytes == null || imagenBytes.length == 0) {
            errores.append("- Debes seleccionar una imagen válida.\n");
        }

        if (errores.length() > 0) {
            mostrarAlerta("Errores de Validación", errores.toString());
            return;
        }

        // Crear el objeto LibroModel
        ModeloLibro libro = new ModeloLibro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setEstado(estado);

        // Convertir la imagen a Blob antes de insertar
        try {
            libro.setImagen(DaoLibro.convertirABlob(imagenBytes));
            DaoLibro.insertLibro(libro);
            mostrarAlerta("Éxito", "El libro ha sido registrado correctamente.");

            // Cerrar la ventana después de guardar
            cancelarCambios(event);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo registrar el libro: " + e.getMessage());
        }
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
        this.libro = libro;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
