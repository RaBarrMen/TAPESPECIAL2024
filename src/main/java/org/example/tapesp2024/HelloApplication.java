package org.example.tapesp2024;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.Conexion;
import org.example.tapesp2024.vistas.calculadora;
import org.example.tapesp2024.vistas.loteria;
import org.example.tapesp2024.vistas.calc_dana;

import java.io.IOException;

/* checar brach: git branch
   1er comando: git init
   2do comando: git status
   3er comando: git add .
   4to comando: git commit -a -m "comentario"
   5to comando: git remote add origin https://github.com/RaBarrMen/TAPESPECIAL2024.git
   6to comando: git push -u origin main
*/

public class HelloApplication extends Application {

    private BorderPane border_principal;
    private MenuBar menu_bar_principal;
    private Menu menu_competencia1, menu_competencia2, salida;
    private MenuItem menu_item_calculadora, menu_item_loteria;

    public void CrearUI(){
        menu_item_calculadora = new MenuItem("Calculadora");
        menu_item_calculadora.setOnAction(actionEvent -> new calculadora());

        menu_item_loteria = new MenuItem("Loteria");
        menu_item_loteria.setOnAction(actionEvent -> new loteria());

        menu_competencia1 = new Menu("Competencia 1");
        menu_competencia1.getItems().addAll(menu_item_calculadora, menu_item_loteria);

        menu_bar_principal = new MenuBar(menu_competencia1);
        border_principal = new BorderPane();
        border_principal.setTop(menu_bar_principal);
    }
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        CrearUI();
        Scene scene = new Scene(border_principal, 320, 240);
        scene.getStylesheets().add(getClass().getResource("/estilos/main.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        Conexion.crearConnection();
    }

    public static void main(String... args) {
        launch();
    }
}