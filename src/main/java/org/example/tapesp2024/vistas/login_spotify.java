package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.tapesp2024.models.ClienteDAO;

import java.io.IOException;
import java.util.Optional;

public class login_spotify extends Stage {
    private TextField text_user;
    private PasswordField text_password;
    private Label label_title, label_user, label_pass, label_message;
    private Button btn_login, btn_guest;
    private ClienteDAO clienteDAO;

    public login_spotify() {
        this.setTitle("Inicio de Sesión - Spotify");
        clienteDAO = new ClienteDAO(); // Inicializa el objeto clienteDAO
        Scene escena = new Scene(createUI(), 300, 400);
        this.setScene(escena);
        this.show();
    }

    private VBox createUI() {
        // Título
        label_title = new Label("Inicio de sesión");
        label_title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        label_title.setTextFill(Color.web("#333333"));

        // Usuario
        label_user = new Label("Usuario");
        label_user.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        text_user = new TextField();
        text_user.setPromptText("Usuario");

        // Contraseña
        label_pass = new Label("Contraseña");
        label_pass.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        text_password = new PasswordField();
        text_password.setPromptText("Contraseña");

        // Botón de inicio de sesión
        btn_login = new Button("Iniciar Sesión");
        btn_login.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btn_login.setOnAction(event -> openMenuView());

        // Botón de invitado
        btn_guest = new Button("Registrarse");
        btn_guest.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        btn_guest.setOnAction(event -> openGuestView());

        // Mensaje de error o éxito
        label_message = new Label();
        label_message.setTextFill(Color.RED);

        // Crear y configurar el VBox
        VBox vbox = new VBox(10, label_title, label_user, text_user, label_pass, text_password, btn_login, btn_guest, label_message);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #B0B0B0; -fx-border-width: 2px; -fx-border-radius: 5px;");

        return vbox;
    }

    private void openGuestView() {
        RegistroCliente registroCliente = new RegistroCliente();
        registroCliente.show();
        this.close();
    }

    private void openMenuView() {
        if (clienteDAO.validateUser(text_user.getText(), text_password.getText())) {
            // Si el usuario es válido, abre la ventana PantallaUsuario
            abrirPantallaUsuario();
            Stage stage = (Stage) text_user.getScene().getWindow();
            stage.close();
        } else {
            mostrarAlerta("Usuario o contraseña incorrecto o no existe");
        }
    }

    private void abrirPantallaUsuario() {
        // Crear una nueva instancia de PantallaUsuario y mostrarla
        PantallaUsuario pantallaUsuario = new PantallaUsuario();
        pantallaUsuario.show();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
