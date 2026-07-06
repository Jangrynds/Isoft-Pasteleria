/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Clases.Pedido;
import Modelo.ConexionBD;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ingri
 */
public class ProduccionDAO {
    
    public void mostrarPedidos(JTable tabla){
        
        Connection con = ConexionBD.getConexionBD();
        
        DefaultTableModel modelo =
        new DefaultTableModel();

        modelo.addColumn("No. Pedido");
        modelo.addColumn("Cliente");
        modelo.addColumn("Fecha Inicio");
        modelo.addColumn("Fecha Entrega");
        modelo.addColumn("Estado");
        modelo.addColumn("Observaciones");

        tabla.setModel(modelo);
        
        String sql = "SELECT p.idPedido, "
        + "p.cliente, "
        + "p.fechaInicio, "
        + "p.fechaEntrega, "
        + "p.observaciones, "
        + "e.nombreEstadoPedido "
        + "FROM pedido p "
        + "INNER JOIN estadoPedido e "
        + "ON p.idEstadoPedido = e.idEstadoPedido";

    String datos[] = new String[6];

        try{

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){

                datos[0] = rs.getString("idPedido");
                datos[1] = rs.getString("cliente");
                datos[2] = rs.getString("fechaInicio");
                datos[3] = rs.getString("fechaEntrega");
                datos[4] = rs.getString("nombreEstadoPedido");
                datos[5] = rs.getString("observaciones");

                modelo.addRow(datos);

            }

            tabla.setModel(modelo);

        }catch(Exception e){

            System.out.println("Error: " + e);

        }
    }
    
    public int guardarPedido(Pedido ped){

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql = "INSERT INTO pedido "
                    + "(cliente, fechaInicio, fechaEntrega, "
                    + "observaciones, idEstadoPedido) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps =
                con.prepareStatement(
                sql,
                PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, ped.getCliente());

            ps.setDate(2, new java.sql.Date(
                ped.getFechaInicio().getTime()));

            ps.setDate(3, new java.sql.Date(
                ped.getFechaEntrega().getTime()));

            ps.setString(4, ped.getObservaciones());

            ps.setInt(5, ped.getIdEstadoPedido());

            ps.executeUpdate();

            ResultSet rs =
                    ps.getGeneratedKeys();

            if(rs.next()){

                return rs.getInt(1);

            }
            
            return -1;
            
        }catch(Exception e){

            System.out.println("Error: " + e);

             return -1;

        }
    }
    
    public void mostrarDetallePedido(JTable tabla, int idPedido){
        
        Connection con = ConexionBD.getConexionBD();
        DefaultTableModel modelo =
        new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Subtotal");

        tabla.setModel(modelo);
        
        String sql = "SELECT p.idProducto, "
        + "p.nombreProducto, "
        + "dp.cantidad, "
        + "p.precioVenta "
        + "FROM detallePedido dp "
        + "INNER JOIN producto p "
        + "ON dp.idProducto = p.idProducto "
        + "WHERE dp.idPedido = ?";
        
        String datos[] = new String[5];

        try{

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idPedido);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                datos[0] = rs.getString("idProducto");
                datos[1] = rs.getString("nombreProducto");
                datos[2] = rs.getString("cantidad");
                datos[3] = rs.getString("precioVenta");

                double cantidad =
                        rs.getDouble("cantidad");

                double precio =
                        rs.getDouble("precioVenta");

                double subtotal =
                        cantidad * precio;

                datos[4] = String.valueOf(subtotal);

                modelo.addRow(datos);

            }

            tabla.setModel(modelo);

        }catch(Exception e){

            System.out.println("Error: " + e);

        }
    }
    
    public boolean guardarDetallePedido(int idPedido, int idProducto, int cantidad){
        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "INSERT INTO detallePedido "
                    + "(idPedido, idProducto, cantidad) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idPedido);

            ps.setInt(2, idProducto);

            ps.setInt(3, cantidad);

            ps.executeUpdate();

            return true;

        }catch(Exception e){

            System.out.println("Error: " + e);

            return false;

        }
    }
    
    public boolean eliminarDetallePedido(int idPedido){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "DELETE FROM detallePedido "
                    + "WHERE idPedido=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idPedido);

            ps.executeUpdate();

            return true;

        }catch(Exception e){

            System.out.println("Error: " + e);

            return false;

        }

    }
    
    public boolean eliminarPedido(int idPedido){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "DELETE FROM pedido "
                    + "WHERE idPedido=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idPedido);

            ps.executeUpdate();

            return true;

        }catch(Exception e){

            System.out.println("Error: " + e);

            return false;

        }

    }
    
    public boolean editarPedido(Pedido ped){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "UPDATE pedido SET "
                    + "cliente=?, "
                    + "fechaEntrega=?, "
                    + "observaciones=?, "
                    + "idEstadoPedido=? "
                    + "WHERE idPedido=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, ped.getCliente());

            ps.setDate(2,
                    new java.sql.Date(
                            ped.getFechaEntrega().getTime()));

            ps.setString(3, ped.getObservaciones());

            ps.setInt(4, ped.getIdEstadoPedido());

            ps.setInt(5, ped.getIdPedido());

            ps.executeUpdate();

            return true;

        }catch(Exception e){

            System.out.println("Error: " + e);

            return false;

        }

    }
    
        public void llenarComboPedidos(JComboBox combo){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "SELECT idPedido, cliente "
                  + "FROM pedido";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            combo.removeAllItems();

            while(rs.next()){

                combo.addItem(
                    rs.getInt("idPedido")
                    + " - "
                    + rs.getString("cliente"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
}
