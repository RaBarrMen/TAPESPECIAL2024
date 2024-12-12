package org.example.tapesp2024.vistas;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.tapesp2024.models.AlbumDAO;
import org.example.tapesp2024.models.CancionDAO;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.example.tapesp2024.models.GeneroDAO;

import java.io.*;
import java.util.Arrays;

public class AddNewSong extends Dialog<ButtonType> {
    public final TextField titleField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField geenroField = new TextField();
    public byte[] imageBytes = null;
    private final ComboBox<GeneroDAO> generoComboBox = new ComboBox<>();
    private final ComboBox<AlbumDAO> albumDAOComboBox = new ComboBox<>();
    private TableView<CancionDAO> tableViewCanciones = new TableView<>();
    HBox hbox_table;
    Button btn_guardar;
    CancionDAO cancionSeleccionada;
    ImageView imagenCancionSeleccionada = new ImageView();



    public AddNewSong() {
        setTitle("Añadir nueva cancion");
        setHeaderText("Ingresa los detalles para la cancion");
        initializeDialogPane();
        cargarGeneros();
        cargarAlbums();
        CrearTabla();
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

    private void cargarAlbums() {
        AlbumDAO albumDAO = new AlbumDAO();
        ObservableList<AlbumDAO> album = albumDAO.obtenerAlbumesConCanciones();
        albumDAOComboBox.setItems(album);

        albumDAOComboBox.setCellFactory(param -> new ListCell<AlbumDAO>() {
            @Override
            protected void updateItem(AlbumDAO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getAlbum());
            }
        });

        albumDAOComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(AlbumDAO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getAlbum());
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

        gridPane.add(new Label("Album:"), 0, 3);
        gridPane.add(albumDAOComboBox, 1, 3);

        hbox_table = new HBox(10, gridPane, tableViewCanciones);
        hbox_table.setPadding(new Insets(10));

        // Botón para seleccionar una imagen
        gridPane.add(new Label("Imagen de la canción:"), 0, 4);
        Button selectImageButton = new Button("Seleccionar imagen");
        selectImageButton.setOnAction(event -> openImageFileChooser());
        gridPane.add(selectImageButton, 1, 4);

        gridPane.add(imagenCancionSeleccionada, 0, 5);
        imagenCancionSeleccionada.setVisible(false);

        btn_guardar = new Button("Guardar");
        btn_guardar.setVisible(false);
        btn_guardar.setOnAction(e -> {
            if (cancionSeleccionada != null) {
                GeneroDAO generoSeleccionado = generoComboBox.getValue();
                AlbumDAO albumSeleccionado = albumDAOComboBox.getValue();
                cancionSeleccionada.setCancion(titleField.getText());
                cancionSeleccionada.setCosto_cancion(Float.parseFloat(priceField.getText()));
                cancionSeleccionada.setId_genero(generoSeleccionado.getId_genero());
                cancionSeleccionada.setId_album(albumSeleccionado.getId_album());
                cancionSeleccionada.setImagen_cancion(imageBytes);
                btn_guardar.setVisible(false);
                cancionSeleccionada.update(cancionSeleccionada);
                tableViewCanciones.setItems(cancionSeleccionada.SELECTALL());
                titleField.setText("");
                priceField.setText("");
                generoComboBox.setValue(null);
                albumDAOComboBox.setValue(null);
                imagenCancionSeleccionada.setVisible(false);
            }
        });

        gridPane.add(btn_guardar, 0, 6);
        getDialogPane().setContent(hbox_table);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    private void guardarCambios() {

    }

    private void openImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                // Convertir el archivo a byte[]
                imageBytes = convertFileToByteArray(file);
                // Mostrar imagen en el ImageView
                Image imageSeleccionada = new Image(new ByteArrayInputStream(imageBytes));
                imagenCancionSeleccionada.setImage(imageSeleccionada);
                imagenCancionSeleccionada.setFitWidth(150);
                imagenCancionSeleccionada.setFitHeight(150);
                imagenCancionSeleccionada.setVisible(true);

                // Mensaje de confirmación
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
    public byte[] convertFileToByteArray(File file) throws IOException {
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
            AlbumDAO albumSeleccionado = albumDAOComboBox.getValue();


            if (generoSeleccionado == null) {
                throw new IllegalArgumentException("Debes seleccionar un género.");
            }

            System.out.println(Arrays.toString(imageBytes));
            return new CancionDAO(
                    0,
                    titleField.getText().trim(), // Título de la canción
                    costoCancion,               // Costo
                    generoSeleccionado.getId_genero(), // ID del género seleccionado
                    imageBytes,                  // Imagen
                    albumSeleccionado.getId_album()
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
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
        tableColumnGenero.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));
        tableColumnGenero.getStyleClass().add("column");

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
        TableColumn<CancionDAO, Void> tableColumnAccionesEliminar = new TableColumn<>("Acciones");
        tableColumnAccionesEliminar.setCellFactory(tc -> new TableCell<>() {
            private final Button btnComprar = new Button("Eliminar");

            {
                btnComprar.getStyleClass().add("button"); // Aplica estilo definido para botones
                btnComprar.setOnAction(e -> {
                    CancionDAO cancionSeleccionada = getTableView().getItems().get(getIndex());
                    if (cancionSeleccionada != null) {
                        eliminarCancion(cancionSeleccionada);
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

        TableColumn<CancionDAO, Void> tableColumnAccionesActualizar = new TableColumn<>("Acciones");
        tableColumnAccionesActualizar.setCellFactory(tc -> new TableCell<>() {
            private final Button btnComprar = new Button("Actualizar");

            {
                btnComprar.getStyleClass().add("button"); // Aplica estilo definido para botones
                btnComprar.setOnAction(e -> {
                    cancionSeleccionada = getTableView().getItems().get(getIndex());
                    if (cancionSeleccionada != null) {
                        actualizarCancion(cancionSeleccionada);
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

        // Agregar las columnas a la tabla
        tableViewCanciones.getColumns().addAll(tableColumnNombre, tableColumnPrecio, tableColumnGenero, tableColumnImagen, tableColumnAccionesEliminar, tableColumnAccionesActualizar);

        // Configurar items
        tableViewCanciones.setItems(cancionDAO.SELECTALL());
    }

    private void actualizarCancion(CancionDAO cancionSeleccionada) {
        System.out.println(Arrays.toString(cancionSeleccionada.getImagen_cancion()));
        titleField.setText(cancionSeleccionada.getCancion()); ;
        priceField.setText(String.valueOf(cancionSeleccionada.getCosto_cancion()));
        imagenCancionSeleccionada.setVisible(true);
        byte[] imagenBytes = cancionSeleccionada.getImagen_cancion();
        if (imagenBytes != null && imagenBytes.length > 0) {

            imagenCancionSeleccionada.setFitWidth(150);
            imagenCancionSeleccionada.setFitHeight(150);
            imagenCancionSeleccionada.setImage(new Image(new ByteArrayInputStream(imagenBytes)));

        }
        ObservableList<GeneroDAO> generos = generoComboBox.getItems();


        for (GeneroDAO genero : generos) {
            if (genero.getId_genero() == cancionSeleccionada.getId_genero()) {
                generoComboBox.getSelectionModel().select(genero);
                break;
            }
        }

        ObservableList<AlbumDAO> albumDAOObservableList = albumDAOComboBox.getItems();

        for (AlbumDAO albumDAO : albumDAOObservableList) {
            if (albumDAO.getId_album() == cancionSeleccionada.getId_genero()) {
                albumDAOComboBox.getSelectionModel().select(albumDAO);
                break;
            }
        }
        btn_guardar.setVisible(true);
    }

    private void eliminarCancion(CancionDAO cancionSeleccionada) {
        cancionSeleccionada.delete(cancionSeleccionada.getId_cancion());
        tableViewCanciones.setItems(cancionSeleccionada.SELECTALL());
    }


}

