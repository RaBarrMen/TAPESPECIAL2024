package org.example.tapesp2024.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.AlbumDAO;

public class PantallaAdminAlbum extends Stage {
     TextField txtNombre = new TextField();
     TextField txtCosto = new TextField();
     Label lblNombre = new Label();
     Label lblCosto = new Label();
     Button btnGuardar = new Button();
     Button btnSalir = new Button();
     VBox vbox_album = new VBox();
     AlbumDAO album;
     int id_usuario;

     public PantallaAdminAlbum(int id_usuario) {
         this.id_usuario = id_usuario;
         CrearIU();
         this.setTitle("Pantalla de Administrador");
         Scene escena = new Scene(vbox_album, 400, 500);
         escena.getStylesheets().add(getClass().getResource("/estilos/PantallaAdminCanciones.css").toExternalForm());
         this.setScene(escena);
         this.show();
     }

     private void CrearIU() {
         lblNombre = new Label("Nombre: ");
         txtNombre = new TextField();

         lblCosto = new Label("Costo: ");
         txtCosto = new TextField();


         btnGuardar = new Button("Guardar");
         btnGuardar.setOnAction(event -> guardarAlbum());

         btnSalir = new Button("Regresar");
         btnSalir.setOnAction(event -> regresarPantallaAdmin());

         vbox_album.setAlignment(Pos.CENTER);
         vbox_album.getChildren().addAll(lblNombre,txtNombre, lblCosto, txtCosto, btnGuardar, btnSalir);
     }

    private void guardarAlbum() {
         album = new AlbumDAO();
         album.setAlbum(txtNombre.getText());
         album.setCosto_album(Double.parseDouble(txtCosto.getText()));
         album.INSERT();
    }

    private void regresarPantallaAdmin() {
         PantallaAdminCanciones pantallaAdminCanciones = new PantallaAdminCanciones(this.id_usuario);
         pantallaAdminCanciones.show();
         this.close();
    }

}
