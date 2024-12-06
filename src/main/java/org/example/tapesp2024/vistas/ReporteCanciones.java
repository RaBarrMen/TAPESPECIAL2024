package org.example.tapesp2024.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.VentaDAO;
import org.example.tapesp2024.models.Venta_DetalleDAO;
import org.example.tapesp2024.models.ClienteDAO;

import java.util.List;

public class ReporteCanciones extends Stage {
    VBox vbox_report = new VBox(10);

    public ReporteCanciones(int id_usuario) {
        this.setTitle("Reporte de Canciones Compradas");

        // Obtener los datos de las canciones compradas
        VentaDAO ventaDAO = new VentaDAO();
        List<Venta_DetalleDAO> cancionesCompradas = ventaDAO.obtenerCancionesPorUsuario(id_usuario);

        // Crear etiquetas para mostrar los detalles de cada compra
        for (Venta_DetalleDAO ventaDetalle : cancionesCompradas) {
            ClienteDAO clienteDAO = new ClienteDAO();
            String nombreUsuario = clienteDAO.SELECTNAME(id_usuario).toString();
            String reporte = "Canción: " + ventaDetalle.getId_venta() + ", Fecha: " + ventaDetalle.getFecha() + ", Usuario: " + nombreUsuario;

            Label label = new Label(reporte);
            vbox_report.getChildren().add(label);
        }

        // Crear la escena para la pantalla del reporte
        Scene escenaReporte = new Scene(vbox_report, 400, 300);
        this.setScene(escenaReporte);
        this.show();
    }
}
