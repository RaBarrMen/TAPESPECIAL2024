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
    private Button btnclear;
    private TextField txtpantalla;
    private GridPane gpdteclado;
    private VBox vBox;
    private Scene escena;
    private String[] strteclas = {"7", "8", "9", "*", "4", "5", "6", "/", "1", "2", "3", "+", "0", ".", "=", "-" };
    private String primerNumero = "";
    private String operadorActual = "";
    private boolean nuevoNumero = false;
    private boolean operadorSeleccionado = false;

    private void CrearUI(){
        arrbtns = new Button[4][4];
        txtpantalla = new TextField();
        txtpantalla.setAlignment(Pos.CENTER_RIGHT);
        txtpantalla.setEditable(false);
        gpdteclado = new GridPane();
        CrearTeclado();
        btnclear = new Button("CE");
        btnclear.setId("font-button");
        btnclear.setOnAction(e -> limpiarPantalla());
        vBox = new VBox(txtpantalla, btnclear, gpdteclado);
        escena = new Scene(vBox, 215, 270);
        escena.getStylesheets().add(getClass().getResource("/estilos/calc.css").toExternalForm());
    }

    private void CrearTeclado(){
        for (int i = 0; i < arrbtns.length; i++) {
            for (int j = 0; j < arrbtns.length; j++) {
                arrbtns[j][i] = new Button(strteclas[4*i+j]);
                arrbtns[j][i].setPrefSize(50, 50);
                int finalI = i;
                int finalJ = j;
                arrbtns[j][i].setOnAction(actionEvent -> detectar_tecla(strteclas[4*finalI + finalJ])); // Deteccion de teclas
                gpdteclado.add(arrbtns[j][i], j, i);
            }
        }
    }

    public calculadora() {
        CrearUI();
        this.setTitle("Calculadora jitomatera");
        this.setScene(escena);
        this.show();
    }

    private void detectar_tecla(String tecla) {
        if (esOperador(tecla)) {
            // Permitir iniciar con número negativo
            if (tecla.equals("-") && txtpantalla.getText().isEmpty()) {
                txtpantalla.appendText(tecla);  // Permitir ingresar "-" al principio
                nuevoNumero = false;  // Indicar que se está ingresando un número
                return;
            }

            // Si es un operador, y no se ha seleccionado otro antes, permite operacion
            if (!operadorSeleccionado) {
                if (!primerNumero.isEmpty()) {
                    calcular_resultado();
                }
                operadorActual = tecla;
                primerNumero = txtpantalla.getText();
                nuevoNumero = true;
                operadorSeleccionado = true;  // Marca que ya hay un operador seleccionado
            } else {
                // Actualiza el operador si se presiona mas de una vez sin calcular
                operadorActual = tecla;
            }
        } else if (tecla.equals("=")) {
            calcular_resultado();  // Evalua la expresion al presionar "="
            operadorSeleccionado = false;  // Resetea el estado del operador
        } else {
            // Si es un numero o punto decimal, agrega a la pantalla
            if (nuevoNumero) {
                txtpantalla.clear();
                nuevoNumero = false;
            }
            if (tecla.equals(".") && txtpantalla.getText().contains(".")) {
                return;  // Evita mas de un punto decimal por número
            }
            txtpantalla.appendText(tecla);
            operadorSeleccionado = false;  // Permite nuevo operador
        }
    }

    private void limpiarPantalla() {
        txtpantalla.setText("");
        primerNumero = "";
        operadorActual = "";
        nuevoNumero = false;
        operadorSeleccionado = false;
    }

    private void calcular_resultado() {
        try {
            String segundoNumero = txtpantalla.getText();
            if (!primerNumero.isEmpty() && !segundoNumero.isEmpty() && !operadorActual.isEmpty()) {
                double resultado = realizarOperacion(Double.parseDouble(primerNumero), Double.parseDouble(segundoNumero), operadorActual);
                txtpantalla.setText(String.valueOf(resultado));
                primerNumero = String.valueOf(resultado);  // Actualiza el primer numero con el resultado
                operadorActual = "";  // Reinicia el operador
                nuevoNumero = true;  // Permite ingreso de nuevo número
                operadorSeleccionado = false;
            }
        } catch (Exception e) {
            txtpantalla.setText("Error");
        }
    }

    private double realizarOperacion(double a, double b, String operador) {
        switch (operador) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    txtpantalla.setText("Error: Div por 0");
                    return 0;
                }
                return a / b;
            default:
                txtpantalla.setText("Operador no válido");
                return 0;
        }
    }


    private boolean esOperador(String tecla) {
        return tecla.equals("+") || tecla.equals("-") || tecla.equals("*") || tecla.equals("/");
    }
}