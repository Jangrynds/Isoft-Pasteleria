/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequisicionTest {

    @Test
    public void testGettersYSetters() {
        Requisicion requisicion = new Requisicion();
        Date fecha = new Date();

        // Asignamos valores usando los setters
        requisicion.setIdRequisicion(1);
        requisicion.setFecha(fecha);
        requisicion.setIdDepartamento(2);
        requisicion.setIdPedido(10);
        requisicion.setSolicitante("Juan Pérez");
        requisicion.setObservaciones("Urgente para producción");
        requisicion.setTotal(1250.75);
        requisicion.setEntregadoPor("Carlos Gómez");
        requisicion.setRecibidoPor("María López");

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, requisicion.getIdRequisicion());
        assertEquals(fecha, requisicion.getFecha());
        assertEquals(2, requisicion.getIdDepartamento());
        assertEquals(10, requisicion.getIdPedido());
        assertEquals("Juan Pérez", requisicion.getSolicitante());
        assertEquals("Urgente para producción", requisicion.getObservaciones());
        assertEquals(1250.75, requisicion.getTotal(), 0.001);
        assertEquals("Carlos Gómez", requisicion.getEntregadoPor());
        assertEquals("María López", requisicion.getRecibidoPor());
    }
}