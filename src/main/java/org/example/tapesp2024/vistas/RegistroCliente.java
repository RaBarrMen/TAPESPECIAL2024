package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.ClienteDAO;

public class RegistroCliente extends Stage {
    private TextField nombre;
    private TextField telefono;
    private TextField contrasenia;
    private TextField usuario;
    private Button btn_guardar, btn_salir;
    private VBox vbox;
    private ClienteDAO clienteDAO;
    private Scene escena;
    private Label titulo;
    int id_usuario;

    public RegistroCliente() {
        CrearIU();
        if (clienteDAO != null) {
            this.clienteDAO =  clienteDAO;
            telefono.setText(clienteDAO.getTelefono());
            usuario.setText(clienteDAO.getUsuario());
            contrasenia.setText(clienteDAO.getContrasenia());
            this.setTitle("Editar Cliente");
        }else{
            this.clienteDAO = new ClienteDAO();
            this.setTitle("Agregar Cliente");
        }
        this.setTitle("Registro");
        this.setScene(escena);
        this.show();
    }

    private void CrearIU() {
        titulo = new Label();
        titulo.setText("Registro");

        nombre = new TextField();
        nombre.setPromptText("nombre");

        telefono = new TextField();
        telefono.setPromptText("Telefono");


        usuario = new TextField();
        usuario.setPromptText("Usuario");

        contrasenia = new TextField();
        contrasenia.setPromptText("Contraseña");

        btn_guardar = new Button("Guardar");
        btn_guardar.setOnAction(actionEvent -> GuardarCliente());

        btn_salir = new Button("Salir");
        btn_salir.setOnAction(actionEvent -> salirLogin(this.id_usuario));

        vbox = new VBox(titulo, nombre, telefono, usuario, contrasenia, btn_guardar, btn_salir);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        vbox.getStyleClass().add("card");
        vbox.setAlignment(Pos.CENTER);
        titulo.getStyleClass().add("title");
        btn_guardar.getStyleClass().add("btn");
        btn_salir.getStyleClass().add("btn");
        nombre.getStyleClass().add("field");
        usuario.getStyleClass().add("field");
        telefono.getStyleClass().add("field");
        contrasenia.getStyleClass().add("field");
        escena = new Scene(vbox, 200, 285);
        escena.getStylesheets().add(getClass().getResource("/estilos/registro_cliente.css").toExternalForm());

    }

    private void salirLogin(int id_usuario) {
        login_spotify login = new login_spotify(this.id_usuario);
        login.show();
        this.close();
    }

    private void GuardarCliente() {
        if (usuario.getText().isEmpty() ||
                telefono.getText().isEmpty() || contrasenia.getText().isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText("No se puede guardar el usuario porque no se han ingresado datos.");
            alerta.showAndWait();
            return;
        }

        clienteDAO.setNombre(nombre.getText());
        clienteDAO.setUsuario(usuario.getText());
        clienteDAO.setTelefono(telefono.getText());
        clienteDAO.setContrasenia(contrasenia.getText());
        clienteDAO.setId_rol(2);

        String mensaje;
        Alert.AlertType type;

        if (clienteDAO.getId_usuario() > 0) {
            clienteDAO.UPDATE();
            mensaje = "Usuario actualizado exitosamente";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (clienteDAO.INSERT() > 0) {
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