/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class VentaTest {

    @Test
    public void testGettersYSetters() {
        Venta venta = new Venta();
        Date fecha = new Date();

        // Asignamos valores usando los setters
        venta.setIdVenta(1);
        venta.setFecha(fecha);
        venta.setIdTipo(2);
        venta.setTipo("Efectivo");
        venta.setTotal(550.50f);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, venta.getIdVenta());
        assertEquals(fecha, venta.getFecha());
        assertEquals(2, venta.getIdTipo());
        assertEquals("Efectivo", venta.getTipo());
        assertEquals(550.50f, venta.getTotal(), 0.001);
    }
}