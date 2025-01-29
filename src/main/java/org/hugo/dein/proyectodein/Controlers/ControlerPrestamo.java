package org.hugo.dein.proyectodein.Controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class ControlerPrestamo {

    @FXML
    private Button btt_cancelar;

    @FXML
    private Button btt_guardar;

    @FXML
    private ComboBox<?> comboEstudiante;

    @FXML
    private ComboBox<?> comboLibro;

    @FXML
    private DatePicker dpFechaPrestamo;

    @FXML
    void cancelarCambios(ActionEvent event) {

    }

    @FXML
    void guardarCambios(ActionEvent event) {

    }

    private void mostrarError(String titulo, String mensaje) {
        // Crear una ventana emergente de tipo "error"
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null); // No queremos un encabezado
        alert.setContentText(mensaje); // El mensaje que queremos mostrar
        alert.showAndWait(); // Mostrar el mensaje y esperar a que el usuario lo cierre
    }
}
