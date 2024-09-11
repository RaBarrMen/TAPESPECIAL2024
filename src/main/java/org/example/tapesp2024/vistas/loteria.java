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

public class loteria extends Stage {
    private HBox hbox_main, hbox_buttons;
    private VBox vbox_tablilla, vbox_mazo;
    private Button btn_iniciar, btn_anterior, btn_siguiente;
    private Label lbl_timer;
    private GridPane gdp_tablilla;
    private ImageView imagen_mazo;
    private Scene escena;
    private String[] arreglo_imagenes = {"barril.jpeg", "botella.jpeg", "catrin.jpeg", "chavorruco.jpeg", "concha.jpeg", "luchador.jpeg", "maceta.jpeg", "rosa.jpeg", "tacos.jpeg", "venado.jpeg"};
    private Button[][] arreglo_botones_tablilla;
    private Panel panel_principal;


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
        btn_siguiente = new Button();
        btn_siguiente.setGraphic(iv_siguiente);

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
        //Image imagenmazo = new Image(getClass().getResource("/images/siguiente.png").toString());
        lbl_timer = new Label("00:00");
        imagen_mazo = new ImageView(new Image( getClass().getResource("/images/dorso.jpeg").toString()));
        imagen_mazo.setFitHeight(400);
        imagen_mazo.setFitWidth(200);;
        btn_iniciar = new Button("Iniciar juego");
        btn_iniciar.getStyleClass().setAll("btn-sm", "btn-success");
        vbox_mazo = new VBox(lbl_timer, imagen_mazo,btn_iniciar);
        vbox_mazo.setSpacing(10);
    }

    private void CrearTablilla() {
        arreglo_botones_tablilla = new Button[4][4];
        Image img;
        ImageView iv = new ImageView();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                img = new Image(getClass().getResource("/images/barril.jpeg").toString());
                iv = new ImageView(img);
                arreglo_botones_tablilla[j][i] = new Button();
                iv.setFitWidth(70);
                iv.setFitHeight(120);
                arreglo_botones_tablilla[j][i].setGraphic(iv);
                gdp_tablilla.add(arreglo_botones_tablilla[j][i], j, i);
            }
        }
    }
}
