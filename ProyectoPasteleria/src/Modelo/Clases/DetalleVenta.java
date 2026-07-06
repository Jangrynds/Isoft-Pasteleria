/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

/**
 *
 * @author ingri
 */
public class DetalleVenta {
    private int idDetalleVenta;
    private int idVenta;
    private int idProducto;
    private int cantidad;
    private float precioUnitario;
    private float total;
    
    public DetalleVenta(){
        
    };

    public int getIdDetalleVenta() {
        return idDetalleVenta;
    };

    public int getIdVenta() {
        return idVenta;
    };

    public int getIdProducto() {
        return idProducto;
    };

    public int getCantidad() {
        return cantidad;
    };

    public float getPrecioUnitario() {
        return precioUnitario;
    };

    public float getTotal() {
        return total;
    };

    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    };

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    };

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    };

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    };

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    };

    public void setTotal(float total) {
        this.total = total;
    }; 
}
