package org.hugo.dein.proyectodein2.Controlers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class ControlerLibros {

    @FXML
    private CheckBox cbBajaLibro;

    @FXML
    private ComboBox<?> cbEstadoLibro;

    @FXML
    private ImageView ivImagenDePortada;

    @FXML
    private TextField tfAutorLibro;

    @FXML
    private TextField tfEditorialLibro;

    @FXML
    private TextField tfTituloLibro;

    @FXML
    void aniadirImagen(ActionEvent event) {

    }

    @FXML
    void borrarImagen(ActionEvent event) {

    }

}
