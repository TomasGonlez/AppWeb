package com.example.appweb.UTIL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static ConexionDB instancia; //instancia unica
    private Connection conexion;

    private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String user = "TEST";
    private final String password = "test";

    //Constructor privado que solo se utiliza una vez en el ciclo de vida del SW
    private ConexionDB() throws SQLException {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.conexion = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e){
            throw new SQLException(e.getMessage());
        }
    }

    //Metodo publico para obtener instancia unica
    public static ConexionDB getInstance() throws SQLException {
        if (instancia == null || (instancia.conexion.isClosed())) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}
