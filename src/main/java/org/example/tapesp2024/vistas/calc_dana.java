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

public class calc_dana extends Stage {

    private HBox hBoxMain, hBoxButtons;
    private VBox vbxTablilla, vbxMazo;
    private Button btnAnteriror, btnSiguiente, btnIniciar;
    private Label lblTimer;
    private GridPane gdpTablilla;

    private ImageView imvMazo;
    private Scene escena;
    private String[] arImages = {"barril.jpeg","botella.jpeg","catrin.jpeg", "chavorruco.jpeg","concha.jpeg","luchador.jpeg","maceta.jpeg","rosa.jpeg","venado,jpeg"};
    private Button[][] artBtnTab;

    public calc_dana(){
        CrearUI();
        this.setTitle("Loteria Mexicana 🙂");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvAnt, imvSig;
        imvAnt = new ImageView(new Image(getClass().getResource("/images/anterior.png").toString()));
        imvAnt.setFitHeight(50);
        imvAnt.setFitWidth(50);
        imvSig = new ImageView(new Image(getClass().getResource("/images/siguiente,png").toString()));
        imvSig.setFitHeight(50);
        imvSig.setFitWidth(50);

        gdpTablilla = new GridPane();
        CrearTabilla();
        btnAnteriror = new Button();
        btnAnteriror.setGraphic(imvAnt);
        btnSiguiente = new Button();
        btnSiguiente.setGraphic(imvSig);

        hBoxButtons = new HBox(btnAnteriror,btnSiguiente);
        vbxTablilla = new VBox(gdpTablilla,hBoxButtons);

        CrearMazo();

        hBoxMain = new HBox(vbxTablilla,vbxMazo);
        hBoxMain.setSpacing(20);
        hBoxMain.setPadding(new Insets(20));
        escena = new Scene(hBoxMain,800,600);

    }

    private void CrearMazo() {
        //Image imgMazo = new Image(getClass().getResource("/images/siguiente,png").toString());
        lblTimer = new Label("00:00");
        imvMazo = new ImageView(new Image(getClass().getResource("/images/siguiente,png").toString()));
        imvMazo.setFitHeight(400);
        imvMazo.setFitWidth(200);
        btnIniciar = new Button("Iniciar Juego");
        vbxMazo = new VBox(lblTimer,imvMazo,btnIniciar);
        vbxMazo.setSpacing(10);
    }

    private void CrearTabilla() {
        artBtnTab = new Button[4][4];
        Image img;
        ImageView imv = new ImageView();
        for (int i = 0; i < 4 ; i++){
            for (int j = 0; j < 4 ; j++){
                img = new Image(getClass().getResource("/images/rosa.jpeg").toString());
                imv = new ImageView(img);
                imv.setFitWidth(70);
                imv.setFitHeight(120);
                artBtnTab[j][i] = new Button();
                artBtnTab[j][i].setGraphic(imv);
                gdpTablilla.add(artBtnTab[j][i],j,i);

            }
        }
    }

}
