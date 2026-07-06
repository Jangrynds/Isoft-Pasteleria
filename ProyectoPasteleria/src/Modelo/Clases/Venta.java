/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

import java.util.Date;

/**
 *
 * @author ingri
 */
public class Venta {
    private int idVenta;
    private Date fecha;
    private int idTipo;
    private String tipo;
    private float total;
    
    public Venta(){
        
    };

    public int getIdVenta() {
        return idVenta;
    };

    public Date getFecha() {
        return fecha;
    };

    public int getIdTipo() {
        return idTipo;
    };

    public String getTipo() {
        return tipo;
    };

    public float getTotal() {
        return total;
    };

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    };

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    };

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    };

    public void setTipo(String tipo) {
        this.tipo = tipo;
    };

    public void setTotal(float total) {
        this.total = total;
    };
}
