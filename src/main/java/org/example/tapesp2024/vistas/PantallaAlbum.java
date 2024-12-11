package org.example.tapesp2024.vistas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.AlbumDAO;

public class PantallaAlbum extends Stage {

    private TableView<AlbumDAO> tableViewAlbum;
    private Button btnComprarAlbum, btnSalir;
    private VBox vbox;
    private Scene escena;
    private AlbumDAO album;
    private int id_usuario;

    public PantallaAlbum(int id_usuario) {
        this.id_usuario = id_usuario;
        CrearUI();
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        // Crear TableView
        tableViewAlbum = new TableView<>();

        // Crear las columnas de la tabla
        CrearTabla();

        // Crear botón Comprar Álbum
        btnComprarAlbum = new Button("Comprar Álbum");
        btnComprarAlbum.setOnAction(actionEvent -> comprarAlbum());
        btnComprarAlbum.setOnAction(event -> obtenerIdAlbumSeleccionado());

        btnSalir = new Button("Regresar");
        btnSalir.setOnAction(actionEvent -> salirPantallaCompra(this.id_usuario));

        // Crear layout principal (root)
        vbox = new VBox(10, tableViewAlbum, btnComprarAlbum, btnSalir);
        vbox.setPadding(new Insets(10));

        // Crear escena
        escena = new Scene(vbox, 600, 400);
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

        // Agregar columnas al TableView
        tableViewAlbum.getColumns().addAll(tableColumnNombre, tableColumnPrecio);

        ObservableList<AlbumDAO> observableList = FXCollections.observableArrayList(albumDAO.obtenerAlbumesConCanciones());
        // Cargar las canciones del álbum
        tableViewAlbum.setItems(observableList);
    }

    private void comprarAlbum() {
        if (album != null) {
            // Lógica para realizar la compra del álbum
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra de Álbum");
            alert.setHeaderText("Compra Exitosa");
            alert.setContentText("El álbum '" + album.getAlbum() + "' ha sido comprado exitosamente.");
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
        AlbumDAO albumSeleccionado = tableViewAlbum.getSelectionModel().getSelectedItem();
        AlbumDAO.comprarCancionesAlbum(albumSeleccionado.getId_album(), this.id_usuario);
        //System.out.println(albumSeleccionado.getId_album());

        if (albumSeleccionado != null) {
            int idAlbum = albumSeleccionado.getId_album();
            // Realizar acciones con el ID
        } else {
            System.out.println("No se ha seleccionado ningún álbum.");
        }
    }

}
