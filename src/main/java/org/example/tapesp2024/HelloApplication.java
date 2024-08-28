package org.example.tapesp2024;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
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

    private Button btn1, btn2, btn3;
    private VBox vbox;
    private HBox hbox;

    public void CrearUI(){
        btn1 = new Button("Button 1");
        btn2 = new Button("Button 2");
        btn3 = new Button("Button 3");
        vbox = new VBox(btn1, btn2, btn3);
        hbox = new HBox(btn1, btn2, btn3);

    }
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        CrearUI();
        Scene scene = new Scene(hbox, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}