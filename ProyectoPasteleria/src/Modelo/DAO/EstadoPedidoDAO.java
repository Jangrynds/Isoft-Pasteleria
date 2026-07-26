package Modelo.DAO;

import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;

/**
 * Clase DAO con soporte para Inyección de Dependencias.
 */
public class EstadoPedidoDAO {

    private Connection conexion;

    // 1. Constructor para PRODUCCIÓN
    public EstadoPedidoDAO() {
    }

    // 2. Constructor para PRUEBAS UNITARIAS (el que están buscando las pruebas)
    public EstadoPedidoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método auxiliar para decidir qué conexión usar
    private Connection getConexion() throws Exception {
        if (this.conexion != null) {
            return this.conexion;
        }
        return ConexionBD.getConexionBD();
    }
    
    public void mostrarEstadoPedido(JComboBox cmbEstadoPedido){
        try {
            Connection con = getConexion();
            String sql = "SELECT * FROM estadopedido ORDER BY idEstadoPedido";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                cmbEstadoPedido.addItem(rs.getString("nombreEstadoPedido"));
            }

        } catch(Exception e) {
            System.out.println("Error: " + e);
        }
    }
}