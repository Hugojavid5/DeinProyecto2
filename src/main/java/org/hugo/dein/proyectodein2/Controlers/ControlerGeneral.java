package org.hugo.dein.proyectodein2.Controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ControlerGeneral {

    @FXML
    private Button btt_aniadir;

    @FXML
    private Button btt_baja;

    @FXML
    private Button btt_modificar;

    @FXML
    private ComboBox<?> cbFiltroHistorico;

    @FXML
    private Tab colAlumnos;

    @FXML
    private Tab colLibros;

    @FXML
    private TableColumn<?, ?> col_ape1;

    @FXML
    private TableColumn<?, ?> col_autor;

    @FXML
    private TableColumn<?, ?> col_baja;

    @FXML
    private TableColumn<?, ?> col_dni;

    @FXML
    private TableColumn<?, ?> col_editorial;

    @FXML
    private TableColumn<?, ?> col_estado;

    @FXML
    private TableColumn<?, ?> col_nombre;

    @FXML
    private TableColumn<?, ?> col_titulo;

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
    private TableView<?> tablaAlumnos;

    @FXML
    private TableView<?> tablaDevoluciones;

    @FXML
    private TabPane tablaGeneral;

    @FXML
    private TableView<?> tablaHistorico;

    @FXML
    private TableView<?> tablaLibros;

    @FXML
    private TextField tfFiltrarPrestamo;

    @FXML
    private TextField txt_filtarAlumn;

    @FXML
    private TextField txt_filtrarLibros;

    @FXML
    private VBox vbox_general;

    @FXML
    void aniadirAlumno(ActionEvent event) {

    }

    @FXML
    void aniadirLibro(ActionEvent event) {

    }

    @FXML
    void bajaLibro(ActionEvent event) {

    }

    @FXML
    void cargarGuia(ActionEvent event) {

    }

    @FXML
    void cargarInforme2(ActionEvent event) {

    }

    @FXML
    void cargarInforme3(ActionEvent event) {

    }

    @FXML
    void cargarInforme4(ActionEvent event) {

    }

    @FXML
    void devolverLibro(ActionEvent event) {

    }

    @FXML
    void filtrarAlumno(ActionEvent event) {

    }

    @FXML
    void filtrarHistorico(ActionEvent event) {

    }

    @FXML
    void filtrarLibros(ActionEvent event) {

    }

    @FXML
    void filtrarPrestamo(ActionEvent event) {

    }

    @FXML
    void idiomaEspaniol(ActionEvent event) {

    }

    @FXML
    void idiomaIngles(ActionEvent event) {

    }

    @FXML
    void modificarAlumno(ActionEvent event) {

    }

    @FXML
    void modificarLibro(ActionEvent event) {

    }

    @FXML
    void prestarLibro(ActionEvent event) {

    }

}
