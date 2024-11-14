package org.example.tapesp2024.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PantallaUsuario extends Stage {
    Label label_bienvenida;
    Button btn_comprarCanciones, btn_historialCompras, btn_datosPersonales, btn_salir_login;

    public PantallaUsuario() {
        CrearIU();
        this.setTitle("Panel de Usuario");
        Scene escena = new Scene(CrearIU(), 400, 500);
        this.setScene(escena);
        this.show();
    }

    private VBox CrearIU() {
        // Crear componentes
        label_bienvenida = new Label("Bienvenido, ");
        btn_comprarCanciones = new Button("Comprar Canciones o Álbumes");
        btn_historialCompras = new Button("Ver Historial de Compras");
        btn_datosPersonales = new Button("Ver Datos Personales");
        btn_salir_login = new Button("Salir al login");

        // Configurar las acciones de los botones
        btn_comprarCanciones.setOnAction(event -> mostrarPantallaCompra());
        btn_historialCompras.setOnAction(event -> mostrarHistorialCompras());
        btn_datosPersonales.setOnAction(event -> mostrarDatosPersonales());
        btn_salir_login.setOnAction(event -> mostrarLogin());
        // Configurar el layout
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label_bienvenida, btn_comprarCanciones, btn_historialCompras, btn_datosPersonales, btn_salir_login);

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
        PantallaCompra pantalla_compra = new PantallaCompra();
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

