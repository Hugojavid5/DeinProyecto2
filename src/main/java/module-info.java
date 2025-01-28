module org.hugo.dein.proyectodein2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.hugo.dein.proyectodein2 to javafx.fxml;
    exports org.hugo.dein.proyectodein2;
}