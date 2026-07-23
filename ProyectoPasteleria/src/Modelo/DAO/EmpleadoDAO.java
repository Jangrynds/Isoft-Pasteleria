package Modelo.DAO;

import Modelo.Clases.Empleado;
import Modelo.ConexionBD;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author ingri
 */
public class EmpleadoDAO {
    
    // Variable para almacenar la conexión (real o simulada)
    private Connection conexion;

    // Constructor por defecto para producción
    public EmpleadoDAO() {
        this.conexion = ConexionBD.getConexionBD();
    }

    // Constructor para inyección de dependencias en pruebas unitarias
    public EmpleadoDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public void mostrarEmpleados(JTable tabla){

    Connection con = (this.conexion != null) ? this.conexion : ConexionBD.getConexionBD();

    DefaultTableModel modelo = new DefaultTableModel();

    modelo.addColumn("ID");
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellido Paterno");
    modelo.addColumn("Apellido Materno");
    modelo.addColumn("Teléfono");
    modelo.addColumn("Departamento");
    modelo.addColumn("Salario por Hora");

    tabla.setModel(modelo);

    String sql = "SELECT e.idEmpleado, "
        + "e.nombre, "
        + "e.apellidoP, "
        + "e.apellidoM, "
        + "e.telefono, "
        + "d.nombreDepartamento, "
        + "e.salarioHora "
        + "FROM empleado e "
        + "INNER JOIN departamento d "
        + "ON e.idDepartamento = d.idDepartamento";

    String datos[] = new String[7];

        try{

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                datos[0] = rs.getString("idEmpleado");
                datos[1] = rs.getString("nombre");
                datos[2] = rs.getString("apellidoP");
                datos[3] = rs.getString("apellidoM");
                datos[4] = rs.getString("telefono");
                datos[5] = rs.getString("nombreDepartamento");
                datos[6] = rs.getString("salarioHora");

                modelo.addRow(datos);

            }

            tabla.setModel(modelo);

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
    
    public boolean guardarEmpleado(Empleado emp){
        try{

            Connection con = (this.conexion != null) ? this.conexion : ConexionBD.getConexionBD();

            String sql = "INSERT INTO empleado(nombre, apellidoP, apellidoM, telefono, idDepartamento, salarioHora) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, emp.getNombre());

            ps.setString(2, emp.getApellidoPaterno());

            ps.setString(3, emp.getApellidoMaterno());

            ps.setString(4, emp.getTelefono());

            ps.setInt(5, emp.getIdDepartamento());

            ps.setDouble(6, emp.getSalarioHora());

            ps.execute();

            return true;

        }catch(Exception e){

            System.out.println("Error: " + e);

            return false;

        }
    }
    
    public boolean editarEmpleado(Empleado emp){

        try{

            Connection con = (this.conexion != null) ? this.conexion : ConexionBD.getConexionBD();

            String sql = "UPDATE empleado SET nombre=?, apellidoP=?, apellidoM=?, telefono=?, idDepartamento=?, salarioHora=? WHERE idEmpleado=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, emp.getNombre());

            ps.setString(2, emp.getApellidoPaterno());

            ps.setString(3, emp.getApellidoMaterno());

            ps.setString(4, emp.getTelefono());

            ps.setInt(5, emp.getIdDepartamento());
            
            ps.setDouble(6, emp.getSalarioHora());

            ps.setInt(7, emp.getIdEmpleado());

            ps.executeUpdate();

            return true;

        }catch(Exception e){

            System.out.println("Error: " + e);

            return false;

        }

    }
    
    public boolean eliminarEmpleado(int idEmpleado){
        try{

        Connection con = (this.conexion != null) ? this.conexion : ConexionBD.getConexionBD();

        String sql = "DELETE FROM empleado WHERE idEmpleado=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idEmpleado);

        ps.executeUpdate();

        return true;

        }catch(Exception e){

            System.out.println("Error: " + e);

            return false;

        }
    }
}