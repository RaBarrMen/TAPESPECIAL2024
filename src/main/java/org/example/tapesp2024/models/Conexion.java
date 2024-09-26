package org.example.tapesp2024.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
    static private String DB = "spotify";
    static private String USER = "topicos";
    static private String PWD = "123";

    static public Connection connection;
    public static void crearConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+
                            DB+"?allowPublicKeyRetrieval=true&useSSL=false",
                    USER,PWD);
            System.out.println("Conectao");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
