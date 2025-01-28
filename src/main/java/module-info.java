module org.hugo.dein.proyectodein2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.hugo.dein.proyectodein2 to javafx.fxml;
    opens org.hugo.dein.proyectodein2.Modelos to javafx.base;

}