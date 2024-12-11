package org.example.tapesp2024.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static org.example.tapesp2024.models.Conexion.connection;

public class AlbumDAO {
    private int id_album;
    private String album;
    private double costo_album;

    public double getCosto_album() {
        return costo_album;
    }

    public void setCosto_album(double costo_album) {
        this.costo_album = costo_album;
    }

    public int getId_album() {
        return id_album;
    }

    public void setId_album(int id_album) {
        this.id_album = id_album;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int INSERT() {
        int row_count = 0;
        String query = "INSERT INTO album (album, costo_album) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, this.album);
            stmt.setDouble(2, this.costo_album);
            row_count = stmt.executeUpdate();

            if (row_count > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id_album = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row_count;
    }

    public void INSERT_CANCION(int id_album, int id_cancion){
        String query = "insert into cancion_album(id_cancion, id_album) values(?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id_cancion);
            stmt.setInt(2, id_album);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ObservableList<AlbumDAO> obtenerAlbumesConCanciones() {
        ObservableList<AlbumDAO> listaAlbumes = FXCollections.observableArrayList();

        String query = "select * from album";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                AlbumDAO album = new AlbumDAO();
                album.setId_album(rs.getInt("id_album"));
                album.setAlbum(rs.getString("album"));
                album.setCosto_album(rs.getDouble("costo_album"));
                listaAlbumes.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAlbumes;
    }

    public static void comprarCancionesAlbum(int id_album, int id_usuario) {
        String queryCanciones = "SELECT c.id_cancion, c.costo_cancion FROM cancion_album ca " +
                "JOIN cancion c ON ca.id_cancion = c.id_cancion " +
                "WHERE ca.id_album = ? AND c.id_cancion NOT IN (" +
                "SELECT v.id_cancion FROM venta v WHERE v.id_usuario = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(queryCanciones)) {
            stmt.setInt(1, id_album);
            stmt.setInt(2, id_usuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id_cancion = rs.getInt("id_cancion");
                float costo_cancion = rs.getFloat("costo_cancion");

                // Crear una nueva venta
                VentaDAO ventaDAO = new VentaDAO();
                ventaDAO.setId_cancion(id_cancion);
                ventaDAO.setId_usuario(id_usuario);

                int rowsInserted = ventaDAO.INSERT();

                if (rowsInserted > 0) {
                    Venta_DetalleDAO ventaDetalle = new Venta_DetalleDAO();
                    ventaDetalle.setId_venta(ventaDAO.getId_venta());
                    ventaDetalle.setMonto(costo_cancion);
                    ventaDetalle.setFecha(new java.util.Date());

                    ventaDetalle.INSERT();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

