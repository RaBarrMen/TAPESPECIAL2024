package org.example.tapesp2024.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tapesp2024.models.ClienteDAO;



public class PantallaUsuario extends Stage {
    private ClienteDAO Cliente;
    Label label_bienvenida;
    Button btn_comprarCanciones, btn_historialCompras, btn_datosPersonales, btn_salir_login;
    ClienteDAO clienteDAO = new ClienteDAO();
    int id_usuario;

    public PantallaUsuario(int idUsuario) {
        this.id_usuario = idUsuario;
        System.out.println(this.id_usuario+"Pene si se paso");
        CrearIU();
        this.setTitle("Panel de Usuario");
        Scene escena = new Scene(CrearIU(), 300, 400);
        escena.getStylesheets().add(getClass().getResource("/estilos/pantalla_usuario.css").toExternalForm());
        this.setScene(escena);
        this.show();
    }

    private VBox CrearIU() {
        String nombreUsuario = clienteDAO.getNombre() != null ? clienteDAO.getNombre() : "Usuario";
        label_bienvenida = new Label("Bienvenido de nuevo, " + nombreUsuario);
        // Crear componentes
        label_bienvenida = new Label("Bienvenido");
        btn_comprarCanciones = new Button("Comprar Canciones o Álbumes");
        btn_historialCompras = new Button("Ver Historial de Compras");
        btn_datosPersonales = new Button("Ver Datos Personales");
        btn_salir_login = new Button("Salir al login");

        label_bienvenida.getStyleClass().add("title");
        btn_comprarCanciones.getStyleClass().add("btn");
        btn_historialCompras.getStyleClass().add("btn");
        btn_historialCompras.getStyleClass().add("btn");
        btn_datosPersonales.getStyleClass().add("btn");
        btn_salir_login.getStyleClass().add("btn");

        btn_comprarCanciones.setOnAction(event -> mostrarPantallaCompra());
        btn_historialCompras.setOnAction(event -> mostrarHistorialCompras());
        btn_datosPersonales.setOnAction(event -> mostrarDatosPersonales());
        btn_salir_login.setOnAction(event -> mostrarLogin());
        // Configurar el layout
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label_bienvenida, btn_comprarCanciones, btn_historialCompras, btn_datosPersonales, btn_salir_login);
        vbox.getStyleClass().add("card");
        return vbox;
    }

    private void mostrarLogin() {
        // Crear y mostrar la ventana de login
        login_spotify loginWindow = new login_spotify();
        loginWindow.show();

        this.close();
    }


    // Métodos para mostrar las distintas pantallas

    private void mostrarPantallaCompra() {
        PantallaCompra pantalla_compra = new PantallaCompra(this.id_usuario);
        pantalla_compra.show();
        this.close();
    }

    private void mostrarHistorialCompras() {
        Stage ventanaHistorial = new Stage();
        Label labelHistorial = new Label("Historial de Compras");
        // Añadir lista de compras e información detallada de cada una
        VBox vboxHistorial = new VBox(10, labelHistorial);
        vboxHistorial.setAlignment(Pos.CENTER);
        Scene escenaHistorial = new Scene(vboxHistorial, 300, 200);
        ventanaHistorial.setScene(escenaHistorial);
        ventanaHistorial.setTitle("Historial de Compras");
        ventanaHistorial.show();
    }

    private void mostrarDatosPersonales() {
        Stage ventanaDatos = new Stage();
        Label labelDatos = new Label("Datos Personales");
        // Aquí puedes agregar campos para mostrar la información personal del usuario
        VBox vboxDatos = new VBox(10, labelDatos);
        vboxDatos.setAlignment(Pos.CENTER);
        Scene escenaDatos = new Scene(vboxDatos, 300, 200);
        ventanaDatos.setScene(escenaDatos);
        ventanaDatos.setTitle("Datos Personales");
        ventanaDatos.show();
    }
}

