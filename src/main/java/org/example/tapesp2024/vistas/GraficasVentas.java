package org.example.tapesp2024.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.VentasDAO;

import java.util.Map;

public class GraficasVentas extends Stage {
    private VBox vboxGraficas;
    private Button btnSalir;
    private VentasDAO ventasDAO = new VentasDAO();
    int id_usuario;

    public GraficasVentas(int id_usuario) {
        this.id_usuario = id_usuario;
        CrearIU();
        this.setTitle("Gráficas de Ventas");
        Scene escena = new Scene(vboxGraficas, 800, 600);
        this.setScene(escena);
        this.show();
    }

    private void CrearIU() {
        // Crear gráficas
        PieChart pieChartCanciones = crearPieChartCanciones();
        BarChart<String, Number> barChartAlbumes = crearBarChartAlbumes();

        // Botón para salir
        btnSalir = new Button("Regresar");
        btnSalir.setOnAction(event -> regresarPantallaAdmin());

        // Configurar layout principal
        vboxGraficas = new VBox(10, pieChartCanciones, barChartAlbumes, btnSalir);
        vboxGraficas.setAlignment(Pos.CENTER);
    }

    private PieChart crearPieChartCanciones() {
        Map<String, Integer> cancionesMasVendidas = ventasDAO.obtenerCancionesMasVendidasMes();

        PieChart pieChart = new PieChart();
        pieChart.setTitle("Canciones Más Vendidas");

        cancionesMasVendidas.forEach((cancion, ventas) -> {
            PieChart.Data slice = new PieChart.Data(cancion, ventas);
            pieChart.getData().add(slice);
        });

        return pieChart;
    }

    private BarChart<String, Number> crearBarChartAlbumes() {
        Map<String, Integer> albumesMasVendidos = ventasDAO.obtenerAlbumesMasVendidosMes();

        // Ejes del gráfico
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Álbumes");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ventas");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Álbumes Más Vendidos");

        // Serie de datos
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Ventas por Álbum");

        albumesMasVendidos.forEach((album, ventas) -> {
            dataSeries.getData().add(new XYChart.Data<>(album, ventas));
        });

        barChart.getData().add(dataSeries);
        return barChart;
    }

    private void regresarPantallaAdmin() {
        // Lógica para regresar a la pantalla principal de administrador
        PantallaAdminCanciones pantallaAdmin = new PantallaAdminCanciones(0); // Reemplaza con el ID de usuario si aplica
        pantallaAdmin.show();
        this.close();
    }
}
