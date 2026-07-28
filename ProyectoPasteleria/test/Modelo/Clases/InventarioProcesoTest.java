/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class InventarioProcesoTest {

    @Test
    public void testGettersYSetters() {
        InventarioProceso inventario = new InventarioProceso();

        // Asignamos valores usando los setters
        inventario.setIdProduccion(15);
        inventario.setCantidad(50);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(15, inventario.getIdProduccion());
        assertEquals(50, inventario.getCantidad());
    }
}