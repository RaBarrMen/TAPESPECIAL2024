package org.example.tapesp2024.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PantallaCompra extends Stage {
    Label label_Bienvenida;
    VBox vbox_compra = new VBox(15);
    Button button_regresar_usuario, button_comprar_cancion, button_comprar_album;
    HBox hbox_compra = new HBox(15);
    HBox hbox_icon = new HBox(15);
    ImageView imageViewCancion, imageViewAlbum;

    public PantallaCompra() {
        CrearIU();
        this.setTitle("Pantalla de Compra");
        Scene escena = new Scene(vbox_compra, 400, 500);
        this.setScene(escena);
        this.show();
    }

    private void CrearIU() {
        label_Bienvenida = new Label("Bienvenido");
        button_regresar_usuario = new Button("Regresar a pantalla de usuario");
        button_regresar_usuario.setOnAction(event -> mostarPantallaUsuario());

        button_comprar_cancion = new Button("Comprar canción");
        button_comprar_cancion.setOnAction(event -> cancionComprar());

        button_comprar_album = new Button("Comprar álbum");
        button_comprar_album.setOnAction(event -> albumComprar());

        // Cargar las imágenes con verificación
        Image image_cancion = new Image(getClass().getResourceAsStream("/images/iconCancion.png"));
        if (image_cancion != null) {
            imageViewCancion = new ImageView(image_cancion);
            imageViewCancion.setFitWidth(100);
            imageViewCancion.setFitHeight(100);
        } else {
            System.out.println("Error: Imagen 'iconCancion.png' no encontrada.");
        }

        Image image_album = new Image(getClass().getResourceAsStream("/images/albumCancion.png"));
        if (image_album != null) {
            imageViewAlbum = new ImageView(image_album);
            imageViewAlbum.setFitWidth(100);
            imageViewAlbum.setFitHeight(100);
        } else {
            System.out.println("Error: Imagen 'albumCancion.png' no encontrada.");
        }

        hbox_icon.setAlignment(Pos.CENTER);
        if (imageViewCancion != null && imageViewAlbum != null) {
            hbox_icon.getChildren().addAll(imageViewCancion, imageViewAlbum);
        }

        hbox_compra.setAlignment(Pos.CENTER);
        hbox_compra.getChildren().addAll(button_comprar_cancion, button_comprar_album);

        vbox_compra.setAlignment(Pos.CENTER);
        vbox_compra.getChildren().addAll(label_Bienvenida, hbox_icon, hbox_compra, button_regresar_usuario);
    }

    private void albumComprar() {
        // Implementación para comprar álbum
    }

    private void cancionComprar() {
        PantallaCancion pantalla = new PantallaCancion();
        pantalla.show();
        this.close();
    }

    private void mostarPantallaUsuario() {
        PantallaUsuario pantalla = new PantallaUsuario();
        pantalla.show();
        this.close();
    }
}
