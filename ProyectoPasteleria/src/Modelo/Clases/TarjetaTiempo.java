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
public class TarjetaTiempo {
     private int idTarjeta;
    private int idEmpleado;
    private int idPedido;
    private Date fecha;
    private String observaciones;
    private double totalHoras;
    private double totalCosto;
    
    public TarjetaTiempo(){
        
    };

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public double getTotalHoras() {
        return totalHoras;
    }

    public double getTotalCosto() {
        return totalCosto;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setTotalHoras(double totalHoras) {
        this.totalHoras = totalHoras;
    }

    public void setTotalCosto(double totalCosto) {
        this.totalCosto = totalCosto;
    }

    
}
