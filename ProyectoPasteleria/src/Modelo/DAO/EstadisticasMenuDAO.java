/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ingri
 */
public class EstadisticasMenuDAO {
    
    public int contarEmpleados(){

        int total = 0;

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "SELECT COUNT(*) total "
                  + "FROM empleado";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                total =
                        rs.getInt("total");

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return total;

    }
    
    public int contarIngredientes(){

        int total = 0;

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "SELECT COUNT(*) total "
                  + "FROM ingredientes";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                total =
                        rs.getInt("total");

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return total;

    }
    
    public int contarPedidos(){

        int total = 0;

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "SELECT COUNT(*) total "
                  + "FROM pedido";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                total =
                        rs.getInt("total");

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return total;

    }
    
    public int contarProductos(){

        int total = 0;

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "SELECT COUNT(*) total "
                  + "FROM producto";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                total =
                        rs.getInt("total");

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return total;

    }
    
    public int contarTarjetas(){

        int total = 0;

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "SELECT COUNT(*) total "
                  + "FROM tarjetaTiempo";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                total =
                        rs.getInt("total");

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return total;

    }
    
}
