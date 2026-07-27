package Modelo.DAO;

import Modelo.Clases.Ingrediente;
import org.junit.Before;
import org.junit.Test;
import javax.swing.JTable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class IngredienteDAOTest {

    private JTable tablaPrueba;

    @Before
    public void setUp() {
        tablaPrueba = new JTable();
    }

    // ==========================================
    // STUBS MEDIANTE PROXY DINÁMICO NATIVO
    // ==========================================

    private Connection crearConnectionStub(boolean lanzarExcepcion) {
        return (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (lanzarExcepcion) {
                            throw new SQLException("Error simulado de Base de Datos");
                        }
                        if ("createStatement".equals(method.getName())) {
                            return crearStatementStub(false);
                        }
                        if ("prepareStatement".equals(method.getName())) {
                            return crearPreparedStatementStub(false);
                        }
                        return null;
                    }
                }
        );
    }

    private Statement crearStatementStub(boolean lanzarExcepcion) {
        return (Statement) Proxy.newProxyInstance(
                Statement.class.getClassLoader(),
                new Class<?>[]{Statement.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (lanzarExcepcion) {
                            throw new SQLException("Error en Statement");
                        }
                        if ("executeQuery".equals(method.getName())) {
                            return crearResultSetStub();
                        }
                        return null;
                    }
                }
        );
    }

    private PreparedStatement crearPreparedStatementStub(boolean lanzarExcepcion) {
        return (PreparedStatement) Proxy.newProxyInstance(
                PreparedStatement.class.getClassLoader(),
                new Class<?>[]{PreparedStatement.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (lanzarExcepcion) {
                            throw new SQLException("Error en PreparedStatement");
                        }
                        if ("execute".equals(method.getName())) {
                            return true;
                        }
                        if ("executeUpdate".equals(method.getName())) {
                            return 1;
                        }
                        return null;
                    }
                }
        );
    }

    private ResultSet crearResultSetStub() {
        return (ResultSet) Proxy.newProxyInstance(
                ResultSet.class.getClassLoader(),
                new Class<?>[]{ResultSet.class},
                new InvocationHandler() {
                    private int contadorFilas = 0;

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String nombreMetodo = method.getName();

                        if ("next".equals(nombreMetodo)) {
                            contadorFilas++;
                            return contadorFilas <= 1; // 1 sola fila de datos simulada
                        }
                        if ("getDouble".equals(nombreMetodo)) {
                            String col = (String) args[0];
                            if ("cantidad".equals(col)) return 5.0;
                            if ("precioUnitario".equals(col)) return 12.5;
                        }
                        if ("getString".equals(nombreMetodo)) {
                            String col = (String) args[0];
                            if ("idIngrediente".equals(col)) return "1";
                            if ("nombreIngrediente".equals(col)) return "Harina";
                            if ("medida".equals(col)) return "Kg";
                        }
                        return null;
                    }
                }
        );
    }

    // ==========================================
    // CASOS DE PRUEBA: mostrarIngredientes
    // ==========================================

    @Test
    public void testMostrarIngredientes_Exito() {
        Connection conMock = crearConnectionStub(false);
        IngredienteDAO dao = new IngredienteDAO(conMock);

        dao.mostrarIngredientes(tablaPrueba);

        assertEquals(1, tablaPrueba.getRowCount());
        assertEquals("1", tablaPrueba.getValueAt(0, 0));
        assertEquals("Harina", tablaPrueba.getValueAt(0, 1));
        assertEquals("5.0", tablaPrueba.getValueAt(0, 2));
        assertEquals("Kg", tablaPrueba.getValueAt(0, 3));
        assertEquals("12.5", tablaPrueba.getValueAt(0, 4));
        assertEquals("62.5", tablaPrueba.getValueAt(0, 5)); // 5.0 * 12.5 = 62.5
    }

    @Test
    public void testMostrarIngredientes_ExcepcionBD() {
        Connection conMock = crearConnectionStub(true); // Lanza error
        IngredienteDAO dao = new IngredienteDAO(conMock);

        dao.mostrarIngredientes(tablaPrueba);

        // El catch captura la excepción y no inserta filas
        assertEquals(0, tablaPrueba.getRowCount());
    }

    // ==========================================
    // CASOS DE PRUEBA: guardarIngrediente
    // ==========================================

    @Test
    public void testGuardarIngrediente_Exito() {
        Connection conMock = crearConnectionStub(false);
        IngredienteDAO dao = new IngredienteDAO(conMock);

        Ingrediente ing = new Ingrediente();
        ing.setNombreIngrediente("Azúcar");
        ing.setCantidad(10);
        ing.setMedida("Kg");
        ing.setPrecioUnitario(20.0);

        boolean resultado = dao.guardarIngrediente(ing);

        assertTrue("El guardado debe retornar true", resultado);
    }

    @Test
    public void testGuardarIngrediente_ObjetoNulo() {
        Connection conMock = crearConnectionStub(false);
        IngredienteDAO dao = new IngredienteDAO(conMock);

        boolean resultado = dao.guardarIngrediente(null);

        assertFalse("Debe capturar NullPointerException y retornar false", resultado);
    }

    @Test
    public void testGuardarIngrediente_ExcepcionBD() {
        Connection conMock = crearConnectionStub(true);
        IngredienteDAO dao = new IngredienteDAO(conMock);

        Ingrediente ing = new Ingrediente();
        ing.setNombreIngrediente("Azúcar");

        boolean resultado = dao.guardarIngrediente(ing);

        assertFalse("Debe capturar SQLException y retornar false", resultado);
    }

    // ==========================================
    // CASOS DE PRUEBA: editarIngrediente
    // ==========================================

    @Test
    public void testEditarIngrediente_Exito() {
        Connection conMock = crearConnectionStub(false);
        IngredienteDAO dao = new IngredienteDAO(conMock);

        Ingrediente ing = new Ingrediente();
        ing.setIdIngrediente(1);
        ing.setNombreIngrediente("Harina Integral");
        ing.setCantidad(8);
        ing.setMedida("Kg");
        ing.setPrecioUnitario(15.0);

        boolean resultado = dao.editarIngrediente(ing);

        assertTrue("La edición debe retornar true", resultado);
    }

    @Test
    public void testEditarIngrediente_ObjetoNulo() {
        Connection conMock = crearConnectionStub(false);
        IngredienteDAO dao = new IngredienteDAO(conMock);

        boolean resultado = dao.editarIngrediente(null);

        assertFalse("Debe capturar NullPointerException y retornar false", resultado);
    }

    @Test
    public void testEditarIngrediente_ExcepcionBD() {
        Connection conMock = crearConnectionStub(true);
        IngredienteDAO dao = new IngredienteDAO(conMock);

        Ingrediente ing = new Ingrediente();
        ing.setIdIngrediente(1);

        boolean resultado = dao.editarIngrediente(ing);

        assertFalse("Debe capturar SQLException y retornar false", resultado);
    }

    // ==========================================
    // CASOS DE PRUEBA: eliminarIngrediente
    // ==========================================

    @Test
    public void testEliminarIngrediente_Exito() {
        Connection conMock = crearConnectionStub(false);
        IngredienteDAO dao = new IngredienteDAO(conMock);

        boolean resultado = dao.eliminarIngrediente(1);

        assertTrue("La eliminación debe retornar true", resultado);
    }

    @Test
    public void testEliminarIngrediente_ExcepcionBD() {
        Connection conMock = crearConnectionStub(true);
        IngredienteDAO dao = new IngredienteDAO(conMock);

        boolean resultado = dao.eliminarIngrediente(1);

        assertFalse("Debe capturar SQLException y retornar false", resultado);
    }
}