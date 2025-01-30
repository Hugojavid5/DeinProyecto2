package org.hugo.dein.proyectodein.Controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;
import org.hugo.dein.proyectodein.Dao.DaoLibro;

import java.io.IOException;

public class ControlerGeneral {

    @FXML
    private Button btt_aniadir;

    @FXML
    private Button btt_baja;

    @FXML
    private Button btt_modificar;

    @FXML
    private ComboBox<?> cbFiltroHistorico;

    @FXML
    private Tab colAlumnos;

    @FXML
    private Tab colLibros;

    @FXML
    private TableColumn<String, ModeloAlumno> col_ape1;

    @FXML
    private TableColumn<String, ModeloAlumno> col_dni;

    @FXML
    private TableColumn<String, ModeloAlumno> col_nombre;

    @FXML
    private ImageView imgView_biblio;

    @FXML
    private ImageView imgView_biblio1;

    @FXML
    private Label lbl_titulo;

    @FXML
    private MenuBar menuBar_iia;

    @FXML
    private MenuItem menuItem_ayuda;

    @FXML
    private MenuItem menuItem_espaniol;

    @FXML
    private MenuItem menuItem_euskera;

    @FXML
    private MenuItem menuItem_inforDatos;

    @FXML
    private MenuItem menuItem_inforGra;

    @FXML
    private MenuItem menuItem_inforLib;

    @FXML
    private Menu menu_ayuda;

    @FXML
    private Menu menu_idioma;

    @FXML
    private Menu menu_informes;

    @FXML
    private TableView<ModeloAlumno> tablaAlumnos;

    @FXML
    private TabPane tablaGeneral;

    @FXML
    private TableView<ModeloLibro> tablaLibros;

    @FXML
    private TableColumn<?, ?> tcAutorTabLibros;

    @FXML
    private TableColumn<?, ?> tcBajaTabLibros;

    @FXML
    private TableColumn<?, ?> tcEditorialTabLibros;

    @FXML
    private TableColumn<?, ?> tcEstadoTabLibros;

    @FXML
    private TableColumn<?, ?> tcTituloTabLibros;

    @FXML
    private TextField tfFiltrarPrestamo;

    @FXML
    private TextField txt_filtarAlumn;

    @FXML
    private TextField txt_filtrarLibros;

    @FXML
    private VBox vbox_general;

    @FXML
    void aniadirAlumno(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/alumnos.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Añadir Alumno");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
     }
    }

    @FXML
    void aniadirLibro(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/libro.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Añadir Libro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
           // stage.setOnHidden(windowEvent -> cargarLibros());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void bajaLibro(ActionEvent event) {
        // Primero, obtenemos el libro seleccionado en la tabla
        ModeloLibro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        // Si no hay libro seleccionado, mostramos un mensaje de error
        if (libroSeleccionado == null) {
            mostrarAlerta("Error", "No has seleccionado ningún libro", "Por favor, selecciona un libro para dar de baja.");
            return;
        }

        // Si hay libro seleccionado, mostramos una ventana emergente de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Baja");
        alert.setHeaderText("¿Estás seguro de que deseas dar de baja el libro?");
        alert.setContentText("Libro: " + libroSeleccionado.getTitulo());

        // Si el usuario confirma, llamamos al metodo bajaDelLibro
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Llamamos al metodo bajaDelLibro para dar de baja el libro en la base de datos
                boolean exito = DaoLibro.darDeBaja(libroSeleccionado);

                // Mostramos un mensaje dependiendo si la operación fue exitosa o no
                if (exito) {
                    mostrarAlerta("Éxito", "Libro dado de baja", "El libro ha sido dado de baja correctamente.");
                    // Aquí podríamos actualizar la tabla para reflejar los cambios
                    //cargarDatosTablas();
                } else {
                    mostrarAlerta("Error", "No se pudo dar de baja el libro", "Hubo un error al intentar dar de baja el libro.");
                }
            }
        });
    }

    @FXML
    void cargarGuia(ActionEvent event) {

    }

    @FXML
    void cargarInforme2(ActionEvent event) {

    }

    @FXML
    void cargarInforme3(ActionEvent event) {

    }

    @FXML
    void cargarInforme4(ActionEvent event) {

    }

    @FXML
    void devolverLibro(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/devolucion.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Devolver un prestamo");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            // stage.setOnHidden(windowEvent -> cargarPrestamos());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void filtrarAlumno(ActionEvent event) {

    }

    @FXML
    void filtrarHistorico(ActionEvent event) {

    }

    @FXML
    void filtrarLibros(ActionEvent event) {

    }

    @FXML
    void filtrarPrestamo(ActionEvent event) {

    }

    @FXML
    void idiomaEspaniol(ActionEvent event) {

    }

    @FXML
    void idiomaIngles(ActionEvent event) {

    }

    @FXML
    void modificarAlumno(ActionEvent event) {
        ModeloAlumno alumno=tablaAlumnos.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/alumnos.fxml"));
            Parent root = fxmlLoader.load();

            ControlerAlumno controller=fxmlLoader.getController();
            controller.setAlumnos(alumno);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Editar alumno");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            // stage.setOnHidden(windowEvent -> cargarAlumnos());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void modificarLibro(ActionEvent event) {
        ModeloLibro libro=tablaLibros.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/libro.fxml"));
            Parent root = fxmlLoader.load();

            ControlerLibros controller=fxmlLoader.getController();
            controller.setLibro(libro);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Editar Libro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
           // stage.setOnHidden(windowEvent -> cargarLibros());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void prestarLibro(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/prestamo.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Prestar un libro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            // stage.setOnHidden(windowEvent -> cargarPrestamos());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Metodo para mostrar alertas
    private void mostrarAlerta(String titulo, String cabecera, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
