package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
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
    private TableView<ClienteDAO> tableView_cliente;

    private Scene escena;
    public FormularioCliente(TableView<ClienteDAO> tableView, ClienteDAO objeto_cliente) {
        this.tableView_cliente = tableView;
        CrearIU();
        if (objeto_cliente != null) {
            this.clienteDAO =  objeto_cliente;
            cliente.setText(clienteDAO.getCliente());
            telefono.setText(clienteDAO.getTelefono());
            email.setText(clienteDAO.getUsuario());
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
        // Asignar los valores ingresados en los campos de texto al clienteDAO
        clienteDAO.setCliente(cliente.getText());
        clienteDAO.setUsuario(email.getText());
        clienteDAO.setTelefono(telefono.getText());

        String mensaje;
        Alert.AlertType type;

        if (clienteDAO.getId_cliente() > 0) {
            clienteDAO.UPDATE();
            mensaje = "Cliente actualizado exitosamente";
            type = Alert.AlertType.INFORMATION;
        } else {  // Si no tiene ID, se realiza un insert
            if (clienteDAO.INSERT() > 0) {
                mensaje = "Cliente guardado exitosamente";
                type = Alert.AlertType.INFORMATION;
            } else {
                mensaje = "Cliente no guardado";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del sistema :)");
        alerta.setContentText(mensaje);
        alerta.showAndWait();

        tableView_cliente.setItems(clienteDAO.SELECTALL());
        tableView_cliente.refresh();

        this.close();
    }




}
