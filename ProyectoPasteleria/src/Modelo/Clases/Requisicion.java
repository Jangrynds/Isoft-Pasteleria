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
public class Requisicion {
    private int idRequisicion;
    private Date fecha;
    private int idDepartamento;
    private int idPedido;
    private String solicitante;
    private String observaciones;
    private double total;
    private String entregadoPor;
    private String recibidoPor;
    
    public Requisicion(){
        
    }

    public int getIdRequisicion() {
        return idRequisicion;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public double getTotal() {
        return total;
    }

    public String getEntregadoPor() {
        return entregadoPor;
    }

    public String getRecibidoPor() {
        return recibidoPor;
    }

    public void setIdRequisicion(int idRequisicion) {
        this.idRequisicion = idRequisicion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setEntregadoPor(String entregadoPor) {
        this.entregadoPor = entregadoPor;
    }

    public void setRecibidoPor(String recibidoPor) {
        this.recibidoPor = recibidoPor;
    }
    
    
}
