/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.DAO;

import Modelo.Clases.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmpleadoDAOTest {

    @Test
    public void testGuardarEmpleado_CaminoFeliz() {
        try {
            // 1. Crear un objeto empleado con datos válidos
            Empleado emp = new Empleado();
            emp.setNombre("Juan");
            emp.setApellidoPaterno("Granados");
            emp.setApellidoMaterno("Reyes");
            emp.setTelefono("2281234567");
            emp.setIdDepartamento(1);
            emp.setSalarioHora(150.0);

            // 2. Crear Stub nativo para simular Connection y PreparedStatement
            Connection conexionStub = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[] { Connection.class },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("prepareStatement")) {
                            return Proxy.newProxyInstance(
                                PreparedStatement.class.getClassLoader(),
                                new Class<?>[] { PreparedStatement.class },
                                new InvocationHandler() {
                                    @Override
                                    public Object invoke(Object psProxy, Method psMethod, Object[] psArgs) throws Throwable {
                                        // Simulamos ejecución exitosa sin hacer nada en la BD real
                                        if (psMethod.getName().equals("execute")) {
                                            return true;
                                        }
                                        return null;
                                    }
                                }
                            );
                        }
                        return null;
                    }
                }
            );

            // 3. Instanciar el DAO con la conexión inyectada
            EmpleadoDAO dao = new EmpleadoDAO(conexionStub);

            // 4. Ejecutar el método
            boolean resultado = dao.guardarEmpleado(emp);

            // 5. Verificar resultado esperado
            assertTrue("El método guardarEmpleado debe retornar true en el camino feliz", resultado);

        } catch (Exception e) {
            fail("La prueba falló por una excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testGuardarEmpleado_CaminoExcepcion_EmpleadoNulo() {
        // Valida el manejo de excepción ante un objeto nulo
        Connection conexionStub = (Connection) Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[] { Connection.class },
            (proxy, method, args) -> null
        );

        EmpleadoDAO dao = new EmpleadoDAO(conexionStub);
        boolean resultado = dao.guardarEmpleado(null);

        assertFalse("El método debe retornar false al recibir un empleado nulo", resultado);
    }
}