package org.hugo.dein.proyectodein.Controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;
import org.hugo.dein.proyectodein.Dao.DaoAlumno;

public class ControlerAlumno {
    public ControlerAlumno(ModeloAlumno a) {
        alumnos = a;
    }
    public ControlerAlumno(){
        this(null);
    }
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

        // Verificar que el alumno ya exista y que se trata de una modificación
        if (alumnos != null) {
            // Modificar el alumno existente
            txt_dni.setDisable(true);
            alumnos.setNombre(nombre);
            alumnos.setApellido1(primerApellido);
            alumnos.setApellido2(segundoApellido);

            // Actualizar el alumno en la base de datos
            try {
                DaoAlumno.modificar(alumnos);  // Actualizamos el alumno
                mostrarAlerta("Éxito", "El alumno ha sido actualizado correctamente.");

                // Ejecutar el callback para actualizar la tabla
                if (onCloseCallback != null) {
                    onCloseCallback.run();
                }

                cancelar(event);  // Cerrar la ventana
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo actualizar el alumno: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Error", "No se ha seleccionado un alumno para modificar.");
        }
    }



    private final ModeloAlumno alumnos;

    public void initialize() {
        if (alumnos != null) {
            txt_dni.setText(alumnos.getDni());
            txt_dni.setDisable(true);
            txt_nombre.setText(alumnos.getNombre());
            txt_ape1.setText(alumnos.getApellido1());
            txt_ape2.setText(alumnos.getApellido2());
        }
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
