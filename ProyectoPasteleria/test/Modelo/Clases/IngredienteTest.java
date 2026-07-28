/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class IngredienteTest {

    @Test
    public void testGettersYSetters() {
        Ingrediente ingrediente = new Ingrediente();

        // Asignamos valores usando los setters
        ingrediente.setIdIngrediente(1);
        ingrediente.setNombreIngrediente("Harina");
        ingrediente.setCantidad(10);
        ingrediente.setMedida("kg");
        ingrediente.setPrecioUnitario(25.50);
        ingrediente.setPrecio(255.00);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, ingrediente.getIdIngrediente());
        assertEquals("Harina", ingrediente.getNombreIngrediente());
        assertEquals(10, ingrediente.getCantidad());
        assertEquals("kg", ingrediente.getMedida());
        assertEquals(25.50, ingrediente.getPrecioUnitario(), 0.001);
        assertEquals(255.00, ingrediente.getPrecio(), 0.001);
    }
}