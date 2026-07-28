/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmpleadoTest {

    @Test
    public void testGettersYSetters() {
        Empleado empleado = new Empleado();

        // Asignamos valores usando los setters
        empleado.setIdEmpleado(1);
        empleado.setNombre("Juan");
        empleado.setApellidoPaterno("Pérez");
        empleado.setApellidoMaterno("Gómez");
        empleado.setTelefono("2281234567");
        empleado.setIdDepartamento(2);
        empleado.setContrasena("secreto123");
        empleado.setSalarioHora(120.50);

        // Verificamos con los getters
        assertEquals(1, empleado.getIdEmpleado());
        assertEquals("Juan", empleado.getNombre());
        assertEquals("Pérez", empleado.getApellidoPaterno());
        assertEquals("Gómez", empleado.getApellidoMaterno());
        assertEquals("2281234567", empleado.getTelefono());
        assertEquals(2, empleado.getIdDepartamento());
        assertEquals("secreto123", empleado.getContrasena());
        assertEquals(120.50, empleado.getSalarioHora(), 0.001);
    }

    @Test
    public void testConstructorParametrizado() {
        // Probamos el constructor que recibe los datos principales
        Empleado empleado = new Empleado(5, "María", "López", "Solis", "2289876543", 3, "pass456");

        assertEquals(5, empleado.getIdEmpleado());
        assertEquals("María", empleado.getNombre());
        assertEquals("López", empleado.getApellidoPaterno());
        assertEquals("Solis", empleado.getApellidoMaterno());
        assertEquals("2289876543", empleado.getTelefono());
        assertEquals(3, empleado.getIdDepartamento());
        assertEquals("pass456", empleado.getContrasena());
    }
}