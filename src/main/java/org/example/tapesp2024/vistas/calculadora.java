package org.example.tapesp2024.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class calculadora extends Stage {

    private Button[] arrbtns = new Button[16];
    private TextField txtpantalla;
    private GridPane teclado;
    private VBox vBox;
    private Scene escena;

    private void CrearUI(){
        arrbtns = new Button[16];
        txtpantalla = new TextField("0");
        teclado = new GridPane();
        vBox = new VBox();
        escena = new Scene();
    }

    public Calculadora() {
        this.setTitle("Calculadora jitomatera");
        this.setScene();
        this.show();
    }
}
