package org.example.tapesp2024.vistas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    TableView tableViewAlbum = new TableView();
    AlbumDAO albumSeleccionado = new AlbumDAO();
    Button btnActualizar;

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
        CrearTabla();
        lblNombre = new Label("Nombre: ");
        txtNombre = new TextField();

        lblCosto = new Label("Costo: ");
        txtCosto = new TextField();

        btnActualizar = new Button("Actualizar");
        btnActualizar.setVisible(false);
        btnActualizar.setOnAction(e -> {
                    if (albumSeleccionado != null) {
                        btnGuardar.setVisible(true);
                        albumSeleccionado.setAlbum(txtNombre.getText());
                        albumSeleccionado.setCosto_album(Float.parseFloat(txtCosto.getText()));
                        btnActualizar.setVisible(false);
                        albumSeleccionado.UPDATE();
                        tableViewAlbum.setItems(albumSeleccionado.obtenerAlbumesConCanciones());
                        txtNombre.setText("");
                        txtCosto.setText("");
                    }
                }

        );

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> guardarAlbum());

        btnSalir = new Button("Regresar");
        btnSalir.setOnAction(event -> regresarPantallaAdmin());

        vbox_album.setAlignment(Pos.CENTER);
        vbox_album.getChildren().addAll(tableViewAlbum, lblNombre, txtNombre, lblCosto, txtCosto, btnGuardar, btnSalir, btnActualizar);
    }

    private void guardarAlbum() {
        album = new AlbumDAO();
        album.setAlbum(txtNombre.getText());
        album.setCosto_album(Double.parseDouble(txtCosto.getText()));
        album.INSERT();
        tableViewAlbum.setItems(album.obtenerAlbumesConCanciones());
        txtNombre.setText("");
        txtCosto.setText("");
    }

    private void regresarPantallaAdmin() {
        PantallaAdminCanciones pantallaAdminCanciones = new PantallaAdminCanciones(this.id_usuario);
        pantallaAdminCanciones.show();
        this.close();
    }

    private void CrearTabla() {
        AlbumDAO albumDAO = new AlbumDAO();

        TableColumn<AlbumDAO, String> tableColumnNombre = new TableColumn<>("Album");
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("album"));

        TableColumn<AlbumDAO, Float> tableColumnPrecio = new TableColumn<>("Costo");
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("costo_album"));

        TableColumn<AlbumDAO, Void> tableColumnAccionesEliminar = new TableColumn<>("Acciones");
        tableColumnAccionesEliminar.setCellFactory(tc -> new TableCell<>() {
            private final Button btnComprar = new Button("Eliminar");

            {
                btnComprar.getStyleClass().add("button");
                btnComprar.setOnAction(e -> {
                    AlbumDAO albumSeleccionado = getTableView().getItems().get(getIndex());
                    if (albumSeleccionado != null) {
                        eliminarAlbum(albumSeleccionado);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnComprar);
                }
            }
        });

        TableColumn<AlbumDAO, Void> tableColumnAccionesActualizar = new TableColumn<>("Acciones");
        tableColumnAccionesActualizar.setCellFactory(tc -> new TableCell<>() {
            private final Button actualizar = new Button("Actualizar");

            {
                actualizar.getStyleClass().add("button");
                actualizar.setOnAction(e -> {
                    albumSeleccionado = getTableView().getItems().get(getIndex());
                    if (albumSeleccionado != null) {
                        actualizarAlbum(albumSeleccionado);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(actualizar);
                }
            }

        });

        // Agregar columnas al TableView
        tableViewAlbum.getColumns().addAll(tableColumnNombre, tableColumnPrecio, tableColumnAccionesActualizar, tableColumnAccionesEliminar);

        ObservableList<AlbumDAO> observableList = FXCollections.observableArrayList(albumDAO.obtenerAlbumesConCanciones());
        // Cargar las canciones del álbum
        tableViewAlbum.setItems(observableList);
    }

    private void actualizarAlbum(AlbumDAO albumSeleccionado) {
        txtNombre.setText(albumSeleccionado.getAlbum());
        txtCosto.setText(String.valueOf(albumSeleccionado.getCosto_album()));
        btnActualizar.setVisible(true);
        btnGuardar.setVisible(false);
    }

    private void eliminarAlbum(AlbumDAO albumSeleccionado) {
        albumSeleccionado.DELETE();
        tableViewAlbum.setItems(albumSeleccionado.obtenerAlbumesConCanciones());
    }

}
