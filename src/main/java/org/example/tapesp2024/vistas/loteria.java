package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.net.URL;

public class loteria extends Stage {
    private HBox hbox_main, hbox_buttons;
    private VBox vbox_tablilla, vbox_mazo;
    private Button btn_iniciar, btn_anterior, btn_siguiente;
    private Label lbl_timer;
    private GridPane gdp_tablilla;
    private ImageView imagen_mazo;
    private Scene escena;
    private String[] arreglo_imagenes = {"", "barril.jpeg", "botella.jpeg", "catrin.jpeg", "chavorruco.jpeg", "concha.jpeg", "luchador.jpeg", "maceta.jpeg", "rosa.jpeg", "tacos.jpeg", "venado.jpeg", "guajolota.jpg", "mirrey.jpg", "pendejo.png", "twitter.jpg", "feminista.jpg", "uber.jpg"};
    private Button[][] arreglo_botones_tablilla;
    private Panel panel_principal;

    private Timeline timeline;
    private int currentImageIndex = 0;
    private int tiempoRestante = 3; // Tiempo en segundos para cambiar la carta
    private TextField txt_tiempoRestante;

    // nuevo
    private TextField txt_puntos;
    private int puntos = 0;

    public loteria() {
        CrearUI();
        this.setTitle("Loteria Mexicana");
        this.setScene(escena);
        this.setMaximized(true);
        this.show();
    }

    private void CrearUI() {
        ImageView iv_anterior, iv_siguiente;
        iv_anterior = new ImageView(new Image(getClass().getResource("/images/anterior.png").toString()));
        iv_anterior.setFitHeight(50);
        iv_anterior.setFitWidth(50);
        iv_siguiente = new ImageView(new Image(getClass().getResource("/images/siguiente.png").toString()));
        iv_siguiente.setFitHeight(50);
        iv_siguiente.setFitWidth(50);

        gdp_tablilla = new GridPane();
        CrearTablilla();

        btn_anterior = new Button();
        btn_anterior.setGraphic(iv_anterior);
        btn_anterior.setOnAction(e -> CrearTablilla());
        btn_siguiente = new Button();
        btn_siguiente.setGraphic(iv_siguiente);
        btn_siguiente.setOnAction(e -> CrearTablilla());

        hbox_buttons = new HBox(btn_anterior, btn_siguiente);
        vbox_tablilla = new VBox(gdp_tablilla, hbox_buttons);

        CrearMazo();
        hbox_main = new HBox(vbox_tablilla, vbox_mazo);
        panel_principal = new Panel("Loteria mexa :)");
        panel_principal.getStyleClass().add("panel-success");
        panel_principal.setBody(hbox_main);
        hbox_main.setSpacing(20);
        hbox_main.setPadding(new Insets(20));
        escena = new Scene(panel_principal, 800, 600);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        escena.getStylesheets().add(getClass().getResource("/estilos/loteria.css").toExternalForm());
    }

    private void CrearMazo() {
        lbl_timer = new Label("00:00");
        imagen_mazo = new ImageView(new Image(getClass().getResource("/images/dorso.jpeg").toString()));
        imagen_mazo.setFitHeight(400);
        imagen_mazo.setFitWidth(200);

        txt_tiempoRestante = new TextField(String.valueOf(tiempoRestante));
        txt_tiempoRestante.setEditable(false);  // Hacer que el campo no sea editable

        btn_iniciar = new Button("Iniciar juego");
        btn_iniciar.getStyleClass().setAll("btn-sm", "btn-success");
        btn_iniciar.setOnAction(e -> iniciarJuego());

        vbox_mazo = new VBox(lbl_timer, imagen_mazo, txt_tiempoRestante, btn_iniciar);
        vbox_mazo.setSpacing(10);

        //nuevo
        txt_puntos = new TextField("0");
        txt_puntos.setEditable(false);
        vbox_mazo = new VBox(lbl_timer, imagen_mazo, txt_tiempoRestante, txt_puntos, btn_iniciar);

    }

    private void iniciarJuego() {
        if (timeline != null) {
            timeline.stop();  // Detener el timeline si ya estaba corriendo
        }

        // Reiniciar el índice de la imagen y puntos
        currentImageIndex = 0;
        puntos = 0;
        txt_puntos.setText("0");

        // Deshabilitar los botones para cambiar la tablilla
        btn_anterior.setDisable(true);
        btn_siguiente.setDisable(true);
        btn_iniciar.setDisable(true);  // Deshabilitar botón de iniciar

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    tiempoRestante--;
                    txt_tiempoRestante.setText(String.valueOf(tiempoRestante));

                    // Cuando el tiempo llega a 0, cambiar la imagen
                    if (tiempoRestante <= 0) {
                        cambiarImagenMazo();
                        tiempoRestante = 3;  // Reiniciar el tiempo
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);  // Hacer que se repita indefinidamente
        timeline.play();
    }



    private void cambiarImagenMazo() {
        if (currentImageIndex >= arreglo_imagenes.length - 1) {
            timeline.stop();  // Detener el juego
            verificarResultado();  // Verificar si se ha ganado o perdido
            return;  // No continuar si ya mostramos todas las cartas
        }

        // Cambiar la imagen del mazo
        currentImageIndex = (currentImageIndex + 1) % arreglo_imagenes.length ;
        imagen_mazo.setImage(new Image(getClass().getResource("/images/" + arreglo_imagenes[currentImageIndex]).toString()));
    }

    private void verificarResultado() {
        boolean todasCasillasMarcadas = true;

        // Verificar si todas las casillas están deshabilitadas
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!arreglo_botones_tablilla[i][j].isDisabled()) {
                    todasCasillasMarcadas = false;
                    break;
                }
            }
        }

        if (todasCasillasMarcadas) {
            // Mostrar mensaje de victoria
            System.out.println("¡Has ganado!");
        } else {
            // Mostrar mensaje de derrota
            System.out.println("Has perdido. No seleccionaste todas las casillas a tiempo.");
        }
    }


    private void CrearTablilla() {
        arreglo_botones_tablilla = new Button[4][4];

        // Crear una lista con las imágenes disponibles, excluyendo las vacías
        List<String> imagenesDisponibles = new ArrayList<>();
        for (String imagen : arreglo_imagenes) {
            if (!imagen.isEmpty()) {
                // Verificar si el archivo de la imagen existe
                URL imagenURL = getClass().getResource("/images/" + imagen);
                if (imagenURL != null) {
                    imagenesDisponibles.add(imagen);
                }
            }
        }

        // Mezclar las imágenes aleatoriamente
        Collections.shuffle(imagenesDisponibles);

        // Limitar la lista a 16 imágenes, o menos si no hay suficientes
        List<String> imagenesSeleccionadas = imagenesDisponibles.size() >= 16
                ? imagenesDisponibles.subList(0, 16)
                : imagenesDisponibles;

        int imgIndex = 0;  // Índice para recorrer la lista de imágenes

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (imgIndex < imagenesSeleccionadas.size()) {
                    String nombreImagen = imagenesSeleccionadas.get(imgIndex);
                    Image img = new Image(getClass().getResource("/images/" + nombreImagen).toString());
                    ImageView iv = new ImageView(img);
                    iv.setFitWidth(70);
                    iv.setFitHeight(120);

                    arreglo_botones_tablilla[j][i] = new Button();
                    arreglo_botones_tablilla[j][i].setGraphic(iv);

                    // Guardar la imagen en la propiedad del botón para compararla después
                    arreglo_botones_tablilla[j][i].setUserData(nombreImagen);

                    // Añadir evento para cuando se hace clic en la carta
                    arreglo_botones_tablilla[j][i].setOnAction(e -> {
                        Button botonSeleccionado = (Button) e.getSource();
                        String imagenBoton = (String) botonSeleccionado.getUserData();

                        // Verificar si coincide con la imagen actual del mazo
                        if (imagenBoton.equals(arreglo_imagenes[currentImageIndex])) {
                            puntos++;
                            txt_puntos.setText(String.valueOf(puntos));
                            botonSeleccionado.setDisable(true); // Deshabilitar el botón si es la imagen correcta
                        }
                    });

                    gdp_tablilla.add(arreglo_botones_tablilla[j][i], j, i);
                    imgIndex++;
                } else {
                    // Si no hay más imágenes, crear botones vacíos o deshabilitados
                    arreglo_botones_tablilla[j][i] = new Button("Vacío");
                    arreglo_botones_tablilla[j][i].setDisable(true);  // Deshabilitar el botón
                    gdp_tablilla.add(arreglo_botones_tablilla[j][i], j, i);
                }
            }
        }
    }


}

