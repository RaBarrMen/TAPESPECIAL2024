package org.example.tapesp2024.vistas;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.AlbumDAO;
import org.example.tapesp2024.models.CancionDAO;

import java.io.ByteArrayInputStream;

public class PantallaAlbum extends Stage {

    private TableView<AlbumDAO> tableViewAlbum;
    private Button btnComprarAlbum, btnSalir;
    private VBox vbox;
    private Scene escena;
    private AlbumDAO album;
    private int id_usuario;
    AlbumDAO albumSeleccionado = new AlbumDAO();
    TableView<CancionDAO> tableViewCanciones;
    CancionDAO cancionDAO = new CancionDAO();

    public PantallaAlbum(int id_usuario) {
        this.id_usuario = id_usuario;
        CrearUI();
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        // Crear TableView
        tableViewAlbum = new TableView<>();
        tableViewCanciones = new TableView<>();
        CrearTablaCanciones();

        // Crear las columnas de la tabla
        CrearTabla();

        // Crear botón Comprar Álbum
        btnComprarAlbum = new Button("Comprar Álbum");
        btnComprarAlbum.setOnAction(actionEvent -> comprarAlbum());


        btnSalir = new Button("Regresar");
        btnSalir.setOnAction(actionEvent -> salirPantallaCompra(this.id_usuario));


        // Crear layout principal (root)
        vbox = new VBox(10, tableViewAlbum, btnComprarAlbum, btnSalir, tableViewCanciones);
        vbox.setPadding(new Insets(10));

        // Crear escena
        escena = new Scene(vbox, 400, 600);
    }

    private void salirPantallaCompra(int id_usuario) {
        PantallaCompra pantallaCompra = new PantallaCompra(this.id_usuario);
        pantallaCompra.show();
        this.close();
    }

    private void CrearTabla() {
        AlbumDAO albumDAO = new AlbumDAO();

        // Crear columnas para CancionDAO
        TableColumn<AlbumDAO, String> tableColumnNombre = new TableColumn<>("Album");
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("album"));

        TableColumn<AlbumDAO, Float> tableColumnPrecio = new TableColumn<>("Costo");
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("costo_album"));

        TableColumn<AlbumDAO, Void> tableColumnAccionesCanciones = new TableColumn<>("Acciones");
        tableColumnAccionesCanciones.setCellFactory(tc -> new TableCell<>() {
            private final Button btnMostrarCancion = new Button("Canciones");

            {
                btnMostrarCancion.getStyleClass().add("button"); // Aplica estilo definido para botones
                btnMostrarCancion.setOnAction(e ->{
                    albumSeleccionado = getTableView().getItems().get(getIndex());
                    System.out.println(albumSeleccionado.getId_album());
                    if (albumSeleccionado != null) {
                        tableViewCanciones.setItems(cancionDAO.SELECTBYALBUM(albumSeleccionado.getId_album()));
                    }}
                );
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnMostrarCancion);
                }
            }
        });

        // Agregar columnas al TableView
        tableViewAlbum.getColumns().addAll(tableColumnNombre, tableColumnPrecio, tableColumnAccionesCanciones);

        ObservableList<AlbumDAO> observableList = FXCollections.observableArrayList(albumDAO.obtenerAlbumesConCanciones());
        // Cargar las canciones del álbum
        tableViewAlbum.setItems(observableList);
    }

    private void CrearTablaCanciones() {
        CancionDAO cancionDAO = new CancionDAO(0, "", 0.0f, 0, null);

        // Crear columnas
        TableColumn<CancionDAO, String> tableColumnNombre = new TableColumn<>("Canción");
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("cancion"));
        tableColumnNombre.getStyleClass().add("column"); // Aplica estilo personalizado

        TableColumn<CancionDAO, Float> tableColumnPrecio = new TableColumn<>("Costo");
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("costo_cancion"));
        tableColumnPrecio.getStyleClass().add("column");

        TableColumn<CancionDAO, Integer> tableColumnGenero = new TableColumn<>("Género");
        tableColumnGenero.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));
        tableColumnGenero.getStyleClass().add("column");

        TableColumn<CancionDAO, ImageView> tableColumnImagen = new TableColumn<>("Imagen");
        tableColumnImagen.setMinWidth(120);
        tableColumnImagen.setCellValueFactory(data -> {
            byte[] imagenBytes = data.getValue().getImagen_cancion();
            if (imagenBytes != null && imagenBytes.length > 0) {
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imagenBytes)));
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.getStyleClass().add("table-cell-image");// Aplica estilo a la celda de la imagen
                tableColumnImagen.getStyleClass().add("column");
                return new SimpleObjectProperty<>(imageView);
            }
            return new SimpleObjectProperty<>(null);
        });


        // Agregar las columnas a la tabla
        tableViewCanciones.getColumns().addAll(tableColumnNombre, tableColumnPrecio, tableColumnGenero, tableColumnImagen);
    }

    private void comprarAlbum() {
        AlbumDAO.comprarCancionesAlbum(albumSeleccionado.getId_album(), this.id_usuario);
        if (albumSeleccionado != null) {
            // Lógica para realizar la compra del álbum
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra de Álbum");
            alert.setHeaderText("Compra Exitosa");
            alert.setContentText("El álbum '" + albumSeleccionado.getAlbum() + "' ha sido comprado exitosamente.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Compra");
            alert.setHeaderText("No se pudo realizar la compra");
            alert.setContentText("El álbum no está disponible.");
            alert.showAndWait();
        }
    }

    private void obtenerIdAlbumSeleccionado() {
        albumSeleccionado = tableViewAlbum.getSelectionModel().getSelectedItem();

        System.out.println(albumSeleccionado.getId_album());

        if (albumSeleccionado != null) {
            int idAlbum = albumSeleccionado.getId_album();
            // Realizar acciones con el ID
        } else {
            System.out.println("No se ha seleccionado ningún álbum.");
        }
    }

}
