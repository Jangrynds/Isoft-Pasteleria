/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

/**
 *
 * @author ingri
 */
public class InventarioTerminado {
    private int idProducto;
    private int stock;
    
    public InventarioTerminado(){
        
    };

    public int getIdProducto() {
        return idProducto;
    };

    public int getStock() {
        return stock;
    };

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    };

    public void setStock(int stock) {
        this.stock = stock;
    };
}
