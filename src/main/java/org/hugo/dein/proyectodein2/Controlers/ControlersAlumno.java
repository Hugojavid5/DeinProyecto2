package org.hugo.dein.proyectodein2.Controlers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.hugo.dein.proyectodein2.Modelos.ModeloAlumno;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControlersAlumno {

    // Elementos del FXML
    @FXML private ImageView logotipo; // Logotipo de la biblioteca
    @FXML private Text nombreBiblioteca; // Nombre de la biblioteca
    @FXML private Text tituloAlumnos; // Título de la página
    @FXML private TextField dniField; // Campo DNI
    @FXML private TextField nombreField; // Campo Nombre
    @FXML private TextField apellido1Field; // Campo Apellido 1
    @FXML private TextField apellido2Field; // Campo Apellido 2
    @FXML private TableView<ModeloAlumno> tablaAlumnos; // Tabla para mostrar los alumnos
    @FXML private TableColumn<ModeloAlumno, String> dniColumna; // Columna DNI
    @FXML private TableColumn<ModeloAlumno, String> nombreColumna; // Columna Nombre
    @FXML private TableColumn<ModeloAlumno, String> apellido1Columna; // Columna Apellido 1
    @FXML private TableColumn<ModeloAlumno, String> apellido2Columna; // Columna Apellido 2
    @FXML private Button guardarBtn; // Botón Guardar
    @FXML private Button editarBtn; // Botón Editar
    @FXML private Button eliminarBtn; // Botón Eliminar
    @FXML private Text piePagina; // Pie de página
    @FXML private Text numeroPagina; // Número de página

    // Lista observable de alumnos
    private ObservableList<ModeloAlumno> listaAlumnos = FXCollections.observableArrayList();

    public void initialize() {
        // Inicializar los datos adicionales de la biblioteca
        nombreBiblioteca.setText("Biblioteca Central");
        tituloAlumnos.setText("Gestión de Alumnos");

        // Establecer el logotipo de la biblioteca
        Image logo = new Image("file:src/resources/images/logotipo.png"); // Asegúrate de tener la ruta correcta de la imagen
        logotipo.setImage(logo);

        // Configurar la tabla de alumnos
        dniColumna.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
        nombreColumna.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        apellido1Columna.setCellValueFactory(cellData -> cellData.getValue().apellido1Property());
        apellido2Columna.setCellValueFactory(cellData -> cellData.getValue().apellido2Property());

        // Establecer el pie de página y el número de página
        piePagina.setText("© 2025 Biblioteca Central");
        numeroPagina.setText("Página 1");

        // Cargar los datos de la tabla (esto se puede mejorar dependiendo de tu lógica de base de datos)
        cargarTablaAlumnos();
    }

    private void cargarTablaAlumnos() {
        // Aquí deberías cargar los alumnos desde la base de datos o archivo
        // Por ahora vamos a agregar algunos datos de ejemplo
        listaAlumnos.clear();
        listaAlumnos.add(new ModeloAlumno("12345678A", "Juan", "Pérez", "González"));
        listaAlumnos.add(new ModeloAlumno("23456789B", "Ana", "López", "Martínez"));

        tablaAlumnos.setItems(listaAlumnos);
    }

    // Acción para guardar un nuevo alumno
    @FXML
    private void guardarAlumno() {
        String dni = dniField.getText();
        String nombre = nombreField.getText();
        String apellido1 = apellido1Field.getText();
        String apellido2 = apellido2Field.getText();

        // Lógica para guardar el alumno
        ModeloAlumno nuevoAlumno = new ModeloAlumno(dni, nombre, apellido1, apellido2);
        listaAlumnos.add(nuevoAlumno);  // Agregar el nuevo alumno a la lista

        // Limpiar los campos después de guardar
        dniField.clear();
        nombreField.clear();
        apellido1Field.clear();
        apellido2Field.clear();

        // Volver a cargar la tabla de alumnos
        cargarTablaAlumnos();
    }

    // Acción para editar un alumno
    @FXML
    private void editarAlumno() {
        ModeloAlumno alumnoSeleccionado = tablaAlumnos.getSelectionModel().getSelectedItem();

        if (alumnoSeleccionado != null) {
            dniField.setText(alumnoSeleccionado.getDni());
            nombreField.setText(alumnoSeleccionado.getNombre());
            apellido1Field.setText(alumnoSeleccionado.getApellido1());
            apellido2Field.setText(alumnoSeleccionado.getApellido2());
        }
    }

    // Acción para eliminar un alumno
    @FXML
    private void eliminarAlumno() {
        ModeloAlumno alumnoSeleccionado = tablaAlumnos.getSelectionModel().getSelectedItem();

        if (alumnoSeleccionado != null) {
            listaAlumnos.remove(alumnoSeleccionado);  // Eliminar el alumno de la lista

            // Volver a cargar la tabla de alumnos
            cargarTablaAlumnos();
        }
    }
}
