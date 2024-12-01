package org.example.tapesp2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.example.tapesp2024.models.Conexion.connection;

public class AdminDAO {
    private int id_admin;
    private String admin;
    private String usuario;
    private String contrasenia;

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
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
        String query = "insert into admin(admin, usuario, contrasenia) values('"+this.admin+"','"+this.usuario +"','"+this.contrasenia+"')";
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
        String query = "UPDATE admin SET admin = '"+this.admin+"', usuario = '"+this.usuario +"' WHERE id_admin = '"+this.id_admin +"'";
        try {
            Statement state = connection.createStatement();
            state.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void DELETE(){
        String query = "DELETE FROM admin WHERE id_admin = " + this.id_admin;
        try {
            Statement state = connection.createStatement();
            state.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public ObservableList<AdminDAO> SELECTALL(){
        AdminDAO clienteDAO;
        String query = "SELECT * FROM admin";
        ObservableList<AdminDAO> list_cliente = FXCollections.observableArrayList();
        try {
            Statement state = connection.createStatement();
            state.executeQuery(query);
            ResultSet res = state.executeQuery(query);
            while(res.next()){
                clienteDAO  = new AdminDAO();
                clienteDAO.id_admin = res.getInt(1);
                clienteDAO.admin = res.getString(2);
                clienteDAO.usuario = res.getString(4);
                list_cliente.add(clienteDAO);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list_cliente;
    }

    public Optional<AdminDAO> findById(int id) {
        Optional<AdminDAO> optEmp = Optional.empty();
        String query = "select * from admin where id_admin = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                AdminDAO user = new AdminDAO();
                user.setId_admin(rs.getInt("IDUsuario"));
                user.setUsuario(rs.getString("username"));
                user.setContrasenia(rs.getString("contrasena"));
                user.setAdmin(rs.getString("nombre"));
                optEmp = Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return optEmp;
    }

    public int getUserID(String username){
        String query = "select id_admin from admin where usuario=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_admin");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public boolean validateUser(String user, String contrasenia) {
        String query = "select * from admin where admin = ? and contrasenia = ?";
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

}
