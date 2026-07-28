/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class HojaCostosTest {

    @Test
    public void testGettersYSetters() {
        HojaCostos hoja = new HojaCostos();
        Date fechaInicio = new Date();
        Date fechaFin = new Date();

        // Asignamos valores usando los setters
        hoja.setIdOrden(1);
        hoja.setIdProduccion(10);
        hoja.setDescripcion("Pastel de tres leches");
        hoja.setCantidad(5);
        hoja.setFechaInicio(fechaInicio);
        hoja.setFechaFin(fechaFin);
        hoja.setCostoMateriales(500.0f);
        hoja.setCostoManoDeObra(200.0f);
        hoja.setCostosIndirectos(50.0f);
        hoja.setCostoTotal(750.0f);
        hoja.setCostoUnitario(150.0f);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, hoja.getIdOrden());
        assertEquals(10, hoja.getIdProduccion());
        assertEquals("Pastel de tres leches", hoja.getDescripcion());
        assertEquals(5, hoja.getCantidad());
        assertEquals(fechaInicio, hoja.getFechaInicio());
        assertEquals(fechaFin, hoja.getFechaFin());
        assertEquals(500.0f, hoja.getCostoMateriales(), 0.001);
        assertEquals(200.0f, hoja.getCostoManoDeObra(), 0.001);
        assertEquals(50.0f, hoja.getCostosIndirectos(), 0.001);
        assertEquals(750.0f, hoja.getCostoTotal(), 0.001);
        assertEquals(150.0f, hoja.getCostoUnitario(), 0.001);
    }
}