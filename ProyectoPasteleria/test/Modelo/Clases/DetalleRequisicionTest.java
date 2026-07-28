/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class DetalleRequisicionTest {

    @Test
    public void testGettersPorDefecto() {
        DetalleRequisicion detalle = new DetalleRequisicion();

        // Verificamos que los valores iniciales al instanciar sean 0
        assertEquals(0, detalle.getIdDetalleReq());
        assertEquals(0, detalle.getIdRequsicion());
        assertEquals(0, detalle.getIdIngrediente());
        assertEquals(0, detalle.getCantidad());
        assertEquals(0.0f, detalle.getCostoUnitario(), 0.001);
        assertEquals(0.0f, detalle.getCostoTotal(), 0.001);
    }
}