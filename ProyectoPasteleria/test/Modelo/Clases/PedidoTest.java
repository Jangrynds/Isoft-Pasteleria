/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

public class PedidoTest {

    @Test
    public void testGettersYSetters() {
        Pedido pedido = new Pedido();
        Date fechaInicio = new Date();
        Date fechaEntrega = new Date();

        // Asignamos valores usando los setters
        pedido.setIdPedido(1);
        pedido.setCliente("Juan Pérez");
        pedido.setFechaInicio(fechaInicio);
        pedido.setFechaEntrega(fechaEntrega);
        pedido.setObservaciones("Entregar por la tarde");
        pedido.setIdEstadoPedido(2);
        pedido.setCantidad(4);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, pedido.getIdPedido());
        assertEquals("Juan Pérez", pedido.getCliente());
        assertEquals(fechaInicio, pedido.getFechaInicio());
        assertEquals(fechaEntrega, pedido.getFechaEntrega());
        assertEquals("Entregar por la tarde", pedido.getObservaciones());
        assertEquals(2, pedido.getIdEstadoPedido());
        assertEquals(4, pedido.getCantidad());
    }
}