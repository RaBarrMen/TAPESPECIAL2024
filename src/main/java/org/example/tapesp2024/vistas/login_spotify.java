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
    private Button btn_login, btn_guest, btn_admin;
    private ClienteDAO clienteDAO;
    int id_usuario;

    public login_spotify(int idUsuario) {
        this.id_usuario = idUsuario;
        this.setTitle("Inicio de Sesión - Spotify");
        clienteDAO = new ClienteDAO(); // Inicializa el objeto clienteDAO
        Scene escena = new Scene(createUI(), 300, 400);
        escena.getStylesheets().add(getClass().getResource("/estilos/login_spotify.css").toExternalForm());
        this.setScene(escena);
        this.show();
    }

    private VBox createUI() {
        // Título
        label_title = new Label("Inicio de sesión");

        // Usuario
        label_user = new Label("Usuario");
        text_user = new TextField();
        text_user.setPromptText("Usuario");

        // Contraseña
        label_pass = new Label("Contraseña");
        text_password = new PasswordField();
        text_password.setPromptText("Contraseña");

        // Botón de inicio de sesión
        btn_login = new Button("Iniciar Sesión");
        btn_login.setOnAction(event -> openMenuView());

        // Botón de invitado
        btn_guest = new Button("Registrarse");
        btn_guest.setOnAction(event -> openGuestView());

        btn_admin = new Button("Admin");
        btn_admin.setOnAction(event -> openAdminView(this.id_usuario));



        // Mensaje de error o éxito
        label_message = new Label();
        label_message.setTextFill(Color.RED);

        // Crear y configurar el VBox
        VBox vBox2 = new VBox(btn_login,btn_guest,btn_admin);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setPadding(new Insets(20));
        vBox2.getStyleClass().add("card");
        vBox2.setStyle("-fx-spacing: 8");
        VBox vbox = new VBox(10, label_title, label_user, text_user, label_pass, text_password,vBox2, label_message);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.getStyleClass().add("card");


        label_user.getStyleClass().add("label-text-field");
        label_pass.getStyleClass().add("label-text-field");
        label_message.getStyleClass().add("label-text-field");
        label_title.getStyleClass().add("title");
        text_user.getStyleClass().add("field");
        text_password.getStyleClass().add("field");
        btn_login.getStyleClass().add("btn");
        btn_guest.getStyleClass().add("btn");
        btn_admin.getStyleClass().add("btn");
        btn_login.setText(btn_login.getText().toUpperCase());
        btn_guest.setText(btn_guest.getText().toUpperCase());
        btn_admin.setText(btn_admin.getText().toUpperCase());
        return vbox;
    }

    private void openGuestView() {
        RegistroCliente registroCliente = new RegistroCliente();
        registroCliente.show();
        this.close();
    }

    /*private void openMenuView() {
        if (clienteDAO.validateUser(text_user.getText(), text_password.getText())) {
            // Si el usuario es válido, abre la ventana PantallaUsuario
            abrirPantallaUsuario();
            Stage stage = (Stage) text_user.getScene().getWindow();
            stage.close();
        } else {
            mostrarAlerta("Usuario o contraseña incorrecto o no existe");
        }
    }*/

    private void openMenuView() {
        if (clienteDAO.validateUser(text_user.getText(), text_password.getText())) {
            // Si el usuario es válido, obtener su ID
            int userId = clienteDAO.getUserID(text_user.getText());
            abrirPantallaUsuario(userId); // Pasar el ID a la siguiente pantalla
            Stage stage = (Stage) text_user.getScene().getWindow();
            stage.close();
        } else {
            mostrarAlerta("Usuario o contraseña incorrecto o no existe");
        }
    }

    private void abrirPantallaUsuario(int userId) {
        PantallaUsuario pantallaUsuario = new PantallaUsuario(userId);
        pantallaUsuario.show();
    }


    private void openAdminView(int userId){
        login_admin_spotify loguin = new login_admin_spotify(userId);
        loguin.show();
        this.close();
    }

    /*private void abrirPantallaUsuario() {
        PantallaUsuario pantallaUsuario = new PantallaUsuario();
        pantallaUsuario.show();
    }*/

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
