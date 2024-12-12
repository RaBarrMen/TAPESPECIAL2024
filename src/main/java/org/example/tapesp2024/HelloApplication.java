package org.example.tapesp2024;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

import org.example.tapesp2024.models.Conexion;
import org.example.tapesp2024.vistas.*;

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
    private MenuItem menu_item_calculadora, menu_item_loteria, menu_item_spotify, menu_item_buscaminas, menu_item_corredor, menu_item_impresion, menu_item_spotify2,menu_item_spotify3,menu_item_spotify4;
    int id_usuario;

    public void CrearUI(){
        menu_item_calculadora = new MenuItem("Calculadora");
        menu_item_calculadora.setOnAction(actionEvent -> new calculadora());

        menu_item_loteria = new MenuItem("Loteria");
        menu_item_loteria.setOnAction(actionEvent -> new loteria());

        menu_item_spotify = new MenuItem("Spotify");
        menu_item_spotify.setOnAction(actionEvent -> new login_spotify(this.id_usuario));

        menu_item_buscaminas = new MenuItem("Buscaminas");
        menu_item_buscaminas.setOnAction(actionEvent -> new PantallaBuscaminas());

        menu_item_corredor = new MenuItem("Pista");
        menu_item_corredor.setOnAction(actionEvent -> new Pista());

        menu_item_impresion = new MenuItem("Impresion");
        menu_item_impresion.setOnAction(actionEvent -> new Simulacion());

        //pantallas de prueba anti pendejos

//        menu_item_spotify2 = new MenuItem("Spotify2");
//        menu_item_spotify2.setOnAction(actionEvent -> new PantallaAdminCanciones());
//
//        menu_item_spotify3 = new MenuItem("Spotify3");
//        menu_item_spotify3.setOnAction(actionEvent -> new login_admin_spotify());
//
//        menu_item_spotify4 = new MenuItem("Spotify4");
//        menu_item_spotify4.setOnAction(actionEvent -> new CancionComprar());

        //menu_item_spotify5 = new MenuItem("Spotify5");
        //menu_item_spotify5.setOnAction(actionEvent -> new FormularioCancion(TableView< CancionDAO >,CancionDAO));
        //fin de pendejos

        menu_competencia1 = new Menu("Competencia 1");
        menu_competencia1.getItems().addAll(menu_item_calculadora, menu_item_loteria);

        menu_competencia2 = new Menu("Competencia 2");
        menu_competencia2.getItems().addAll(menu_item_spotify, menu_item_buscaminas, menu_item_corredor, menu_item_impresion);


        menu_bar_principal = new MenuBar(menu_competencia1, menu_competencia2);
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