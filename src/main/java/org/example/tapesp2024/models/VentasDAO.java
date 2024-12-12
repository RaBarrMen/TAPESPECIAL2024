package org.example.tapesp2024.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.example.tapesp2024.models.Conexion.connection;

public class VentasDAO {

    // Método para obtener la cantidad de ventas realizadas en el mes
    public int obtenerCantidadVentasMes() {
        String query = "SELECT COUNT(*) AS cantidad_ventas " +
                "FROM venta_detalle " +
                "WHERE MONTH(fecha) = MONTH(CURDATE()) AND YEAR(fecha) = YEAR(CURDATE());";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cantidad_ventas");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Método para obtener los álbumes con más ventas en el mes
    public Map<String, Integer> obtenerAlbumesMasVendidosMes() {
        String query = "SELECT a.album, COUNT(v.id_venta) AS total_ventas " +
                "FROM album a " +
                "JOIN cancion_album ca ON a.id_album = ca.id_album " +
                "JOIN venta v ON ca.id_cancion = v.id_cancion " +
                "JOIN venta_detalle vd ON v.id_venta = vd.id_venta " +
                "WHERE MONTH(vd.fecha) = MONTH(CURDATE()) AND YEAR(vd.fecha) = YEAR(CURDATE()) " +
                "GROUP BY a.album " +
                "ORDER BY total_ventas DESC limit 5;";
        Map<String, Integer> albumesMasVendidos = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                albumesMasVendidos.put(rs.getString("album"), rs.getInt("total_ventas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albumesMasVendidos;
    }

    // Método para obtener las canciones más vendidas
    public Map<String, Integer> obtenerCancionesMasVendidasMes() {
        String query = "SELECT c.cancion, COUNT(v.id_venta) AS total_ventas " +
                "FROM cancion c " +
                "JOIN venta v ON c.id_cancion = v.id_cancion " +
                "JOIN venta_detalle vd ON v.id_venta = vd.id_venta " +
                "WHERE MONTH(vd.fecha) = MONTH(CURDATE()) AND YEAR(vd.fecha) = YEAR(CURDATE()) " +
                "GROUP BY c.cancion " +
                "ORDER BY total_ventas DESC limit 5;";
        Map<String, Integer> cancionesMasVendidas = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cancionesMasVendidas.put(rs.getString("cancion"), rs.getInt("total_ventas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cancionesMasVendidas;
    }
}
