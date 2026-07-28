/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductoTest {

    @Test
    public void testGettersYSetters() {
        Producto producto = new Producto();

        // Asignamos valores usando los setters
        producto.setIdProducto(1);
        producto.setNombreProducto("Pastel de Chocolate");
        producto.setIdCategoria(3);
        producto.setTamano("Grande");
        producto.setDescripcion("Pastel de chocolate con relleno de fudge");
        producto.setPrecioVenta(350.00);
        producto.setIdEstadoProducto(1);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, producto.getIdProducto());
        assertEquals("Pastel de Chocolate", producto.getNombreProducto());
        assertEquals(3, producto.getIdCategoria());
        assertEquals("Grande", producto.getTamano());
        assertEquals("Pastel de chocolate con relleno de fudge", producto.getDescripcion());
        assertEquals(350.00, producto.getPrecioVenta(), 0.001);
        assertEquals(1, producto.getIdEstadoProducto());
    }
}