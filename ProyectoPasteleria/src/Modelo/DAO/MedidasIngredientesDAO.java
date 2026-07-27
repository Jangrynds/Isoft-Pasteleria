package Modelo.DAO;

import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;

/**
 * @author ingri
 */
public class MedidasIngredientesDAO {

    private Connection conexionInyectada;

    // Constructor por defecto para producción
    public MedidasIngredientesDAO() {
    }

    // Constructor para inyección de dependencias (Pruebas Unitarias)
    public MedidasIngredientesDAO(Connection conexionInyectada) {
        this.conexionInyectada = conexionInyectada;
    }

    // Método auxiliar para obtener la conexión adecuada
    private Connection getConexion() throws Exception {
        if (this.conexionInyectada != null) {
            return this.conexionInyectada;
        }
        return ConexionBD.getConexionBD();
    }

    public void mostrarMedidasIngredientes(JComboBox JcomboMedidasIngredientes) {
        try {
            Connection con = getConexion();
            String sql = "SELECT * FROM medidasIngredientes ORDER BY idMedida";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                JcomboMedidasIngredientes.addItem(rs.getString("nombreMedida"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}