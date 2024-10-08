package org.example.tapesp2024.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.ClienteDAO;

public class ListaClientes extends Stage {

    private TableView<ClienteDAO> tableViewClientes;
    private Button btn_agregar;
    private ToolBar toolBarMenu;
    private VBox vbox;
    private Scene escena;

    public ListaClientes() {
        CrearUI();
        this.setTitle("Lista de Clientes");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        toolBarMenu = new ToolBar();
        ImageView imageView = new ImageView(getClass().getResource("/images/siguiente.png").toString());
        Button btn_agregar_cliente = new Button();
        btn_agregar_cliente.setOnAction(actionEvent -> new FormularioCliente());
        btn_agregar_cliente.setGraphic(imageView);
        toolBarMenu.getItems().add(btn_agregar_cliente);

        tableViewClientes = new TableView<>();
        vbox = new VBox(toolBarMenu, tableViewClientes);
        escena = new Scene(vbox, 500, 250);
    }
}
