/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import Modelo.ConexionBD;
import javax.swing.JComboBox;
import java.sql.PreparedStatement;
/**
 *
 * @author ingri
 */
public class CategoriaProductoDAO {
    
    public void mostrarMedidasIngredientes(JComboBox JcomboMedidasIngredientes){

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql = "SELECT * FROM categoriaproducto ORDER BY idCategoria";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                JcomboMedidasIngredientes.addItem(rs.getString("nombreCategoria"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
    
    public int obtenerIdCategoria(String nombreCategoria){

    int idCategoria = 0;

    try{

        Connection con = ConexionBD.getConexionBD();

        String sql =
        "SELECT idCategoria FROM categoriaproducto "
        + "WHERE nombreCategoria = ?";

        PreparedStatement ps =
                con.prepareStatement(sql);

        ps.setString(1, nombreCategoria);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){

            idCategoria =
                    rs.getInt("idCategoria");

        }

    }catch(Exception e){

        System.out.println("Error: " + e);

    }

    return idCategoria;

    }
}
