package org.example.tapesp2024.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class calculadora extends Stage {

    private Button[][] arrbtns;
    private TextField txtpantalla;
    private GridPane gpdteclado;
    private VBox vBox;
    private Scene escena;
    private String[] strteclas = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", "0", ".", "=", "-" };

    private void CrearUI(){
        arrbtns = new Button[4][4];
        txtpantalla = new TextField("0");
        txtpantalla.setAlignment(Pos.CENTER_RIGHT);
        txtpantalla.setEditable(false);
        gpdteclado = new GridPane();
        CrearTeclado();
        vBox = new VBox(txtpantalla, gpdteclado);
        escena = new Scene(vBox, 200, 200);
    }

    private void CrearTeclado(){
        for (int i = 0; i < arrbtns.length; i++) {
            for (int j = 0; j < arrbtns.length; j++) {
                arrbtns[j][i] = new Button(strteclas[4*i+j]);
                arrbtns[j][i].setPrefSize(50,50);
                int finalI = i;
                int finalJ = j;
                arrbtns[j][i].setOnAction(actionEvent -> detectar_tecla(strteclas[4* finalI + finalJ]));
                gpdteclado.add(arrbtns[j][i],j,i);
            }
        }
    }

    public calculadora() {
        CrearUI();
        this.setTitle("Calculadora jitomatera");
        this.setScene(escena);
        this.show();
    }

    private void detectar_tecla(String tecla){
        //txtpantalla.clear();
        txtpantalla.appendText(tecla);
    }
}
