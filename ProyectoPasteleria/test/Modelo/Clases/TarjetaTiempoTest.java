/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class TarjetaTiempoTest {

    @Test
    public void testGettersYSetters() {
        TarjetaTiempo tarjeta = new TarjetaTiempo();
        Date fecha = new Date();

        // Asignamos valores usando los setters
        tarjeta.setIdTarjeta(1);
        tarjeta.setIdEmpleado(10);
        tarjeta.setIdPedido(5);
        tarjeta.setFecha(fecha);
        tarjeta.setObservaciones("Turno matutino completado");
        tarjeta.setTotalHoras(8.5);
        tarjeta.setTotalCosto(425.00);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, tarjeta.getIdTarjeta());
        assertEquals(10, tarjeta.getIdEmpleado());
        assertEquals(5, tarjeta.getIdPedido());
        assertEquals(fecha, tarjeta.getFecha());
        assertEquals("Turno matutino completado", tarjeta.getObservaciones());
        assertEquals(8.5, tarjeta.getTotalHoras(), 0.001);
        assertEquals(425.00, tarjeta.getTotalCosto(), 0.001);
    }
}