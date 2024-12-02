package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.tapesp2024.models.AdminDAO;
import org.example.tapesp2024.models.ClienteDAO;

public class login_admin_spotify extends Stage {
    private TextField text_user;
    private PasswordField text_password;
    private Label label_title, label_user, label_pass, label_message;
    private Button btn_login, btn_regresar_login;
    private ClienteDAO clienteDAO;

    public login_admin_spotify() {
        this.setTitle("Inicio de Sesión - Spotify");
        clienteDAO = new ClienteDAO();
        Scene escena = new Scene(createUI(), 300, 400);
        escena.getStylesheets().add(getClass().getResource("/estilos/login_admin_spotify.css").toExternalForm());
        this.setScene(escena);
        this.show();
    }

    private VBox createUI() {
        label_title = new Label("Inicio de sesión \nAdministrador");
        label_title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        label_title.setTextFill(Color.web("#333333"));
        label_user = new Label("Usuario");
        label_user.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        text_user = new TextField();
        text_user.setPromptText("Usuario");
        label_pass = new Label("Contraseña");
        label_pass.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        text_password = new PasswordField();
        text_password.setPromptText("Contraseña");
        btn_login = new Button("Iniciar Sesión");
        btn_login.setOnAction(event -> openMenuView());
        btn_regresar_login = new Button("Regresar al \nlogin");
        btn_regresar_login.setOnAction(event -> openLoginNormal());
        label_message = new Label();
        label_message.setTextFill(Color.RED);

        VBox vbox = new VBox(10, label_title, label_user, text_user, label_pass, text_password, btn_login, btn_regresar_login, label_message);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(25));
        vbox.getStyleClass().add("card");
        vbox.setAlignment(Pos.CENTER);
        label_title.getStyleClass().add("title");
        label_user.getStyleClass().add("label-text-field");
        label_pass.getStyleClass().add("label-text-field");
        label_message.getStyleClass().add("label-text-field");
        text_user.getStyleClass().add("field");
        text_password.getStyleClass().add("field");
        btn_login.getStyleClass().add("btn");
        btn_regresar_login.getStyleClass().add("btn");


        return vbox;
    }

    private void openGuestView() {
        RegistroAdmin registroAdmin = new RegistroAdmin();
        registroAdmin.show();
        this.close();
    }

    private void openLoginNormal(){
        login_spotify login = new login_spotify();
        login.show();
        this.close();
    }

    private void openMenuView() {
        String username = text_user.getText();
        String password = text_password.getText();

        if (clienteDAO.validateUser(username, password)) {
            if (clienteDAO.isAdmin(username, password)) {
                abrirPantallaAdmin(); // Usuario con rol de administrador
                Stage stage = (Stage) text_user.getScene().getWindow();
                stage.close();
            } else {
                mostrarAlerta("Acceso denegado: solo los administradores pueden ingresar.");
            }
        } else {
            mostrarAlerta("Usuario o contraseña incorrectos.");
        }
    }


    private void abrirPantallaAdmin() {
        // Crear una nueva instancia de PantallaUsuario y mostrarla
        PantallaAdminCanciones pantallaUsuario = new PantallaAdminCanciones();
        pantallaUsuario.show();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
