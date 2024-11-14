package org.example.tapesp2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.example.tapesp2024.models.Conexion.connection;

public class ClienteDAO {
    private int id_cliente;
    private String cliente;
    private String telefono;
    private String usuario;
    private String contrasenia;

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int INSERT(){
        int row_count;
        String query = "insert into cliente(cliente, telefono, usuario, contrasenia) values('"+this.cliente+"','"+this.telefono+"','"+this.usuario +"','"+this.contrasenia+"')";
        try {
            Statement state = connection.createStatement();
            row_count = state.executeUpdate(query);
        } catch (Exception e) {
            row_count = 0;
            e.printStackTrace();
        }
        return row_count;
    };

    public void UPDATE(){
        String query = "UPDATE cliente SET cliente = '"+this.cliente+"', telefono = '"+this.telefono+"', usuario = '"+this.usuario +"' WHERE id_cliente = '"+this.id_cliente+"'";
        try {
            Statement state = connection.createStatement();
            state.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void DELETE(){
        String query = "DELETE FROM cliente WHERE id_cliente = " + this.id_cliente;
        try {
            Statement state = connection.createStatement();
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
            Statement state = connection.createStatement();
            state.executeQuery(query);
            ResultSet res = state.executeQuery(query);
            while(res.next()){
                clienteDAO  = new ClienteDAO();
                clienteDAO.id_cliente = res.getInt(1);
                clienteDAO.cliente = res.getString(2);
                clienteDAO.telefono = res.getString(3);
                clienteDAO.usuario = res.getString(4);
                list_cliente.add(clienteDAO);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list_cliente;
    }

    public Optional<ClienteDAO> findById(int id) {
        Optional<ClienteDAO> optEmp = Optional.empty();
        String query = "select * from usuario where IDUsuario = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                ClienteDAO user = new ClienteDAO();
                user.setId_cliente(rs.getInt("IDUsuario"));
                user.setUsuario(rs.getString("username"));
                user.setContrasenia(rs.getString("contrasena"));
                user.setCliente(rs.getString("nombre"));
                user.setTelefono(rs.getString("telefono"));
                optEmp = Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return optEmp;
    }

    public int getUserID(String username){
        String query = "select IDUsuario from usuario where username=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("IDUsuario");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean validateUser(String user, String contrasenia) {
        String query = "select * from cliente where usuario = ? and contrasenia = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user);
            statement.setString(2, contrasenia);

            ResultSet rs = statement.executeQuery();
            return rs.next(); // Retorna true si el usuario existe
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
