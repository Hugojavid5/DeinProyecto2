module org.hugo.dein.proyectodein {
    requires javafx.controls;
    requires javafx.fxml;
    requires cdi.api;
    requires java.sql.rowset;

    opens org.hugo.dein.proyectodein to javafx.fxml;
    opens org.hugo.dein.proyectodein.Modelos to javafx.base;
    opens org.hugo.dein.proyectodein.Controlers to javafx.fxml;

    exports org.hugo.dein.proyectodein;
}
