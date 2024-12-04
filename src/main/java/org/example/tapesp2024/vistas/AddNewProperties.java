package org.example.tapesp2024.vistas;

import javafx.collections.ObservableList;
import org.example.tapesp2024.models.CancionDAO;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.example.tapesp2024.models.GeneroDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddNewProperties extends Dialog<ButtonType> {
    public final TextField titleField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField geenroField = new TextField();
    private byte[] imageBytes = null; // Almacenar la imagen en formato byte[]
    private final ComboBox<GeneroDAO> generoComboBox = new ComboBox<>();


    public AddNewProperties() {
        setTitle("Añadir nueva propiedad");
        setHeaderText("Ingresa los detalles para la propiedad");
        initializeDialogPane();
        cargarGeneros();
    }

    private void cargarGeneros() {
        GeneroDAO generoDAO = new GeneroDAO();
        ObservableList<GeneroDAO> generos = generoDAO.SELECTALL();
        generoComboBox.setItems(generos);

        generoComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(GeneroDAO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getGenero());
            }
        });

        generoComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(GeneroDAO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getGenero());
            }
        });
    }


    private void initializeDialogPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Campo para el título de la canción
        gridPane.add(new Label("Título de la canción:"), 0, 0);
        gridPane.add(titleField, 1, 0);

        // Campo para el costo de la canción
        gridPane.add(new Label("Costo de la canción:"), 0, 1);
        gridPane.add(priceField, 1, 1);

        // ComboBox para seleccionar el género
        gridPane.add(new Label("Género:"), 0, 2);
        gridPane.add(generoComboBox, 1, 2);

        // Botón para seleccionar una imagen
        gridPane.add(new Label("Imagen de la canción:"), 0, 3);
        Button selectImageButton = new Button("Seleccionar imagen");
        selectImageButton.setOnAction(event -> openImageFileChooser());
        gridPane.add(selectImageButton, 1, 3);

        // Establecer el contenido en el panel de diálogo
        getDialogPane().setContent(gridPane);

        // Añadir botones OK y CANCELAR
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }



    // Método para abrir el FileChooser y seleccionar una imagen
    /*private void openImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(null); // Aquí se puede usar una referencia a tu ventana principal
        if (file != null) {
            try {
                imageBytes = convertFileToByteArray(file); // Convertir la imagen seleccionada a byte[]
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    */
    private void openImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(null); // Aquí se puede usar una referencia a tu ventana principal
        if (file != null) {
            try {
                // Cambiar el texto del botón para indicar que ya se seleccionó un archivo
                imageBytes = convertFileToByteArray(file);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Archivo seleccionado");
                alert.setHeaderText(null);
                alert.setContentText("Archivo seleccionado: " + file.getName());
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No se seleccionó archivo");
            alert.setHeaderText(null);
            alert.setContentText("No seleccionaste ningún archivo.");
            alert.showAndWait();
        }
    }


    // Método para convertir un archivo de imagen a byte[]
    private byte[] convertFileToByteArray(File file) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] fileBytes = new byte[(int) file.length()];
            inputStream.read(fileBytes);
            return fileBytes;
        }
    }

    // Método para obtener la canción que el usuario ingresó
    public CancionDAO getCancion() {
        try {
            float costoCancion = Float.parseFloat(priceField.getText());
            GeneroDAO generoSeleccionado = generoComboBox.getValue();

            if (generoSeleccionado == null) {
                throw new IllegalArgumentException("Debes seleccionar un género.");
            }

            return new CancionDAO(
                    0,
                    titleField.getText().trim(), // Título de la canción
                    costoCancion,               // Costo
                    generoSeleccionado.getId_genero(), // ID del género seleccionado
                    imageBytes                  // Imagen
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }



}

