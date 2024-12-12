package org.example.tapesp2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Arrays;

import static org.example.tapesp2024.models.Conexion.connection;

public class CancionDAO {
    private int id_cancion;
    private String cancion;
    private float costo_cancion;
    private int id_genero;
    private byte[] imagen_cancion;
    private String nombreGenero;
    private int id_album;

    public CancionDAO() {

    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

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

    public int INSERT() {
        int row_count = 0;
        String query = "INSERT INTO cancion(cancion, costo_cancion, id_genero, imagen_cancion) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.cancion);
            pstmt.setFloat(2, this.costo_cancion);
            pstmt.setInt(3, this.id_genero);
            pstmt.setBytes(4, this.imagen_cancion); // Aquí se establece la imagen como bytes

            row_count = pstmt.executeUpdate();

            // Obtener el ID generado automáticamente
            if (row_count > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id_cancion = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row_count;
    }


    public ObservableList<CancionDAO> SELECTALL() {
        ObservableList<CancionDAO> list_cancion = FXCollections.observableArrayList();
        String query = "SELECT c.id_cancion, c.cancion, c.costo_cancion, c.id_genero, c.imagen_cancion, g.genero " +
                                "FROM cancion c " +
                                "JOIN genero g ON c.id_genero = g.id_genero";

        try (Statement state = connection.createStatement(); ResultSet res = state.executeQuery(query)) {
            while (res.next()) {
                CancionDAO cancionDao = new CancionDAO(
                        res.getInt("id_cancion"),
                        res.getString("cancion"),
                        res.getFloat("costo_cancion"),
                        res.getInt("id_genero"),
                        res.getBytes("imagen_cancion")
                );
                cancionDao.setNombreGenero(res.getString("genero"));
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean update(CancionDAO cancion) {
        String query = "UPDATE cancion SET cancion = ?, costo_cancion = ?, id_genero = ?, imagen_cancion = ? WHERE id_cancion = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, this.cancion);
            pstmt.setFloat(2, this.costo_cancion);
            pstmt.setInt(3, this.id_genero);
            pstmt.setBytes(4, this.imagen_cancion);
            pstmt.setInt(5, this.id_cancion);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id_cancion) {
        String query = "DELETE FROM cancion WHERE id_cancion = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id_cancion);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<CancionDAO> SELECTBYALBUM(int id_album) {
        ObservableList<CancionDAO> canciones = FXCollections.observableArrayList();
        String query = "SELECT c.id_cancion, c.cancion, c.costo_cancion, c.id_genero, c.imagen_cancion, g.genero " +
                "FROM cancion c " +
                "JOIN genero g ON c.id_genero = g.id_genero " +
                "JOIN cancion_album ca ON c.id_cancion = ca.id_cancion " +
                "WHERE ca.id_album = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id_album);
            try (ResultSet res = pstmt.executeQuery()) {
                while (res.next()) {
                    CancionDAO cancion = new CancionDAO(
                            res.getInt("id_cancion"),
                            res.getString("cancion"),
                            res.getFloat("costo_cancion"),
                            res.getInt("id_genero"),
                            res.getBytes("imagen_cancion")
                    );
                    cancion.setNombreGenero(res.getString("genero")); // Asigna el nombre del género
                    canciones.add(cancion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return canciones;
    }


}
