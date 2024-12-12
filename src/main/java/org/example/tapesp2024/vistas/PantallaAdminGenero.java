package org.example.tapesp2024.vistas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.GeneroDAO;

public class PantallaAdminGenero extends Stage {
    TextField txtNombre = new TextField();
    TextField txtCosto = new TextField();
    Label lblNombre = new Label();
    Label lblCosto = new Label();
    Button btnGuardar = new Button();
    Button btnSalir = new Button();
    VBox vbox_album = new VBox();
    GeneroDAO genero;
    int id_usuario;
    TableView tableViewGenero = new TableView();
    GeneroDAO generoSeleccionado = new GeneroDAO();
    Button btnActualizar;

    public PantallaAdminGenero(int id_usuario) {
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
        lblNombre = new Label("Genero: ");
        txtNombre = new TextField();


        btnActualizar = new Button("Actualizar");
        btnActualizar.setVisible(false);
        btnActualizar.setOnAction(e -> {
                        if (generoSeleccionado != null) {
                            btnGuardar.setVisible(true);
                            generoSeleccionado.setGenero(txtNombre.getText());
                            btnActualizar.setVisible(false);
                            generoSeleccionado.UPDATE();
                            tableViewGenero.setItems(generoSeleccionado.SELECTALL());
                            txtNombre.setText("");
                        }
                    }
        );

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> guardarAlbum());

        btnSalir = new Button("Regresar");
        btnSalir.setOnAction(event -> regresarPantallaAdmin());

        vbox_album.setAlignment(Pos.CENTER);
        vbox_album.getChildren().addAll(tableViewGenero, lblNombre, txtNombre, btnGuardar, btnSalir, btnActualizar);
    }

    private void guardarAlbum() {
        genero = new GeneroDAO();
        genero.setGenero(txtNombre.getText());
        genero.INSERT();
        tableViewGenero.setItems(genero.SELECTALL());
        txtNombre.setText("");
        txtCosto.setText("");
    }

    private void regresarPantallaAdmin() {
        PantallaAdminCanciones pantallaAdminCanciones = new PantallaAdminCanciones(this.id_usuario);
        pantallaAdminCanciones.show();
        this.close();
    }

    private void CrearTabla() {
        GeneroDAO albumDAO = new GeneroDAO();

        TableColumn<GeneroDAO, String> tableColumnNombre = new TableColumn<>("Album");
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("genero"));

        TableColumn<GeneroDAO, Void> tableColumnAccionesEliminar = new TableColumn<>("Acciones");
        tableColumnAccionesEliminar.setCellFactory(tc -> new TableCell<>() {
            private final Button btnComprar = new Button("Eliminar");

            {
                btnComprar.getStyleClass().add("button");
                btnComprar.setOnAction(e -> {
                    GeneroDAO albumSeleccionado = getTableView().getItems().get(getIndex());
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

        TableColumn<GeneroDAO, Void> tableColumnAccionesActualizar = new TableColumn<>("Acciones");
        tableColumnAccionesActualizar.setCellFactory(tc -> new TableCell<>() {
            private final Button actualizar = new Button("Actualizar");

            {
                actualizar.getStyleClass().add("button");
                actualizar.setOnAction(e -> {
                    generoSeleccionado = getTableView().getItems().get(getIndex());
                    if (generoSeleccionado != null) {
                        actualizarAlbum(generoSeleccionado);
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
        tableViewGenero.getColumns().addAll(tableColumnNombre, tableColumnAccionesActualizar, tableColumnAccionesEliminar);

        ObservableList<GeneroDAO> observableList = FXCollections.observableArrayList(albumDAO.SELECTALL());
        // Cargar las canciones del álbum
        tableViewGenero.setItems(observableList);
    }

    private void actualizarAlbum(GeneroDAO albumSeleccionado) {
        txtNombre.setText(albumSeleccionado.getGenero());
        btnActualizar.setVisible(true);
        btnGuardar.setVisible(false);
    }

    private void eliminarAlbum(GeneroDAO albumSeleccionado) {
        albumSeleccionado.DELETE();
        tableViewGenero.setItems(albumSeleccionado.SELECTALL());
    }

}

