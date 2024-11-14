package org.example.tapesp2024.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.example.tapesp2024.models.Conexion.connection;

public class CancionDAO {
    private int id_cancion;
    private String cancion;
    private float costo_cancion;
    private int id_genero;
    private Blob imagen_cancion;

    public CancionDAO(Blob imagen_cancion, int id_genero, float costo_cancion, String cancion, int id_cancion) {
        this.imagen_cancion = imagen_cancion;
        this.id_genero = id_genero;
        this.costo_cancion = costo_cancion;
        this.cancion = cancion;
        this.id_cancion = id_cancion;
    }

    public CancionDAO() {
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

    public Blob getImagen_cancion() {
        return imagen_cancion;
    }

    public void setImagen_cancion(Blob imagen_cancion) {
        this.imagen_cancion = imagen_cancion;
    }

    public int INSERT(){
        int row_count;
        String query = "INSERT INTO cancion(cancion, costo_cancion, id_genero, imagen_cancion) VALUES('"
                + this.cancion + "','" + this.costo_cancion + "','" + this.id_genero + "','" + this.imagen_cancion + "')";
        try {
            Statement state = connection.createStatement();
            row_count = state.executeUpdate(query);
        } catch (Exception e) {
            row_count = 0;
            e.printStackTrace();
        }
        return row_count;
    }

    public ObservableList<CancionDAO> SELECTALL(){
        CancionDAO cancionDao;
        String query = "SELECT * FROM cancion";
        ObservableList<CancionDAO> list_cancion = FXCollections.observableArrayList();
        try {
            Statement state = connection.createStatement();
            ResultSet res = state.executeQuery(query);
            while(res.next()){
                cancionDao = new CancionDAO();
                cancionDao.id_cancion = res.getInt(1);
                cancionDao.cancion = res.getString(2);
                cancionDao.costo_cancion = res.getFloat(3);
                cancionDao.imagen_cancion = res.getBlob(4);
                list_cancion.add(cancionDao);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list_cancion;
    }
}
