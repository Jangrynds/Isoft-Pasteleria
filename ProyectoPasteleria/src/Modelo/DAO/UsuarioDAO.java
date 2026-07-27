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

    private final Connection conexionInyectada;

    /**
     * Constructor por defecto utilizado en producción.
     * Mantiene la compatibilidad con la arquitectura original.
     */
    public UsuarioDAO() {
        this.conexionInyectada = null;
    }

    /**
     * Constructor para Inyección de Dependencias (uso en Pruebas Unitarias/Mocks/Stubs).
     * @param conexion Conexión simulada a la base de datos.
     */
    public UsuarioDAO(Connection conexion) {
        this.conexionInyectada = conexion;
    }

    /**
     * Obtiene la conexión a la base de datos (inyectada o por defecto).
     */
    private Connection getConexion() throws Exception {
        if (this.conexionInyectada != null) {
            return this.conexionInyectada;
        }
        return ConexionBD.getConexionBD();
    }

    public boolean validarUsuario(String nombre, String contrasena) {
        try {
            Connection con = getConexion();
            String sql = "SELECT * FROM empleado WHERE nombre=? AND contrasena=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
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