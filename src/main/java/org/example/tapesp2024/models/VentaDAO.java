package org.example.tapesp2024.models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<Venta_DetalleDAO> obtenerCancionesPorUsuario(int id_usuario) {
        List<Venta_DetalleDAO> ventasDetalles = new ArrayList<>();
        String query = "SELECT c.id_cancion, c.cancion, c.costo_cancion, c.id_genero, vd.fecha " +
                "FROM venta v " +
                "JOIN venta_detalle vd ON v.id_venta = vd.id_venta " +
                "JOIN cancion c ON v.id_cancion = c.id_cancion " +
                "WHERE v.id_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id_usuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Venta_DetalleDAO ventaDetalle = new Venta_DetalleDAO();
                ventaDetalle.setId_venta(rs.getInt("id_cancion"));
                ventaDetalle.setMonto(rs.getFloat("costo_cancion"));
                ventaDetalle.setFecha(rs.getDate("fecha"));

                ventasDetalles.add(ventaDetalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ventasDetalles;
    }


}

