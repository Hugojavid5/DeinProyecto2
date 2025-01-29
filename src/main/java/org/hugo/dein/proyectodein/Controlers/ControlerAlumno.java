package org.hugo.dein.proyectodein.Controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;

public class ControlerAlumno {

    @FXML
    private TextField txt_ape1;

    @FXML
    private TextField txt_ape2;

    @FXML
    private TextField txt_dni;
    @FXML
    private TextField txt_nombre;

    private Runnable onCloseCallback;
    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    @FXML
    void cancelar(ActionEvent event) {
        // Ejecutar el callback si está configurado
        if (onCloseCallback != null) {
            onCloseCallback.run();
        }

        // Cerrar la ventana
        Stage stage = (Stage) txt_dni.getScene().getWindow();
        stage.close();
    }

    @FXML
    void guardarAlumno(ActionEvent event) {
        StringBuilder errores = new StringBuilder();

        // Validar los campos
        String dni = txt_dni.getText();
        String nombre = txt_nombre.getText();
        String primerApellido = txt_ape1.getText();
        String segundoApellido = txt_ape2.getText();

        if (dni == null || dni.isEmpty() || !dni.matches("\\d{8}[A-Za-z]")) {
            errores.append("- El DNI debe tener 8 números seguidos de una letra (ejemplo: 12345678A).\n");
        }

        if (nombre == null || nombre.isEmpty()) {
            errores.append("- El nombre no puede estar vacío.\n");
        }

        if (primerApellido == null || primerApellido.isEmpty()) {
            errores.append("- El primer apellido no puede estar vacío.\n");
        }

        if (segundoApellido == null || segundoApellido.isEmpty()) {
            errores.append("- El segundo apellido no puede estar vacío.\n");
        }

        // Mostrar errores si los hay
        if (errores.length() > 0) {
            mostrarAlerta("Errores de Validación", errores.toString());
            return;
        }


        ModeloAlumno alumno = new ModeloAlumno();
        alumno.setDni(dni);
        alumno.setNombre(nombre);
        alumno.setApellido1(primerApellido);
        alumno.setApellido2(segundoApellido);

        // Insertar el alumno en la base de datos
        try {
            DaoAlumno.insertAlumno(alumno);
            mostrarAlerta("Éxito", "El alumno ha sido registrado correctamente.");

            // Cerrar la ventana después de guardar
            cancelar(event);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo registrar el alumno: " + e.getMessage());
        }
    }

    private ModeloAlumno alumnos;

    public void setAlumnos(ModeloAlumno alumnos) {
        this.alumnos = alumnos;
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
