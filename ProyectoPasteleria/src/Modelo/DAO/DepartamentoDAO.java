/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import Modelo.ConexionBD;
/**
 *
 * @author ingri
 */
public class DepartamentoDAO {
    
    // Variable para la conexión (real o simulada)
    private Connection conexion;

    // Constructor por defecto para el sistema en producción
    public DepartamentoDAO() {
        this.conexion = ConexionBD.getConexionBD();
    }

    // Constructor para inyección de dependencias en pruebas unitarias
    public DepartamentoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public void mostrarDepartamentos(JComboBox cmbDepartamento){

        try{
            Connection con = (this.conexion != null) ? this.conexion : ConexionBD.getConexionBD();

            String sql = "SELECT * FROM departamento ORDER BY idDepartamento";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                cmbDepartamento.addItem(rs.getString("nombreDepartamento"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
}