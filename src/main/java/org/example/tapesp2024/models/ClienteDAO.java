package org.example.tapesp2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    public int INSERT(){
        int row_count;
        String query = "insert into cliente(cliente, telefono, email) values('"+this.cliente+"','"+this.telefono+"','"+this.email+"')";
        try {
            Statement state = Conexion.connection.createStatement();
            row_count = state.executeUpdate(query);
        } catch (Exception e) {
            row_count = 0;
            e.printStackTrace();
        }
        return row_count;
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

    public ObservableList<ClienteDAO> SELECTALL(){
        ClienteDAO clienteDAO;
        String query = "SELECT * FROM cliente";
        ObservableList<ClienteDAO> list_cliente = FXCollections.observableArrayList();
        try {
            Statement state = Conexion.connection.createStatement();
            state.executeQuery(query);
            ResultSet res = state.executeQuery(query);
            while(res.next()){
                clienteDAO  = new ClienteDAO();
                clienteDAO.id_cliente = res.getInt(0);
                clienteDAO.cliente = res.getString(1);
                clienteDAO.telefono = res.getString(2);
                clienteDAO.email = res.getString(3);
                list_cliente.add(clienteDAO);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list_cliente;
    }
}
