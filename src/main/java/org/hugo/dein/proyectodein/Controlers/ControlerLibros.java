package org.hugo.dein.proyectodein.Controlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hugo.dein.proyectodein.Dao.DaoLibro;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;

import javax.enterprise.inject.Model;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Controlador para gestionar las operaciones relacionadas con los libros en la interfaz de usuario.
 * Permite agregar, editar, eliminar y cargar libros, incluyendo la gestión de imágenes asociadas.
 */
public class ControlerLibros {

    @FXML
    private Button btt_borrar;

    @FXML
    private Button btt_cancelar;

    @FXML
    private Button btt_guardar;

    @FXML
    private Button btt_seleccionar;

    @FXML
    private ComboBox<String> comboEstado;

    @FXML
    private ImageView img_portada;

    @FXML
    private Label lbl_imagen;

    @FXML
    private TextField txt_autor;

    @FXML
    private TextField txt_editorial;

    @FXML
    private TextField txt_libro;

    private Runnable onCloseCallback;
    private ModeloLibro libroSeleccionado;
    private byte[] imagenBytes;

    /**
     * Establece el libro seleccionado y carga sus datos en la interfaz de usuario.
     * @param libroSeleccionado El libro que se desea cargar en la interfaz.
     */
    public void setLibroSeleccionado(ModeloLibro libroSeleccionado) {
        this.libroSeleccionado = libroSeleccionado;
        cargarDatosLibro(libroSeleccionado); // Cargar los datos del libro en los campos de la interfaz
    }

    /**
     * Establece el callback que se ejecutará al cerrar la ventana.
     * @param onCloseCallback El callback a ejecutar al cerrar la ventana.
     */
    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    /**
     * Inicializa el controlador, configurando la lista de estados de los libros en el ComboBox.
     */
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

    /**
     * Borra la imagen de portada y la reemplaza por una imagen predeterminada.
     * @param event El evento que dispara la acción de borrar la imagen.
     */
    @FXML
    void borrarImagen(ActionEvent event) {
        // Cargar la imagen desde la carpeta "imagenes" en resources
        String imagePath = getClass().getResource("/imagenes/portada.jpg").toExternalForm();
        img_portada.setImage(new Image(imagePath));

        // Convertir la imagen a bytes
        imagenBytes = convertirImagenABytes(new Image(imagePath));
    }

    /**
     * Convierte una imagen a un arreglo de bytes.
     * @param image La imagen a convertir.
     * @return Un arreglo de bytes que representa la imagen.
     */
    private byte[] convertirImagenABytes(Image image) {
        try {
            // Cargar la imagen desde la carpeta "imagenes" en resources
            InputStream inputStream = getClass().getResourceAsStream("/imagenes/portada.jpg");

            if (inputStream == null) {
                mostrarAlerta("Error", "No se pudo encontrar el recurso.");
                return null;
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo convertir la imagen: " + e.getMessage());
            return null;
        }
    }

    /**
     * Cancela los cambios y cierra la ventana, ejecutando el callback si está configurado.
     * @param event El evento que dispara la acción de cancelar cambios.
     */
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

    /**
     * Carga los datos de un libro seleccionado en los campos de la interfaz de usuario.
     * @param libroSeleccionado El libro cuyos datos se desean cargar.
     */
    public void cargarDatosLibro(ModeloLibro libroSeleccionado) {
        if (libroSeleccionado != null) {
            // Rellenar los campos con los datos del libro seleccionado
            txt_libro.setText(libroSeleccionado.getTitulo());
            txt_autor.setText(libroSeleccionado.getAutor());
            txt_editorial.setText(libroSeleccionado.getEditorial());
            comboEstado.setValue(libroSeleccionado.getEstado());

            // Cargar la imagen
            byte[] imagenBytes = BlobABytes(libroSeleccionado.getImagen());
            if (imagenBytes != null && imagenBytes.length > 0) {
                Image image = new Image(new ByteArrayInputStream(imagenBytes));
                img_portada.setImage(image);
                this.imagenBytes = imagenBytes;
            } else {
                // Cargar una imagen de la carpeta "imagenes" en resources
                String imagePath = getClass().getResource("/imagenes/portada.jpg").toExternalForm();
                img_portada.setImage(new Image(imagePath));
            }

        }
    }

    /**
     * Convierte un objeto Blob a un arreglo de bytes.
     * @param blob El Blob a convertir.
     * @return Un arreglo de bytes que representa el Blob.
     */
    public byte[] BlobABytes(java.sql.Blob blob) {
        if (blob == null) {
            return null;
        }
        try {
            InputStream inputStream = blob.getBinaryStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace(); // Si ocurre un error, imprímelo para depurar
            return null;
        }
    }

    /**
     * Guarda los cambios realizados en un libro. Si el libro tiene un ID, se actualiza, si no, se inserta como nuevo.
     * @param event El evento que dispara la acción de guardar los cambios.
     */
    @FXML
    void guardarCambios(ActionEvent event) {
        StringBuilder errores = new StringBuilder();

        // Validar los campos
        String titulo = txt_libro.getText();
        String autor = txt_autor.getText();
        String editorial = txt_editorial.getText();
        String estado = comboEstado.getValue(); // Estado seleccionado

        if (titulo == null || titulo.isEmpty()) {
            errores.append("El campo titulo no puede estar vacío.\n");
        }

        if (autor == null || autor.isEmpty()) {
            errores.append("El campo autor no puede estar vacío.\n");
        }

        if (editorial == null || editorial.isEmpty()) {
            errores.append("El campo editorial no puede estar vacía.\n");
        }

        if (estado == null || estado.isEmpty()) {
            errores.append("Selecciona un estado");
        }

        if (imagenBytes == null || imagenBytes.length == 0) {
            errores.append("Debes seleccionar una imagen.\n");
        }

        if (errores.length() > 0) {
            mostrarAlerta("Errores en la introducion de los campos", errores.toString());
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

            // Si el libro tiene un ID, significa que es una edición, si no, es una inserción
            if (libroSeleccionado != null && libroSeleccionado.getCodigo() != 0) {
                libro.setCodigo(libroSeleccionado.getCodigo());  // Asignar el código del libro para editarlo
                DaoLibro.updateLibro(libro);
                mostrarAlerta("Éxito", "El libro ha sido modificado correctamente.");
            } else {
                DaoLibro.insertLibro(libro);
                mostrarAlerta("Éxito", "El libro ha sido registrado correctamente.");
            }

            // Cerrar la ventana después de guardar
            cancelarCambios(event);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo registrar o modificar el libro: " + e.getMessage());
        }
    }

    /**
     * Permite seleccionar una imagen desde el sistema de archivos y cargarla en el ImageView.
     * @param event El evento que dispara la acción de seleccionar una imagen.
     */
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
                // Verificar el tamaño del archivo
                long fileSize = Files.size(file.toPath());
                if (fileSize > 64 * 1024) { // 64 KB en bytes
                    mostrarAlerta("Error", "La imagen seleccionada es demasiado grande. Debe ser menor a 64 KB.");
                    return; // No continuar con la carga de la imagen
                }

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

    /**
     * Muestra una alerta con el título y mensaje proporcionados.
     * @param titulo El título de la alerta.
     * @param mensaje El mensaje a mostrar en la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
