/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Modelo.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;

public class EstadisticasMenuDAOTest {


    // STUB: Generador de simulación para PreparedStatement y ResultSet
    private Connection crearConexionStub(boolean exito, int valorRetornoEsperado) {
        return (Connection) Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[] { Connection.class },
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (method.getName().equals("prepareStatement")) {
                        if (!exito) {
                            throw new SQLException("Error simulado de base de datos");
                        }
                        return Proxy.newProxyInstance(
                            PreparedStatement.class.getClassLoader(),
                            new Class<?>[] { PreparedStatement.class },
                            new InvocationHandler() {
                                @Override
                                public Object invoke(Object psProxy, Method psMethod, Object[] psArgs) throws Throwable {
                                    if (psMethod.getName().equals("executeQuery")) {
                                        return Proxy.newProxyInstance(
                                            ResultSet.class.getClassLoader(),
                                            new Class<?>[] { ResultSet.class },
                                            new InvocationHandler() {
                                                private boolean leido = false;
                                                @Override
                                                public Object invoke(Object rsProxy, Method rsMethod, Object[] rsArgs) throws Throwable {
                                                    if (rsMethod.getName().equals("next")) {
                                                        if (!leido) {
                                                            leido = true;
                                                            return true;
                                                        }
                                                        return false;
                                                    }
                                                    if (rsMethod.getName().equals("getInt")) {
                                                        return valorRetornoEsperado;
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

    // PRUEBAS: contarEmpleados
    @Test
    public void testContarEmpleados_CaminoFeliz() {
        Connection stub = crearConexionStub(true, 15);
        EstadisticasMenuDAO dao = new EstadisticasMenuDAO(stub);
        int resultado = dao.contarEmpleados();
        assertEquals("Debe retornar 15 empleados", 15, resultado);
    }

    @Test
    public void testContarEmpleados_CaminoExcepcion() {
        Connection stub = crearConexionStub(false, 0);
        EstadisticasMenuDAO dao = new EstadisticasMenuDAO(stub);
        int resultado = dao.contarEmpleados();
        assertEquals("Debe retornar 0 al atrapar la excepción", 0, resultado);
    }

    // PRUEBAS: contarIngredientes
    @Test
    public void testContarIngredientes_CaminoFeliz() {
        Connection stub = crearConexionStub(true, 45);
        EstadisticasMenuDAO dao = new EstadisticasMenuDAO(stub);
        int resultado = dao.contarIngredientes();
        assertEquals("Debe retornar 45 ingredientes", 45, resultado);
    }

    @Test
    public void testContarIngredientes_CaminoExcepcion() {
        Connection stub = crearConexionStub(false, 0);
        EstadisticasMenuDAO dao = new EstadisticasMenuDAO(stub);
        int resultado = dao.contarIngredientes();
        assertEquals("Debe retornar 0 al atrapar la excepción", 0, resultado);
    }

    // PRUEBAS: contarPedidos
    @Test
    public void testContarPedidos_CaminoFeliz() {
        Connection stub = crearConexionStub(true, 12);
        EstadisticasMenuDAO dao = new EstadisticasMenuDAO(stub);
        int resultado = dao.contarPedidos();
        assertEquals("Debe retornar 12 pedidos", 12, resultado);
    }

    @Test
    public void testContarPedidos_CaminoExcepcion() {
        Connection stub = crearConexionStub(false, 0);
        EstadisticasMenuDAO dao = new EstadisticasMenuDAO(stub);
        int resultado = dao.contarPedidos();
        assertEquals("Debe retornar 0 al atrapar la excepción", 0, resultado);
    }

    // PRUEBAS: contarProductos
    @Test
    public void testContarProductos_CaminoFeliz() {
        Connection stub = crearConexionStub(true, 30);
        EstadisticasMenuDAO dao = new EstadisticasMenuDAO(stub);
        int resultado = dao.contarProductos();
        assertEquals("Debe retornar 30 productos", 30, resultado);
    }

    @Test
    public void testContarProductos_CaminoExcepcion() {
        Connection stub = crearConexionStub(false, 0);
        EstadisticasMenuDAO dao = new EstadisticasMenuDAO(stub);
        int resultado = dao.contarProductos();
        assertEquals("Debe retornar 0 al atrapar la excepción", 0, resultado);
    }

    // PRUEBAS: contarTarjetas
    @Test
    public void testContarTarjetas_CaminoFeliz() {
        Connection stub = crearConexionStub(true, 100);
        EstadisticasMenuDAO dao = new EstadisticasMenuDAO(stub);
        int resultado = dao.contarTarjetas();
        assertEquals("Debe retornar 100 tarjetas", 100, resultado);
    }

    @Test
    public void testContarTarjetas_CaminoExcepcion() {
        Connection stub = crearConexionStub(false, 0);
        EstadisticasMenuDAO dao = new EstadisticasMenuDAO(stub);
        int resultado = dao.contarTarjetas();
        assertEquals("Debe retornar 0 al atrapar la excepción", 0, resultado);
    }
}