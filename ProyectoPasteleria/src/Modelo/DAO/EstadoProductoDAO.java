/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JComboBox;

/**
 *
 * @author ingri
 */
public class EstadoProductoDAO {
    public void mostrarEstadoProducto(JComboBox JcomboEstadoProducto){

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql = "SELECT * FROM estadoproducto ORDER BY idEstadoProducto";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                JcomboEstadoProducto.addItem(rs.getString("nombreEstado"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
    
    public int obtenerIdEstado(String nombreEstado){

    int idEstado = 0;

    try{

        Connection con = ConexionBD.getConexionBD();

        String sql =
        "SELECT idEstadoProducto "
        + "FROM estadoproducto "
        + "WHERE nombreEstado = ?";

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setString(1, nombreEstado);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){

            idEstado =
            rs.getInt("idEstadoProducto");

        }

    }catch(Exception e){

        System.out.println("Error: " + e);

    }

    return idEstado;

    }
}
