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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TamanoProductoDAOTest {

    private MockDatabaseContext mockContext;
    private TamanoProductoDAO dao;

    @Before
    public void setUp() {
        System.setProperty("java.awt.headless", "true");
        mockContext = new MockDatabaseContext();
        dao = new TamanoProductoDAO(mockContext::getConnection);
    }

    @Test
    public void testMostrarTamanoProducto_Exito() {
        JComboBox combo = new JComboBox();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("nombretamanoP", "Chico");

        Map<String, Object> f2 = new HashMap<>();
        f2.put("nombretamanoP", "Mediano");

        Map<String, Object> f3 = new HashMap<>();
        f3.put("nombretamanoP", "Grande");

        filas.add(f1);
        filas.add(f2);
        filas.add(f3);

        mockContext.statementResultSet = mockContext.createMockResultSet(filas);

        dao.mostrarTamanoProducto(combo);

        assertEquals(3, combo.getItemCount());
        assertEquals("Chico", combo.getItemAt(0));
        assertEquals("Mediano", combo.getItemAt(1));
        assertEquals("Grande", combo.getItemAt(2));
    }

    @Test
    public void testMostrarTamanoProducto_ExcepcionSQL() {
        JComboBox combo = new JComboBox();
        mockContext.throwExceptionOnStatement = true;

        try {
            dao.mostrarTamanoProducto(combo);
        } catch (Exception e) {
            fail("El DAO debe capturar la excepcion internamente: " + e.getMessage());
        }

        assertEquals(0, combo.getItemCount());
    }

    @Test
    public void testMostrarTamanoProducto_TablaVacia() {
        JComboBox combo = new JComboBox();
        mockContext.statementResultSet = mockContext.createMockResultSet(new ArrayList<>());

        dao.mostrarTamanoProducto(combo);

        assertEquals(0, combo.getItemCount());
    }

    private static class MockDatabaseContext {
        boolean throwExceptionOnStatement = false;
        ResultSet statementResultSet;

        public Connection getConnection() {
            return (Connection) Proxy.newProxyInstance(
                    Connection.class.getClassLoader(),
                    new Class<?>[]{Connection.class},
                    (proxy, method, args) -> {
                        String mName = method.getName();
                        if ("createStatement".equals(mName)) {
                            if (throwExceptionOnStatement) {
                                throw new SQLException("Error simulado en Statement");
                            }
                            return createMockStatement();
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