package org.example.tapesp2024.models;

import java.sql.Statement;
import java.util.Date;

import static org.example.tapesp2024.models.Conexion.connection;

public class Venta_DetalleDAO {
    private int id_venta_detalle;
    private int id_venta;
    private double monto;
    private Date fecha;

    public int getId_venta_detalle() {
        return id_venta_detalle;
    }

    public void setId_venta_detalle(int id_venta_detalle) {
        this.id_venta_detalle = id_venta_detalle;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int INSERT() {
        int row_count = 0;
        String query = "INSERT INTO venta_detalle(id_venta, monto, fecha) VALUES ('" + this.id_venta + "','" + this.monto + "','" + new java.sql.Date(this.fecha.getTime()) + "')";
        try {
            Statement state = connection.createStatement();
            row_count = state.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row_count;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha = fecha_compra;
    }

}
