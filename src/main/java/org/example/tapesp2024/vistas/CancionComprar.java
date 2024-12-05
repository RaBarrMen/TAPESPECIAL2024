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
import org.example.tapesp2024.models.VentaDAO;
import org.example.tapesp2024.models.Venta_DetalleDAO;

import java.io.ByteArrayInputStream;


public class CancionComprar extends Stage {

    private TableView<CancionDAO> tableViewCanciones;
    private Button btnSalir;
    private ToolBar toolBarMenu;
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

        tableViewCanciones = new TableView<>();
        CrearTabla();

        btnSalir = new Button("Salir");
        btnSalir.setOnAction(actionEvent -> SalirPantallaCompra());

        vbox = new VBox(tableViewCanciones, btnSalir);
        escena = new Scene(vbox, 600, 300);
        escena.getStylesheets().add(getClass().getResource("/estilos/CancionComprar.css").toExternalForm());
    }

    private void SalirPantallaCompra() {
        PantallaCompra pantalla = new PantallaCompra(0);//Esta mal
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

                        tableViewCanciones.getStyleClass().add("table-view");
                        btnSalir.getStyleClass().add("btn");
                        tableColumnNombre.getStyleClass().add("column");

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
                btnEjecutarCompra.setOnAction(e -> {
                    ejecutarCompra(cancion);
                    ventanaCompra.close();
                });

                Button btnCerrar = new Button("Cerrar");
                btnCerrar.setOnAction(e -> ventanaCompra.close());

                layout.getChildren().addAll(lblNombre, lblCosto, imagenCancion, btnEjecutarCompra, btnCerrar);

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

    private void ejecutarCompra(CancionDAO cancion) {
        try {
            // Crear una instancia de VentaDAO
            VentaDAO ventaDAO = new VentaDAO();

            // Asignar valores para la venta
            ventaDAO.setId_cancion(cancion.getId_cancion());  // ID de la canción
            ventaDAO.setId_usuario(this.id_usuario);  // ID del usuario que está comprando

            // Insertar la venta
            int rowCountVenta = ventaDAO.INSERT();  // Inserta la venta

            if (rowCountVenta > 0) {
                // Si la venta fue exitosa, insertamos el detalle de venta
                Venta_DetalleDAO ventaDetalleDAO = new Venta_DetalleDAO();
                ventaDetalleDAO.setId_venta(ventaDAO.getId_venta());  // Usamos el ID de la venta insertada
                ventaDetalleDAO.setMonto(cancion.getCosto_cancion());  // Usamos el costo de la canción
                ventaDetalleDAO.setFecha(new java.util.Date());  // Usamos la fecha actual

                // Insertar detalle de venta
                int rowCountDetalle = ventaDetalleDAO.INSERT();
                if (rowCountDetalle > 0) {
                    // Si el detalle también fue insertado correctamente
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Compra Exitosa");
                    alert.setHeaderText("La compra se realizó exitosamente");
                    alert.setContentText("La canción ha sido comprada.");
                    alert.showAndWait();
                } else {
                    // Si hubo un error al insertar el detalle
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error en la compra");
                    alert.setHeaderText("Error al insertar el detalle de la venta");
                    alert.setContentText("Por favor, intente nuevamente.");
                    alert.showAndWait();
                }
            } else {
                // Si hubo un error al insertar la venta
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error en la compra");
                alert.setHeaderText("Error al realizar la venta");
                alert.setContentText("Por favor, intente nuevamente.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar cualquier excepción
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en la compra");
            alert.setHeaderText("Error inesperado");
            alert.setContentText("Ocurrió un error al realizar la compra.");
            alert.showAndWait();
        }
    }



}
