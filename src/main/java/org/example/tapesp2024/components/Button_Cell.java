package org.example.tapesp2024.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import org.example.tapesp2024.models.ClienteDAO;
import org.example.tapesp2024.vistas.FormularioCliente;

import java.util.Optional;

public class Button_Cell extends TableCell<ClienteDAO, String> {
    Button boton_celda;

    public Button_Cell(String str){
        boton_celda = new Button(str);
        boton_celda.setOnAction(Event -> EventoBoton(str));
    }

    private void EventoBoton(String str) {
        ClienteDAO clienteDAO = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")){
            new FormularioCliente(this.getTableView(), clienteDAO);

        } else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion :)");
            alert.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> opcion = alert.showAndWait();
            if(opcion.get() == ButtonType.OK){
                clienteDAO.DELETE();
                this.getTableView().setItems(clienteDAO.SELECTALL());
                this.getTableView().refresh();
            }
        }
    }

    @Override
    protected void updateItem(String s, boolean b) {
        super.updateItem(s, b);
        if(!b){
            this.setGraphic(boton_celda);
        }
    }
}
