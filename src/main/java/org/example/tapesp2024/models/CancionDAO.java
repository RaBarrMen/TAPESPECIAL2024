package org.example.tapesp2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static org.example.tapesp2024.models.Conexion.connection;

public class CancionDAO {
    private int id_cancion;
    private String cancion;
    private float costo_cancion;
    private int id_genero;
    private byte[] imagen_cancion;

    private int id_album;


    public CancionDAO(int id_cancion, String cancion, float costo_cancion, int id_genero, byte[] imagen_cancion) {
        this.id_cancion = id_cancion;
        this.cancion = cancion;
        this.costo_cancion = costo_cancion;
        this.id_genero = id_genero;
        this.imagen_cancion = imagen_cancion;
    }

    public CancionDAO(int id_cancion, String cancion, float costo_cancion, int id_genero, byte[] imagen_cancion, int id_album) {
        this.id_cancion = id_cancion;
        this.cancion = cancion;
        this.costo_cancion = costo_cancion;
        this.id_genero = id_genero;
        this.imagen_cancion = imagen_cancion;
        this.id_album = id_album;
    }


    public int getId_album() {
        return id_album;
    }

    public void setId_album(int id_album) {
        this.id_album = id_album;
    }

    public int getId_cancion() {
        return id_cancion;
    }

    public void setId_cancion(int id_cancion) {
        this.id_cancion = id_cancion;
    }

    public String getCancion() {
        return cancion;
    }

    public void setCancion(String cancion) {
        this.cancion = cancion;
    }

    public float getCosto_cancion() {
        return costo_cancion;
    }

    public void setCosto_cancion(float costo_cancion) {
        this.costo_cancion = costo_cancion;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public byte[] getImagen_cancion() {
        return imagen_cancion;
    }

    public void setImagen_cancion(byte[] imagen_cancion) {
        this.imagen_cancion = imagen_cancion;
    }

    public int INSERT(Connection connection) {
        int row_count;
        String query = "INSERT INTO cancion(cancion, costo_cancion, id_genero, imagen_cancion) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Asignar los valores de los parámetros
            pstmt.setString(1, this.cancion); // Asignar el valor de "cancion"
            pstmt.setFloat(2, this.costo_cancion); // Asignar el valor de "costo_cancion"
            pstmt.setInt(3, this.id_genero); // Asignar el valor de "id_genero"
            pstmt.setBytes(4, this.imagen_cancion); // Asignar el valor de "imagen_cancion" como byte[]

            // Ejecutar la consulta y obtener el número de filas afectadas
            row_count = pstmt.executeUpdate();
        } catch (Exception e) {
            row_count = 0;
            e.printStackTrace();
        }

        return row_count;
    }

    public ObservableList<CancionDAO> SELECTALL() {
        ObservableList<CancionDAO> list_cancion = FXCollections.observableArrayList();
        String query = "SELECT * FROM cancion";

        try (Statement state = connection.createStatement(); ResultSet res = state.executeQuery(query)) {
            while (res.next()) {
                CancionDAO cancionDao = new CancionDAO(
                        res.getInt("id_cancion"),
                        res.getString("cancion"),
                        res.getFloat("costo_cancion"),
                        res.getInt("id_genero"),
                        res.getBytes("imagen_cancion")
                );
                list_cancion.add(cancionDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list_cancion;
    }

    public void INSERT_ALBUM() {
        String query = "INSERT INTO cancion_album(id_cancion, id_album) VALUES(?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Asignar los valores de los parámetros
            pstmt.setInt(1, this.id_cancion); // Asignar el valor de "id_cancion"
            pstmt.setInt(2, this.id_album);  // Asignar el valor de "id_album"

            // Ejecutar la consulta
            System.out.println(this.id_cancion);
            int rowsAffected = pstmt.executeUpdate();

            // Verificar si la inserción fue exitosa
            if (rowsAffected > 0) {
                System.out.println("Inserción exitosa: " + rowsAffected + " fila(s) afectada(s).");
            } else {
                System.out.println("No se insertaron filas.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
