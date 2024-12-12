package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.tapesp2024.models.ClienteDAO;

import java.util.Optional;

public class DatosPersonales extends Stage {
    int id_usuario;

    public DatosPersonales(int id_usuario) {
        this.id_usuario = id_usuario;
        crearUI();
        this.setTitle("Datos Personales");
        this.show();
    }

    private void crearUI() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #2a2b38;");

        Label lblTitulo = new Label("Datos Personales");
        lblTitulo.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #f5f5f5; -fx-font-family: Constantia;");

        Label lblNombre = new Label();
        Label lblTelefono = new Label();
        Label lblUsuario = new Label();

        lblNombre.setStyle("-fx-text-fill: #f5f5f5; -fx-font-size: 14px;");
        lblTelefono.setStyle("-fx-text-fill: #f5f5f5; -fx-font-size: 14px;");
        lblUsuario.setStyle("-fx-text-fill: #f5f5f5; -fx-font-size: 14px;");

        Optional<ClienteDAO> clienteOpt = obtenerDatosUsuario(id_usuario);
        if (clienteOpt.isPresent()) {
            ClienteDAO cliente = clienteOpt.get();
            lblNombre.setText("Nombre: " + cliente.getNombre());
            lblTelefono.setText("Teléfono: " + cliente.getTelefono());
            lblUsuario.setText("Usuario: " + cliente.getUsuario());
        } else {
            lblNombre.setText("Nombre: No disponible");
            lblTelefono.setText("Teléfono: No disponible");
            lblUsuario.setText("Usuario: No disponible");
        }

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("-fx-background-color: #ffeba7; -fx-text-fill: #5e6681; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        btnCerrar.setOnMouseEntered(e -> btnCerrar.setStyle("-fx-background-color: #5e6681; -fx-text-fill: #ffeba7; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));
        btnCerrar.setOnMouseExited(e -> btnCerrar.setStyle("-fx-background-color: #ffeba7; -fx-text-fill: #5e6681; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));
        btnCerrar.setOnAction(actionEvent -> salirPantallaUsuario());

        vbox.getChildren().addAll(lblTitulo, lblNombre, lblTelefono, lblUsuario, btnCerrar);

        Scene escena = new Scene(vbox, 400, 300);
        this.setScene(escena);
    }

    private void salirPantallaUsuario() {
        PantallaUsuario pantallaUsuario = new PantallaUsuario(this.id_usuario);
        pantallaUsuario.show();
        this.close();
    }

    // Método para obtener los datos del usuario por ID
    private Optional<ClienteDAO> obtenerDatosUsuario(int id_usuario) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.findById(id_usuario);
    }
}
