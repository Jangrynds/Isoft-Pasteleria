package Modelo.DAO;

import Modelo.Clases.Requisicion;
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

public class RequisicionDAOTest {

    private MockDatabaseContext mockContext;
    private RequisicionDAO dao;

    @Before
    public void setUp() {
        System.setProperty("java.awt.headless", "true");
        mockContext = new MockDatabaseContext();
        dao = new RequisicionDAO(mockContext::getConnection);
    }

    @Test
    public void testLlenarDepartamentos_Exito() {
        JComboBox combo = new JComboBox();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("nombreDepartamento", "Cocina");
        Map<String, Object> f2 = new HashMap<>();
        f2.put("nombreDepartamento", "Repostería");
        filas.add(f1);
        filas.add(f2);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.llenarDepartamentos(combo);

        assertEquals(2, combo.getItemCount());
        assertEquals("Cocina", combo.getItemAt(0));
        assertEquals("Repostería", combo.getItemAt(1));
    }

    @Test
    public void testLlenarDepartamentos_ExcepcionSQL() {
        JComboBox combo = new JComboBox();
        mockContext.throwExceptionOnPreparedStatement = true;

        try {
            dao.llenarDepartamentos(combo);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, combo.getItemCount());
    }

    @Test
    public void testLlenarSolicitantes_Exito() {
        JComboBox combo = new JComboBox();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("nombre", "Carlos");
        f1.put("apellidoP", "López");
        f1.put("apellidoM", "Gómez");
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.llenarSolicitantes(combo, "Cocina");

        assertEquals(1, combo.getItemCount());
        assertEquals("Carlos López Gómez", combo.getItemAt(0));
    }

    @Test
    public void testLlenarSolicitantes_ExcepcionSQL() {
        JComboBox combo = new JComboBox();
        mockContext.throwExceptionOnPreparedStatement = true;

        try {
            dao.llenarSolicitantes(combo, "Cocina");
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, combo.getItemCount());
    }

    @Test
    public void testMostrarMaterialesSolicitados_Exito() {
        JTable tabla = new JTable();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("nombreIngrediente", "Harina");
        f1.put("cantidadNecesaria", 10.0);
        f1.put("medida", "Kg");
        f1.put("precioUnitario", 15.0);
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.mostrarMaterialesSolicitados(tabla, 10);

        assertEquals(1, tabla.getRowCount());
        assertEquals("Harina", tabla.getValueAt(0, 1));
        assertEquals(150.0, (Double) tabla.getValueAt(0, 5), 0.001);
    }

    @Test
    public void testMostrarMaterialesSolicitados_ExcepcionSQL() {
        JTable tabla = new JTable();
        mockContext.throwExceptionOnPreparedStatement = true;

        try {
            dao.mostrarMaterialesSolicitados(tabla, 10);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, tabla.getRowCount());
    }

    @Test
    public void testObtenerIdDepartamento_Exito() {
        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("idDepartamento", 5);
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        int id = dao.obtenerIdDepartamento("Cocina");
        assertEquals(5, id);
    }

    @Test
    public void testObtenerIdDepartamento_NoEncontrado() {
        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(new ArrayList<>());

        int id = dao.obtenerIdDepartamento("Inexistente");
        assertEquals(-1, id);
    }

    @Test
    public void testObtenerIdIngrediente_Exito() {
        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("idIngrediente", 12);
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        int id = dao.obtenerIdIngrediente("Azúcar");
        assertEquals(12, id);
    }

    @Test
    public void testObtenerIdIngrediente_NoEncontrado() {
        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(new ArrayList<>());

        int id = dao.obtenerIdIngrediente("Fantasma");
        assertEquals(-1, id);
    }

    @Test
    public void testGuardarDetalleRequisicion_Exito() {
        boolean resultado = dao.guardarDetalleRequisicion(1, 12, 5.0, 20.0, 100.0);
        assertTrue(resultado);
    }

    @Test
    public void testGuardarDetalleRequisicion_ExcepcionSQL() {
        mockContext.throwExceptionOnExecuteUpdate = true;
        boolean resultado = dao.guardarDetalleRequisicion(1, 12, 5.0, 20.0, 100.0);
        assertFalse(resultado);
    }

    @Test
    public void testGuardarRequisicion_Exito() {
        Requisicion req = createSampleRequisicion();

        List<Map<String, Object>> keys = new ArrayList<>();
        Map<String, Object> keyRow = new HashMap<>();
        keyRow.put("1", 200);
        keys.add(keyRow);

        mockContext.generatedKeysResultSet = mockContext.createMockResultSet(keys);

        int idGenerado = dao.guardarRequisicion(req);
        assertEquals(200, idGenerado);
    }

    @Test
    public void testGuardarRequisicion_ExcepcionSQL() {
        Requisicion req = createSampleRequisicion();
        mockContext.throwExceptionOnExecuteUpdate = true;

        int resultado = dao.guardarRequisicion(req);
        assertEquals(-1, resultado);
    }

    @Test
    public void testMostrarRequisiciones_Exito() {
        JTable tabla = new JTable();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("idRequisicion", "200");
        f1.put("fecha", "2026-03-01");
        f1.put("nombreDepartamento", "Cocina");
        f1.put("solicitante", "Carlos");
        f1.put("idPedido", "10");
        f1.put("total", "100.0");
        filas.add(f1);

        mockContext.statementResultSet = mockContext.createMockResultSet(filas);

        dao.mostrarRequisiciones(tabla);

        assertEquals(1, tabla.getRowCount());
        assertEquals("200", tabla.getValueAt(0, 0));
        assertEquals("Cocina", tabla.getValueAt(0, 2));
    }

    @Test
    public void testMostrarRequisiciones_ExcepcionSQL() {
        JTable tabla = new JTable();
        mockContext.throwExceptionOnStatement = true;

        try {
            dao.mostrarRequisiciones(tabla);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, tabla.getRowCount());
    }

    @Test
    public void testEliminarRequisicion_Exito() {
        boolean resultado = dao.eliminarRequisicion(200);
        assertTrue(resultado);
    }

    @Test
    public void testEliminarRequisicion_ExcepcionSQL() {
        mockContext.throwExceptionOnExecuteUpdate = true;
        boolean resultado = dao.eliminarRequisicion(200);
        assertFalse(resultado);
    }

    @Test
    public void testMostrarDetalleRequisicion_Exito() {
        JTable tabla = new JTable();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("nombreIngrediente", "Azúcar");
        f1.put("medida", "Kg");
        f1.put("cantidad", 5.0);
        f1.put("costoUnitario", 20.0);
        f1.put("total", 100.0);
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.mostrarDetalleRequisicion(tabla, 200);

        assertEquals(1, tabla.getRowCount());
        assertEquals("Azúcar", tabla.getValueAt(0, 1));
    }

    @Test
    public void testDescontarInventario_Exito() {
        boolean resultado = dao.descontarInventario(12, 5.0);
        assertTrue(resultado);
    }

    @Test
    public void testDescontarInventario_ExcepcionSQL() {
        mockContext.throwExceptionOnExecuteUpdate = true;
        boolean resultado = dao.descontarInventario(12, 5.0);
        assertFalse(resultado);
    }

    @Test
    public void testVerificarExistencia_Suficiente() {
        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("cantidad", 10.0);
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        boolean existe = dao.verificarExistencia(12, 5.0);
        assertTrue(existe);
    }

    @Test
    public void testVerificarExistencia_Insuficiente() {
        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("cantidad", 2.0);
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        boolean existe = dao.verificarExistencia(12, 5.0);
        assertFalse(existe);
    }

    private Requisicion createSampleRequisicion() {
        Requisicion r = new Requisicion();
        r.setFecha(new Date());
        r.setIdDepartamento(5);
        r.setIdPedido(10);
        r.setSolicitante("Carlos López");
        r.setObservaciones("Ninguna");
        r.setTotal(100.0);
        r.setEntregadoPor("Ana");
        r.setRecibidoPor("Pedro");
        return r;
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