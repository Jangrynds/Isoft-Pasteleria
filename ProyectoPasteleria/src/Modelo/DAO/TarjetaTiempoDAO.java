/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Clases.TarjetaTiempo;
import Modelo.ConexionBD;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.time.LocalTime;
import java.time.Duration;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ingri
 */
public class TarjetaTiempoDAO {
    
    public void llenarEmpleados(JComboBox combo){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql = "SELECT idEmpleado, nombre, apellidoP "
              + "FROM empleado";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            combo.removeAllItems();

            while(rs.next()){

                combo.addItem(
                        rs.getInt("idEmpleado")
                        + " - "
                        + rs.getString("nombre")
                        + " "
                        + rs.getString("apellidoP"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
    
    public void obtenerDatosEmpleado(int idEmpleado,JTextField txtIdEmpleado,JTextField txtTasaHora){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "SELECT idEmpleado, salarioHora "
              + "FROM empleado "
              + "WHERE idEmpleado = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idEmpleado);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                txtIdEmpleado.setText(
                        rs.getString("idEmpleado"));

                txtTasaHora.setText(
                        rs.getString("salarioHora"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
    
    public void llenarNoPedido(JComboBox combo){
        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql = "SELECT idPedido, cliente "
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
    
    
    public int guardarTarjetaTiempo(TarjetaTiempo tarjeta){

        int idTarjeta = 0;

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "INSERT INTO tarjetaTiempo "
              + "(idEmpleado,idPedido,"
              + "fecha,observaciones,"
              + "totalHoras,totalCosto) "
              + "VALUES(?,?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(
                            sql,
                            PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(
                    1,
                    tarjeta.getIdEmpleado());

            ps.setInt(
                    2,
                    tarjeta.getIdPedido());

            ps.setDate(
                    3,
                    new java.sql.Date(
                            tarjeta.getFecha().getTime()));

            ps.setString(
                    4,
                    tarjeta.getObservaciones());

            ps.setDouble(
                    5,
                    tarjeta.getTotalHoras());

            ps.setDouble(
                    6,
                    tarjeta.getTotalCosto());

            ps.executeUpdate();

            ResultSet rs =
                    ps.getGeneratedKeys();

            if(rs.next()){

                idTarjeta =
                        rs.getInt(1);

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return idTarjeta;

    }
    
    public boolean guardarDetalleTiempo(int idTarjeta, String dia, String horaInicio, String horaFin, double tiempoTotal, double tasaHora, double costoTotal){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "INSERT INTO detalleTarjetaTiempo "
              + "(idTarjeta,dia,"
              + "horaInicio,horaFin,"
              + "tiempoTotal,tasaHora,"
              + "costoTotal) "
              + "VALUES(?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,idTarjeta);

            ps.setString(2,dia);

            ps.setString(3,horaInicio);

            ps.setString(4,horaFin);

            ps.setDouble(5,tiempoTotal);

            ps.setDouble(6,tasaHora);

            ps.setDouble(7,costoTotal);

            return ps.executeUpdate() > 0;

        }catch(Exception e){

            System.out.println( "Error: " + e);

        }

        return false;

    }
    
    public void mostrarTarjetas(JTable tabla){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "SELECT t.idTarjeta, "
              + "t.idEmpleado, "
              + "CONCAT(e.nombre,' ',e.apellidoP) empleado, "
              + "t.idPedido, "
              + "t.fecha, "
              + "t.totalHoras, "
              + "t.totalCosto, "
              + "t.observaciones "
              + "FROM tarjetaTiempo t "
              + "INNER JOIN empleado e "
              + "ON t.idEmpleado = e.idEmpleado";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            DefaultTableModel modelo =
                    (DefaultTableModel)
                    tabla.getModel();

            modelo.setRowCount(0);

            while(rs.next()){

                modelo.addRow(new Object[]{

                rs.getInt("idTarjeta"),
                rs.getString("empleado"),
                rs.getInt("idEmpleado"),
                rs.getInt("idPedido"),
                rs.getDate("fecha"),
                rs.getDouble("totalHoras"),
                rs.getDouble("totalCosto"),
                rs.getString("observaciones")

            });

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

    }
    
    public void mostrarDetalleTarjeta(JTable tabla,int idTarjeta){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "SELECT dia, "
              + "horaInicio, "
              + "horaFin, "
              + "tiempoTotal, "
              + "tasaHora, "
              + "costoTotal "
              + "FROM detalleTarjetaTiempo "
              + "WHERE idTarjeta = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idTarjeta);

            ResultSet rs =
                    ps.executeQuery();

            DefaultTableModel modelo =
                    (DefaultTableModel)
                    tabla.getModel();

            modelo.setRowCount(0);

            int contador = 1;

            while(rs.next()){

                modelo.addRow(new Object[]{

                    contador++,
                    rs.getString("dia"),
                    rs.getString("horaInicio"),
                    rs.getString("horaFin"),
                    rs.getDouble("tiempoTotal"),
                    rs.getDouble("tasaHora"),
                    rs.getDouble("costoTotal")

                });

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

    }
    
    public TarjetaTiempo obtenerTarjetaTiempo(int idTarjeta){

        TarjetaTiempo tarjeta = new TarjetaTiempo();

        try{

            Connection con = ConexionBD.getConexionBD();

            String sql =
                "SELECT * "
              + "FROM tarjetaTiempo "
              + "WHERE idTarjeta = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,idTarjeta);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                tarjeta.setIdTarjeta(rs.getInt("idTarjeta"));

                tarjeta.setIdEmpleado(rs.getInt("idEmpleado"));

                tarjeta.setIdPedido(rs.getInt("idPedido"));

                tarjeta.setFecha(rs.getDate("fecha"));

                tarjeta.setObservaciones(rs.getString("observaciones"));

                tarjeta.setTotalHoras(rs.getDouble("totalHoras"));

                tarjeta.setTotalCosto(rs.getDouble("totalCosto"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

        return tarjeta;

    }
    
    public boolean actualizarTarjeta(
        TarjetaTiempo tarjeta){

            try{

                Connection con =
                        ConexionBD.getConexionBD();

                String sql =
                    "UPDATE tarjetaTiempo "
                  + "SET idEmpleado=?, "
                  + "idPedido=?, "
                  + "fecha=?, "
                  + "observaciones=?, "
                  + "totalHoras=?, "
                  + "totalCosto=? "
                  + "WHERE idTarjeta=?";

                PreparedStatement ps =
                        con.prepareStatement(sql);

                ps.setInt(1,
                        tarjeta.getIdEmpleado());

                ps.setInt(2,
                        tarjeta.getIdPedido());

                ps.setDate(3,
                        new java.sql.Date(
                                tarjeta.getFecha().getTime()));

                ps.setString(4,
                        tarjeta.getObservaciones());

                ps.setDouble(5,
                        tarjeta.getTotalHoras());

                ps.setDouble(6,
                        tarjeta.getTotalCosto());

                ps.setInt(7,
                        tarjeta.getIdTarjeta());

                return ps.executeUpdate() > 0;

            }catch(Exception e){

                System.out.println(
                        "Error: " + e);

                return false;

            }

        }
}
