package org.example.tapesp2024.vistas;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.Venta_DetalleDAO;
import org.example.tapesp2024.models.VentaDAO;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.scene.image.ImageView;

import static org.example.tapesp2024.models.Conexion.connection;

public class VentanaHistorial extends Stage {

    private TableView<Venta_DetalleDAO> tableViewHistorial;
    private Button btnCerrar;
    private VBox vbox;
    private Scene escena;
    int id_usuario;

    public VentanaHistorial(int id_usuario) {
        this.id_usuario = id_usuario;
        CrearUI();
        this.setTitle("Historial de Compras");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tableViewHistorial = new TableView<>();
        CrearTabla();

        btnCerrar = new Button("Cerrar");
        btnCerrar.setOnAction(actionEvent -> regresarPantallaUsuario());

        vbox = new VBox(tableViewHistorial, btnCerrar);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        escena = new Scene(vbox, 600, 400);
        escena.getStylesheets().add(getClass().getResource("/estilos/VentanaHistorial.css").toExternalForm());

        btnCerrar.getStyleClass().add("btn");
        btnCerrar.getStyleClass().add("btn:hover");
    }

    private void regresarPantallaUsuario() {
        PantallaUsuario pantallaUsuario = new PantallaUsuario(this.id_usuario);
        pantallaUsuario.show();
        this.close();
    }

    private void CrearTabla() {
        TableColumn<Venta_DetalleDAO, String> tableColumnNombre = new TableColumn<>("Canción");
        tableColumnNombre.setCellValueFactory(data -> {
            int idCancion = data.getValue().getId_venta();
            return new SimpleObjectProperty<>(obtenerNombreCancionPorId(idCancion));
        });

        TableColumn<Venta_DetalleDAO, Float> tableColumnPrecio = new TableColumn<>("Costo");
        tableColumnPrecio.setCellValueFactory(new PropertyValueFactory<>("monto"));

        TableColumn<Venta_DetalleDAO, String> tableColumnFecha = new TableColumn<>("Fecha de Compra");
        tableColumnFecha.setCellValueFactory(data -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return new SimpleObjectProperty<>(sdf.format(data.getValue().getFecha()));
        });

        // Columna para la imagen
        TableColumn<Venta_DetalleDAO, ImageView> tableColumnImagen = new TableColumn<>("Imagen");
        tableColumnImagen.setCellValueFactory(data -> {
            int idCancion = data.getValue().getId_venta();
            byte[] imagenBytes = obtenerImagenCancionPorId(idCancion);
            if (imagenBytes != null && imagenBytes.length > 0) {
                ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(imagenBytes)));
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                return new SimpleObjectProperty<>(imageView);
            }
            return new SimpleObjectProperty<>(null);
        });

        tableViewHistorial.getColumns().addAll(tableColumnNombre, tableColumnPrecio, tableColumnFecha, tableColumnImagen);

        // Poblar la tabla con los datos
        List<Venta_DetalleDAO> ventasDetalles = obtenerHistorialDeCompras();
        tableViewHistorial.getItems().setAll(ventasDetalles);
    }

    private List<Venta_DetalleDAO> obtenerHistorialDeCompras() {
        VentaDAO ventaDAO = new VentaDAO();
        return ventaDAO.obtenerCancionesPorUsuario(id_usuario);
    }

    private String obtenerNombreCancionPorId(int idCancion) {
        String query = "SELECT cancion FROM cancion WHERE id_cancion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idCancion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("cancion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Canción no encontrada";
    }

    private byte[] obtenerImagenCancionPorId(int idCancion) {
        String query = "SELECT imagen_cancion FROM cancion WHERE id_cancion = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idCancion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBytes("imagen_cancion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
