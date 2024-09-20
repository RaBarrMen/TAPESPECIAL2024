module org.example.tapesp2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.tapesp2024 to javafx.fxml;
    exports org.example.tapesp2024;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
}