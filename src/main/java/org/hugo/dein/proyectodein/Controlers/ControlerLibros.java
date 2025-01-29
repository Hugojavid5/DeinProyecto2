package org.hugo.dein.proyectodein.Controlers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hugo.dein.proyectodein.Modelos.ModeloLibro;

public class ControlerLibros{

    @FXML
    private ComboBox<?> comboEstado;

    @FXML
    private Label lbl_imagen;

    @FXML
    private TextField txt_autor;

    @FXML
    private TextField txt_editorial;

    @FXML
    private TextField txt_libro;


    private ModeloLibro libro;
    public void setLibro(ModeloLibro libro) {
        this.libro=libro;
    }
}
