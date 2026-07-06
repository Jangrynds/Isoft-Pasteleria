/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Clases.Requisicion;
import javax.swing.JComboBox;
import Modelo.ConexionBD;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ingri
 */
public class RequisicionDAO {
    
    public void llenarDepartamentos(JComboBox combo){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                    "SELECT nombreDepartamento " + "FROM departamento";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            combo.removeAllItems();

            while(rs.next()){

                combo.addItem( rs.getString("nombreDepartamento"));

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
    
    public void llenarSolicitantes(JComboBox combo, String departamento){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "SELECT e.nombre, "
              + "e.apellidoP, "
              + "e.apellidoM "
              + "FROM empleado e "
              + "INNER JOIN departamento d "
              + "ON e.idDepartamento = d.idDepartamento "
              + "WHERE d.nombreDepartamento = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, departamento);

            ResultSet rs =
                    ps.executeQuery();

            combo.removeAllItems();

            while(rs.next()){

                String nombreCompleto =
                    rs.getString("nombre")
                    + " "
                    + rs.getString("apellidoP")
                    + " "
                    + rs.getString("apellidoM");

                combo.addItem(nombreCompleto);

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

    }
    
    public void mostrarMaterialesSolicitados(JTable tabla, int idPedido){
        Connection con =ConexionBD.getConexionBD();

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("#");
        modelo.addColumn("Material/Ingrediente");
        modelo.addColumn("Unidad");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Costo Unitario");
        modelo.addColumn("Total");

        tabla.setModel(modelo);

        String sql =
            "SELECT "
          + "i.nombreIngrediente, "
          + "SUM(dp.cantidad * r.cantidad) "
          + "AS cantidadNecesaria, "
          + "i.medida, "
          + "i.precioUnitario "
          + "FROM detallePedido dp "
          + "INNER JOIN receta r "
          + "ON dp.idProducto = r.idProducto "
          + "INNER JOIN ingredientes i "
          + "ON r.idIngrediente = i.idIngrediente "
          + "WHERE dp.idPedido = ? "
          + "GROUP BY "
          + "i.idIngrediente, "
          + "i.nombreIngrediente, "
          + "i.medida, "
          + "i.precioUnitario";

        try{

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idPedido);

            ResultSet rs =
                    ps.executeQuery();

            int contador = 1;

            while(rs.next()){

                double cantidad =
                        rs.getDouble(
                                "cantidadNecesaria");

                double precio =
                        rs.getDouble(
                                "precioUnitario");

                double total =
                        cantidad * precio;

                modelo.addRow(new Object[]{

                    contador,
                    rs.getString(
                            "nombreIngrediente"),
                    rs.getString(
                            "medida"),
                    cantidad,
                    precio,
                    total

                });

                contador++;

            }

            tabla.setModel(modelo);

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }
    }
    
    public int obtenerIdDepartamento(String nombreDepartamento){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "SELECT idDepartamento "
              + "FROM departamento "
              + "WHERE nombreDepartamento = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1,
                    nombreDepartamento);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                return rs.getInt(
                        "idDepartamento");

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return -1;

    }
    
    public int obtenerIdIngrediente(String nombreIngrediente){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "SELECT idIngrediente "
              + "FROM ingredientes "
              + "WHERE nombreIngrediente = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1,
                    nombreIngrediente);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                return rs.getInt(
                        "idIngrediente");

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return -1;

    }
    
    public boolean guardarDetalleRequisicion(int idRequisicion, int idIngrediente,double cantidad,double costoUnitario,double total){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "INSERT INTO detalleRequisicion "
              + "(idRequisicion, "
              + "idIngrediente, "
              + "cantidad, "
              + "costoUnitario, "
              + "total) "
              + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idRequisicion);

            ps.setInt(2, idIngrediente);

            ps.setDouble(3, cantidad);

            ps.setDouble(4, costoUnitario);

            ps.setDouble(5, total);

            ps.executeUpdate();

            return true;

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

            return false;

        }

    }
    
    public int guardarRequisicion(Requisicion req){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "INSERT INTO requisicion "
              + "(fecha, idDepartamento, "
              + "idPedido, solicitante, "
              + "observaciones, total, "
              + "entregadoPor, recibidoPor) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps =
                con.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setDate(1,
                new java.sql.Date(
                    req.getFecha().getTime()));

            ps.setInt(2,
                    req.getIdDepartamento());

            ps.setInt(3,
                    req.getIdPedido());

            ps.setString(4,
                    req.getSolicitante());

            ps.setString(5,
                    req.getObservaciones());

            ps.setDouble(6,
                    req.getTotal());

            ps.setString(7,
                    req.getEntregadoPor());

            ps.setString(8,
                    req.getRecibidoPor());

            ps.executeUpdate();

            ResultSet rs =
                    ps.getGeneratedKeys();

            if(rs.next()){

                return rs.getInt(1);

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

        return -1;

    }
    
    public void mostrarRequisiciones(JTable tabla){

        Connection con = ConexionBD.getConexionBD();

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("No. Req");
        modelo.addColumn("Fecha");
        modelo.addColumn("Departamento");
        modelo.addColumn("Solicitante");
        modelo.addColumn("Pedido");
        modelo.addColumn("Total");

        tabla.setModel(modelo);

        String sql =
            "SELECT "
          + "r.idRequisicion, "
          + "r.fecha, "
          + "d.nombreDepartamento, "
          + "r.solicitante, "
          + "r.idPedido, "
          + "r.total "
          + "FROM requisicion r "
          + "INNER JOIN departamento d "
          + "ON r.idDepartamento = d.idDepartamento";

        String datos[] = new String[6];

        try{

            Statement st =
                    con.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            while(rs.next()){

                datos[0] =
                        rs.getString(
                                "idRequisicion");

                datos[1] =
                        rs.getString(
                                "fecha");

                datos[2] =
                        rs.getString(
                                "nombreDepartamento");

                datos[3] =
                        rs.getString(
                                "solicitante");

                datos[4] =
                        rs.getString(
                                "idPedido");

                datos[5] =
                        rs.getString(
                                "total");

                modelo.addRow(datos);

            }

            tabla.setModel(modelo);

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }

    }
    
    public boolean eliminarRequisicion(int idRequisicion){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sqlDetalle =
                "DELETE FROM detalleRequisicion "
              + "WHERE idRequisicion = ?";

            PreparedStatement psDetalle =
                    con.prepareStatement(sqlDetalle);

            psDetalle.setInt(1, idRequisicion);

            psDetalle.executeUpdate();

            String sqlReq =
                "DELETE FROM requisicion "
              + "WHERE idRequisicion = ?";

            PreparedStatement psReq =
                    con.prepareStatement(sqlReq);

            psReq.setInt(1, idRequisicion);

            psReq.executeUpdate();

            return true;

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

            return false;

        }

    }
    
    public void mostrarDetalleRequisicion(JTable tabla, int idRequisicion){
        
        Connection con = ConexionBD.getConexionBD();

        DefaultTableModel modelo =
                new DefaultTableModel();

        modelo.addColumn("#");
        modelo.addColumn("Material/Ingrediente");
        modelo.addColumn("Unidad");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Costo Unitario");
        modelo.addColumn("Total");

        tabla.setModel(modelo);

        String sql =
            "SELECT "
          + "i.nombreIngrediente, "
          + "i.medida, "
          + "dr.cantidad, "
          + "dr.costoUnitario, "
          + "dr.total "
          + "FROM detalleRequisicion dr "
          + "INNER JOIN ingredientes i "
          + "ON dr.idIngrediente = i.idIngrediente "
          + "WHERE dr.idRequisicion = ?";

        try{

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idRequisicion);

            ResultSet rs =
                    ps.executeQuery();

            int contador = 1;

            while(rs.next()){

                modelo.addRow(new Object[]{

                    contador,
                    rs.getString(
                            "nombreIngrediente"),
                    rs.getString(
                            "medida"),
                    rs.getDouble(
                            "cantidad"),
                    rs.getDouble(
                            "costoUnitario"),
                    rs.getDouble(
                            "total")

                });

                contador++;

            }

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

        }
    }
    
    public boolean descontarInventario(int idIngrediente,double cantidad){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "UPDATE ingredientes "
              + "SET cantidad = cantidad - ? "
              + "WHERE idIngrediente = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setDouble(1, cantidad);

            ps.setInt(2, idIngrediente);

            ps.executeUpdate();

            return true;

        }catch(Exception e){

            System.out.println(
                    "Error: " + e);

            return false;

        }

    }
    
    public boolean verificarExistencia(int idIngrediente,double cantidadNecesaria){

        try{

            Connection con =
                    ConexionBD.getConexionBD();

            String sql =
                "SELECT cantidad "
              + "FROM ingredientes "
              + "WHERE idIngrediente = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, idIngrediente);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()){

                double existencia =
                        rs.getDouble("cantidad");

                return existencia >= cantidadNecesaria;

            }

        }catch(Exception e){

            System.out.println("Error: " + e);

        }

        return false;

    }
    
}
