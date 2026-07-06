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
public class Pedido {
    private int idPedido;
    private String cliente;
    private Date fechaInicio;
    private Date fechaEntrega;
    private String observaciones;
    private int idEstadoPedido;
    private int cantidad;
    
    public Pedido(){
        
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public int getIdEstadoPedido() {
        return idEstadoPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setIdEstadoPedido(int idEstadoPedido) {
        this.idEstadoPedido = idEstadoPedido;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
