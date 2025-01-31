package org.hugo.dein.proyectodein.Controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.hugo.dein.proyectodein.Dao.DaoHisoricoPrestamo;
import org.hugo.dein.proyectodein.Dao.DaoLibro;
import org.hugo.dein.proyectodein.Dao.DaoPrestamo;
import org.hugo.dein.proyectodein.Modelos.ModeloHistoricoPrestamo;
import org.hugo.dein.proyectodein.Modelos.ModeloPrestamo;

import javax.enterprise.inject.Model;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ControlerDevolucion {

    @FXML
    private Button btt_cancelar;

    @FXML
    private Button btt_guardar;

    @FXML
    private ComboBox<String> comboLibro;

    @FXML
    private DatePicker doFechaPrestamo;

    private Runnable onCloseCallback;
    public void setOnCloseCallback(Runnable callback) {
        this.onCloseCallback = callback;
    }



    @FXML
    void cancelarCambios(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    void guardarCambios(ActionEvent event) {
        if (prestamoSeleccionado == null) {
            mostrarAlerta("Error", "No se ha seleccionado ningún préstamo.", Alert.AlertType.ERROR);
            return;
        }

        LocalDate fechaDevolucion = doFechaPrestamo.getValue();

        if (fechaDevolucion == null) {
            mostrarAlerta("Error", "Debe seleccionar una fecha de devolución.", Alert.AlertType.ERROR);
            return;
        }

        LocalDateTime fechaDevolucionDateTime = fechaDevolucion.atStartOfDay();

        if (fechaDevolucionDateTime.isBefore(prestamoSeleccionado.getFecha_prestamo())) {
            mostrarAlerta("Error", "La fecha de devolución no puede ser anterior a la fecha de préstamo.", Alert.AlertType.ERROR);
            return;
        }

        // Eliminar el préstamo de la base de datos
        boolean eliminado = DaoPrestamo.deletePrestamo(prestamoSeleccionado.getId_prestamo());

        if (!eliminado) {
            mostrarAlerta("Error", "No se pudo eliminar el préstamo de la base de datos.", Alert.AlertType.ERROR);
            return;
        }

        // Si el estado del libro ha cambiado, actualizarlo en la base de datos
        String nuevoEstado = comboLibro.getValue();
        if (nuevoEstado != null && !nuevoEstado.equals(prestamoSeleccionado.getLibro().getEstado())) {
            boolean actualizado = DaoLibro.updateLibroEstado(prestamoSeleccionado.getLibro().getCodigo(), nuevoEstado);
            if (!actualizado) {
                mostrarAlerta("Error", "No se pudo actualizar el estado del libro.", Alert.AlertType.ERROR);
                return;
            }
        }

        // Insertar en el historial de préstamos
        ModeloHistoricoPrestamo historial = new ModeloHistoricoPrestamo(
                prestamoSeleccionado.getId_prestamo(),
                prestamoSeleccionado.getAlumno(),
                prestamoSeleccionado.getLibro(),
                prestamoSeleccionado.getFecha_prestamo(),
                fechaDevolucionDateTime
        );

        boolean insertado = DaoHisoricoPrestamo.insertHistorialPrestamo(historial);

        if (!insertado) {
            mostrarAlerta("Error", "No se pudo registrar el préstamo en el historial.", Alert.AlertType.ERROR);
            return;
        }

        mostrarAlerta("Éxito", "El préstamo ha sido devuelto correctamente.", Alert.AlertType.INFORMATION);
        cerrarVentana();
    }
    private void cerrarVentana() {
        if (onCloseCallback != null) {
            onCloseCallback.run();
        }
        Stage stage = (Stage) comboLibro.getScene().getWindow();
        stage.close();
    }
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    @FXML
    public void initialize() {
        // Cargar los estados posibles del libro en el ComboBox
        comboLibro.getItems().addAll(
                "Nuevo", "Usado nuevo", "Usado seminuevo", "Usado estropeado", "Restaurado"
        );
    }
    private ModeloPrestamo prestamoSeleccionado;
    public void setPrestamo(ModeloPrestamo prestamo) {
        this.prestamoSeleccionado = prestamo;
        if (prestamo != null) {
            // Establecer el estado actual del libro en el ComboBox
            comboLibro.setValue(prestamo.getLibro().getEstado());
        }
    }

}
