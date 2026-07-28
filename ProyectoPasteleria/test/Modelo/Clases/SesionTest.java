/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class SesionTest {

    @Test
    public void testGettersYSettersEstaticos() {
        // Asignamos valores usando los setters estáticos
        Sesion.setIdEmpleado(42);
        Sesion.setIdDepartamento(3);

        // Verificamos con los getters estáticos que devuelvan los valores correctos
        assertEquals(42, Sesion.getIdEmpleado());
        assertEquals(3, Sesion.getIdDepartamento());
    }
}