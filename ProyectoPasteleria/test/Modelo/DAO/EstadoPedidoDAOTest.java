package Modelo.DAO;

import org.junit.Before;
import org.junit.Test;
import javax.swing.JComboBox;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Pruebas unitarias para EstadoPedidoDAO usando JUnit 4.
 */
public class EstadoPedidoDAOTest {

    private JComboBox<String> cmbEstadoPedido;

    @Before
    public void setUp() {
        cmbEstadoPedido = new JComboBox<>();
    }

    // =========================================================================
    // CASOS DE PRUEBA
    // =========================================================================

    @Test
    public void testMostrarEstadoPedido_CaminoFeliz() throws Exception {
        // CP-01: Preparar stub con datos
        List<String> datosSimulados = Arrays.asList("Pendiente", "En Proceso", "Entregado");
        Connection stubConexion = crearStubConnection(datosSimulados, false);

        EstadoPedidoDAO dao = new EstadoPedidoDAO(stubConexion);
        dao.mostrarEstadoPedido(cmbEstadoPedido);

        // Verificación
        assertEquals(3, cmbEstadoPedido.getItemCount());
        assertEquals("Pendiente", cmbEstadoPedido.getItemAt(0));
        assertEquals("En Proceso", cmbEstadoPedido.getItemAt(1));
        assertEquals("Entregado", cmbEstadoPedido.getItemAt(2));
    }

    @Test
    public void testMostrarEstadoPedido_TablaVacia() throws Exception {
        // CP-02: ResultSet sin filas
        List<String> datosVacios = Arrays.asList();
        Connection stubConexion = crearStubConnection(datosVacios, false);

        EstadoPedidoDAO dao = new EstadoPedidoDAO(stubConexion);
        dao.mostrarEstadoPedido(cmbEstadoPedido);

        // Verificación
        assertEquals(0, cmbEstadoPedido.getItemCount());
    }

    @Test
    public void testMostrarEstadoPedido_ExcepcionSQL() throws Exception {
        // CP-03: Simulación de fallo en la BD (SQLException)
        Connection stubConexion = crearStubConnection(null, true);

        EstadoPedidoDAO dao = new EstadoPedidoDAO(stubConexion);
        
        // No debe lanzar excepción no controlada hacia arriba debido al try-catch interno
        dao.mostrarEstadoPedido(cmbEstadoPedido);

        // Verificación: El JComboBox debe permanecer intacto
        assertEquals(0, cmbEstadoPedido.getItemCount());
    }

    // =========================================================================
    // INFRAESTRUCTURA DE STUBS CON PROXY DINÁMICO (Compatible con Java 8 a 21)
    // =========================================================================

    private Connection crearStubConnection(final List<String> datos, final boolean lanzarExcepcion) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("createStatement".equals(method.getName())) {
                    if (lanzarExcepcion) {
                        throw new SQLException("Error de conexión simulado con la BD");
                    }
                    return crearStubStatement(datos);
                }
                return null;
            }
        };

        return (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                handler
        );
    }

    private Statement crearStubStatement(final List<String> datos) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("executeQuery".equals(method.getName())) {
                    return crearStubResultSet(datos);
                }
                return null;
            }
        };

        return (Statement) Proxy.newProxyInstance(
                Statement.class.getClassLoader(),
                new Class<?>[]{Statement.class},
                handler
        );
    }

    private ResultSet crearStubResultSet(final List<String> datos) {
        InvocationHandler handler = new InvocationHandler() {
            private int cursor = -1;

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String nombreMetodo = method.getName();

                if ("next".equals(nombreMetodo)) {
                    cursor++;
                    return cursor < datos.size();
                }
                if ("getString".equals(nombreMetodo)) {
                    if (args != null && args.length > 0 && "nombreEstadoPedido".equals(args[0])) {
                        return datos.get(cursor);
                    }
                }
                return null;
            }
        };

        return (ResultSet) Proxy.newProxyInstance(
                ResultSet.class.getClassLoader(),
                new Class<?>[]{ResultSet.class},
                handler
        );
    }
}