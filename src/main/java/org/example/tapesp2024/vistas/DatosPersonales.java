package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.ClienteDAO;

import java.util.Optional;

public class DatosPersonales extends Stage {
    int id_usuario;

    public DatosPersonales(int id_usuario) {
        this.id_usuario = id_usuario;
        CrearUI();  // Llamada para crear la UI
        this.setTitle("Datos Personales");
        this.show();
    }

    private void CrearUI() {
        // Crear un VBox para los elementos de la interfaz
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // Crear las etiquetas de información
        Label lblNombre = new Label("Nombre: ");
        Label lblTelefono = new Label("Teléfono: ");
        Label lblUsuario = new Label("Usuario: ");

        // Obtener los datos del usuario
        Optional<ClienteDAO> clienteOpt = obtenerDatosUsuario(id_usuario);
        if (clienteOpt.isPresent()) {
            ClienteDAO cliente = clienteOpt.get();

            // Actualizar las etiquetas con los valores del usuario
            lblNombre.setText("Nombre: " + cliente.getNombre());
            lblTelefono.setText("Teléfono: " + cliente.getTelefono());
            lblUsuario.setText("Usuario: " + cliente.getUsuario());
        } else {
            // En caso de que no se encuentren los datos
            lblNombre.setText("Nombre: No disponible");
            lblTelefono.setText("Teléfono: No disponible");
            lblUsuario.setText("Usuario: No disponible");
        }

        // Crear el botón de cierre
        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setOnAction(actionEvent -> salirPantallaUsuario());

        // Agregar las etiquetas y el botón al VBox
        vbox.getChildren().addAll(lblNombre, lblTelefono, lblUsuario, btnCerrar);

        // Crear la escena y asignarla a la ventana
        Scene escena = new Scene(vbox, 400, 250);
        this.setScene(escena);  // Establecer la escena
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
