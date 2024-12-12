package org.example.tapesp2024.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.CancionDAO;
import org.example.tapesp2024.models.ClienteDAO;
import org.example.tapesp2024.models.Conexion;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class PantallaAdminCanciones extends Stage {
    Label label_Bienvenida;
    VBox vbox_compra = new VBox(15);
    Button button_regresar_usuario, button_comprar_cancion, button_comprar_album, buton_modificar_genero, button_reporte;
    HBox hbox_compra = new HBox(15);
    HBox hbox_icon = new HBox(15);
    ImageView imageViewCancion, imageViewAlbum, imageViewGenero;
    ClienteDAO clienteDAO = new ClienteDAO();
    int id_usuario;

    public PantallaAdminCanciones(int id_usuario) {
        this.id_usuario = id_usuario;
        CrearIU();
        this.setTitle("Pantalla de Administrador");
        Scene escena = new Scene(vbox_compra, 900, 500);
        escena.getStylesheets().add(getClass().getResource("/estilos/PantallaAdminCanciones.css").toExternalForm());
        this.setScene(escena);
        this.show();
    }

    private void CrearIU() {
        String nombreUsuario = clienteDAO.SELECTNAME(this.id_usuario).toString() /*!= null ? clienteDAO.getNombre() : "Usuario"*/;
        label_Bienvenida = new Label("Bienvenido de nuevo admin, " + nombreUsuario);

        button_regresar_usuario = new Button("Regresar al login");
        button_regresar_usuario.setOnAction(event -> mostarPantallaUsuario());

        button_comprar_cancion = new Button("Añadir canción");
        button_comprar_cancion.setOnAction(event -> {
            AddNewSong addNewSongDialog = new AddNewSong();
            addNewSongDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    CancionDAO cancion = addNewSongDialog.getCancion();
                    if (cancion != null) {
                        if (Conexion.connection == null) {
                            Conexion.crearConnection();
                        }

                        try {
                            cancion.setImagen_cancion(addNewSongDialog.imageBytes);
                            System.out.println(Arrays.toString(addNewSongDialog.imageBytes));
                            int rowsAffected = cancion.INSERT();
                            boolean success = rowsAffected > 0;
                            System.out.println(Arrays.toString(cancion.getImagen_cancion()));
                            if (success) {
                                cancion.INSERT_ALBUM();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Canción agregada");
                                alert.setHeaderText("Canción agregada con éxito");
                                alert.setContentText("Título: " + cancion.getCancion());
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("Hubo un problema al agregar la canción");
                                alert.showAndWait();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Ocurrió un error al guardar la canción");
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Acción cancelada");
                        alert.setHeaderText("No se completó la acción");
                        alert.showAndWait();
                    }
                }
            });
        });



        button_comprar_album = new Button("Añadir álbum");
        button_comprar_album.setOnAction(event -> albumComprar());

        buton_modificar_genero = new Button("Modificar genero");
        buton_modificar_genero.setOnAction(event -> generoModificar());

        button_reporte = new Button("Reporte");
        button_reporte.setOnAction(event -> abrirReporte());

        label_Bienvenida.getStyleClass().add("title");
        button_regresar_usuario.getStyleClass().add("btn");
        button_comprar_cancion.getStyleClass().add("btn");
        button_comprar_album.getStyleClass().add("btn");
        button_reporte.getStyleClass().add("btn");
        buton_modificar_genero.getStyleClass().add("btn");


        Image image_cancion = new Image(getClass().getResourceAsStream("/images/iconCancion.jpg"));
        if (image_cancion != null) {
            imageViewCancion = new ImageView(image_cancion);
            imageViewCancion.setFitWidth(150);
            imageViewCancion.setFitHeight(150);
            imageViewCancion.getStyleClass().add("imagenes");
        } else {
            System.out.println("Error: Imagen 'iconCancion.png' no encontrada.");
        }

        Image image_album = new Image(getClass().getResourceAsStream("/images/albumCancion.jpg"));
        if (image_album != null) {
            imageViewAlbum = new ImageView(image_album);
            imageViewAlbum.setFitWidth(150);
            imageViewAlbum.setFitHeight(150);
            imageViewAlbum.getStyleClass().add("imagenes");
        } else {
            System.out.println("Error: Imagen 'albumCancion.png' no encontrada.");
        }

        Image image_genero = new Image(getClass().getResourceAsStream("/images/genero.jpeg"));
        if (image_genero != null) {
            imageViewGenero = new ImageView(image_genero);
            imageViewGenero.setFitWidth(150);
            imageViewGenero.setFitHeight(150);
            imageViewGenero.getStyleClass().add("imagenes");
        } else {
            System.out.println("Error: Imagen 'albumCancion.png' no encontrada.");
        }


        hbox_icon.setAlignment(Pos.CENTER);
        if (imageViewCancion != null && imageViewAlbum != null) {
            hbox_icon.getChildren().addAll(imageViewCancion, imageViewAlbum, imageViewGenero);
        }

        hbox_compra.setAlignment(Pos.CENTER);
        hbox_compra.getChildren().addAll(button_comprar_cancion, button_comprar_album, buton_modificar_genero);

        vbox_compra.setAlignment(Pos.CENTER);
        vbox_compra.getChildren().addAll(label_Bienvenida, hbox_icon, hbox_compra, button_reporte, button_regresar_usuario);
        vbox_compra.getStyleClass().add("card");
    }

    private void generoModificar() {
        PantallaAdminGenero genero = new PantallaAdminGenero(this.id_usuario);
        genero.show();
        this.close();

    }

    private void albumComprar() {
        PantallaAdminAlbum album = new PantallaAdminAlbum(this.id_usuario);
        album.show();
        this.close();
    }

    private void cancionComprar() {
        PantallaCancion pantalla = new PantallaCancion();
        pantalla.show();
        this.close();
    }

    private void mostarPantallaUsuario() {
        login_spotify pantalla = new login_spotify(this.id_usuario);
        pantalla.show();
        this.close();
    }

    private void abrirReporte() {
        GraficasVentas graficasVentas = new GraficasVentas(this.id_usuario);
        graficasVentas.show();
        this.close();
    }
}
