package org.hugo.dein.proyectodein.Controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hugo.dein.proyectodein.Dao.DaoAlumno;
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

    // Referencia al controlador principal
    private Runnable onCloseCallback;
    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    private ModeloAlumno alumnoSeleccionado; // Variable para almacenar el alumno a modificar

    public void cargarDatosAlumno(ModeloAlumno alumno) {
        this.alumnoSeleccionado = alumno;
        txt_dni.setText(alumno.getDni());
        txt_nombre.setText(alumno.getNombre());
        txt_ape1.setText(alumno.getApellido1());
        txt_ape2.setText(alumno.getApellido2());
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
    void guardarLibro(ActionEvent event) {
        StringBuilder errores = new StringBuilder();

        // Validar los campos
        String dni = txt_dni.getText().toUpperCase();
        String nombre = txt_nombre.getText();
        String ap1 = txt_ape1.getText();
        String ape2 = txt_ape2.getText();
        if (dni == null || dni.isEmpty() || !dni.matches("\\d{8}[A-Za-z]")) {
            errores.append("El DNI debe tener 8 nums y una letra\n");
        }
        if (nombre == null || nombre.isEmpty()) {
            errores.append("El campo nombre no puede estar vacío.\n");
        }
        if (ap1 == null || ap1.isEmpty()) {
            errores.append("El campo del  primer apellido no puede estar vacío.\n");
        }

        if (ape2 == null || ape2.isEmpty()) {
            errores.append("El campo  segundo apellido no puede estar vacío.\n");
        }
        // Mostrar errores si los hay
        if (errores.length() > 0) {
            mostrarAlerta("Hay error en algun campo", errores.toString());
            return;
        }
        // Crear un objeto ModeloAlumno con los nuevos datos
        ModeloAlumno alumno = new ModeloAlumno();
        alumno.setDni(dni);
        alumno.setNombre(nombre);
        alumno.setApellido1(ap1);
        alumno.setApellido2(ape2);

        // Verificar si realmente hay cambios en los datos
        boolean hayCambios = !alumno.equals(alumnoSeleccionado);

        if (!hayCambios) {
            mostrarAlerta("Sin cambios", "No se han realizado cambios en los datos del alumno.");
            cancelar(event);
            return;
        }
        // Si es una inserción, verificar si el alumno ya existe
        if (alumnoSeleccionado == null) {
            if (DaoAlumno.existeAlumno(dni)) {
                mostrarAlerta("Error", "Ya existe un alumno con el mismo DNI en la base de datos.");
                return;
            }
            // Si no existe, insertar el nuevo alumno
            try {
                DaoAlumno.insertAlumno(alumno);
                mostrarAlerta("Éxito", "El alumno ha sido registrado correctamente.");
                cancelar(event);

            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo registrar el alumno: " + e.getMessage());
            }
        } else {// Si es una modificación, actualizar el alumno en la base de datos
            try {
                DaoAlumno.updateAlumno(alumno);
                mostrarAlerta("Éxito", "El alumno ha sido modificado correctamente.");
                cancelar(event);

            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo modificar el alumno: " + e.getMessage());
            }
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
