package org.example.tapesp2024.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    static private String DB = "spotify1";
    static private String USER = "spotify";
    static private String PWD = "12345";

    static public Connection connection;
    public static void crearConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:53306/"+
                            DB+"?allowPublicKeyRetrieval=true&useSSL=false",
                    USER,PWD);
            System.out.println("Conectao");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
