/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class InventarioTerminadoTest {

    @Test
    public void testGettersYSetters() {
        InventarioTerminado inventario = new InventarioTerminado();

        // Asignamos valores usando los setters
        inventario.setIdProducto(101);
        inventario.setStock(25);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(101, inventario.getIdProducto());
        assertEquals(25, inventario.getStock());
    }
}
