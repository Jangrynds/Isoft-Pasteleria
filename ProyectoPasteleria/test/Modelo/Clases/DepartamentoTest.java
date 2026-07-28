/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class DepartamentoTest {

    @Test
    public void testGettersYSetters() {
        Departamento departamento = new Departamento();

        departamento.setIdDepartamento(1);
        departamento.setNombreDepartamento("Producción");
        departamento.setDescripcion("Área encargada de la elaboración de pasteles");

        assertEquals(1, departamento.getIdDepartamento());
        assertEquals("Producción", departamento.getNombreDepartamento());
        assertEquals("Área encargada de la elaboración de pasteles", departamento.getDescripcion());
    }
}