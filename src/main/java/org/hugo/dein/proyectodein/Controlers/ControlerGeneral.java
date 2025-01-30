package org.hugo.dein.proyectodein.Controlers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hugo.dein.proyectodein.BBDD.ConexionBBDD;
import org.hugo.dein.proyectodein.Dao.DaoAlumno;
import org.hugo.dein.proyectodein.Dao.DaoHistorialPrestamo;
import org.hugo.dein.proyectodein.Dao.DaoPrestamo;
import org.hugo.dein.proyectodein.Modelos.ModeloAlumno;
import org.hugo.dein.proyectodein.Modelos.ModeloHistoricoPrestamo;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;
import org.hugo.dein.proyectodein.Dao.DaoLibro;
import org.hugo.dein.proyectodein.Modelos.ModeloPrestamo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControlerGeneral implements Initializable {

    @FXML
    private Button btt_aniadir;

    @FXML
    private TableColumn<LocalDateTime, ModeloPrestamo> col_fecha;
    @FXML
    private TableColumn<ModeloAlumno, String> col_alumno;
    @FXML
    private TableColumn<ModeloLibro, String> col_libroPrestamo;
    @FXML
    private TableColumn<ModeloHistoricoPrestamo, String> col_AlumnoHistorico;
    @FXML
    private TableColumn<ModeloHistoricoPrestamo, String> col_LibroHistorico;

    @FXML
    private Button btt_baja;

    @FXML
    private TableColumn<LocalDateTime, ModeloHistoricoPrestamo> col_fechaPrestamo;

    @FXML
    private TableColumn<LocalDateTime, ModeloHistoricoPrestamo> col_fechaDevolucion;

    @FXML
    private Button btt_modificar;


    @FXML
    private ComboBox<String> cbFiltroHistorico;

    @FXML
    private TableColumn<ModeloLibro, ImageView> tcBajaTabLibros;
    @FXML
    private Tab colAlumnos;

    @FXML
    private Tab colLibros;

    @FXML
    private TableColumn<String, ModeloAlumno> col_ape1;
    @FXML
    private TableColumn<String, ModeloAlumno> col_ape2;
    @FXML
    private TableColumn<String, ModeloAlumno> col_dni;

    @FXML
    private TableColumn<String, ModeloAlumno> col_nombre;

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
    private TableView<ModeloHistoricoPrestamo> tablaHistorico;
    @FXML
    private TabPane tablaGeneral;

    @FXML
    private TableView<ModeloLibro> tablaLibros;

    @FXML
    private TableColumn<String, ModeloLibro> tcAutorTabLibros;


    @FXML
    private TableColumn<String, ModeloLibro> tcEditorialTabLibros;

    @FXML
    private TableColumn<String, ModeloLibro> tcEstadoTabLibros;

    @FXML
    private TableColumn<String, ModeloLibro> tcTituloTabLibros;
    @FXML
    private TableView<ModeloPrestamo> tablaPrestamos;

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
//      cargarFiltrosHistorico();
    }
    void cargarDatosTablas() {
        // Tabla de alumnos
        List<ModeloAlumno> alumnos = DaoAlumno.cargarListado();
        tablaAlumnos.getItems().setAll(alumnos);
        col_dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_ape1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        col_ape2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));

        //Tabla de libros
        List<ModeloLibro> libros = DaoLibro.cargarListado();
        tablaLibros.getItems().setAll(libros);
        tcTituloTabLibros.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tcAutorTabLibros.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tcEditorialTabLibros.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        tcBajaTabLibros.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModeloLibro, ImageView>, ObservableValue<ImageView>>() {
            @Override
            public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<ModeloLibro, ImageView> cellData) {
                ModeloLibro libro = cellData.getValue();
                ImageView imageView = new ImageView();

                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);

                if (libro.getPortada() != null) {
                    imageView.setImage(blobToImage(libro.getPortada()));
                }

                return new SimpleObjectProperty<>(imageView);
            }
        });

        // Tabla de préstamos
        List<ModeloPrestamo> prestamos = DaoPrestamo.cargarListado();
        tablaPrestamos.getItems().setAll(prestamos);
        col_fecha.setCellValueFactory(new PropertyValueFactory<>("fecha_prestamo"));
        col_alumno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        col_libroPrestamo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));

        // Tabla de histórico de préstamos
        List<ModeloHistoricoPrestamo> historicoPrestamos = DaoHistorialPrestamo.cargarListado();
        tablaHistorico.getItems().setAll(historicoPrestamos);
        col_fechaPrestamo.setCellValueFactory(new PropertyValueFactory<>("fecha_prestamo"));
        col_fechaDevolucion.setCellValueFactory(new PropertyValueFactory<>("fecha_devolucion"));
        col_AlumnoHistorico.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlumno().getDni()));
        col_LibroHistorico.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLibro().getTitulo())
        );



    }
    private Image blobToImage(Blob blob) {
        try {
            if (blob != null) {
                byte[] bytes = blob.getBytes(1, (int) blob.length());
                return new Image(new ByteArrayInputStream(bytes));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




    @FXML
    void aniadirAlumno(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/alumnos.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Añadir Alumno");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
     }
    }

    @FXML
    void aniadirLibro(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/libro.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Añadir Libro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
           // stage.setOnHidden(windowEvent -> cargarLibros());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void bajaLibro(ActionEvent event) {
        // Primero, obtenemos el libro seleccionado en la tabla
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
                boolean exito = DaoLibro.darDeBaja(libroSeleccionado);

                // Mostramos un mensaje dependiendo si la operación fue exitosa o no
                if (exito) {
                    mostrarAlerta("Éxito", "Libro dado de baja", "El libro ha sido dado de baja correctamente.");
                    // Aquí podríamos actualizar la tabla para reflejar los cambios
                    //cargarDatosTablas();
                } else {
                    mostrarAlerta("Error", "No se pudo dar de baja el libro", "Hubo un error al intentar dar de baja el libro.");
                }
            }
        });
    }


    private void generarReporte(String reportePath, Map<String, Object> parameters) {
        try {
            ConexionBBDD conexionbbdd = new ConexionBBDD();
            InputStream reportStream = getClass().getResourceAsStream(reportePath);

            if (reportStream == null) {
                System.out.println("El archivo jasper no se encuentra " + reportePath);
                return;
            }

            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, conexionbbdd.getConnection());
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException | JRException e) {
            e.printStackTrace();
            mostrarError("Error", "Problema al generar el informe");
        }
    }
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void cargarGuia(ActionEvent event) {

    }

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

    public void cargarInforme4Datos(ActionEvent event) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("IMAGE_PATH", getClass().getResource("/imagenes/").toString());
        generarReporte("/jasper/Informe4Datos.jasper", parameters);
    }

    @FXML
    void devolverLibro(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/devolucion.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Devolver un prestamo");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            // stage.setOnHidden(windowEvent -> cargarPrestamos());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void filtrarAlumno(ActionEvent event) {
        String filtro = txt_filtarAlumn.getText().trim().toLowerCase();
        List<ModeloAlumno> alumnosFiltrados = DaoAlumno.cargarListado().stream()
                .filter(alumno -> alumno.getNombre().toLowerCase().contains(filtro))
                .collect(Collectors.toList());

        tablaAlumnos.getItems().setAll(alumnosFiltrados);
    }

    @FXML
    void filtrarHistorico(ActionEvent event) {
        String filtro = txt_FiltrarPrestamo.getText().trim();

        List<ModeloPrestamo> prestamosFiltrados = DaoPrestamo.cargarListado().stream()
                .filter(prestamo -> prestamo.getFecha_prestamo().toString().contains(filtro))
                .collect(Collectors.toList());

        tablaPrestamos.getItems().setAll(prestamosFiltrados);
    }

    @FXML
    void filtrarLibros(ActionEvent event) {
        String filtro = txt_filtrarLibros.getText().trim().toLowerCase();

        List<ModeloLibro> librosFiltrados = DaoLibro.cargarListado().stream()
                .filter(libro -> libro.getTitulo().toLowerCase().contains(filtro))
                .collect(Collectors.toList());

        tablaLibros.getItems().setAll(librosFiltrados);
    }

    @FXML
    void filtrarPrestamo(ActionEvent event) {
        String filtro = txt_FiltrarPrestamo.getText().trim();

        List<ModeloPrestamo> prestamosFiltrados = DaoPrestamo.cargarListado().stream()
                .filter(prestamo -> prestamo.getFecha_prestamo().toString().contains(filtro))
                .collect(Collectors.toList());

        tablaPrestamos.getItems().setAll(prestamosFiltrados);
    }


    @FXML
    void idiomaEspaniol(ActionEvent event) {

    }

    @FXML
    void idiomaIngles(ActionEvent event) {

    }

    @FXML
    void modificarAlumno(ActionEvent event) {
        ModeloAlumno alumno=tablaAlumnos.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/alumnos.fxml"));
            Parent root = fxmlLoader.load();

            ControlerAlumno controller=fxmlLoader.getController();
            controller.setAlumnos(alumno);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Editar alumno");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            // stage.setOnHidden(windowEvent -> cargarAlumnos());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    void modificarLibro(ActionEvent event) {
        ModeloLibro libro=tablaLibros.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/libro.fxml"));
            Parent root = fxmlLoader.load();

            ControlerLibros controller=fxmlLoader.getController();
            controller.setLibro(libro);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Editar Libro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
           // stage.setOnHidden(windowEvent -> cargarLibros());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void prestarLibro(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/prestamo.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Prestar un libro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            // stage.setOnHidden(windowEvent -> cargarPrestamos());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void cargarFiltrosHistorico() {
        ObservableList<String> filtros = FXCollections.observableArrayList("Por DNI Del alumno", "Por Título de libro");
        cbFiltroHistorico.setItems(filtros);
        cbFiltroHistorico.getSelectionModel().selectFirst();
    }
    @FXML
    void filtrarHistoricoPrestamos(ActionEvent event) {
        String filtro = txt_FiltrarPrestamo.getText().trim().toLowerCase();
        String criterio = cbFiltroHistorico.getSelectionModel().getSelectedItem();

        List<ModeloHistoricoPrestamo> historicoFiltrado = DaoHistorialPrestamo.cargarListado().stream()
                .filter(historico -> {
                    if ("dni del alumno".equals(criterio)) {
                        return historico.getAlumno().getDni().toLowerCase().contains(filtro);
                    } else if ("titulo libro".equals(criterio)) {
                        return historico.getLibro().getTitulo().toLowerCase().contains(filtro);
                    }
                    return true;
                })
                .collect(Collectors.toList());

        tablaHistorico.getItems().setAll(historicoFiltrado);
    }



    private void mostrarAlerta(String titulo, String cabecera, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
