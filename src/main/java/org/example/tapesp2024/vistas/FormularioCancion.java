package org.example.tapesp2024.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Modality;
import org.example.tapesp2024.models.CancionDAO;

import java.io.File;
import java.nio.file.Files;

public class FormularioCancion extends Stage {

    private TextField txtNombre;
    private TextField txtCosto;
    private ComboBox<String> cmbGenero;
    private ImageView imageView;
    private Button btnCargarImagen;
    private Button btnGuardar;
    private Button btnCancelar;
    private CancionDAO cancionDAO;
    private byte[] imagenBytes;

    public FormularioCancion(TableView<CancionDAO> tableView, CancionDAO cancionExistente) {
        if (cancionExistente != null) {
            this.cancionDAO = cancionExistente;
        } else {
            this.cancionDAO = new CancionDAO(0, "", 0.0f, 0, null);
        }

        CrearUI(tableView);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle(cancionExistente == null ? "Agregar Canción" : "Editar Canción");
        this.show();
    }

    private void CrearUI(TableView<CancionDAO> tableView) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label lblNombre = new Label("Nombre:");
        txtNombre = new TextField(cancionDAO.getCancion());

        Label lblCosto = new Label("Costo:");
        txtCosto = new TextField(String.valueOf(cancionDAO.getCosto_cancion()));

        Label lblGenero = new Label("Género:");
        cmbGenero = new ComboBox<>();
        cmbGenero.getItems().addAll();
        cmbGenero.getSelectionModel().select(cancionDAO.getId_genero() - 1);

        Label lblImagen = new Label("Imagen:");
        imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        btnCargarImagen = new Button("Cargar Imagen");
        btnCargarImagen.setOnAction(e -> cargarImagen());

        btnGuardar = new Button("Guardar");
        //btnGuardar.setOnAction(e -> guardarCancion(tableView));

        btnCancelar = new Button("Cancelar");
        btnCancelar.setOnAction(e -> this.close());

        gridPane.add(lblNombre, 0, 0);
        gridPane.add(txtNombre, 1, 0);
        gridPane.add(lblCosto, 0, 1);
        gridPane.add(txtCosto, 1, 1);
        gridPane.add(lblGenero, 0, 2);
        gridPane.add(cmbGenero, 1, 2);
        gridPane.add(lblImagen, 0, 3);
        gridPane.add(imageView, 1, 3);
        gridPane.add(btnCargarImagen, 1, 4);
        gridPane.add(btnGuardar, 0, 5);
        gridPane.add(btnCancelar, 1, 5);

        Scene escena = new Scene(gridPane, 400, 300);
        this.setScene(escena);
    }

    private void cargarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(this);
        if (file != null) {
            try {
                imagenBytes = Files.readAllBytes(file.toPath());
                imageView.setImage(new Image(file.toURI().toString()));
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo cargar la imagen", ButtonType.OK);
                alert.show();
            }
        }
    }

    /*private void guardarCancion(TableView<CancionDAO> tableView) {
        try {
            cancionDAO.setCancion(txtNombre.getText());
            cancionDAO.setCosto_cancion(Float.parseFloat(txtCosto.getText()));
            cancionDAO.setId_genero(cmbGenero.getSelectionModel().getSelectedIndex() + 1); // Asumiendo que los géneros empiezan en 1
            cancionDAO.setImagen_cancion(imagenBytes);

            int resultado;
            if (cancionDAO.getId_cancion() == 0) {
                resultado = cancionDAO.INSERT(connection);
            } else {
                resultado = cancionDAO.UPDATE(connection);
            }

            if (resultado > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Canción guardada exitosamente", ButtonType.OK);
                alert.show();
                tableView.setItems(cancionDAO.SELECTALL());
                this.close();
            } else {
                throw new Exception("Error al guardar la canción");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo guardar la canción: " + e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }*/
}
