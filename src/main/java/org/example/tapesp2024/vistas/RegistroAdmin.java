package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.AdminDAO;

public class RegistroAdmin extends Stage {
    private TextField admin;
    private TextField contrasenia;
    private TextField usuario;
    private Button tbn_guardar, btn_salir;
    private VBox vbox;
    private AdminDAO adminDAO;
    private Scene escena;
    int id_usuario;

    public RegistroAdmin() {
        CrearIU();
        if (adminDAO != null) {
            this.adminDAO = adminDAO;
            admin.setText(adminDAO.getAdmin());
            usuario.setText(adminDAO.getUsuario());
            contrasenia.setText(adminDAO.getContrasenia());
            this.setTitle("Editar Cliente");
        }else{
            this.adminDAO = new AdminDAO();
            this.setTitle("Agregar Cliente");
        }
        this.setTitle("Agregar Cliente :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearIU() {
        admin = new TextField();
        admin.setPromptText("Cliente");
        usuario = new TextField();
        usuario.setPromptText("Usuario");
        contrasenia = new TextField();
        contrasenia.setPromptText("Contraseña");
        tbn_guardar = new Button("Guardar");
        tbn_guardar.setOnAction(actionEvent -> GuardarCliente());
        btn_salir = new Button("Salir");
        btn_salir.setOnAction(actionEvent -> salirLoginAdmin());
        vbox = new VBox(admin, usuario, contrasenia, tbn_guardar, btn_salir);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        escena = new Scene(vbox, 150, 220);
    }

    private void salirLoginAdmin() {
        login_admin_spotify login = new login_admin_spotify(this.id_usuario);
        login.show();
        this.close();
    }

    private void GuardarCliente() {
        if (admin.getText().isEmpty() && usuario.getText().isEmpty() && contrasenia.getText().isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText("No se puede guardar el usuario porque no se han ingresado datos.");
            alerta.showAndWait();

            return;
        }

        adminDAO.setAdmin(admin.getText());
        adminDAO.setUsuario(usuario.getText());
        adminDAO.setContrasenia(contrasenia.getText());

        String mensaje;
        Alert.AlertType type;

        if (adminDAO.getId_admin() > 0) {
            adminDAO.UPDATE();
            mensaje = "Usuario actualizado exitosamente";
            type = Alert.AlertType.INFORMATION;
        } else {  // Si no tiene ID, se realiza un insert
            if (adminDAO.INSERT() > 0) {
                mensaje = "Usuario guardado exitosamente";
                type = Alert.AlertType.INFORMATION;
            } else {
                mensaje = "Usuario no guardado";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del sistema :)");
        alerta.setContentText(mensaje);
        alerta.showAndWait();

    }
}