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

/**
 * Controlador para la gestión de préstamos en la aplicación.
 * Permite realizar el registro de un préstamo, mostrando un formulario para seleccionar el estudiante, el libro y la fecha.
 */
public class ControlerPrestamo {

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

    private Runnable onCloseCallback;

    /**
     * Configura el callback que se ejecutará al cerrar el formulario.
     *
     * @param onCloseCallback el callback que se ejecutará al cerrar el formulario.
     */
    public void setOnCloseCallback(Runnable onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    /**
     * Método de inicialización que carga los estudiantes y los libros disponibles en el formulario.
     */
    @FXML
    public void initialize() {
        cargarEstudiantes();
        cargarLibrosDisponibles();
    }

    /**
     * Carga la lista de estudiantes en el ComboBox.
     */
    private void cargarEstudiantes() {
        comboEstudiante.getItems().setAll(org.hugo.dein.proyectodein.Dao.DaoAlumno.getTodosAlumnos());
    }

    /**
     * Carga la lista de libros disponibles en el ComboBox.
     */
    private void cargarLibrosDisponibles() {
        comboLibro.getItems().setAll(org.hugo.dein.proyectodein.Dao.DaoLibro.getLibrosDisponibles());
    }

    /**
     * Cancela el registro del préstamo y cierra el formulario.
     *
     * @param event el evento que ha disparado la acción.
     */
    @FXML
    void cancelarCambios(ActionEvent event) {
        if (onCloseCallback != null) {
            onCloseCallback.run();
        }
        Stage stage = (Stage) comboEstudiante.getScene().getWindow();
        stage.close();
    }

    /**
     * Guarda los cambios del préstamo, realizando las validaciones necesarias y generando el reporte correspondiente.
     *
     * @param event el evento que ha disparado la acción.
     */
    @FXML
    void guardarCambios(ActionEvent event) {
        List<String> errores = new ArrayList<>();

        ModeloAlumno alumnoSeleccionado = comboEstudiante.getValue();
        if (alumnoSeleccionado == null) {
            errores.add("Selecciona un estudiante.");
        }

        ModeloLibro libroSeleccionado = comboLibro.getValue();
        if (libroSeleccionado == null) {
            errores.add("Selecciona un libro disponible.");
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

        boolean exito = DaoPrestamo.insertPrestamo(nuevoPrestamo);
        if (exito) {
            mostrarInformacion("Éxito", "Préstamo registrado correctamente.");

            // Obtener el ID del préstamo recién creado
            int idPrestamo = nuevoPrestamo.getId_prestamo();  // Asegúrate de que este campo esté correctamente configurado en ModeloPrestamo

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

    /**
     * Genera un reporte en formato Jasper utilizando los parámetros proporcionados.
     *
     * @param reportePath la ruta al archivo Jasper.
     * @param parameters los parámetros necesarios para generar el reporte.
     */
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
            mostrarError("Error en la base de datos", "O no se ha podido establecer conexion con la bbdd o no se ha podido generar el informe.");
        }
    }

    /**
     * Muestra un mensaje de error en una ventana emergente.
     *
     * @param titulo el título de la ventana de error.
     * @param mensaje el mensaje de error a mostrar.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de información en una ventana emergente.
     *
     * @param titulo el título de la ventana de información.
     * @param mensaje el mensaje de información a mostrar.
     */
    private void mostrarInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
