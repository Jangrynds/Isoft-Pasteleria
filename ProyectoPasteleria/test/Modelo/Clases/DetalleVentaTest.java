/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class DetalleVentaTest {

    @Test
    public void testGettersYSetters() {
        DetalleVenta detalle = new DetalleVenta();

        // Asignamos valores usando los setters
        detalle.setIdDetalleVenta(1);
        detalle.setIdVenta(100);
        detalle.setIdProducto(5);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(75.50f);
        detalle.setTotal(151.00f);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, detalle.getIdDetalleVenta());
        assertEquals(100, detalle.getIdVenta());
        assertEquals(5, detalle.getIdProducto());
        assertEquals(2, detalle.getCantidad());
        assertEquals(75.50f, detalle.getPrecioUnitario(), 0.001);
        assertEquals(151.00f, detalle.getTotal(), 0.001);
    }
}