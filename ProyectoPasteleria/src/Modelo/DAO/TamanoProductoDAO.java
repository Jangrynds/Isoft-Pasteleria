package Modelo.DAO;

import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.function.Supplier;
import javax.swing.JComboBox;

/**
 *
 * @author ingri
 */
public class TamanoProductoDAO {

    private final Supplier<Connection> connectionSupplier;

    public TamanoProductoDAO() {
        this(ConexionBD::getConexionBD);
    }

    public TamanoProductoDAO(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    private Connection getConnection() {
        return connectionSupplier.get();
    }

    public void mostrarTamanoProducto(JComboBox JcomboTamanoProductos) {

        try {

            Connection con = getConnection();

            String sql = "SELECT * FROM tamanoProducto ORDER BY idtamanoProducto";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                JcomboTamanoProductos.addItem(rs.getString("nombretamanoP"));

            }

        } catch (Exception e) {

            System.out.println("Error: " + e);

        }

    }
}
