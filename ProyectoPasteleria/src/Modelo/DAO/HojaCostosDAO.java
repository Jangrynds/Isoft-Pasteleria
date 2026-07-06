/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Clases.Pedido;
import Modelo.ConexionBD;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JComboBox;

/**
 *
 * @author ingri
 */
public class HojaCostosDAO {
    
    public Pedido obtenerPedido(int idPedido){

    Pedido pedido = new Pedido();

    try{

        Connection con = ConexionBD.getConexionBD();

        String sql =
            "SELECT * "
          + "FROM pedido "
          + "WHERE idPedido = ?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1,idPedido);

        ResultSet rs = ps.executeQuery();

            if(rs.next()){

                pedido.setIdPedido( rs.getInt("idPedido"));

                pedido.setCliente( rs.getString("cliente"));

                pedido.setFechaInicio( 
                        rs.getDate("fechaInicio"));

                pedido.setFechaEntrega( rs.getDate("fechaEntrega"));

                pedido.setObservaciones( rs.getString("observaciones"));
                
                pedido.setCantidad(rs.getInt("cantidad"));

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return pedido;

    }
    
    public void llenarNoPedido(JComboBox combo){

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql =
                    "SELECT idPedido, cliente "
                  + "FROM pedido";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            combo.removeAllItems();

            while(rs.next()){

                combo.addItem(rs.getInt("idPedido")
                        + " - "
                        + rs.getString("cliente"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
    
    public double mostrarMaterialesPorPedido(JTable tabla,int idPedido){

        double subtotal = 0;

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "SELECT idRequisicion, "
              + "fecha, "
              + "total "
              + "FROM requisicion "
              + "WHERE idPedido = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1,idPedido);

            ResultSet rs =
                    ps.executeQuery();

            DefaultTableModel modelo =
                    (DefaultTableModel)
                    tabla.getModel();

            modelo.setRowCount(0);

            while(rs.next()){

                double total =
                        rs.getDouble(
                                "total");

                subtotal += total;

                modelo.addRow(new Object[]{

                    rs.getDate("fecha"),
                    rs.getInt("idRequisicion"),
                    total

                });

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return subtotal;

    }
    
    public double mostrarManoObraPorPedido(JTable tabla, int idPedido){

        double subtotal = 0;

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql =
                "SELECT t.idTarjeta, "
              + "t.fecha, "
              + "t.totalHoras, "
              + "t.totalCosto, "
              + "e.salarioHora "
              + "FROM tarjetaTiempo t "
              + "INNER JOIN empleado e "
              + "ON t.idEmpleado = e.idEmpleado "
              + "WHERE t.idPedido = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,idPedido);

            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo =(DefaultTableModel)tabla.getModel();

            modelo.setRowCount(0);

            while(rs.next()){

                double costo = rs.getDouble("totalCosto");

                subtotal += costo;

                modelo.addRow(new Object[]{

                    rs.getDate("fecha"),
                    rs.getInt("idTarjeta"),
                    rs.getDouble("totalHoras"),
                    rs.getDouble("salarioHora"),
                    costo

                });

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return subtotal;

    }
    
}
