package Modelo.DAO;

import org.junit.Before;
import org.junit.Test;

import javax.swing.JComboBox;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class EstadoProductoDAOTest {

    private Connection mockConnection;
    private Statement mockStatement;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    private EstadoProductoDAO estadoProductoDAO;

    // --- MÉTODOS AUXILIARES PARA CREAR STUBS NATIVOS SIN MOCKITO ---

    @SuppressWarnings("unchecked")
    private <T> T crearStub(Class<T> interfaz, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(
                interfaz.getClassLoader(),
                new Class<?>[]{interfaz},
                handler
        );
    }

    @Before
    public void setUp() {
        // Inicializamos el DAO con la conexión simulada nativa
        estadoProductoDAO = new EstadoProductoDAO(mockConnection);
    }

    // ==========================================
    // PRUEBAS DE: mostrarEstadoProducto()
    // ==========================================

    @Test
    public void testMostrarEstadoProducto_CaminoFeliz() {
        // Arrange
        JComboBox comboTest = new JComboBox();

        mockResultSet = crearStub(ResultSet.class, new InvocationHandler() {
            private int cursor = 0;
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                if (method.getName().equals("next")) {
                    cursor++;
                    return cursor <= 2; // Retorna true 2 veces, luego false
                }
                if (method.getName().equals("getString")) {
                    return cursor == 1 ? "Disponible" : "Agotado";
                }
                return null;
            }
        });

        mockStatement = crearStub(Statement.class, (proxy, method, args) -> {
            if (method.getName().equals("executeQuery")) return mockResultSet;
            return null;
        });

        mockConnection = crearStub(Connection.class, (proxy, method, args) -> {
            if (method.getName().equals("createStatement")) return mockStatement;
            return null;
        });

        estadoProductoDAO = new EstadoProductoDAO(mockConnection);

        // Act
        estadoProductoDAO.mostrarEstadoProducto(comboTest);

        // Assert
        assertEquals(2, comboTest.getItemCount());
        assertEquals("Disponible", comboTest.getItemAt(0));
        assertEquals("Agotado", comboTest.getItemAt(1));
    }

    @Test
    public void testMostrarEstadoProducto_ExcepcionSQL() {
        // Arrange
        JComboBox comboTest = new JComboBox();

        mockConnection = crearStub(Connection.class, (proxy, method, args) -> {
            if (method.getName().equals("createStatement")) {
                throw new SQLException("Error de conexion simulado");
            }
            return null;
        });

        estadoProductoDAO = new EstadoProductoDAO(mockConnection);

        // Act
        estadoProductoDAO.mostrarEstadoProducto(comboTest);

        // Assert
        assertEquals(0, comboTest.getItemCount());
    }

    // ==========================================
    // PRUEBAS DE: obtenerIdEstado()
    // ==========================================

    @Test
    public void testObtenerIdEstado_CaminoFeliz() {
        // Arrange
        mockResultSet = crearStub(ResultSet.class, new InvocationHandler() {
            private boolean consultado = false;
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                if (method.getName().equals("next")) {
                    if (!consultado) {
                        consultado = true;
                        return true;
                    }
                    return false;
                }
                if (method.getName().equals("getInt")) return 1;
                return null;
            }
        });

        mockPreparedStatement = crearStub(PreparedStatement.class, (proxy, method, args) -> {
            if (method.getName().equals("executeQuery")) return mockResultSet;
            return null;
        });

        mockConnection = crearStub(Connection.class, (proxy, method, args) -> {
            if (method.getName().equals("prepareStatement")) return mockPreparedStatement;
            return null;
        });

        estadoProductoDAO = new EstadoProductoDAO(mockConnection);

        // Act
        int id = estadoProductoDAO.obtenerIdEstado("Disponible");

        // Assert
        assertEquals(1, id);
    }

    @Test
    public void testObtenerIdEstado_NoEncontrado() {
        // Arrange
        mockResultSet = crearStub(ResultSet.class, (proxy, method, args) -> {
            if (method.getName().equals("next")) return false;
            return null;
        });

        mockPreparedStatement = crearStub(PreparedStatement.class, (proxy, method, args) -> {
            if (method.getName().equals("executeQuery")) return mockResultSet;
            return null;
        });

        mockConnection = crearStub(Connection.class, (proxy, method, args) -> {
            if (method.getName().equals("prepareStatement")) return mockPreparedStatement;
            return null;
        });

        estadoProductoDAO = new EstadoProductoDAO(mockConnection);

        // Act
        int id = estadoProductoDAO.obtenerIdEstado("Inexistente");

        // Assert
        assertEquals(0, id);
    }

    @Test
    public void testObtenerIdEstado_ExcepcionSQL() {
        // Arrange
        mockConnection = crearStub(Connection.class, (proxy, method, args) -> {
            if (method.getName().equals("prepareStatement")) {
                throw new SQLException("Fallo SQL");
            }
            return null;
        });

        estadoProductoDAO = new EstadoProductoDAO(mockConnection);

        // Act
        int id = estadoProductoDAO.obtenerIdEstado("Disponible");

        // Assert
        assertEquals(0, id);
    }
}