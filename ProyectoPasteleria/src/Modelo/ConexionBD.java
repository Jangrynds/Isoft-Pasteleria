/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ingri
 */
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/pasteleria";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    public static Connection getConexionBD(){
        Connection con = null;
        
        try{
          con = DriverManager.getConnection(URL, USER, PASSWORD);
          System.out.println("Conexion exitosa");
        }catch(SQLException e){
            System.out.println("Error de conexión: " + e.getMessage());
        }
        
        return con; 
    };
}
