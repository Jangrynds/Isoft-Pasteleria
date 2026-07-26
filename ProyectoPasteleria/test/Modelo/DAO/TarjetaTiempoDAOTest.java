package Modelo.DAO;

import Modelo.Clases.TarjetaTiempo;
import org.junit.Before;
import org.junit.Test;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
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

public class TarjetaTiempoDAOTest {

    private MockDatabaseContext mockContext;
    private TarjetaTiempoDAO dao;

    @Before
    public void setUp() {
        System.setProperty("java.awt.headless", "true");
        mockContext = new MockDatabaseContext();
        dao = new TarjetaTiempoDAO(mockContext::getConnection);
    }

    @Test
    public void testLlenarEmpleados_Exito() {
        JComboBox combo = new JComboBox();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("idEmpleado", 10);
        f1.put("nombre", "Juan");
        f1.put("apellidoP", "Pérez");
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.llenarEmpleados(combo);

        assertEquals(1, combo.getItemCount());
        assertEquals("10 - Juan Pérez", combo.getItemAt(0));
    }

    @Test
    public void testLlenarEmpleados_ExcepcionSQL() {
        JComboBox combo = new JComboBox();
        mockContext.throwExceptionOnPreparedStatement = true;

        try {
            dao.llenarEmpleados(combo);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, combo.getItemCount());
    }

    @Test
    public void testObtenerDatosEmpleado_Exito() {
        JTextField txtId = new JTextField();
        JTextField txtTasa = new JTextField();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("idEmpleado", "10");
        f1.put("salarioHora", "85.50");
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.obtenerDatosEmpleado(10, txtId, txtTasa);

        assertEquals("10", txtId.getText());
        assertEquals("85.50", txtTasa.getText());
    }

    @Test
    public void testObtenerDatosEmpleado_NoEncontrado() {
        JTextField txtId = new JTextField();
        JTextField txtTasa = new JTextField();

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(new ArrayList<>());

        dao.obtenerDatosEmpleado(99, txtId, txtTasa);

        assertEquals("", txtId.getText());
        assertEquals("", txtTasa.getText());
    }

    @Test
    public void testObtenerDatosEmpleado_ExcepcionSQL() {
        JTextField txtId = new JTextField();
        JTextField txtTasa = new JTextField();
        mockContext.throwExceptionOnPreparedStatement = true;

        try {
            dao.obtenerDatosEmpleado(10, txtId, txtTasa);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals("", txtId.getText());
    }

    @Test
    public void testLlenarNoPedido_Exito() {
        JComboBox combo = new JComboBox();

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("idPedido", 101);
        f1.put("cliente", "Pastelería Rosa");
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.llenarNoPedido(combo);

        assertEquals(1, combo.getItemCount());
        assertEquals("101 - Pastelería Rosa", combo.getItemAt(0));
    }

    @Test
    public void testLlenarNoPedido_ExcepcionSQL() {
        JComboBox combo = new JComboBox();
        mockContext.throwExceptionOnPreparedStatement = true;

        try {
            dao.llenarNoPedido(combo);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, combo.getItemCount());
    }

    @Test
    public void testGuardarTarjetaTiempo_Exito() {
        TarjetaTiempo tt = createSampleTarjeta();

        List<Map<String, Object>> keys = new ArrayList<>();
        Map<String, Object> keyRow = new HashMap<>();
        keyRow.put("1", 1);
        keys.add(keyRow);

        mockContext.generatedKeysResultSet = mockContext.createMockResultSet(keys);

        int idGenerado = dao.guardarTarjetaTiempo(tt);
        assertEquals(1, idGenerado);
    }

    @Test
    public void testGuardarTarjetaTiempo_ExcepcionSQL() {
        TarjetaTiempo tt = createSampleTarjeta();
        mockContext.throwExceptionOnExecuteUpdate = true;

        int idGenerado = dao.guardarTarjetaTiempo(tt);
        assertEquals(0, idGenerado);
    }

    @Test
    public void testGuardarTarjetaTiempo_SinClavesGeneradas() {
        TarjetaTiempo tt = createSampleTarjeta();
        mockContext.generatedKeysResultSet = mockContext.createMockResultSet(new ArrayList<>());

        int idGenerado = dao.guardarTarjetaTiempo(tt);
        assertEquals(0, idGenerado);
    }

    @Test
    public void testGuardarDetalleTiempo_Exito() {
        boolean resultado = dao.guardarDetalleTiempo(1, "Lunes", "08:00", "16:00", 8.0, 85.50, 684.0);
        assertTrue(resultado);
    }

    @Test
    public void testGuardarDetalleTiempo_ExcepcionSQL() {
        mockContext.throwExceptionOnExecuteUpdate = true;
        boolean resultado = dao.guardarDetalleTiempo(1, "Lunes", "08:00", "16:00", 8.0, 85.50, 684.0);
        assertFalse(resultado);
    }

    @Test
    public void testMostrarTarjetas_Exito() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Empleado");
        modelo.addColumn("ID Emp");
        modelo.addColumn("ID Pedido");
        modelo.addColumn("Fecha");
        modelo.addColumn("Horas");
        modelo.addColumn("Costo");
        modelo.addColumn("Obs");
        JTable tabla = new JTable(modelo);

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("idTarjeta", 1);
        f1.put("idEmpleado", 10);
        f1.put("empleado", "Juan Pérez");
        f1.put("idPedido", 101);
        f1.put("fecha", new java.sql.Date(new Date().getTime()));
        f1.put("totalHoras", 8.0);
        f1.put("totalCosto", 684.0);
        f1.put("observaciones", "Turno Completo");
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.mostrarTarjetas(tabla);

        assertEquals(1, tabla.getRowCount());
        assertEquals("Juan Pérez", tabla.getValueAt(0, 1));
    }

    @Test
    public void testMostrarTarjetas_ExcepcionSQL() {
        JTable tabla = new JTable(new DefaultTableModel());
        mockContext.throwExceptionOnPreparedStatement = true;

        try {
            dao.mostrarTarjetas(tabla);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, tabla.getRowCount());
    }

    @Test
    public void testMostrarDetalleTarjeta_Exito() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Día");
        modelo.addColumn("Inicio");
        modelo.addColumn("Fin");
        modelo.addColumn("Horas");
        modelo.addColumn("Tasa");
        modelo.addColumn("Costo");
        JTable tabla = new JTable(modelo);

        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("dia", "Lunes");
        f1.put("horaInicio", "08:00");
        f1.put("horaFin", "16:00");
        f1.put("tiempoTotal", 8.0);
        f1.put("tasaHora", 85.50);
        f1.put("costoTotal", 684.0);
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        dao.mostrarDetalleTarjeta(tabla, 1);

        assertEquals(1, tabla.getRowCount());
        assertEquals(1, tabla.getValueAt(0, 0));
        assertEquals("Lunes", tabla.getValueAt(0, 1));
    }

    @Test
    public void testMostrarDetalleTarjeta_ExcepcionSQL() {
        JTable tabla = new JTable(new DefaultTableModel());
        mockContext.throwExceptionOnPreparedStatement = true;

        try {
            dao.mostrarDetalleTarjeta(tabla, 1);
        } catch (Exception e) {
            fail("No deberia lanzar excepcion hacia afuera: " + e.getMessage());
        }
        assertEquals(0, tabla.getRowCount());
    }

    @Test
    public void testObtenerTarjetaTiempo_Exito() {
        List<Map<String, Object>> filas = new ArrayList<>();
        Map<String, Object> f1 = new HashMap<>();
        f1.put("idTarjeta", 1);
        f1.put("idEmpleado", 10);
        f1.put("idPedido", 101);
        f1.put("fecha", new java.sql.Date(new Date().getTime()));
        f1.put("observaciones", "Turno Completo");
        f1.put("totalHoras", 8.0);
        f1.put("totalCosto", 684.0);
        filas.add(f1);

        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(filas);

        TarjetaTiempo tt = dao.obtenerTarjetaTiempo(1);

        assertEquals(1, tt.getIdTarjeta());
        assertEquals(10, tt.getIdEmpleado());
        assertEquals(101, tt.getIdPedido());
    }

    @Test
    public void testObtenerTarjetaTiempo_NoEncontrado() {
        mockContext.preparedStatementResultSet = mockContext.createMockResultSet(new ArrayList<>());

        TarjetaTiempo tt = dao.obtenerTarjetaTiempo(99);

        assertEquals(0, tt.getIdTarjeta());
    }

    @Test
    public void testObtenerTarjetaTiempo_ExcepcionSQL() {
        mockContext.throwExceptionOnPreparedStatement = true;

        TarjetaTiempo tt = dao.obtenerTarjetaTiempo(1);

        assertEquals(0, tt.getIdTarjeta());
    }

    @Test
    public void testActualizarTarjeta_Exito() {
        TarjetaTiempo tt = createSampleTarjeta();
        tt.setIdTarjeta(1);

        boolean resultado = dao.actualizarTarjeta(tt);
        assertTrue(resultado);
    }

    @Test
    public void testActualizarTarjeta_ExcepcionSQL() {
        TarjetaTiempo tt = createSampleTarjeta();
        tt.setIdTarjeta(1);
        mockContext.throwExceptionOnExecuteUpdate = true;

        boolean resultado = dao.actualizarTarjeta(tt);
        assertFalse(resultado);
    }

    private TarjetaTiempo createSampleTarjeta() {
        TarjetaTiempo tt = new TarjetaTiempo();
        tt.setIdEmpleado(10);
        tt.setIdPedido(101);
        tt.setFecha(new Date());
        tt.setObservaciones("Turno Completo");
        tt.setTotalHoras(8.0);
        tt.setTotalCosto(684.0);
        return tt;
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
                            if ("getString".equals(mName) || "getInt".equals(mName) || "getDouble".equals(mName) || "getDate".equals(mName)) {
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
                                if ("getDate".equals(mName)) {
                                    if (val instanceof java.sql.Date) return val;
                                    if (val instanceof Date) return new java.sql.Date(((Date) val).getTime());
                                    return new java.sql.Date(System.currentTimeMillis());
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