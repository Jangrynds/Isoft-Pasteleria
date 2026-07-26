package Modelo.DAO;

import Modelo.Clases.Pedido;
import org.junit.Before;
import org.junit.Test;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class HojaCostosDAOTest {

    private HojaCostosDAO dao;
    private MockJDBC mockJdbc;

    @Before
    public void setUp() {
        mockJdbc = new MockJDBC();
        // Inyección de la conexión simulada al DAO a evaluar
        dao = new HojaCostosDAO(mockJdbc.getConnectionProxy());
    }

    // ==========================================
    // PRUEBAS PARA: obtenerPedido
    // ==========================================

    @Test
    public void testObtenerPedido_CaminoFeliz() {
        // Arrange
        mockJdbc.addQueryResult(new Object[][]{
            {"idPedido", "cliente", "fechaInicio", "fechaEntrega", "observaciones", "cantidad"},
            {100, "Empresa X", Date.valueOf("2026-01-01"), Date.valueOf("2026-01-15"), "Sin notas", 50}
        });

        // Act
        Pedido resultado = dao.obtenerPedido(100);

        // Assert
        assertNotNull(resultado);
        assertEquals(100, resultado.getIdPedido());
        assertEquals("Empresa X", resultado.getCliente());
        assertEquals(50, resultado.getCantidad());
    }

    @Test
    public void testObtenerPedido_ExcepcionSinResultados() {
        // Arrange: ResultSet vacío
        mockJdbc.addQueryResult(new Object[][]{
            {"idPedido", "cliente", "fechaInicio", "fechaEntrega", "observaciones", "cantidad"}
        });

        // Act
        Pedido resultado = dao.obtenerPedido(999);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.getIdPedido());
        assertNull(resultado.getCliente());
    }

    // ==========================================
    // PRUEBAS PARA: llenarNoPedido
    // ==========================================

    @Test
    public void testLlenarNoPedido_CaminoFeliz() {
        // Arrange
        mockJdbc.addQueryResult(new Object[][]{
            {"idPedido", "cliente"},
            {1, "Cliente A"},
            {2, "Cliente B"}
        });
        JComboBox<String> combo = new JComboBox<>();

        // Act
        dao.llenarNoPedido(combo);

        // Assert
        assertEquals(2, combo.getItemCount());
        assertEquals("1 - Cliente A", combo.getItemAt(0));
        assertEquals("2 - Cliente B", combo.getItemAt(1));
    }

    @Test
    public void testLlenarNoPedido_ExcepcionComboNull() {
        // Arrange
        mockJdbc.addQueryResult(new Object[][]{
            {"idPedido", "cliente"},
            {1, "Cliente A"}
        });

        // Act & Assert (Captura la excepción; no rompe el programa)
        try {
            dao.llenarNoPedido(null);
            assertTrue(true); // Si llega aquí, el catch interno del DAO manejó el error
        } catch (Exception e) {
            fail("El DAO no debió propagar la excepción hacia afuera: " + e.getMessage());
        }
    }

    // ==========================================
    // PRUEBAS PARA: mostrarMaterialesPorPedido
    // ==========================================

    @Test
    public void testMostrarMaterialesPorPedido_CaminoFeliz() {
        // Arrange
        mockJdbc.addQueryResult(new Object[][]{
            {"fecha", "idRequisicion", "total"},
            {Date.valueOf("2026-02-01"), 10, 150.50},
            {Date.valueOf("2026-02-02"), 11, 249.50}
        });

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Fecha", "ID Requisición", "Total"}, 0);
        JTable tabla = new JTable(model);

        // Act
        double subtotal = dao.mostrarMaterialesPorPedido(tabla, 100);

        // Assert
        assertEquals(400.00, subtotal, 0.001);
        assertEquals(2, model.getRowCount());
        assertEquals(150.50, model.getValueAt(0, 2));
    }

    @Test
    public void testMostrarMaterialesPorPedido_ExcepcionTablaNull() {
        // Arrange
        mockJdbc.addQueryResult(new Object[][]{
            {"fecha", "idRequisicion", "total"},
            {Date.valueOf("2026-02-01"), 10, 150.50}
        });

        // Act
        double subtotal = dao.mostrarMaterialesPorPedido(null, 100);

        // Assert
        assertEquals(0.0, subtotal, 0.001);
    }

    // ==========================================
    // PRUEBAS PARA: mostrarManoObraPorPedido
    // ==========================================

    @Test
    public void testMostrarManoObraPorPedido_CaminoFeliz() {
        // Arrange
        mockJdbc.addQueryResult(new Object[][]{
            {"fecha", "idTarjeta", "totalHoras", "salarioHora", "totalCosto"},
            {Date.valueOf("2026-02-05"), 5, 8.0, 20.0, 160.00}
        });

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Fecha", "ID Tarjeta", "Horas", "Salario", "Costo"}, 0);
        JTable tabla = new JTable(model);

        // Act
        double subtotal = dao.mostrarManoObraPorPedido(tabla, 100);

        // Assert
        assertEquals(160.00, subtotal, 0.001);
        assertEquals(1, model.getRowCount());
        assertEquals(160.00, model.getValueAt(0, 4));
    }

    @Test
    public void testMostrarManoObraPorPedido_ExcepcionTablaNull() {
        // Arrange
        mockJdbc.addQueryResult(new Object[][]{
            {"fecha", "idTarjeta", "totalHoras", "salarioHora", "totalCosto"},
            {Date.valueOf("2026-02-05"), 5, 8.0, 20.0, 160.00}
        });

        // Act
        double subtotal = dao.mostrarManoObraPorPedido(null, 100);

        // Assert
        assertEquals(0.0, subtotal, 0.001);
    }

    // =========================================================================
    // IMPLEMENTACIÓN DE STUBS LIGEROS VÍA PROXY DINÁMICO (COMPATIBLE CON CUALQUIER JDK)
    // =========================================================================

    private static class MockJDBC {
        private final List<Object[][]> queryResults = new ArrayList<>();
        private int currentQueryIndex = 0;

        public void addQueryResult(Object[][] data) {
            queryResults.add(data);
        }

        public Connection getConnectionProxy() {
            return (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                (proxy, method, args) -> {
                    if ("prepareStatement".equals(method.getName()) || "createStatement".equals(method.getName())) {
                        return getPreparedStatementProxy();
                    }
                    return null;
                }
            );
        }

        private PreparedStatement getPreparedStatementProxy() {
            return (PreparedStatement) Proxy.newProxyInstance(
                PreparedStatement.class.getClassLoader(),
                new Class<?>[]{PreparedStatement.class},
                (proxy, method, args) -> {
                    if ("executeQuery".equals(method.getName())) {
                        Object[][] data = currentQueryIndex < queryResults.size() 
                            ? queryResults.get(currentQueryIndex++) 
                            : new Object[][]{{}};
                        return getResultSetProxy(data);
                    }
                    return null;
                }
            );
        }

        private ResultSet getResultSetProxy(Object[][] data) {
            return (ResultSet) Proxy.newProxyInstance(
                ResultSet.class.getClassLoader(),
                new Class<?>[]{ResultSet.class},
                new InvocationHandler() {
                    private int rowIndex = 0; // Se posiciona antes de la primera fila de datos (fila 0 son encabezados)
                    private final String[] headers = extractHeaders(data);

                    private String[] extractHeaders(Object[][] data) {
                        if (data.length == 0) return new String[0];
                        String[] h = new String[data[0].length];
                        for (int i = 0; i < data[0].length; i++) {
                            h[i] = data[0][i].toString();
                        }
                        return h;
                    }

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String name = method.getName();

                        if ("next".equals(name)) {
                            rowIndex++;
                            return rowIndex < data.length;
                        }

                        if ("getInt".equals(name) || "getString".equals(name) || "getDate".equals(name) || "getDouble".equals(name)) {
                            String colName = (String) args[0];
                            int colIndex = getColumnIndex(colName);
                            if (colIndex != -1 && rowIndex < data.length) {
                                Object val = data[rowIndex][colIndex];
                                if ("getInt".equals(name)) return val instanceof Number ? ((Number) val).intValue() : 0;
                                if ("getDouble".equals(name)) return val instanceof Number ? ((Number) val).doubleValue() : 0.0;
                                if ("getString".equals(name)) return val != null ? val.toString() : null;
                                if ("getDate".equals(name)) return val instanceof Date ? val : null;
                            }
                        }
                        return null;
                    }

                    private int getColumnIndex(String columnName) {
                        for (int i = 0; i < headers.length; i++) {
                            if (headers[i].equalsIgnoreCase(columnName)) return i;
                        }
                        return -1;
                    }
                }
            );
        }
    }
}