package org.example.tapesp2024.vistas;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.tapesp2024.components.Button_Cell;
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

        // Ajustar el tamaño de la imagen manteniendo las proporciones
        imageView.setFitWidth(25); // Reducir ancho de la imagen
        imageView.setPreserveRatio(true); // Mantener la proporción de la imagen

        Button btn_agregar_cliente = new Button();
        btn_agregar_cliente.setOnAction(actionEvent -> new FormularioCliente(tableViewClientes, null));
        btn_agregar_cliente.setGraphic(imageView);

        // El tamaño del botón se ajustará automáticamente al tamaño de la imagen
        toolBarMenu.getItems().add(btn_agregar_cliente);

        tableViewClientes = new TableView<>();
        CrearTabla();

        vbox = new VBox(toolBarMenu, tableViewClientes);
        escena = new Scene(vbox, 500, 250);
    }


    private void CrearTabla() {
        ClienteDAO clienteDAO = new ClienteDAO();
        tableViewClientes = new TableView<ClienteDAO>();
        TableColumn<ClienteDAO, String> table_column_nombre = new TableColumn<>("Cliente");
        table_column_nombre.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        TableColumn<ClienteDAO, String> table_column_email = new TableColumn<>("Email");
        table_column_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<ClienteDAO, String> table_column_telefono = new TableColumn<>("Telefono");
        table_column_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<ClienteDAO, String> table_column_editar = new TableColumn<>("");
        table_column_editar.setCellFactory(new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
            @Override
            public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> clienteDAOStringTableColumn) {
                return new Button_Cell("Editar");
            }
        });

        TableColumn<ClienteDAO, String> table_column_eliminar = new TableColumn<>("");
        table_column_eliminar.setCellFactory(new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
            @Override
            public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> clienteDAOStringTableColumn) {
                return new Button_Cell("Eliminar");
            }
        });



        tableViewClientes.getColumns().addAll(table_column_nombre, table_column_email, table_column_telefono, table_column_editar, table_column_eliminar);
        tableViewClientes.setItems(clienteDAO.SELECTALL());
    }
}
