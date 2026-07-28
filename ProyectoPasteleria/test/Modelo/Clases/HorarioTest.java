/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.Clases;

import org.junit.Test;
import static org.junit.Assert.*;

public class HorarioTest {

    @Test
    public void testGettersYSetters() {
        Horario horario = new Horario();

        // Asignamos valores usando los setters
        horario.setIdHorario(1);
        horario.setIdEmpleado(5);
        horario.setDia("Lunes");
        horario.setHoraInicio(8);
        horario.setHoraFin(16);

        // Verificamos con los getters que devuelvan los valores correctos
        assertEquals(1, horario.getIdHorario());
        assertEquals(5, horario.getIdEmpleado());
        assertEquals("Lunes", horario.getDia());
        assertEquals(8, horario.getHoraInicio());
        assertEquals(16, horario.getHoraFin());
    }
}