package org.example.tapesp2024.models;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.example.tapesp2024.models.Conexion.connection;

public class ClienteDAO {
    private String nombre;
    private String telefono;
    private String usuario;
    private String contrasenia;
    private int id_rol;
    private int id_usuario;

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int INSERT() {
        int row_count;
        String query = "INSERT INTO usuario(nombre, telefono, usuario, contrasenia, id_rol) VALUES ('"+this.nombre+"','"
                + this.telefono + "','" + this.usuario + "', '" + this.contrasenia + "', 2)";
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
        String query = "UPDATE usuario SET telefono = '"+this.telefono+"', usuario = '"+this.usuario +"' WHERE id_usuario = '"+this.id_usuario+"'";
        try {
            Statement state = connection.createStatement();
            state.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void DELETE(){
        String query = "DELETE FROM usuario WHERE id_usuario = " + this.id_usuario;
        try {
            Statement state = connection.createStatement();
            state.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public ObservableList<ClienteDAO> SELECTALL(){
        ClienteDAO clienteDAO;
        String query = "SELECT * FROM usuario";
        ObservableList<ClienteDAO> list_cliente = FXCollections.observableArrayList();
        try {
            Statement state = connection.createStatement();
            state.executeQuery(query);
            ResultSet res = state.executeQuery(query);
            while(res.next()){
                clienteDAO  = new ClienteDAO();
                clienteDAO.id_usuario = res.getInt(1);
                clienteDAO.nombre = res.getString(2);
                clienteDAO.telefono = res.getString(3);
                clienteDAO.usuario = res.getString(4);
                clienteDAO.id_usuario = res.getInt(5);
                list_cliente.add(clienteDAO);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list_cliente;
    }

public Observable SELECTNAME(int id){
        ClienteDAO clienteDAO;

        String query = "SELECT nombre FROM usuario where id_usuario = '"+id+"' ";
        ObservableList<ClienteDAO> list_cliente = FXCollections.observableArrayList();
        try {
            Statement state = connection.createStatement();
            state.executeQuery(query);
            ResultSet res = state.executeQuery(query);
            while(res.next()){
                clienteDAO  = new ClienteDAO();
                clienteDAO.nombre = res.getString(1);
                list_cliente.add(clienteDAO);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list_cliente;
    }

    public Optional<ClienteDAO> findById(int id) {
        Optional<ClienteDAO> optEmp = Optional.empty();
        String query = "select * from usuario where id_usuario = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                ClienteDAO user = new ClienteDAO();
                user.setId_usuario(rs.getInt("id_usuario"));
                user.setUsuario(rs.getString("usuario"));
                user.setContrasenia(rs.getString("contrasenia"));
                user.setTelefono(rs.getString("telefono"));
                user.setId_rol(rs.getInt("id_rol"));
                optEmp = Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return optEmp;
    }

    public int getUserID(String username){
        String query = "select id_usuario from usuario where usuario=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_usuario");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean validateUser(String user, String contrasenia) {
        String query = "select * from usuario where usuario = ? and contrasenia = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user);
            statement.setString(2, contrasenia);

            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAdmin(String user, String contrasenia) {
        String query = "SELECT id_rol FROM usuario WHERE usuario = ? AND contrasenia = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user);
            statement.setString(2, contrasenia);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_rol") == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
