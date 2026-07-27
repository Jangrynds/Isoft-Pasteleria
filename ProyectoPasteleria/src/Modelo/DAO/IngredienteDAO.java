package Modelo.DAO;

import Modelo.Clases.Ingrediente;
import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;

/**
 * DAO refactorizado con Inyección de Dependencias para pruebas unitarias.
 */
public class IngredienteDAO {
    
    private final Connection conexionInyectada;

    // Constructor por defecto usado en producción
    public IngredienteDAO() {
        this.conexionInyectada = null;
    }

    // Constructor para inyección de dependencias (Pruebas Unitarias)
    public IngredienteDAO(Connection conexionInyectada) {
        this.conexionInyectada = conexionInyectada;
    }

    // Obtiene la conexión inyectada si existe; de lo contrario, la conexión estándar de BD
    private Connection getConexion() {
        if (this.conexionInyectada != null) {
            return this.conexionInyectada;
        }
        return ConexionBD.getConexionBD();
    }
    
    public void mostrarIngredientes(JTable tabla){
        Connection con = getConexion();
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID Ingrediente");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Unidad de Medida");
        modelo.addColumn("Precio Unitario");
        modelo.addColumn("Precio Total");

        tabla.setModel(modelo);

        String sql = "SELECT i.idIngrediente, "
            + "i.nombreIngrediente, "
            + "i.cantidad, "
            + "i.medida, "
            + "i.precioUnitario "
            + "FROM ingredientes i ";

        String datos[] = new String[6];

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                double cantidad = rs.getDouble("cantidad");
                double precioUnitario = rs.getDouble("precioUnitario");
                double precioTotal = cantidad * precioUnitario;

                datos[0] = rs.getString("idIngrediente");
                datos[1] = rs.getString("nombreIngrediente");
                datos[2] = String.valueOf(cantidad);
                datos[3] = rs.getString("medida");
                datos[4] = String.valueOf(precioUnitario);
                datos[5] = String.valueOf(precioTotal);

                modelo.addRow(datos);
            }
            tabla.setModel(modelo);
        } catch(Exception e){
            System.out.println("Error: " + e);
        }
    }
    
    public boolean guardarIngrediente(Ingrediente ing){
        try {
            Connection con = getConexion();
            String sql = "INSERT INTO ingredientes(nombreIngrediente, cantidad, medida, precioUnitario) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, ing.getNombreIngrediente());
            ps.setInt(2, ing.getCantidad());
            ps.setString(3, ing.getMedida());
            ps.setDouble(4, ing.getPrecioUnitario());

            ps.execute();
            return true;
        } catch(Exception e){
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    public boolean editarIngrediente(Ingrediente ing){
        try {
            Connection con = getConexion();
            String sql = "UPDATE ingredientes SET nombreIngrediente=?, cantidad=?, medida=?, precioUnitario=? WHERE idIngrediente=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, ing.getNombreIngrediente());
            ps.setInt(2, ing.getCantidad());
            ps.setString(3, ing.getMedida());
            ps.setDouble(4, ing.getPrecioUnitario());
            ps.setInt(5, ing.getIdIngrediente());

            ps.executeUpdate();
            return true;
        } catch(Exception e){
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    public boolean eliminarIngrediente(int idIngrediente){
        try {
            Connection con = getConexion();
            String sql = "DELETE FROM ingredientes WHERE idIngrediente=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idIngrediente);
            ps.executeUpdate();

            return true;
        } catch(Exception e){
            System.out.println("Error: " + e);
            return false;
        }
    }
}
