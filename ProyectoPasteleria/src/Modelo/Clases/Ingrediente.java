/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

/**
 *
 * @author ingri
 */
public class Ingrediente {
    private int idIngrediente;
    private String nombreIngrediente;
    private int cantidad;
    private String medida;
    private Double precioUnitario;
    private Double precio;
    
    public Ingrediente(){
        
    };

    public int getIdIngrediente() {
        return idIngrediente;
    };

    public String getNombreIngrediente() {
        return nombreIngrediente;
    };

    public int getCantidad() {
        return cantidad;
    };
    
    public String getMedida(){
        return medida;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    };

    public double getPrecio() {
        return precio;
    };

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    };

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    };

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    };
    
    public void setMedida(String medida){
        this.medida = medida;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    };

    public void setPrecio(double precio) {
        this.precio = precio;
    };   
}
