/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProduccionTest {

    @Test
    public void testGettersYSetters() {
        Produccion produccion = new Produccion();
        Date fechaInicio = new Date();
        Date fechaFin = new Date();

        // Asignamos valores usando los setters
        produccion.setIdProduccion(1);
        produccion.setIdProducto(100);
        produccion.setFechaInicio(fechaInicio);
        produccion.setFechaFin(fechaFin);
        produccion.setEstado(true);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, produccion.getIdProduccion());
        assertEquals(100, produccion.getIdProducto());
        assertEquals(fechaInicio, produccion.getFechaInicio());
        assertEquals(fechaFin, produccion.getFechaFin());
        assertTrue(produccion.isEstado());
    }
}
