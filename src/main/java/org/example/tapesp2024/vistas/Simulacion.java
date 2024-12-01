package org.example.tapesp2024.vistas;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Simulacion extends Stage {
    private Scene escena;
    private VBox vContenedor;
    private TableView<TareaImpresion> tablaTareas;
    private Button btnAgregarTarea;
    private Button btnControlSimulacion;
    private ProgressBar progressBar;
    private boolean simulacionActiva = false;
    private BlockingQueue<TareaImpresion> colaTareasPendientes = new LinkedBlockingQueue<>();
    private ObservableList<TareaImpresion> listaTareasIniciales = FXCollections.observableArrayList();
    private ObservableList<TareaImpresion> nuevasTareas = FXCollections.observableArrayList();
    private int contadorTareas = 0;
    private static final int RETARDO_INICIAL_MS = 2000;
    private static final int RETARDO_ENTRE_TAREAS_MS = 2000;

    public Simulacion() {
        CrearUI();
        this.setTitle("Simulador de Impresión");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tablaTareas = new TableView<>();
        tablaTareas.setEditable(false);
        TableColumn<TareaImpresion, Integer> colNoArchivo = new TableColumn<>("No. Archivo");
        colNoArchivo.setCellValueFactory(cellData -> cellData.getValue().numeroArchivoProperty().asObject());
        TableColumn<TareaImpresion, String> colNombreArchivo = new TableColumn<>("Nombre de archivo");
        colNombreArchivo.setCellValueFactory(cellData -> cellData.getValue().nombreArchivoProperty());
        TableColumn<TareaImpresion, Integer> colHojasImprimir = new TableColumn<>("Hojas a imprimir");
        colHojasImprimir.setCellValueFactory(cellData -> cellData.getValue().hojasImprimirProperty().asObject());
        TableColumn<TareaImpresion, String> colHoraAcceso = new TableColumn<>("Hora de acceso");
        colHoraAcceso.setCellValueFactory(cellData -> cellData.getValue().horaAccesoProperty());

        tablaTareas.getColumns().addAll(colNoArchivo, colNombreArchivo, colHojasImprimir, colHoraAcceso);

        btnAgregarTarea = new Button("Agregar Tarea");
        btnAgregarTarea.setOnAction(event -> agregarTarea());

        btnControlSimulacion = new Button("Iniciar Simulación");
        btnControlSimulacion.setOnAction(event -> controlSimulacion());

        progressBar = new ProgressBar();
        progressBar.setVisible(false);

        vContenedor = new VBox(tablaTareas, btnAgregarTarea, btnControlSimulacion, progressBar);
        vContenedor.setSpacing(5);
        escena = new Scene(vContenedor, 400, 300);
    }

    private void agregarTarea() {
        Random random = new Random();
        contadorTareas++;
        int numeroArchivo = contadorTareas;
        String nombreArchivo = generarNombreArchivo();
        int hojasImprimir = random.nextInt(20) + 1;
        String horaAcceso = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        TareaImpresion tarea = new TareaImpresion(numeroArchivo, nombreArchivo, hojasImprimir, horaAcceso);

        if (!simulacionActiva) {
            listaTareasIniciales.add(tarea);
            colaTareasPendientes.add(tarea);
            tablaTareas.getItems().add(tarea);
        } else {
            nuevasTareas.add(tarea);
            tablaTareas.getItems().add(tarea);
        }
    }

    private String generarNombreArchivo() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = ahora.format(formatter);
        return "archivo_" + timestamp + ".pdf";
    }

    private void controlSimulacion() {
        simulacionActiva = !simulacionActiva;
        if (simulacionActiva) {
            btnControlSimulacion.setText("Detener Simulación");
            progressBar.setVisible(true);
            iniciarSimulacion();
        } else {
            btnControlSimulacion.setText("Iniciar Simulación");
            progressBar.setVisible(false);
        }
    }

    private void iniciarSimulacion() {
        Task<Void> simulacionTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(RETARDO_INICIAL_MS);

                while (simulacionActiva && !colaTareasPendientes.isEmpty()) {
                    TareaImpresion tarea = colaTareasPendientes.poll();
                    if (tarea != null) {
                        Platform.runLater(() -> tablaTareas.getItems().remove(tarea));
                        simularImpresion(tarea);
                        // Retardo entre tareas
                        Thread.sleep(RETARDO_ENTRE_TAREAS_MS);
                    } else if (simulacionActiva && colaTareasPendientes.isEmpty()) {
                        Thread.sleep(200); // Esperar un momento para evitar un bucle ocupado
                    }
                }
                if (colaTareasPendientes.isEmpty()) {
                    Platform.runLater(() -> {
                        simulacionActiva = false;
                        btnControlSimulacion.setText("Iniciar Simulación");
                        progressBar.setVisible(false);

                        colaTareasPendientes.addAll(nuevasTareas);
                        nuevasTareas.clear();
                    });
                }
                return null;
            }
        };

        new Thread(simulacionTask).start();
    }

    private void simularImpresion(TareaImpresion tarea) throws InterruptedException {
        int totalHojas = tarea.getHojasImprimir();
        for (int i = 0; i < totalHojas; i++) {
            if (!simulacionActiva) {
                break;
            }
            Thread.sleep(200); // Simula el tiempo de impresión por cada hoja
            final double progreso = (i + 1) / (double) totalHojas;
            Platform.runLater(() -> progressBar.setProgress(progreso));
        }
    }
}

class TareaImpresion {
    private final SimpleIntegerProperty numeroArchivo;
    private final SimpleStringProperty nombreArchivo;
    private final SimpleIntegerProperty hojasImprimir;
    private final SimpleStringProperty horaAcceso;

    public TareaImpresion(int numeroArchivo, String nombreArchivo, int hojasImprimir, String horaAcceso) {
        this.numeroArchivo = new SimpleIntegerProperty(numeroArchivo);
        this.nombreArchivo = new SimpleStringProperty(nombreArchivo);
        this.hojasImprimir = new SimpleIntegerProperty(hojasImprimir);
        this.horaAcceso = new SimpleStringProperty(horaAcceso);
    }

    public int getNumeroArchivo() {
        return numeroArchivo.get();
    }

    public SimpleIntegerProperty numeroArchivoProperty() {
        return numeroArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo.get();
    }

    public SimpleStringProperty nombreArchivoProperty() {
        return nombreArchivo;
    }

    public int getHojasImprimir() {
        return hojasImprimir.get();
    }

    public SimpleIntegerProperty hojasImprimirProperty() {
        return hojasImprimir;
    }

    public String getHoraAcceso() {
        return horaAcceso.get();
    }

    public SimpleStringProperty horaAccesoProperty() {
        return horaAcceso;
    }
}
