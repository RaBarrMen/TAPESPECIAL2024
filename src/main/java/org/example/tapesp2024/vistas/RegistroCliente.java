package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.ClienteDAO;

public class RegistroCliente extends Stage {
    private TextField cliente;
    private TextField telefono;
    private TextField contrasenia;
    private TextField usuario;
    private Button tbn_guardar, btn_salir;
    private VBox vbox;
    private ClienteDAO clienteDAO;
    private Scene escena;

    public RegistroCliente() {
        CrearIU();
        if (clienteDAO != null) {
            this.clienteDAO =  clienteDAO;
            cliente.setText(clienteDAO.getCliente());
            telefono.setText(clienteDAO.getTelefono());
            usuario.setText(clienteDAO.getUsuario());
            contrasenia.setText(clienteDAO.getContrasenia());
            this.setTitle("Editar Cliente");
        }else{
            this.clienteDAO = new ClienteDAO();
            this.setTitle("Agregar Cliente");
        }
        this.setTitle("Agregar Cliente :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearIU() {
        cliente = new TextField();
        cliente.setPromptText("Cliente");
        telefono = new TextField();
        telefono.setPromptText("Telefono");
        usuario = new TextField();
        usuario.setPromptText("Usuario");
        contrasenia = new TextField();
        contrasenia.setPromptText("Contraseña");
        tbn_guardar = new Button("Guardar");
        tbn_guardar.setOnAction(actionEvent -> GuardarCliente());
        btn_salir = new Button("Salir");
        btn_salir.setOnAction(actionEvent -> salirLogin());
        vbox = new VBox(cliente, telefono, usuario, contrasenia, tbn_guardar, btn_salir);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        escena = new Scene(vbox, 150, 220);
    }

    private void salirLogin() {
        login_spotify login = new login_spotify();
        login.show();
        this.close();
    }

    private void GuardarCliente() {
        // Verificar si todos los campos están vacíos
        if (cliente.getText().isEmpty() && usuario.getText().isEmpty() &&
                telefono.getText().isEmpty() && contrasenia.getText().isEmpty()) {

            // Mostrar alerta de error
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText("No se puede guardar el usuario porque no se han ingresado datos.");
            alerta.showAndWait();

            return;  // Salir del método si no hay datos que guardar
        }

        // Asignar los valores ingresados en los campos de texto al clienteDAO
        clienteDAO.setCliente(cliente.getText());
        clienteDAO.setUsuario(usuario.getText());
        clienteDAO.setTelefono(telefono.getText());
        clienteDAO.setContrasenia(contrasenia.getText());

        String mensaje;
        Alert.AlertType type;

        if (clienteDAO.getId_cliente() > 0) {  // Si el cliente ya tiene un ID, se hace el update
            clienteDAO.UPDATE();  // Asume que UPDATE ya realiza la actualización
            mensaje = "Usuario actualizado exitosamente";
            type = Alert.AlertType.INFORMATION;
        } else {  // Si no tiene ID, se realiza un insert
            if (clienteDAO.INSERT() > 0) {
                mensaje = "Usuario guardado exitosamente";
                type = Alert.AlertType.INFORMATION;
            } else {
                mensaje = "Usuario no guardado";
                type = Alert.AlertType.ERROR;
            }
        }

        // Mostrar el mensaje correspondiente en una alerta
        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del sistema :)");
        alerta.setContentText(mensaje);
        alerta.showAndWait();

        // Cerrar la ventana
        this.close();
    }
}