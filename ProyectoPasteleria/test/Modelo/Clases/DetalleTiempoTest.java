/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class DetalleTiempoTest {

    @Test
    public void testGettersYSetters() {
        DetalleTiempo detalle = new DetalleTiempo();

        // Asignamos valores usando los setters
        detalle.setIdDetalleTarjeta(1);
        detalle.setIdTarjeta(10);
        detalle.setDia("Lunes");
        detalle.setHoraInicio("08:00");
        detalle.setHoraFin("16:00");
        detalle.setTiempoTotal(8.0);
        detalle.setTasaHora(50.0);
        detalle.setCostoTotal(400.0);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, detalle.getIdDetalleTarjeta());
        assertEquals(10, detalle.getIdTarjeta());
        assertEquals("Lunes", detalle.getDia());
        assertEquals("08:00", detalle.getHoraInicio());
        assertEquals("16:00", detalle.getHoraFin());
        assertEquals(8.0, detalle.getTiempoTotal(), 0.001);
        assertEquals(50.0, detalle.getTasaHora(), 0.001);
        assertEquals(400.0, detalle.getCostoTotal(), 0.001);
    }
}