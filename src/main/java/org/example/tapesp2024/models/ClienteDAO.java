package org.example.tapesp2024.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDAO {
    private int id_cliente;
    private String cliente;
    private String telefono;
    private String email;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void INSERT(){
        String query = "insert into cliente(cliente, telefono, email) values('"+this.cliente+"','"+this.telefono+"','"+this.email+"')";
        try {
            Statement state = Conexion.connection.createStatement();
            state.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    public void UPDATE(){
        String query = "UPDATE cliente SET cliente = '', telefono = '"+this.telefono+"', email = '"+this.email+"' WHERE id_cliente = '"+this.id_cliente+"'";
        try {
            Statement state = Conexion.connection.createStatement();
            state.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    public void DELETE(){
        String query = "DELETE FROM cliente WHERE id_cliente = " + this.id_cliente;
        try {
            Statement state = Conexion.connection.createStatement();
            state.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    public void SELECTALL(){
        String query = "SELECT * FROM cliente";
        try {
            Statement state = Conexion.connection.createStatement();
            state.executeQuery(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    };
}
