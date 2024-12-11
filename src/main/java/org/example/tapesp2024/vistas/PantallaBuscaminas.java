package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class PantallaBuscaminas extends Stage {

    private TextField campoBombas;
    private Button botonCrearCampo;
    private VBox vbox;
    private GridPane grid;
    private int filas = 10;
    private int columnas = 10;
    private int[][] campoMinado; // 1 para bombas, 0 para espacios vacíos
    private Button[][] botones;
    private int bombas;
    private boolean juegoTerminado = false;

    public PantallaBuscaminas() {
        CrearIU();
        this.setTitle("Buscaminas");
        Scene escena = new Scene(vbox, 385, 485);
        this.setScene(escena);
        this.show();
    }

    private void CrearIU() {
        Label etiquetaBombas = new Label("Cantidad de Bombas:");
        campoBombas = new TextField();
        campoBombas.setPromptText("Número de Bombas");

        botonCrearCampo = new Button("Crear Campo Minado");
        botonCrearCampo.setOnAction(e -> crearCampoMinado());

        vbox = new VBox(10, etiquetaBombas, campoBombas, botonCrearCampo);
        vbox.setPadding(new Insets(10));

        grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);

        vbox.getChildren().add(grid);
    }

    private void crearCampoMinado() {
        // Validar entrada
        try {
            bombas = Integer.parseInt(campoBombas.getText());
            if (bombas == 0) {
                mostrarAlerta("Campo vacío", "No se ha creado el campo ya que no hay bombas.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Introduce un número válido de bombas");
            return;
        }

        // Desactivar el botón para evitar reiniciar el juego una vez iniciado
        botonCrearCampo.setDisable(true);

        // Limpiar el grid y crear la matriz del campo minado
        grid.getChildren().clear();
        campoMinado = new int[filas][columnas]; // Inicializar el campo aquí, sin bombas
        botones = new Button[filas][columnas];
        juegoTerminado = false;
        primerClick = true; // Restablecer para el nuevo juego

        // Crear los botones del campo minado
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button btn = new Button();
                btn.setMinSize(30, 30);
                final int x = i;
                final int y = j;

                // Acción al hacer click izquierdo
                btn.setOnAction(e -> manejarClickIzquierdo(x, y));

                // Acción al hacer click derecho
                btn.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        manejarClickDerecho(btn, x, y);
                    }
                });

                botones[i][j] = btn;
                grid.add(btn, j, i); // Añadir botón al GridPane
            }
        }
    }



    private void colocarBombas(int safeX, int safeY) {
        Random random = new Random();
        int bombasColocadas = 0;

        while (bombasColocadas < bombas) {
            int x = random.nextInt(filas);
            int y = random.nextInt(columnas);

            if (campoMinado[x][y] != 1 && !(x == safeX && y == safeY)) {
                campoMinado[x][y] = 1; // Colocar bomba
                bombasColocadas++;
            }
        }
    }


    private boolean primerClick = true;

    private void manejarClickIzquierdo(int x, int y) {
        if (juegoTerminado || botones[x][y].getText().equals("🚩")) {
            return;
        }

        if (primerClick) {
            colocarBombas(x, y);
            primerClick = false;
        }

        if (campoMinado[x][y] == 1) { // Si hay una bomba
            botones[x][y].setText("💣");
            mostrarAlerta("Perdiste", "¡Has hecho click en una bomba!");
            juegoTerminado = true;
            botonCrearCampo.setDisable(false); // Activar el botón para reiniciar el juego
        } else {
            int bombasAdyacentes = contarBombasAdyacentes(x, y);
            if (bombasAdyacentes == 0) {
                botones[x][y].setText(""); // No mostrar ningún número si no hay bombas adyacentes
                revelarCasillasAdyacentes(x, y); // Revelar y desactivar casillas adyacentes vacías
            } else {
                botones[x][y].setText(String.valueOf(bombasAdyacentes));
                botones[x][y].setDisable(true); // Desactivar el botón al mostrar el número
            }
        }

        // Verificar si el usuario ha ganado
        verificarVictoria();
    }




    private void manejarClickDerecho(Button btn, int x, int y) {
        if (btn.getText().equals("🚩")) {
            btn.setText(""); // Quitar bandera
        } else {
            btn.setText("🚩"); // Colocar bandera
        }
    }

    private int contarBombasAdyacentes(int x, int y) {
        int count = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < filas && j >= 0 && j < columnas) {
                    if (campoMinado[i][j] == 1) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private void revelarCasillasAdyacentes(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < filas && j >= 0 && j < columnas && botones[i][j].isDisable() == false) {
                    int bombasAdyacentes = contarBombasAdyacentes(i, j);
                    if (bombasAdyacentes == 0) {
                        botones[i][j].setText(""); // No mostrar ningún número si no hay bombas adyacentes
                        botones[i][j].setDisable(true); // Desactivar el botón
                        revelarCasillasAdyacentes(i, j); // Llamada recursiva para revelar casillas vacías
                    } else {
                        botones[i][j].setText(String.valueOf(bombasAdyacentes));
                        botones[i][j].setDisable(true); // Desactivar el botón si tiene bombas adyacentes
                    }
                }
            }
        }
    }

    private void verificarVictoria() {
        boolean haGanado = true;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (campoMinado[i][j] == 0 && !botones[i][j].isDisable()) {
                    haGanado = false; // Si hay alguna casilla sin bomba que no está desactivada, no se ha ganado
                    break;
                }
            }
        }

        if (haGanado) {
            mostrarAlerta("¡Ganaste!", "¡Felicidades, has descubierto todas las casillas sin bombas!");
            juegoTerminado = true;
            botonCrearCampo.setDisable(false); // Activar el botón para reiniciar el juego
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

