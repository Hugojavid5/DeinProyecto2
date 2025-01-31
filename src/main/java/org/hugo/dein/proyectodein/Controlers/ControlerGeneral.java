package org.hugo.dein.proyectodein.Controlers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import org.hugo.dein.proyectodein.Dao.DaoAlumno;
import org.hugo.dein.proyectodein.Dao.DaoHisoricoPrestamo;
import org.hugo.dein.proyectodein.Dao.DaoLibro;
import org.hugo.dein.proyectodein.Dao.DaoPrestamo;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;
import org.hugo.dein.proyectodein.Modelos.ModeloHistoricoPrestamo;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;
import org.hugo.dein.proyectodein.Modelos.ModeloPrestamo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ControlerGeneral implements Initializable {

    @FXML
    private Button btt_aniadir;

    @FXML
    private Button btt_baja;

    @FXML
    private Button btt_modificar;

    @FXML
    private ComboBox<String> cbFiltroHistorico;


    @FXML
    private Tab colAlumnos;

    @FXML
    private Tab colLibros;

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
    private TableView<ModeloHistoricoPrestamo> tablaHistorico;

    @FXML
    private TableView<ModeloLibro> tablaLibros;

    @FXML
    private TableView<ModeloPrestamo> tablaPrestamos;

    @FXML
    private Tab tablahistoricosPrestamos;


    //TABLA LIBROS
    @FXML
    private TableColumn<ModeloLibro, String> tcTituloTabLibros;
    @FXML
    private TableColumn<String, ModeloLibro> tcAutorTabLibros;
    @FXML
    private TableColumn<ModeloLibro, String> tcEditorialTabLibros;
    @FXML
    private TableColumn<ModeloLibro, String> tcEstadoTabLibros;
    @FXML
    private TableColumn<ModeloLibro, ImageView> tcImagenTabLibros;

    //TABLA ALUMNOS
    @FXML
    private TableColumn<ModeloAlumno, String> col_dni;
    @FXML
    private TableColumn<ModeloAlumno, String> col_nombre;
    @FXML
    private TableColumn<ModeloAlumno, String> col_ape1;

    @FXML
    private TableColumn<ModeloAlumno, String> col_ape2;

    //TABLA PRESTAMOS
    @FXML
    private TableColumn<ModeloPrestamo, String> col_alumno;
    @FXML
    private TableColumn<ModeloPrestamo, String> col_libroPrestamo;
    @FXML
    private TableColumn<ModeloPrestamo, LocalDateTime> col_fecha;

    //TABLA HISTORICO PRESTAMOS
    @FXML
    private TableColumn<ModeloHistoricoPrestamo, String> col_AlumnoHistorico;
    @FXML
    private TableColumn<ModeloHistoricoPrestamo, String> col_LibroHistorico;
    @FXML
    private TableColumn<ModeloHistoricoPrestamo, LocalDateTime> col_fechaDevolucion;
    @FXML
    private TableColumn<ModeloHistoricoPrestamo, LocalDateTime> col_fechaPrestamo;


    @FXML
    private TextField txtFiltrarporHistoricoPrestamos;

    @FXML
    private TextField txt_FiltrarPrestamo;

    @FXML
    private TextField txt_filtarAlumn;

    @FXML
    private TextField txt_filtrarLibros;

    @FXML
    private VBox vbox_general;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("iniciando...");
        try {
            ConexionBBDD conexionBBDD = new ConexionBBDD();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cargarDatosTablas();
        cargarFiltrosHistorico();
    }


    //METODOS PARA LIBRO
    @FXML
    void aniadirLibro(ActionEvent event) {
        ControlerLibros librosController = cargarPantalla("/fxml/libro.fxml", "Añadir Libro");

        if (librosController != null){
            librosController.setOnCloseCallback(this::cargarDatosTablas);
        }
    }

    @FXML
    void bajaLibro(ActionEvent event) {
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
                boolean exito = DaoLibro.bajaDelLibro(libroSeleccionado.getCodigo());

                // Mostramos un mensaje dependiendo si la operación fue exitosa o no
                if (exito) {
                    mostrarAlerta("Éxito", "Libro dado de baja", "El libro ha sido dado de baja correctamente.");
                    // Aquí podríamos actualizar la tabla para reflejar los cambios
                    cargarDatosTablas();
                } else {
                    mostrarAlerta("Error", "No se pudo dar de baja el libro", "Hubo un error al intentar dar de baja el libro.");
                }
            }
        });
    }

    @FXML
    void devolverLibro(ActionEvent event) {
        // Obtener el préstamo seleccionado en la tabla
        ModeloPrestamo prestamoSeleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();

        // Verificar que se haya seleccionado un préstamo
        if (prestamoSeleccionado == null) {
            mostrarAlerta("Error", "No se pudo devolver el libro","Debes seleccionar un préstamo antes de devolver un libro.");
            return;
        }

        // Cargar la pantalla de devolución
        ControlerDevolucion devolucionController = cargarPantalla("/fxml/devolucion.fxml", "Devolver de un libro");

        if (devolucionController != null) {
            // Pasar el préstamo seleccionado al controlador de devolución
            devolucionController.setPrestamo(prestamoSeleccionado);

            // Configurar el callback para actualizar la tabla al cerrar
            devolucionController.setOnCloseCallback(this::cargarDatosTablas);
        }
    }
    @FXML
    void modificarLibro(ActionEvent event) {
        // Obtener el libro seleccionado de la tabla
        ModeloLibro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado un libro
        if (libroSeleccionado == null) {
            mostrarAlerta("Error", "No has seleccionado ningún libro", "Por favor, selecciona un libro para modificar.");
            return;
        }

        // Cargar la ventana de modificación con los datos del libro seleccionado
        ControlerLibros ControlerLibros = cargarPantalla("/fxml/libro.fxml", "Modificar Libro");

        if (ControlerLibros != null) {
            // Pasar el libro seleccionado al controlador para modificarlo
            ControlerLibros.setLibroSeleccionado(libroSeleccionado);
        }

        if (ControlerLibros != null){
            ControlerLibros.setOnCloseCallback(this::cargarDatosTablas);
        }
    }

    @FXML
    void filtrarLibros(ActionEvent event) {
        String filtro = txt_filtrarLibros.getText().trim().toLowerCase();

        List<ModeloLibro> librosFiltrados = DaoLibro.getTodosLibrosConBajaA0().stream()
                .filter(libro -> libro.getTitulo().toLowerCase().contains(filtro))
                .collect(Collectors.toList());

        tablaLibros.getItems().setAll(librosFiltrados);
    }
    @FXML
    void prestarLibro(ActionEvent event) {
        ControlerPrestamo ControlerPrestamo = cargarPantalla("/fxml/prestamo.fxml", "Prestar Libro");

        if (ControlerPrestamo != null){
            ControlerPrestamo.setOnCloseCallback(this::cargarDatosTablas);
        }
    }

    //METODO PARA ALUMNO
    @FXML
    void aniadirAlumno(ActionEvent event) {
        ControlerAlumno alumnosController = cargarPantalla("/fxml/alumnos.fxml", "Añadir Alumno");

        if (alumnosController != null) {
            // Configurar el callback para actualizar la tabla
            alumnosController.setOnCloseCallback(this::cargarDatosTablas);
        }
    }

    @FXML
    void filtrarAlumno(ActionEvent event) {
        String filtro = txt_filtarAlumn.getText().trim().toLowerCase();

        List<ModeloAlumno> alumnosFiltrados = DaoAlumno.getTodosAlumnos().stream()
                .filter(alumno -> alumno.getNombre().toLowerCase().contains(filtro))
                .collect(Collectors.toList());

        tablaAlumnos.getItems().setAll(alumnosFiltrados);
    }
    @FXML
    void modificarAlumno(ActionEvent event) {
        // Obtener el alumno seleccionado
        ModeloAlumno alumnoSeleccionado = tablaAlumnos.getSelectionModel().getSelectedItem();

        // Si no hay alumno seleccionado, mostramos un error
        if (alumnoSeleccionado == null) {
            mostrarAlerta("Error", "No has seleccionado ningún alumno", "Por favor, selecciona un alumno para modificar.");
            return;
        }

        // Si el alumno está seleccionado, cargar la ventana de modificación
        ControlerAlumno ControlerAlumno = cargarPantalla("/fxml/alumnos.fxml", "Modificar Alumno");

        if (ControlerAlumno != null) {
            // Pasar los datos del alumno seleccionado al controlador de la ventana de alumnos
            ControlerAlumno.cargarDatosAlumno(alumnoSeleccionado);

            // Configurar el callback para actualizar la tabla cuando la ventana se cierre
            ControlerAlumno.setOnCloseCallback(this::cargarDatosTablas);
        }
    }

    //METODO PARA PRESTAMOS
    @FXML
    void filtrarPrestamo(ActionEvent event) {
        String filtro = txt_FiltrarPrestamo.getText().trim();

        List<ModeloPrestamo> prestamosFiltrados = DaoPrestamo.getTodosPrestamo().stream()
                .filter(prestamo -> prestamo.getFecha_prestamo().toString().contains(filtro))
                .collect(Collectors.toList());

        tablaPrestamos.getItems().setAll(prestamosFiltrados);
    }


    //METODOS PARA HISTORICO
    @FXML
    void filtrarHistoricoPrestamos(ActionEvent event) {
        String filtro = txtFiltrarporHistoricoPrestamos.getText().trim().toLowerCase();
        String criterio = cbFiltroHistorico.getSelectionModel().getSelectedItem();

        List<ModeloHistoricoPrestamo> historicoFiltrado = DaoHisoricoPrestamo.getTodosHistorialPrestamo().stream()
                .filter(historico -> {
                    if ("Por DNI Del alumno".equals(criterio)) {
                        return historico.getAlumno().getDni().toLowerCase().contains(filtro);
                    } else if ("Por Título de libro".equals(criterio)) {
                        return historico.getLibro().getTitulo().toLowerCase().contains(filtro);
                    }
                    return true;
                })
                .collect(Collectors.toList());

        tablaHistorico.getItems().setAll(historicoFiltrado);
    }


    //METODO PARA LOS JASPER
    //JASPER 1 cargado en el controler prestamo

    @FXML
    void cargarInforme2Libros(ActionEvent event) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("IMAGE_PATH", getClass().getResource("/imagenes/").toString());
        parameters.put("SUBINFORME_PATH", getClass().getResource("/jasper/").toString());
        generarReporte("/jasper/Informe2Libros.jasper", parameters);
    }

    @FXML
    void cargarInforme3Graficos(ActionEvent event) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("IMAGE_PATH", getClass().getResource("/imagenes/").toString());
        parameters.put("SUBINFORME_PATH", getClass().getResource("/jasper/").toString());
        generarReporte("/jasper/Informe3Graficos.jasper", parameters);
    }

    @FXML
    void cargarInforme4Datos(ActionEvent event) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("IMAGE_PATH", getClass().getResource("/imagenes/").toString());
        parameters.put("SUBINFORME_PATH", getClass().getResource("/jasper/").toString());
        generarReporte("/jasper/Informe4Datos.jasper", parameters);
    }

    //METODO PARA LOS IDIOMAS

    @FXML
    void idiomaEspaniol(ActionEvent event) {
        ConexionBBDD.guardarIdioma("es");
        Stage stage = (Stage) txt_filtarAlumn.getScene().getWindow();
        this.actualizarVentana(stage);
    }

    @FXML
    void idiomaIngles(ActionEvent event) {
        ConexionBBDD.guardarIdioma("eus");
        Stage stage = (Stage) txt_filtarAlumn.getScene().getWindow();
        this.actualizarVentana(stage);
    }

    //METODO PARA CARGAR LA PANTALLA
    private <T> T cargarPantalla(String rutaFXML, String titulo) {
        try {
            Properties properties = ConexionBBDD.cargarIdioma();
            String lang = properties.getProperty("language");

            // Cargar el recurso de idioma adecuado utilizando el archivo de propiedadess
            Locale locale = new Locale(lang);
            ResourceBundle bundle = ResourceBundle.getBundle("languages/lang", locale);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML), bundle);
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

            // Devuelve el controlador asociado al archivo FXML
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Maneja el error devolviendo null
        }
    }

    //METODOS AUXILIARES(CARGAR DATOS,PASAR IMG A BYTES)
    void cargarDatosTablas() {
        // Tabla de alumnos
        List<ModeloAlumno> alumnos = DaoAlumno.getTodosAlumnos();
        tablaAlumnos.getItems().setAll(alumnos);
        col_dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_ape1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        col_ape2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));

        // Tabla de libros (con baja = 0)
        List<ModeloLibro> libros = DaoLibro.getTodosLibrosConBajaA0();
        tablaLibros.getItems().setAll(libros);
        tcTituloTabLibros.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tcAutorTabLibros.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tcEditorialTabLibros.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        tcEstadoTabLibros.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tcImagenTabLibros.setCellValueFactory(cellData -> {
            ModeloLibro libro = cellData.getValue();
            ImageView imageView = new ImageView(blobToImage(blobToBytes(libro.getImagen())));

            // Ajustar el tamaño de la imagen en la celda
            imageView.setFitWidth(50);  // Ancho deseado
            imageView.setFitHeight(50); // Alto deseado
            imageView.setPreserveRatio(true);

            return new SimpleObjectProperty<>(imageView);
        });
        // Tabla de préstamos
        List<ModeloPrestamo> prestamos = DaoPrestamo.getTodosPrestamo();
        tablaPrestamos.getItems().setAll(prestamos);
        col_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_prestamo"));
        col_alumno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlumno().getDni()));
        col_libroPrestamo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLibro().getCodigo())));


        // Tabla de histórico de préstamos
        List<ModeloHistoricoPrestamo> historicoPrestamos = DaoHisoricoPrestamo.getTodosHistorialPrestamo();
        tablaHistorico.getItems().setAll(historicoPrestamos);
        col_fechaPrestamo.setCellValueFactory(new PropertyValueFactory<>("fecha_prestamo"));
        col_fechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fecha_devolucion"));
        col_AlumnoHistorico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlumno().getDni()));

        // Ahora muestra el título en lugar del código
        col_LibroHistorico.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLibro().getTitulo())
        );


    }
    private Image blobToImage(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length == 0) {
            // Si no hay imagen, usar una imagen por defecto
            return new Image(getClass().getResource("/IMG/libroIcono.png").toString());
        }

        return new Image(new ByteArrayInputStream(imageBytes));
    }
    private byte[] blobToBytes(Blob blob) {
        if (blob == null) {
            return null; // Si el blob es nulo, devolvemos null
        }
        try (InputStream inputStream = blob.getBinaryStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void mostrarAlerta(String titulo, String cabecera, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private void cargarFiltrosHistorico() {
        ObservableList<String> filtros = FXCollections.observableArrayList("Por DNI Del alumno", "Por Título de libro");
        cbFiltroHistorico.setItems(filtros);
        cbFiltroHistorico.getSelectionModel().selectFirst(); // Seleccionar el primer valor por defecto
    }
    private void generarReporte(String reportePath, Map<String, Object> parameters) {
        try {
            ConexionBBDD db = new ConexionBBDD();
            InputStream reportStream = getClass().getResourceAsStream(reportePath);

            if (reportStream == null) {
                System.out.println("El archivo no está ahí: " + reportePath);
                return;
            }

            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConnection());
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException | JRException e) {
            e.printStackTrace();
            mostrarError("Error en la base de datos", "No se pudo conectar a la base de datos o generar el informe.");
        }
    }
    public void actualizarVentana(Stage stage) {
        try {
            // Cargar las propiedades de idioma y establecer el nuevo locale
            Properties properties = ConexionBBDD.cargarIdioma();
            String lang = properties.getProperty("language");
            Locale locale = new Locale(lang);
            ResourceBundle bundle = ResourceBundle.getBundle("languages/lang", locale);

            // Cargar el archivo FXML de la ventana principal con el nuevo ResourceBundle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/biblioGeneral.fxml"), bundle);
            Parent root = fxmlLoader.load();

            // Verificar que el Stage no sea nulo antes de cambiar la raíz de la escena
            if (stage != null) {
                stage.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
