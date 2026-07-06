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
public class Produccion {
    private int idProduccion;
    private int idProducto;
    private Date fechaInicio;
    private Date fechaFin;
    private boolean estado;
    
    public Produccion(){
        
    };

    public int getIdProduccion() {
        return idProduccion;
    };

    public int getIdProducto() {
        return idProducto;
    };

    public Date getFechaInicio() {
        return fechaInicio;
    };

    public Date getFechaFin() {
        return fechaFin;
    };

    public boolean isEstado() {
        return estado;
    };

    public void setIdProduccion(int idProduccion) {
        this.idProduccion = idProduccion;
    };

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    };

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    };

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    };

    public void setEstado(boolean estado) {
        this.estado = estado;
    };
}
