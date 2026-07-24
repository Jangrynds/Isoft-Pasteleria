/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javax.swing.JComboBox;
import org.junit.Test;
import static org.junit.Assert.*;

public class DepartamentoDAOTest {

    // STUB: Para simular Statement y ResultSet
    private Connection crearConexionStubStatement(boolean conRegistros) {
        return (Connection) Proxy.newProxyInstance(
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
                                                        if (!conRegistros) return false;
                                                        count++;
                                                        return count <= 2; // Simula que hay 2 departamentos
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
    }

    // PRUEBAS PARA: mostrarDepartamentos
    
    @Test
    public void testMostrarDepartamentos_CaminoFeliz() {
        try {
            // 1. Instanciar componente visual
            JComboBox<String> combo = new JComboBox<>();
            
            // 2. Crear Stub que simula encontrar 2 registros
            Connection conexionStub = crearConexionStubStatement(true);
            DepartamentoDAO dao = new DepartamentoDAO(conexionStub);
            
            // 3. Ejecutar método
            dao.mostrarDepartamentos(combo);

            // 4. Validar resultados
            assertEquals("El ComboBox debe tener 2 departamentos agregados", 2, combo.getItemCount());
            assertEquals("El primer departamento debe ser Sistemas", "Sistemas", combo.getItemAt(0));
            assertEquals("El segundo departamento debe ser Administración", "Administración", combo.getItemAt(1));
            
        } catch (Exception e) {
            fail("La prueba falló por una excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testMostrarDepartamentos_CaminoExcepcion_ComboNulo() {
        // 1. Crear Stub que simula encontrar registros (no importa porque fallará el combo)
        Connection conexionStub = crearConexionStubStatement(true);
        DepartamentoDAO dao = new DepartamentoDAO(conexionStub);
        
        try {
            // 2. Mandar un JComboBox nulo a propósito
            dao.mostrarDepartamentos(null);
            
            // 3. Si llega a esta línea sin romper la prueba con NullPointerException, la prueba pasa
            assertTrue("El método controló la excepción del parámetro nulo de manera segura", true);
            
        } catch (Exception e) {
            fail("El método no encapsuló la excepción correctamente: " + e.getMessage());
        }
    }

    @Test
    public void testMostrarDepartamentos_CaminoExcepcion_SinRegistros() {
        try {
            JComboBox<String> combo = new JComboBox<>();
            
            // 1. Crear Stub que simula NO encontrar registros (BD vacía)
            Connection conexionStub = crearConexionStubStatement(false);
            DepartamentoDAO dao = new DepartamentoDAO(conexionStub);
            
            // 2. Ejecutar método
            dao.mostrarDepartamentos(combo);

            // 3. Validar que el componente se quedó vacío
            assertEquals("El ComboBox debe tener 0 elementos al no encontrar registros", 0, combo.getItemCount());
            
        } catch (Exception e) {
            fail("La prueba falló por una excepción inesperada: " + e.getMessage());
        }
    }
}