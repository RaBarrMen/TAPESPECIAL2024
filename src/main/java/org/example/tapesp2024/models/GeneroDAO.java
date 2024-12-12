package org.example.tapesp2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.tapesp2024.models.Conexion.connection;

public class GeneroDAO {
    private int id_genero;
    private String genero;

    public GeneroDAO(int id_genero, String genero) {
        this.id_genero = id_genero;
        this.genero = genero;
    }

    public GeneroDAO() {
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public ObservableList<GeneroDAO> SELECTALL() {
        ObservableList<GeneroDAO> generos = FXCollections.observableArrayList();
        String query = "SELECT id_genero, genero FROM genero"; // Asegúrate de obtener ambos campos

        try (Statement state = connection.createStatement();
             ResultSet res = state.executeQuery(query)) {

            while (res.next()) {
                GeneroDAO generoDAO = new GeneroDAO();
                generoDAO.setId_genero(res.getInt("id_genero")); // Asigna el ID del género
                generoDAO.setGenero(res.getString("genero"));    // Asigna el nombre del género
                generos.add(generoDAO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generos;
    }

    public int UPDATE() {
        int row_count = 0;
        String query = "UPDATE genero SET genero = ? WHERE id_genero = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, this.genero);
            stmt.setInt(2, this.id_genero);
            row_count = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row_count;
    }

    public int DELETE() {
        int row_count = 0;
        String query = "DELETE FROM genero WHERE id_genero = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.id_genero);
            row_count = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row_count;
    }

    public int INSERT() {
        int row_count = 0;
        String query = "INSERT INTO genero (genero) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.genero);
            row_count = stmt.executeUpdate();

            if (row_count > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id_genero = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return row_count;
    }

}
