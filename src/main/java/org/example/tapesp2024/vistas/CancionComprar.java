package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.SimpleObjectProperty;
import org.example.tapesp2024.models.CancionDAO;
import org.example.tapesp2024.models.ClienteDAO;
import org.example.tapesp2024.models.VentaDAO;
import org.example.tapesp2024.models.Venta_DetalleDAO;

import java.io.ByteArrayInputStream;

public class CancionComprar extends Stage {

    private TableView<CancionDAO> tableViewCanciones;
    private Button btnSalir;
    private VBox vbox;
    private Scene escena;
    ClienteDAO clienteDAO = new ClienteDAO();
    int id_usuario;

    public CancionComprar(int id_usuario) {
        this.id_usuario = id_usuario;
        CrearUI();
        this.setTitle("Lista de Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        // Crear TableView
        tableViewCanciones = new TableView<>();
        tableViewCanciones.getStyleClass().add("table-view"); // Clase CSS personalizada

        // Crear las columnas de la tabla
        CrearTabla();

        // Crear botón Salir
        btnSalir = new Button("Salir");
        btnSalir.getStyleClass().add("button"); // Aplica estilo definido para botones en el CSS
        btnSalir.setOnAction(actionEvent -> SalirPantallaCompra());

        // Crear layout principal (root)
        vbox = new VBox(10, tableViewCanciones, btnSalir);
        vbox.setPadding(new Insets(10));
        vbox.getStyleClass().add("root"); // Aplica la clase 'root' al VBox

        // Crear escena y agregar estilos
        escena = new Scene(vbox, 600, 400);
        escena.getStylesheets().add(getClass().getResource("/estilos/CancionComprar.css").toExternalForm());
    }

    private void SalirPantallaCompra() {
        PantallaCompra pantalla = new PantallaCompra(this.id_usuario);
        pantalla.show();
        this.close();
    }


    private void CrearTabla() {
        CancionDAO cancionDAO = new CancionDAO(0, "", 0.0f, 0, null);

        // Crear columnas
        TableColumn<CancionDAO, String> tableColumnNombre = new TableColumn<>("Canción");
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("cancion"));
        tableColumnNombre.getStyleClass().add("column"); // Aplica estilo personalizado

        TableColumn<CancionDAO, Float> tableColumnPrecio = new TableColumn<>("Costo");
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("costo_cancion"));
        tableColumnPrecio.getStyleClass().add("column");

        TableColumn<CancionDAO, Integer> tableColumnGenero = new TableColumn<>("Género");
        tableColumnGenero.setCellValueFactory(new PropertyValueFactory<>("id_genero"));
        tableColumnGenero.getStyleClass().add("column");

        TableColumn<CancionDAO, Integer> tableColumnId = new TableColumn<>("ID");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id_cancion"));
        tableColumnId.getStyleClass().add("column");

        // Columna de Imagen
        TableColumn<CancionDAO, ImageView> tableColumnImagen = new TableColumn<>("Imagen");
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

        // Columna de Acciones
        TableColumn<CancionDAO, Void> tableColumnAcciones = new TableColumn<>("Acciones");
        tableColumnAcciones.setCellFactory(tc -> new TableCell<>() {
            private final Button btnComprar = new Button("Comprar");

            {
                btnComprar.getStyleClass().add("button"); // Aplica estilo definido para botones
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

                Button btnEjecutarCompra = new Button("Ejecutar Compra");
                btnEjecutarCompra.getStyleClass().add("button");
                btnEjecutarCompra.setOnAction(e -> {
                    ejecutarCompra(cancion);
                    ventanaCompra.close();
                });

                Button btnCerrar = new Button("Cerrar");
                btnCerrar.setOnAction(e -> salirPantallaUsuario());

                layout.getChildren().addAll(lblNombre, lblCosto, imagenCancion, btnEjecutarCompra, btnCerrar);

                Scene escena = new Scene(layout, 300, 400);
                escena.getStylesheets().add(getClass().getResource("/estilos/CancionComprar.css").toExternalForm());
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

        // Agregar las columnas a la tabla
        tableViewCanciones.getColumns().addAll(tableColumnNombre, tableColumnPrecio, tableColumnGenero, tableColumnId, tableColumnImagen, tableColumnAcciones);

        // Configurar items
        tableViewCanciones.setItems(cancionDAO.SELECTALL());
    }

    private void salirPantallaUsuario() {
        PantallaUsuario pantallaUsuario = new PantallaUsuario(this.id_usuario);
        pantallaUsuario.show();
        this.close();
    }

    private void ejecutarCompra(CancionDAO cancion) {
        try {
            VentaDAO ventaDAO = new VentaDAO();
            ventaDAO.setId_cancion(cancion.getId_cancion());
            ventaDAO.setId_usuario(this.id_usuario);

            ventaDAO.setId_cancion(cancion.getId_cancion());
            ventaDAO.setId_usuario(this.id_usuario);

            int rowCountVenta = ventaDAO.INSERT();

            if (rowCountVenta > 0) {
                Venta_DetalleDAO ventaDetalleDAO = new Venta_DetalleDAO();
                ventaDetalleDAO.setId_venta(ventaDAO.getId_venta());
                ventaDetalleDAO.setMonto(cancion.getCosto_cancion());
                ventaDetalleDAO.setFecha(new java.util.Date());

                int rowCountDetalle = ventaDetalleDAO.INSERT();
                if (rowCountDetalle > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Compra Exitosa");
                    alert.setHeaderText("La compra se realizó exitosamente");
                    alert.setContentText("La canción ha sido comprada.");
                    alert.showAndWait();
                } else {
                    mostrarError("Error al insertar el detalle de la venta");
                }
            } else {
                mostrarError("Error al realizar la venta");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Ocurrió un error al realizar la compra.");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error en la compra");
        alert.setHeaderText(mensaje);
        alert.setContentText("Por favor, intente nuevamente.");
        alert.showAndWait();
    }
}
