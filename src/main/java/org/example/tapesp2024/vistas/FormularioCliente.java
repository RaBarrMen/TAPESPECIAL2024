package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.ClienteDAO;

public class FormularioCliente extends Stage {
    private TextField cliente;
    private TextField telefono;
    private TextField email;
    private Button tbn_guardar;
    private VBox vbox;
    private ClienteDAO clienteDAO;

    private Scene escena;
    public FormularioCliente() {
        clienteDAO = new ClienteDAO();
        CrearIU();
        this.setTitle("Agregar Cliente :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearIU() {
        cliente = new TextField();
        cliente.setPromptText("Cliente");
        telefono = new TextField();
        telefono.setPromptText("Telefono");
        email = new TextField();
        email.setPromptText("Email");
        tbn_guardar = new Button("Guardar");
        tbn_guardar.setOnAction(actionEvent -> GuardarCliente());
        vbox = new VBox(cliente, telefono, email, tbn_guardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        escena = new Scene(vbox, 150, 150);
    }

    private void GuardarCliente() {
        clienteDAO.setEmail(email.getText());
        clienteDAO.setTelefono(telefono.getText());
        clienteDAO.setCliente(cliente.getText());
        String mensaje;
        Alert.AlertType type;
        if( clienteDAO.INSERT() > 0 ){
            mensaje = "Cliente guardado exitosamente";

            type = Alert.AlertType.INFORMATION;
        }else{
            mensaje = "Cliente no guardado";
            type = Alert.AlertType.ERROR;
        }
        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del sistema :)");
        alerta.setContentText("Registro insertado");
        alerta.showAndWait();
    }


}
