/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.DAO;

import javax.swing.JComboBox;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;

public class DepartamentoDAOTest {

    @Test
    public void testMostrarDepartamentos_CaminoFeliz() {
        try {
            // 1. Crear un JComboBox real para recibir los datos simulados
            JComboBox<String> combo = new JComboBox<>();

            // 2. Crear Stub dinámico nativo para simular Connection, Statement y ResultSet
            Connection conexionStub = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[] { Connection.class },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("createStatement")) {
                            return Proxy.newProxyInstance(
                                Statement.class.getClassLoader(),
                                new Class<?>[] { Statement.class },
                                new InvocationHandler() {
                                    @Override
                                    public Object invoke(Object stProxy, Method stMethod, Object[] stArgs) throws Throwable {
                                        if (stMethod.getName().equals("executeQuery")) {
                                            return Proxy.newProxyInstance(
                                                ResultSet.class.getClassLoader(),
                                                new Class<?>[] { ResultSet.class },
                                                new InvocationHandler() {
                                                    private int count = 0;
                                                    @Override
                                                    public Object invoke(Object rsProxy, Method rsMethod, Object[] rsArgs) throws Throwable {
                                                        if (rsMethod.getName().equals("next")) {
                                                            count++;
                                                            return count <= 2; // Simula que existen 2 registros
                                                        }
                                                        if (rsMethod.getName().equals("getString")) {
                                                            if (count == 1) return "Sistemas";
                                                            if (count == 2) return "Administración";
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
                        }
                        return null;
                    }
                }
            );

            // 3. Instanciar el DAO inyectando la conexión simulada
            DepartamentoDAO dao = new DepartamentoDAO(conexionStub);
            
            // 4. Ejecutar el método a evaluar
            dao.mostrarDepartamentos(combo);

            // 5. Verificar los resultados esperados
            assertEquals("El ComboBox debe contener exactamente 2 elementos", 2, combo.getItemCount());
            assertEquals("El primer departamento debe ser Sistemas", "Sistemas", combo.getItemAt(0));
            assertEquals("El segundo departamento debe ser Administración", "Administración", combo.getItemAt(1));

        } catch (Exception e) {
            fail("La prueba falló por una excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testMostrarDepartamentos_CaminoExcepcion_ComboNulo() {
        // Valida el comportamiento ante un argumento nulo de entrada
        DepartamentoDAO dao = new DepartamentoDAO();
        try {
            dao.mostrarDepartamentos(null);
            assertTrue("El método manejó el valor nulo de manera controlada", true);
        } catch (Exception e) {
            fail("El método no debió propagar una excepción no controlada: " + e.getMessage());
        }
    }
}