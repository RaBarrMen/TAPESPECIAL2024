package org.example.tapesp2024.models;

import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;

import static org.example.tapesp2024.models.Conexion.connection;

public class VentaDAO {
    private int id_venta;
    private int id_cancion;
    private int id_usuario;

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_cancion() {
        return id_cancion;
    }

    public void setId_cancion(int id_cancion) {
        this.id_cancion = id_cancion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int INSERT() {
        int row_count = 0;
        String query = "INSERT INTO venta(id_cancion, id_usuario) VALUES ('" + this.id_cancion + "','" + this.id_usuario + "')";
        try {
            Statement state = connection.createStatement();
            row_count = state.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);  // Obtener el id generado

            // Obtener el id_venta generado
            if (row_count > 0) {
                ResultSet rs = state.getGeneratedKeys();
                if (rs.next()) {
                    this.id_venta = rs.getInt(1);  // Asignar el id_venta generado
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row_count;
    }

}
