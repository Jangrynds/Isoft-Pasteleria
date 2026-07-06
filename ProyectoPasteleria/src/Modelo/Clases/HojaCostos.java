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
public class HojaCostos {
    private int idOrden;
    private int idProduccion;
    private String descripcion;
    private int cantidad;
    private Date fechaInicio;
    private Date fechaFin;
    private float costoMateriales;
    private float costoManoDeObra;
    private float costosIndirectos;
    private float costoTotal;
    private float costoUnitario;
    
    public HojaCostos(){
        
    };

    public int getIdOrden() {
        return idOrden;
    };

    public int getIdProduccion() {
        return idProduccion;
    };

    public String getDescripcion() {
        return descripcion;
    };

    public int getCantidad() {
        return cantidad;
    };

    public Date getFechaInicio() {
        return fechaInicio;
    };

    public Date getFechaFin() {
        return fechaFin;
    };

    public float getCostoMateriales() {
        return costoMateriales;
    };

    public float getCostoManoDeObra() {
        return costoManoDeObra;
    };

    public float getCostosIndirectos() {
        return costosIndirectos;
    };

    public float getCostoTotal() {
        return costoTotal;
    };

    public float getCostoUnitario() {
        return costoUnitario;
    };

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    };

    public void setIdProduccion(int idProduccion) {
        this.idProduccion = idProduccion;
    };

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    };

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    };

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    };

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    };

    public void setCostoMateriales(float costoMateriales) {
        this.costoMateriales = costoMateriales;
    };

    public void setCostoManoDeObra(float costoManoDeObra) {
        this.costoManoDeObra = costoManoDeObra;
    };

    public void setCostosIndirectos(float costosIndirectos) {
        this.costosIndirectos = costosIndirectos;
    };

    public void setCostoTotal(float costoTotal) {
        this.costoTotal = costoTotal;
    };

    public void setCostoUnitario(float costoUnitario) {
        this.costoUnitario = costoUnitario;
    };
}
