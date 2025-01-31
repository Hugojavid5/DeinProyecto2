module org.hugo.dein.proyectodein {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires net.sf.jasperreports.core;
    requires cdi.api;


    opens org.hugo.dein.proyectodein to javafx.fxml;
    exports org.hugo.dein.proyectodein;

    opens org.hugo.dein.proyectodein.Modelos to javafx.base;
    exports org.hugo.dein.proyectodein.Modelos;

    opens org.hugo.dein.proyectodein.Controlers to javafx.fxml;
    exports org.hugo.dein.proyectodein.Controlers;


}
