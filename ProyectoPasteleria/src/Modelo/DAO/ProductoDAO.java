/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Clases.Producto;
import Modelo.ConexionBD;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ingri
 */
public class ProductoDAO {
    
    public void mostrarProductos(JTable tabla){

    Connection con = ConexionBD.getConexionBD();

    DefaultTableModel modelo = new DefaultTableModel();

    modelo.addColumn("ID Producto");
    modelo.addColumn("Nombre del producto");
    modelo.addColumn("Categoría");
    modelo.addColumn("Tamaño");
    modelo.addColumn("Descripción");
    modelo.addColumn("Precio de venta");
    modelo.addColumn("Estado");

    tabla.setModel(modelo);

    String sql = "SELECT p.idProducto, "
        + "p.nombreProducto, "
        + "c.nombreCategoria, "
        + "p.tamano, "
        + "p.descripcion, "
        + "p.precioVenta, "
        + "e.nombreEstado "
        + "FROM producto p "
        + "INNER JOIN categoriaproducto c "
        + "ON p.idCategoria = c.idCategoria "
        + "INNER JOIN estadoProducto e "
        + "ON p.idEstadoProducto = e.idEstadoProducto";

    String datos[] = new String[100];

        try{

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                datos[0] = rs.getString("idProducto");
                datos[1] = rs.getString("nombreProducto");
                datos[2] = rs.getString("nombreCategoria");
                datos[3] = rs.getString("tamano");
                datos[4] = rs.getString("descripcion");
                datos[5] = rs.getString("precioVenta");
                datos[6] = rs.getString("nombreEstado");

                modelo.addRow(datos);

            }

            tabla.setModel(modelo);

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }

    public void guardarProducto(Producto prod){
        Connection con = ConexionBD.getConexionBD();

        String sql = "INSERT INTO producto "
            + "(nombreProducto, idCategoria, tamano, descripcion, "
            + "precioVenta, idEstadoProducto) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

        try{
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, prod.getNombreProducto());
        ps.setInt(2, prod.getIdCategoria());
        ps.setString(3, prod.getTamano());
        ps.setString(4, prod.getDescripcion());
        ps.setDouble(5, prod.getPrecioVenta());
        ps.setInt(6, prod.getIdEstadoProducto());

        ps.executeUpdate();

        JOptionPane.showMessageDialog(null,
                "Producto guardado correctamente");

        }catch(Exception e){

        JOptionPane.showMessageDialog(null,
                "Error al guardar: " + e);
        }
    }
    
    public boolean editarProducto(Producto prod){

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql = "UPDATE producto SET nombreProducto=?, idCategoria=?, tamano=?, descripcion=?, precioVenta=?, idEstadoProducto=? WHERE idProducto=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, prod.getNombreProducto());

            ps.setInt(2, prod.getIdCategoria());

            ps.setString(3, prod.getTamano());

            ps.setString(4, prod.getDescripcion());

            ps.setDouble(5, prod.getPrecioVenta());

            ps.setInt(6, prod.getIdEstadoProducto());
            
            ps.setInt(7, prod.getIdProducto());

            ps.executeUpdate();

            return true;

        }catch(Exception e){

            System.out.println("Error: " + e);

            return false;

        }

    }
    
    public boolean eliminarProducto(int idProducto){

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql = "DELETE FROM producto WHERE idProducto=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idProducto);

            ps.executeUpdate();

            return true;

        }catch(Exception e){

            System.out.println("Error: " + e);
            
            JOptionPane.showMessageDialog(null,
                "No se puede eliminar este producto porque está asociado a pedidos registrados.");

            return false;
        }

    }
    
    public void mostrarNombresProductos(JComboBox cmbNombreProductos){
        try{

            Connection con = ConexionBD.getConexionBD();

            String sql = "SELECT * FROM producto ORDER BY idProducto";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                cmbNombreProductos.addItem(rs.getString("nombreProducto"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }
    }
    
    public double obtenerPrecioProducto(String nombreProducto){
        try{

            Connection con = ConexionBD.getConexionBD();

            String sql = "SELECT precioVenta "
                    + "FROM producto "
                    + "WHERE nombreProducto=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, nombreProducto);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                return rs.getDouble("precioVenta");

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

        return 0;
    }
    
    public int obtenerIdProducto(String nombreProducto){

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql = "SELECT idProducto "
                    + "FROM producto "
                    + "WHERE nombreProducto=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, nombreProducto);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                return rs.getInt("idProducto");

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

        return -1;

    }
    
}
