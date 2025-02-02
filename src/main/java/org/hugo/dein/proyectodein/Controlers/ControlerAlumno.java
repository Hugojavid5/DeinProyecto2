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

    /**
     * Establece el callback que se ejecutará cuando se cierre la ventana.
     *
     * @param onCloseCallback El callback que se ejecutará al cerrar la ventana.
     */
    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    private ModeloAlumno alumnoSeleccionado; // Variable para almacenar el alumno a modificar

    /**
     * Carga los datos de un alumno en los campos de texto para su visualización y modificación.
     *
     * @param alumno El objeto {@link ModeloAlumno} con los datos del alumno a cargar.
     */
    public void cargarDatosAlumno(ModeloAlumno alumno) {
        this.alumnoSeleccionado = alumno;
        txt_dni.setText(alumno.getDni());
        txt_nombre.setText(alumno.getNombre());
        txt_ape1.setText(alumno.getApellido1());
        txt_ape2.setText(alumno.getApellido2());
    }

    /**
     * Cancela la operación y cierra la ventana actual.
     * Si se ha establecido un callback, se ejecutará antes de cerrar la ventana.
     *
     * @param event El evento de acción generado al hacer clic en el botón de cancelar.
     */
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

    /**
     * Guarda los datos del alumno, realizando validaciones antes de insertar o actualizar
     * el registro en la base de datos. Muestra alertas en caso de error o éxito.
     *
     * @param event El evento de acción generado al hacer clic en el botón de guardar.
     */
    @FXML
    void guardarLibro(ActionEvent event) {
        StringBuilder errores = new StringBuilder();

        // Validar los campos
        String dni = txt_dni.getText().toUpperCase();
        String nombre = txt_nombre.getText();
        String primerApellido = txt_ape1.getText();
        String segundoApellido = txt_ape2.getText();

        // Validación del DNI
        if (dni == null || dni.isEmpty() || !dni.matches("\\d{8}[A-Za-z]")) {
            errores.append("El DNI debe tener 8 nums y una letra\n");
        }

        // Validación del nombre
        if (nombre == null || nombre.isEmpty()) {
            errores.append("El campo nombre no puede estar vacío.\n");
        }

        // Validación del primer apellido
        if (primerApellido == null || primerApellido.isEmpty()) {
            errores.append("El campo del primer apellido no puede estar vacío.\n");
        }

        // Validación del segundo apellido
        if (segundoApellido == null || segundoApellido.isEmpty()) {
            errores.append("El campo segundo apellido no puede estar vacío.\n");
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
        alumno.setApellido1(primerApellido);
        alumno.setApellido2(segundoApellido);

        // Verificar si realmente hay cambios en los datos
        boolean cambio = !alumno.equals(alumnoSeleccionado);

        if (!cambio) {
            mostrarAlerta("Sin cambios", "No se han realizado cambios en los datos del alumno.");
            cancelar(event);
            return;
        }

        // Si es una inserción, verificar si el alumno ya existe
        if (alumnoSeleccionado == null) {
            if (DaoAlumno.comprobarSiExiste(dni)) {
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
        } else { // Si es una modificación, actualizar el alumno en la base de datos
            try {
                DaoAlumno.updateAlumno(alumno);
                mostrarAlerta("Éxito", "El alumno ha sido modificado correctamente.");
                cancelar(event);
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo modificar el alumno: " + e.getMessage());
            }
        }
    }

    /**
     * Muestra una alerta con un título y mensaje especificados.
     *
     * @param titulo El título de la alerta.
     * @param mensaje El mensaje que se mostrará en la alerta.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
