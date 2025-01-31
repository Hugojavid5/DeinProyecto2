package org.hugo.dein.proyectodein.Controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import org.hugo.dein.proyectodein.Dao.DaoAlumno;
import org.hugo.dein.proyectodein.Dao.DaoLibro;
import org.hugo.dein.proyectodein.Dao.DaoPrestamo;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;
import org.hugo.dein.proyectodein.Modelos.ModeloPrestamo;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlerPrestamo {

    private Runnable onCloseCallback;
    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    @FXML
    private Button btt_cancelar;

    @FXML
    private Button btt_guardar;

    @FXML
    private ComboBox<ModeloAlumno> comboEstudiante;

    @FXML
    private ComboBox<ModeloLibro> comboLibro;

    @FXML
    private DatePicker dpFechaPrestamo;

    @FXML
    public void initialize() {
        cargarEstudiantes();
        cargarLibros();
    }
    @FXML
    void cancelarCambios(ActionEvent event) {
        if (onCloseCallback != null) {
            onCloseCallback.run();
        }
        Stage stage = (Stage) comboEstudiante.getScene().getWindow();
        stage.close();
    }
    private void cargarLibros() {
        comboLibro.getItems().setAll(DaoLibro.todosLibrosActivos());
    }
    private void cargarEstudiantes() {
        comboEstudiante.getItems().setAll(DaoAlumno.cargarListado());
    }

    @FXML
    void guardarCambios(ActionEvent event) {
        List<String> errores = new ArrayList<>();

        ModeloAlumno alumnoSeleccionado = comboEstudiante.getValue();
        if (alumnoSeleccionado == null) {
            errores.add("Debe seleccionar un estudiante.");
        }

        ModeloLibro libroSeleccionado = comboLibro.getValue();
        if (libroSeleccionado == null) {
            errores.add("Debe seleccionar un libro disponible.");
        }

        LocalDate fechaSeleccionada = dpFechaPrestamo.getValue();
        if (fechaSeleccionada == null) {
            errores.add("Debe seleccionar una fecha de préstamo.");
        } else if (fechaSeleccionada.isBefore(LocalDate.now())) {
            errores.add("La fecha de préstamo no puede ser anterior a la actual.");
        }

        if (!errores.isEmpty()) {
            mostrarError("Errores en el formulario", String.join("\n", errores));
            return;
        }

        // Asignamos los valores necesarios para el informe
        ModeloPrestamo nuevoPrestamo = new ModeloPrestamo(
                0,
                alumnoSeleccionado,
                libroSeleccionado,
                fechaSeleccionada.atStartOfDay()
        );

        int idPrestamo = DaoPrestamo.insertar(nuevoPrestamo);  // Obtenemos el ID del préstamo insertado
        boolean exito = idPrestamo > 0;
        if (exito) {
            mostrarInformacion("Éxito", "Préstamo registrado correctamente.");

            // Preparar los parámetros para el reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("IMAGE_PATH", getClass().getResource("/imagenes/").toString());
            parameters.put("ID_PRESTAMO", idPrestamo);  // Pasar el ID del préstamo

            // Generar el reporte
            generarReporte("/jasper/Informe1Prestamo.jasper", parameters);

            cancelarCambios(event); // Cierra la ventana tras guardar correctamente
        } else {
            mostrarError("Error", "No se pudo registrar el préstamo. Intente nuevamente.");
        }
    }

    private void generarReporte(String reportePath, Map<String, Object> parameters) {
        try {
            ConexionBBDD conexionBBDD = new ConexionBBDD();
            InputStream reportStream = getClass().getResourceAsStream(reportePath);

            if (reportStream == null) {
                System.out.println("El archivo no está ahí: " + reportePath);
                return;
            }

            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, conexionBBDD.getConnection());
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException | JRException e) {
            e.printStackTrace();
            mostrarError("Error en la base de datos", "No se pudo conectar a la base de datos o generar el informe.");
        }
    }
    private void mostrarInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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
