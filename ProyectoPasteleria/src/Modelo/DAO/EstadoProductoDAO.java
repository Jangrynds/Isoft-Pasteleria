package Modelo.DAO;

import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JComboBox;

/**
 * @author ingri
 * Refactorizado para soportar inyección de dependencias en pruebas unitarias.
 */
public class EstadoProductoDAO {
    
    private final Connection conexionInyectada;

    /**
     * Constructor por defecto para Producción.
     * Mantiene la compatibilidad 100% con el código legado.
     */
    public EstadoProductoDAO() {
        this.conexionInyectada = null;
    }

    /**
     * Constructor secundario para Pruebas Unitarias / Entornos de Test.
     * Permite inyectar un Mock de Connection o DB simulada.
     * 
     * @param conexion Connection mockeada o en memoria
     */
    public EstadoProductoDAO(Connection conexion) {
        this.conexionInyectada = conexion;
    }

    /**
     * Método auxiliar privado para resolver qué conexión utilizar.
     */
    private Connection obtenerConexion() throws Exception {
        if (this.conexionInyectada != null) {
            return this.conexionInyectada;
        }
        return ConexionBD.getConexionBD();
    }

    public void mostrarEstadoProducto(JComboBox JcomboEstadoProducto) {
        try {
            Connection con = obtenerConexion();
            String sql = "SELECT * FROM estadoproducto ORDER BY idEstadoProducto";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                JcomboEstadoProducto.addItem(rs.getString("nombreEstado"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public int obtenerIdEstado(String nombreEstado) {
        int idEstado = 0;
        try {
            Connection con = obtenerConexion();
            String sql = "SELECT idEstadoProducto FROM estadoproducto WHERE nombreEstado = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombreEstado);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idEstado = rs.getInt("idEstadoProducto");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return idEstado;
    }
}