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
    private GridPane gpdteclado;
    private VBox vBox;
    private Scene escena;

    private void CrearUI(){
        arrbtns = new Button[16];
        txtpantalla = new TextField("0");
        gpdteclado = new GridPane();
        vBox = new VBox(txtpantalla, gpdteclado);
        escena = new Scene(vBox, 200, 200);
    }

    private void CrearTeclado(){
        gpdteclado.set
    }

    public calculadora() {
        CrearUI();
        this.setTitle("Calculadora jitomatera");
        this.setScene(escena);
        this.show();
    }
}
