package org.example.tapesp2024.utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class PDFGenerator {

    public static void generarPDFConGraficas(PieChart pieChart, BarChart<String, Number> barChart, String rutaArchivo) {
        try {
            // Crear archivo PDF
            PdfWriter writer = new PdfWriter(rutaArchivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Exportar PieChart como imagen
            WritableImage pieChartImage = pieChart.snapshot(null, null);
            byte[] pieChartBytes = convertirImagenAWritableImage(pieChartImage);
            ImageData pieImageData = ImageDataFactory.create(pieChartBytes);
            Image pieImage = new Image(pieImageData);
            pieImage.setAutoScale(true);

            // Exportar BarChart como imagen
            WritableImage barChartImage = barChart.snapshot(null, null);
            byte[] barChartBytes = convertirImagenAWritableImage(barChartImage);
            ImageData barImageData = ImageDataFactory.create(barChartBytes);
            Image barImage = new Image(barImageData);
            barImage.setAutoScale(true);

            // Agregar imágenes al PDF
            document.add(new com.itextpdf.layout.element.Paragraph("Gráfica de Canciones Más Vendidas").setBold());
            document.add(pieImage);

            document.add(new com.itextpdf.layout.element.Paragraph("Gráfica de Álbumes Más Vendidos").setBold());
            document.add(barImage);

            // Cerrar el documento
            document.close();
            System.out.println("PDF generado correctamente en: " + rutaArchivo);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al generar el PDF: " + e.getMessage());
        }
    }

    private static byte[] convertirImagenAWritableImage(WritableImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputStream);
        return outputStream.toByteArray();
    }
}
