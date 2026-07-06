/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;

/**
 *
 * @author ingri
 */
public class TamanoProductoDAO {
    
    public void mostrarTamanoProducto(JComboBox JcomboTamanoProductos){

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql = "SELECT * FROM tamanoProducto ORDER BY idtamanoProducto";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                JcomboTamanoProductos.addItem(rs.getString("nombretamanoP"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
}
