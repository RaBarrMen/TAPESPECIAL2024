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
import org.example.tapesp2024.models.Conexion;

public class PantallaAdminCanciones extends Stage {
    Label label_Bienvenida;
    VBox vbox_compra = new VBox(15);
    Button button_regresar_usuario, button_comprar_cancion, button_comprar_album;
    HBox hbox_compra = new HBox(15);
    HBox hbox_icon = new HBox(15);
    ImageView imageViewCancion, imageViewAlbum;

    public PantallaAdminCanciones() {
        CrearIU();
        this.setTitle("Pantalla de Administrador");
        Scene escena = new Scene(vbox_compra, 400, 500);
        this.setScene(escena);
        this.show();
    }

    private void CrearIU() {
        label_Bienvenida = new Label("Bienvenido");
        button_regresar_usuario = new Button("Regresar al login");
        button_regresar_usuario.setOnAction(event -> mostarPantallaUsuario());

        button_comprar_cancion = new Button("Añadir canción");
        button_comprar_cancion.setOnAction(event -> {
            // Crear el diálogo para añadir una nueva canción
            AddNewProperties addNewPropertiesDialog = new AddNewProperties();

            // Mostrar el diálogo y esperar la respuesta del usuario
            addNewPropertiesDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Obtener la canción ingresada por el usuario
                    CancionDAO cancion = addNewPropertiesDialog.getCancion();

                    if (cancion != null) {
                        // Verificar que la conexión a la base de datos esté creada
                        if (Conexion.connection == null) {
                            Conexion.crearConnection();
                        }

                        try {
                            // Llamar al método INSERT para guardar la canción en la base de datos
                            int rowsAffected = cancion.INSERT(Conexion.connection);
                            boolean success = rowsAffected > 0;

                            // Mostrar mensaje según el resultado de la operación
                            if (success) {
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

                // Volver a la pantalla de administrador
                new PantallaAdminCanciones().show();
                this.close();
            });
        });


        button_comprar_album = new Button("Añadir álbum");
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

    }

    private void cancionComprar() {
        PantallaCancion pantalla = new PantallaCancion();
        pantalla.show();
        this.close();
    }

    private void mostarPantallaUsuario() {
        login_spotify pantalla = new login_spotify();
        pantalla.show();
        this.close();
    }
}
