package Modelo.DAO;

import Modelo.Clases.Pedido;
import org.junit.Before;
import org.junit.Test;

import javax.swing.JComboBox;
import javax.swing.JTable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProduccionDAOTest {

    private MockDatabaseContext mockContext;
    private ProduccionDAO dao;

    @Before
    public void setUp() {
        System.setProperty("java.awt.headless", "true");
        mockContext = new MockDatabaseContext();
        dao = new ProduccionDAO(mockContext::getConnection);
    }

    @Test
    public void testMostrarPedidos_Exito() {
        JTable tabla = new JTable();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> fila1 = new HashMap<>();
        fila1.put("idPedido", "101");
        fila1.put("cliente", "Acme Corp");
        fila1.put("fechaInicio", "2026-03-01");
        fila1.put("fechaEntrega", "2026-03-10");
        fila1.put("nombreEstadoPedido", "En Proceso");
        fila1.put("observaciones", "Urgente");
        filas.add(fila1);

        mockContext.statementResultSet = mockContext.createMockResultSet(filas);

        dao.mostrarPedidos(tabla);

        assertEquals(1, tabla.getRowCount());
        assertEquals("101", tabla.getValueAt(0, 0));
        assertEquals("Acme Corp", tabla.getValueAt(0, 1));
        assertEquals("En Proceso", tabla.getValueAt(0, 4));
    }

    @Test
    public void testMostrarPedidos_ExcepcionSQL() {
        JTable tabla = new JTable();
        mockContext.throwExceptionOnStatement = true;

        try {
            dao.mostrarPedidos(tabla);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, tabla.getRowCount());
    }

    @Test
    public void testGuardarPedido_Exito() {
        Pedido pedido = createSamplePedido();

        List<Map<String, Object>> keys = new ArrayList<>();
        Map<String, Object> keyRow = new HashMap<>();
        keyRow.put("1", 501);
        keys.add(keyRow);

        mockContext.generatedKeysResultSet = mockContext.createMockResultSet(keys);

        int idGenerado = dao.guardarPedido(pedido);

        assertEquals(501, idGenerado);
    }

    @Test
    public void testGuardarPedido_ExcepcionSQL() {
        Pedido pedido = createSamplePedido();
        mockContext.throwExceptionOnExecuteUpdate = true;

        int resultado = dao.guardarPedido(pedido);

        assertEquals(-1, resultado);
    }

    @Test
    public void testGuardarPedido_SinClavesGeneradas() {
        Pedido pedido = createSamplePedido();
        mockContext.generatedKeysResultSet = mockContext.createMockResultSet(new ArrayList<>());

        int resultado = dao.guardarPedido(pedido);

        assertEquals(-1, resultado);
    }

    @Test
    public void testMostrarDetallePedido_Exito() {
        JTable tabla = new JTable();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> fila = new HashMap<>();
        fila.put("idProducto", "10");
        fila.put("nombreProducto", "Caja Cartón");
        fila.put("cantidad", 5.0);
        fila.put("precioVenta", 12.50);
        filas.add(fila);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.mostrarDetallePedido(tabla, 101);

        assertEquals(1, tabla.getRowCount());
        assertEquals("10", tabla.getValueAt(0, 0));
        assertEquals("Caja Cartón", tabla.getValueAt(0, 1));
        assertEquals("62.5", tabla.getValueAt(0, 4));
    }

    @Test
    public void testMostrarDetallePedido_ExcepcionSQL() {
        JTable tabla = new JTable();
        mockContext.throwExceptionOnPreparedStatement = true;

        try {
            dao.mostrarDetallePedido(tabla, 101);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, tabla.getRowCount());
    }

    @Test
    public void testGuardarDetallePedido_Exito() {
        boolean resultado = dao.guardarDetallePedido(101, 10, 5);
        assertTrue(resultado);
    }

    @Test
    public void testGuardarDetallePedido_ExcepcionSQL() {
        mockContext.throwExceptionOnExecuteUpdate = true;
        boolean resultado = dao.guardarDetallePedido(101, 10, 5);
        assertFalse(resultado);
    }

    @Test
    public void testEliminarDetallePedido_Exito() {
        boolean resultado = dao.eliminarDetallePedido(101);
        assertTrue(resultado);
    }

    @Test
    public void testEliminarDetallePedido_ExcepcionSQL() {
        mockContext.throwExceptionOnExecuteUpdate = true;
        boolean resultado = dao.eliminarDetallePedido(101);
        assertFalse(resultado);
    }

    @Test
    public void testEliminarPedido_Exito() {
        boolean resultado = dao.eliminarPedido(101);
        assertTrue(resultado);
    }

    @Test
    public void testEliminarPedido_ExcepcionSQL() {
        mockContext.throwExceptionOnExecuteUpdate = true;
        boolean resultado = dao.eliminarPedido(101);
        assertFalse(resultado);
    }

    @Test
    public void testEditarPedido_Exito() {
        Pedido ped = createSamplePedido();
        ped.setIdPedido(101);

        boolean resultado = dao.editarPedido(ped);
        assertTrue(resultado);
    }

    @Test
    public void testEditarPedido_ExcepcionSQL() {
        Pedido ped = createSamplePedido();
        ped.setIdPedido(101);
        mockContext.throwExceptionOnExecuteUpdate = true;

        boolean resultado = dao.editarPedido(ped);
        assertFalse(resultado);
    }

    @Test
    public void testLlenarComboPedidos_Exito() {
        JComboBox combo = new JComboBox();
        combo.addItem("Item Previo");

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("idPedido", 101);
        f1.put("cliente", "Juan");

        Map<String, Object> f2 = new HashMap<>();
        f2.put("idPedido", 102);
        f2.put("cliente", "María");

        filas.add(f1);
        filas.add(f2);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.llenarComboPedidos(combo);

        assertEquals(2, combo.getItemCount());
        assertEquals("101 - Juan", combo.getItemAt(0));
        assertEquals("102 - María", combo.getItemAt(1));
    }

    @Test
    public void testLlenarComboPedidos_ExcepcionSQL() {
        JComboBox combo = new JComboBox();
        mockContext.throwExceptionOnPreparedStatement = true;

        try {
            dao.llenarComboPedidos(combo);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
    }

    private Pedido createSamplePedido() {
        Pedido p = new Pedido();
        p.setCliente("Juan Pérez");
        p.setFechaInicio(new Date());
        p.setFechaEntrega(new Date());
        p.setObservaciones("Ninguna");
        p.setIdEstadoPedido(1);
        return p;
    }

    private static class MockDatabaseContext {
        boolean throwExceptionOnStatement = false;
        boolean throwExceptionOnPreparedStatement = false;
        boolean throwExceptionOnExecuteUpdate = false;

        ResultSet statementResultSet;
        ResultSet preparedStatementResultSet;
        ResultSet generatedKeysResultSet;

        public Connection getConnection() {
            return (Connection) Proxy.newProxyInstance(
                    Connection.class.getClassLoader(),
                    new Class<?>[]{Connection.class},
                    (proxy, method, args) -> {
                        String mName = method.getName();
                        if ("createStatement".equals(mName)) {
                            if (throwExceptionOnStatement) {
                                throw new SQLException("Error simulado");
                            }
                            return createMockStatement();
                        }
                        if ("prepareStatement".equals(mName)) {
                            if (throwExceptionOnPreparedStatement) {
                                throw new SQLException("Error simulado");
                            }
                            return createMockPreparedStatement();
                        }
                        return defaultValue(method.getReturnType());
                    });
        }

        private Statement createMockStatement() {
            return (Statement) Proxy.newProxyInstance(
                    Statement.class.getClassLoader(),
                    new Class<?>[]{Statement.class},
                    (proxy, method, args) -> {
                        if ("executeQuery".equals(method.getName())) {
                            return statementResultSet != null ? statementResultSet : createMockResultSet(new ArrayList<>());
                        }
                        return defaultValue(method.getReturnType());
                    });
        }

        private PreparedStatement createMockPreparedStatement() {
            return (PreparedStatement) Proxy.newProxyInstance(
                    PreparedStatement.class.getClassLoader(),
                    new Class<?>[]{PreparedStatement.class},
                    (proxy, method, args) -> {
                        String mName = method.getName();
                        if ("executeUpdate".equals(mName)) {
                            if (throwExceptionOnExecuteUpdate) {
                                throw new SQLException("Error simulado");
                            }
                            return 1;
                        }
                        if ("executeQuery".equals(mName)) {
                            return preparedStatementResultSet != null ? preparedStatementResultSet : createMockResultSet(new ArrayList<>());
                        }
                        if ("getGeneratedKeys".equals(mName)) {
                            return generatedKeysResultSet != null ? generatedKeysResultSet : createMockResultSet(new ArrayList<>());
                        }
                        return defaultValue(method.getReturnType());
                    });
        }

        public ResultSet createMockResultSet(List<Map<String, Object>> rows) {
            return (ResultSet) Proxy.newProxyInstance(
                    ResultSet.class.getClassLoader(),
                    new Class<?>[]{ResultSet.class},
                    new InvocationHandler() {
                        private int currentIndex = -1;

                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            String mName = method.getName();
                            if ("next".equals(mName)) {
                                currentIndex++;
                                return currentIndex < rows.size();
                            }
                            if ("getString".equals(mName) || "getInt".equals(mName) || "getDouble".equals(mName)) {
                                if (args == null || args.length == 0) return defaultValue(method.getReturnType());
                                Object colKey = args[0];
                                if (currentIndex < 0 || currentIndex >= rows.size()) {
                                    return defaultValue(method.getReturnType());
                                }
                                Map<String, Object> currentRow = rows.get(currentIndex);
                                Object val = currentRow.get(colKey.toString());

                                if (val == null && colKey instanceof Integer) {
                                    val = currentRow.get(String.valueOf(colKey));
                                }

                                if (val == null) return defaultValue(method.getReturnType());

                                if ("getString".equals(mName)) return String.valueOf(val);
                                if ("getInt".equals(mName)) {
                                    if (val instanceof Number) return ((Number) val).intValue();
                                    return Integer.parseInt(val.toString());
                                }
                                if ("getDouble".equals(mName)) {
                                    if (val instanceof Number) return ((Number) val).doubleValue();
                                    return Double.parseDouble(val.toString());
                                }
                            }
                            return defaultValue(method.getReturnType());
                        }
                    });
        }

        private Object defaultValue(Class<?> returnType) {
            if (returnType.equals(boolean.class)) return false;
            if (returnType.equals(int.class)) return 0;
            if (returnType.equals(long.class)) return 0L;
            if (returnType.equals(double.class)) return 0.0;
            return null;
        }
    }
}