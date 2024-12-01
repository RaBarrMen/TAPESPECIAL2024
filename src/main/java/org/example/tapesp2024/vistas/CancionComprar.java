package org.example.tapesp2024.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.CancionDAO;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;


public class CancionComprar extends Stage {

    private TableView<CancionDAO> tableViewCanciones;
    private Button btnSalir;
    private ToolBar toolBarMenu;
    private VBox vbox;
    private Scene escena;

    public CancionComprar() {
        CrearUI();
        this.setTitle("Lista de Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        toolBarMenu = new ToolBar();
//        Button btn_agregar_cancion = new Button("Agregar Canción");
//        btn_agregar_cancion.setOnAction(actionEvent -> new FormularioCancion(tableViewCanciones, null));
//
//        toolBarMenu.getItems().add(btn_agregar_cancion);

        tableViewCanciones = new TableView<>();
        CrearTabla();

        btnSalir = new Button("Salir");
        btnSalir.setOnAction(actionEvent -> SalirPantallaCompra());

        vbox = new VBox(toolBarMenu, tableViewCanciones, btnSalir);
        escena = new Scene(vbox, 600, 300);
    }

    private void SalirPantallaCompra() {
        PantallaCompra pantalla = new PantallaCompra();
        pantalla.show();
        this.close();
    }

    private void CrearTabla() {
        CancionDAO cancionDAO = new CancionDAO(0, "", 0.0f, 0, null); // Constructor por defecto para acceder a SELECTALL.

        // Columna para el nombre de la canción
        TableColumn<CancionDAO, String> tableColumnNombre = new TableColumn<>("Canción");
        tableColumnNombre.setCellValueFactory(new PropertyValueFactory<>("cancion")); // Debe coincidir con el getter de la clase DAO

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
                imageView.setFitWidth(50); // Ajusta el tamaño de la imagen
                imageView.setFitHeight(50);
                return new SimpleObjectProperty<>(imageView);
            }
            return new SimpleObjectProperty<>(null);
        });
        tableViewCanciones.getColumns().addAll(tableColumnNombre, tableColumnPrecio, tableColumnGenero, tableColumnId, tableColumnImagen);


        tableViewCanciones.setItems(cancionDAO.SELECTALL());


    }
}
