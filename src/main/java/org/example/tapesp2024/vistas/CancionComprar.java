package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.tapesp2024.models.CancionDAO;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.tapesp2024.models.ClienteDAO;

import java.io.ByteArrayInputStream;


public class CancionComprar extends Stage {

    private TableView<CancionDAO> tableViewCanciones;
    private Button btnSalir;
    private ToolBar toolBarMenu;
    private VBox vbox;
    private Scene escena;
    ClienteDAO clienteDAO = new ClienteDAO();

    public CancionComprar() {
        CrearUI();
        this.setTitle("Lista de Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        toolBarMenu = new ToolBar();

        tableViewCanciones = new TableView<>();
        CrearTabla();

        btnSalir = new Button("Salir");
        btnSalir.setOnAction(actionEvent -> SalirPantallaCompra());

        vbox = new VBox(toolBarMenu, tableViewCanciones, btnSalir);
        escena = new Scene(vbox, 600, 300);
    }

    private void SalirPantallaCompra() {
        int idUsuario = clienteDAO.getUserID("nombreDeUsuario");
        PantallaCompra pantalla = new PantallaCompra(idUsuario);
        pantalla.show();
        this.close();
    }

    private void CrearTabla() {
        CancionDAO cancionDAO = new CancionDAO(0, "", 0.0f, 0, null);

        // Columna para el nombre de la canción
        TableColumn<CancionDAO, String> tableColumnNombre = new TableColumn<>("Canción");
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        // Columna para el costo de la canción
        TableColumn<CancionDAO, Float> tableColumnPrecio = new TableColumn<>("Costo");
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("costo_cancion"));

        // Columna para el género (muestra el ID directamente o texto si se adapta)
        TableColumn<CancionDAO, Integer> tableColumnGenero = new TableColumn<>("Género");
        tableColumnGenero.setCellValueFactory(new PropertyValueFactory<>("id_genero"));

        // Columna para el ID de la canción
        TableColumn<CancionDAO, Integer> tableColumnId = new TableColumn<>("ID");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id_cancion"));

        TableColumn<CancionDAO, ImageView> tableColumnImagen = new TableColumn<>("Imagen");
        tableColumnImagen.setCellValueFactory(data -> {
            byte[] imagenBytes = data.getValue().getImagen_cancion();
            if (imagenBytes != null && imagenBytes.length > 0) {
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imagenBytes)));
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                return new SimpleObjectProperty<>(imageView);
            }
            return new SimpleObjectProperty<>(null);
        });

        TableColumn<CancionDAO, Void> tableColumnAcciones = new TableColumn<>("Acciones");
        tableColumnAcciones.setCellFactory(tc -> new TableCell<>() {
            private final Button btnComprar = new Button("Comprar");

            {
                btnComprar.setOnAction(e -> {
                    CancionDAO cancionSeleccionada = getTableView().getItems().get(getIndex());
                    if (cancionSeleccionada != null) {
                        mostrarVentanaCompra(cancionSeleccionada);
                    }
                });
            }

            private void mostrarVentanaCompra(CancionDAO cancion) {
                Stage ventanaCompra = new Stage();
                ventanaCompra.initModality(Modality.APPLICATION_MODAL);
                ventanaCompra.setTitle("Comprar Canción");

                VBox layout = new VBox(10);
                layout.setPadding(new Insets(10));

                Label lblNombre = new Label("Nombre: " + cancion.getCancion());
                Label lblCosto = new Label("Costo: $" + cancion.getCosto_cancion());

                ImageView imagenCancion = new ImageView();
                if (cancion.getImagen_cancion() != null) {
                    imagenCancion.setImage(new Image(new ByteArrayInputStream(cancion.getImagen_cancion())));
                    imagenCancion.setFitWidth(200);
                    imagenCancion.setFitHeight(200);
                }

                Button btnCerrar = new Button("Cerrar");
                btnCerrar.setOnAction(e -> ventanaCompra.close());

                layout.getChildren().addAll(lblNombre, lblCosto, imagenCancion, btnCerrar);

                Scene escena = new Scene(layout, 300, 400);
                ventanaCompra.setScene(escena);
                ventanaCompra.showAndWait();
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
        tableViewCanciones.getColumns().add(tableColumnAcciones);


        tableViewCanciones.getColumns().addAll(tableColumnNombre, tableColumnPrecio, tableColumnGenero, tableColumnId, tableColumnImagen);


        tableViewCanciones.setItems(cancionDAO.SELECTALL());

    }
}
