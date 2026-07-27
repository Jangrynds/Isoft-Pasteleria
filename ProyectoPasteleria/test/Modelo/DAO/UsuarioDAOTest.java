package Modelo.DAO;

import Modelo.Clases.Sesion;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UsuarioDAOTest {

    @Before
    public void setUp() {
        // Limpiamos el estado global de la sesión antes de cada prueba
        Sesion.idEmpleado = 0;
        Sesion.idDepartamento = 0;
    }

    // =========================================================================
    // CASOS DE PRUEBA
    // =========================================================================

    @Test
    public void testValidarUsuario_CaminoFeliz() {
        // Datos simulados para la BD
        final int idEmpleadoEsperado = 101;
        final int idDepartamentoEsperado = 5;

        ResultSet rsMock = crearResultSetMock(true, idEmpleadoEsperado, idDepartamentoEsperado);
        PreparedStatement psMock = crearPreparedStatementMock(rsMock);
        Connection conMock = crearConnectionMock(psMock, false);

        UsuarioDAO dao = new UsuarioDAO(conMock);

        boolean resultado = dao.validarUsuario("juan", "1234");

        assertTrue("El usuario debería validarse correctamente", resultado);
        assertEquals("El idEmpleado en Sesión debe coincidir con la BD", idEmpleadoEsperado, Sesion.idEmpleado);
        assertEquals("El idDepartamento en Sesión debe coincidir con la BD", idDepartamentoEsperado, Sesion.idDepartamento);
    }

    @Test
    public void testValidarUsuario_UsuarioNoEncontrado() {
        ResultSet rsMock = crearResultSetMock(false, 0, 0);
        PreparedStatement psMock = crearPreparedStatementMock(rsMock);
        Connection conMock = crearConnectionMock(psMock, false);

        UsuarioDAO dao = new UsuarioDAO(conMock);

        boolean resultado = dao.validarUsuario("usuario_invalido", "clave_erronea");

        assertFalse("El resultado debe ser false si el usuario no existe", resultado);
        assertEquals("El idEmpleado no debe haberse asignado", 0, Sesion.idEmpleado);
        assertEquals("El idDepartamento no debe haberse asignado", 0, Sesion.idDepartamento);
    }

    @Test
    public void testValidarUsuario_ExcepcionSQLException() {
        // Simulamos un fallo en la BD
        Connection conMock = crearConnectionMock(null, true);

        UsuarioDAO dao = new UsuarioDAO(conMock);

        boolean resultado = dao.validarUsuario("juan", "1234");

        assertFalse("Debe retornar false al capturar la excepción en el catch", resultado);
    }

    // =========================================================================
    // STUBS / PROXIES NATIVOS PARA JDBC (A prueba de Java 9+)
    // =========================================================================

    private ResultSet crearResultSetMock(final boolean existeRegistro, final int idEmp, final int idDepto) {
        return (ResultSet) Proxy.newProxyInstance(
                ResultSet.class.getClassLoader(),
                new Class<?>[]{ResultSet.class},
                new InvocationHandler() {
                    private boolean primerLlamadaNext = true;

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String name = method.getName();
                        if ("next".equals(name)) {
                            if (primerLlamadaNext) {
                                primerLlamadaNext = false;
                                return existeRegistro;
                            }
                            return false;
                        }
                        if ("getInt".equals(name) && args != null && args.length > 0) {
                            String col = (String) args[0];
                            if ("idEmpleado".equals(col)) return idEmp;
                            if ("idDepartamento".equals(col)) return idDepto;
                        }
                        if ("close".equals(name)) return null;
                        return responderPorDefecto(method.getReturnType());
                    }
                }
        );
    }

    private PreparedStatement crearPreparedStatementMock(final ResultSet rsDevuelto) {
        return (PreparedStatement) Proxy.newProxyInstance(
                PreparedStatement.class.getClassLoader(),
                new Class<?>[]{PreparedStatement.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String name = method.getName();
                        if ("executeQuery".equals(name)) {
                            return rsDevuelto;
                        }
                        if ("setString".equals(name) || "close".equals(name)) {
                            return null;
                        }
                        return responderPorDefecto(method.getReturnType());
                    }
                }
        );
    }

    private Connection crearConnectionMock(final PreparedStatement psDevuelto, final boolean lanzarExcepcion) {
        return (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (lanzarExcepcion) {
                            throw new SQLException("Error simulado de conexion a BD");
                        }
                        if ("prepareStatement".equals(method.getName())) {
                            return psDevuelto;
                        }
                        if ("close".equals(method.getName())) {
                            return null;
                        }
                        return responderPorDefecto(method.getReturnType());
                    }
                }
        );
    }

    private Object responderPorDefecto(Class<?> returnType) {
        if (returnType.equals(boolean.class)) return false;
        if (returnType.equals(int.class)) return 0;
        if (returnType.equals(long.class)) return 0L;
        return null;
    }
}