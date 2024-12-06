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
        btn_salir_login.setOnAction(event -> mostrarLogin(this.id_usuario));
        // Configurar el layout
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label_bienvenida, btn_comprarCanciones, btn_historialCompras, btn_datosPersonales, btn_salir_login);
        vbox.getStyleClass().add("card");
        return vbox;
    }

    private void mostrarLogin(int id_usuario) {
        // Crear y mostrar la ventana de login
        login_spotify loginWindow = new login_spotify(this.id_usuario);
        loginWindow.show();

        this.close();
    }

    private void mostrarPantallaCompra() {
        PantallaCompra pantalla_compra = new PantallaCompra(this.id_usuario);
        pantalla_compra.show();
        this.close();
    }

    private void mostrarHistorialCompras() {
        VentanaHistorial ventanaHistorial = new VentanaHistorial(this.id_usuario);
        ventanaHistorial.show();
        this.close();
    }

    private void mostrarDatosPersonales() {
        DatosPersonales datosPersonales = new DatosPersonales(this.id_usuario);
        datosPersonales.show();
        this.close();
    }

}

