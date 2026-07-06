/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

/**
 *
 * @author ingri
 */
public class DetalleRequisicion {
    private int idDetalleReq;
    private int idRequsicion;
    private int idIngrediente;
    private int cantidad;
    private float costoUnitario;
    private float costoTotal;
    
    public DetalleRequisicion(){
        
    };

    public int getIdDetalleReq() {
        return idDetalleReq;
    };

    public int getIdRequsicion() {
        return idRequsicion;
    };

    public int getIdIngrediente() {
        return idIngrediente;
    };

    public int getCantidad() {
        return cantidad;
    };

    public float getCostoUnitario() {
        return costoUnitario;
    };

    public float getCostoTotal() {
        return costoTotal;
    };
};
