module org.example.tapesp2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.tapesp2024 to javafx.fxml;
    exports org.example.tapesp2024;
    //abrir modelos
    opens org.example.tapesp2024.models;

    //necesarios para dependencias
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
}