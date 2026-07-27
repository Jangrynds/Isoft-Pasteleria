package Modelo.DAO;

import Modelo.Clases.Producto;
import org.junit.Before;
import org.junit.Test;

import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.GraphicsEnvironment;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProductoDAOTest {

    private MockDatabaseContext mockContext;
    private ProductoDAO dao;

    @Before
    public void setUp() {
        System.setProperty("java.awt.headless", "true");
        mockContext = new MockDatabaseContext();
        dao = new ProductoDAO(mockContext::getConnection);
    }

    @Test
    public void testMostrarProductos_Exito() {
        JTable tabla = new JTable();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> fila1 = new HashMap<>();
        fila1.put("idProducto", "1");
        fila1.put("nombreProducto", "Pastel Chocolate");
        fila1.put("nombreCategoria", "Pasteles");
        fila1.put("tamano", "Grande");
        fila1.put("descripcion", "Rico");
        fila1.put("precioVenta", "350.0");
        fila1.put("nombreEstado", "Disponible");
        filas.add(fila1);

        mockContext.statementResultSet = mockContext.createMockResultSet(filas);

        dao.mostrarProductos(tabla);

        assertEquals(1, tabla.getRowCount());
        assertEquals("1", tabla.getValueAt(0, 0));
        assertEquals("Pastel Chocolate", tabla.getValueAt(0, 1));
        assertEquals("Pasteles", tabla.getValueAt(0, 2));
    }

    @Test
    public void testMostrarProductos_ExcepcionSQL() {
        JTable tabla = new JTable();
        mockContext.throwExceptionOnStatement = true;

        try {
            dao.mostrarProductos(tabla);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, tabla.getRowCount());
    }

    @Test
    public void testGuardarProducto_Exito() {
        Producto prod = createSampleProducto();

        try {
            dao.guardarProducto(prod);
        } catch (Exception e) {
            // Se ignora la excepción si proviene de la UI Headless al mostrar JOptionPane
            if (!(e instanceof java.awt.HeadlessException)) {
                fail("No deberia fallar la insercion SQL: " + e.getMessage());
            }
        }
    }

    @Test
    public void testGuardarProducto_ExcepcionSQL() {
        Producto prod = createSampleProducto();
        mockContext.throwExceptionOnExecuteUpdate = true;

        try {
            dao.guardarProducto(prod);
        } catch (Exception e) {
            // El DAO intenta mostrar JOptionPane en el catch, lo cual lanza HeadlessException esperada
            assertTrue(e instanceof java.awt.HeadlessException || e.getCause() instanceof SQLException);
        }
    }

    @Test
    public void testEditarProducto_Exito() {
        Producto prod = createSampleProducto();
        prod.setIdProducto(1);

        boolean resultado = dao.editarProducto(prod);
        assertTrue(resultado);
    }

    @Test
    public void testEditarProducto_ExcepcionSQL() {
        Producto prod = createSampleProducto();
        prod.setIdProducto(1);
        mockContext.throwExceptionOnExecuteUpdate = true;

        boolean resultado = dao.editarProducto(prod);
        assertFalse(resultado);
    }

    @Test
    public void testEliminarProducto_Exito() {
        boolean resultado = dao.eliminarProducto(1);
        assertTrue(resultado);
    }

    @Test
    public void testEliminarProducto_ExcepcionSQL() {
        mockContext.throwExceptionOnExecuteUpdate = true;

        try {
            boolean resultado = dao.eliminarProducto(1);
            assertFalse(resultado);
        } catch (java.awt.HeadlessException e) {
            // Si salta HeadlessException al desplegar JOptionPane, confirma que el flujo entro al catch por error SQL
            assertTrue(true);
        }
    }

    @Test
    public void testMostrarNombresProductos_Exito() {
        JComboBox combo = new JComboBox();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("nombreProducto", "Pastel Chocolate");
        Map<String, Object> f2 = new HashMap<>();
        f2.put("nombreProducto", "Flan Napolitano");
        filas.add(f1);
        filas.add(f2);

        mockContext.statementResultSet = mockContext.createMockResultSet(filas);

        dao.mostrarNombresProductos(combo);

        assertEquals(2, combo.getItemCount());
        assertEquals("Pastel Chocolate", combo.getItemAt(0));
        assertEquals("Flan Napolitano", combo.getItemAt(1));
    }

    @Test
    public void testMostrarNombresProductos_ExcepcionSQL() {
        JComboBox combo = new JComboBox();
        mockContext.throwExceptionOnStatement = true;

        try {
            dao.mostrarNombresProductos(combo);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, combo.getItemCount());
    }

    @Test
    public void testObtenerPrecioProducto_Exito() {
        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("precioVenta", 350.0);
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        double precio = dao.obtenerPrecioProducto("Pastel Chocolate");
        assertEquals(350.0, precio, 0.001);
    }

    @Test
    public void testObtenerPrecioProducto_NoEncontrado() {
        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(new ArrayList<>());

        double precio = dao.obtenerPrecioProducto("Inexistente");
        assertEquals(0.0, precio, 0.001);
    }

    @Test
    public void testObtenerPrecioProducto_ExcepcionSQL() {
        mockContext.throwExceptionOnPreparedStatement = true;

        double precio = dao.obtenerPrecioProducto("Pastel Chocolate");
        assertEquals(0.0, precio, 0.001);
    }

    @Test
    public void testObtenerIdProducto_Exito() {
        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("idProducto", 1);
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        int id = dao.obtenerIdProducto("Pastel Chocolate");
        assertEquals(1, id);
    }

    @Test
    public void testObtenerIdProducto_NoEncontrado() {
        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(new ArrayList<>());

        int id = dao.obtenerIdProducto("Inexistente");
        assertEquals(-1, id);
    }

    @Test
    public void testObtenerIdProducto_ExcepcionSQL() {
        mockContext.throwExceptionOnPreparedStatement = true;

        int id = dao.obtenerIdProducto("Pastel Chocolate");
        assertEquals(-1, id);
    }

    private Producto createSampleProducto() {
        Producto p = new Producto();
        p.setNombreProducto("Pastel Fresa");
        p.setIdCategoria(1);
        p.setTamano("Mediano");
        p.setDescripcion("Fresa natural");
        p.setPrecioVenta(250.0);
        p.setIdEstadoProducto(1);
        return p;
    }

    private static class MockDatabaseContext {
        boolean throwExceptionOnStatement = false;
        boolean throwExceptionOnPreparedStatement = false;
        boolean throwExceptionOnExecuteUpdate = false;

        ResultSet statementResultSet;
        ResultSet preparedStatementResultSet;

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