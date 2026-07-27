package Modelo.DAO;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JComboBox;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MedidasIngredientesDAOTest {

    private JComboBox<String> comboPrueba;

    @Before
    public void setUp() {
        comboPrueba = new JComboBox<>();
    }

    // ==========================================
    // STUBS MEDIANTE PROXIES DINÁMICOS NATIVOS
    // ==========================================

    private ResultSet crearResultSetStub(final List<String> datos) {
        return (ResultSet) Proxy.newProxyInstance(
            ResultSet.class.getClassLoader(),
            new Class<?>[]{ResultSet.class},
            new InvocationHandler() {
                private int index = -1;

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    String methodName = method.getName();
                    
                    if ("next".equals(methodName)) {
                        index++;
                        return index < datos.size();
                    }
                    if ("getString".equals(methodName)) {
                        String columnLabel = (String) args[0];
                        if ("nombreMedida".equals(columnLabel)) {
                            return datos.get(index);
                        }
                    }
                    if ("close".equals(methodName)) {
                        return null;
                    }
                    return null;
                }
            }
        );
    }

    private Statement crearStatementStub(final ResultSet rsStub, final boolean lanzarExcepcion) {
        return (Statement) Proxy.newProxyInstance(
            Statement.class.getClassLoader(),
            new Class<?>[]{Statement.class},
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("executeQuery".equals(method.getName())) {
                        if (lanzarExcepcion) {
                            throw new SQLException("Error simulado de la base de datos");
                        }
                        return rsStub;
                    }
                    if ("close".equals(method.getName())) {
                        return null;
                    }
                    return null;
                }
            }
        );
    }

    private Connection crearConexionStub(final Statement stStub) {
        return (Connection) Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[]{Connection.class},
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("createStatement".equals(method.getName())) {
                        return stStub;
                    }
                    if ("close".equals(method.getName())) {
                        return null;
                    }
                    return null;
                }
            }
        );
    }

    // ==========================================
    // CASOS DE PRUEBA (JUNIT 4)
    // ==========================================

    @Test
    public void testTC_DAO_01_MostrarMedidasIngredientes_Exito() {
        // Arrange
        List<String> medidasSimuladas = Arrays.asList("Kilos", "Litros", "Gramos");
        ResultSet rsStub = crearResultSetStub(medidasSimuladas);
        Statement stStub = crearStatementStub(rsStub, false);
        Connection connStub = crearConexionStub(stStub);

        MedidasIngredientesDAO dao = new MedidasIngredientesDAO(connStub);

        // Act
        dao.mostrarMedidasIngredientes(comboPrueba);

        // Assert
        Assert.assertEquals("El JComboBox debe contener exactamente 3 elementos.", 3, comboPrueba.getItemCount());
        Assert.assertEquals("El primer elemento debe ser Kilos.", "Kilos", comboPrueba.getItemAt(0));
        Assert.assertEquals("El segundo elemento debe ser Litros.", "Litros", comboPrueba.getItemAt(1));
        Assert.assertEquals("El tercer elemento debe ser Gramos.", "Gramos", comboPrueba.getItemAt(2));
    }

    @Test
    public void testTC_DAO_02_MostrarMedidasIngredientes_TablaVacia() {
        // Arrange
        List<String> medidasVacias = Collections.emptyList();
        ResultSet rsStub = crearResultSetStub(medidasVacias);
        Statement stStub = crearStatementStub(rsStub, false);
        Connection connStub = crearConexionStub(stStub);

        MedidasIngredientesDAO dao = new MedidasIngredientesDAO(connStub);

        // Act
        dao.mostrarMedidasIngredientes(comboPrueba);

        // Assert
        Assert.assertEquals("El JComboBox debe estar vacío si no hay registros.", 0, comboPrueba.getItemCount());
    }

    @Test
    public void testTC_DAO_03_MostrarMedidasIngredientes_ExcepcionSQL() {
        // Arrange
        Statement stStub = crearStatementStub(null, true); // Provoca SQLException
        Connection connStub = crearConexionStub(stStub);

        MedidasIngredientesDAO dao = new MedidasIngredientesDAO(connStub);

        // Act & Assert (El método captura la excepción en un try-catch interno y no rompe la ejecución)
        try {
            dao.mostrarMedidasIngredientes(comboPrueba);
            Assert.assertEquals("El JComboBox debe continuar vacío tras capturar la excepción.", 0, comboPrueba.getItemCount());
        } catch (Exception e) {
            Assert.fail("El método DAO no debió propagar la excepción hacia afuera: " + e.getMessage());
        }
    }

    @Test
    public void testTC_DAO_04_MostrarMedidasIngredientes_ComboNulo() {
        // Arrange
        List<String> medidasSimuladas = Collections.singletonList("Gramos");
        ResultSet rsStub = crearResultSetStub(medidasSimuladas);
        Statement stStub = crearStatementStub(rsStub, false);
        Connection connStub = crearConexionStub(stStub);

        MedidasIngredientesDAO dao = new MedidasIngredientesDAO(connStub);

        // Act & Assert
        try {
            dao.mostrarMedidasIngredientes(null);
            // Si no lanza excepción hacia afuera, significa que el try-catch interno manejó el NullPointerException
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail("El método DAO debió manejar el componente UI nulo internamente.");
        }
    }
}