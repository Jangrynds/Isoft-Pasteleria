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
public class MedidasIngredientesDAO {
    
    public void mostrarMedidasIngredientes(JComboBox JcomboMedidasIngredientes){

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql = "SELECT * FROM medidasIngredientes ORDER BY idMedida";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                JcomboMedidasIngredientes.addItem(rs.getString("nombreMedida"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
}
