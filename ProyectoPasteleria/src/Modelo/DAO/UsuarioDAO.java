/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;


import Modelo.Clases.Sesion;
import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author ingri
 */
public class UsuarioDAO {
    public boolean validarUsuario(String nombre, String contrasena) {

        try {

            Connection con = ConexionBD.getConexionBD();

            String sql = "SELECT * FROM empleado WHERE nombre=? AND contrasena=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Sesion.idEmpleado = rs.getInt("idEmpleado");

                Sesion.idDepartamento = rs.getInt("idDepartamento");

                return true;

            }

            return false;

        } catch (Exception e) {

            System.out.println("Error: " + e);

            return false;

        }

    }
}
