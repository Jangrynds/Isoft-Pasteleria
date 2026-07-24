package Modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javax.swing.JComboBox;
import org.junit.Test;
import static org.junit.Assert.*;

public class CategoriaProductoDAOTest {

    // STUB 1: Para simular PreparedStatement (Usado en obtenerIdCategoria)
    private Connection crearConexionStubPreparedStatement(boolean encontrarRegistro, int idRetornado) {
        return (Connection) Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[] { Connection.class },
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (method.getName().equals("prepareStatement")) {
                        return Proxy.newProxyInstance(
                            PreparedStatement.class.getClassLoader(),
                            new Class<?>[] { PreparedStatement.class },
                            new InvocationHandler() {
                                @Override
                                public Object invoke(Object psProxy, Method psMethod, Object[] psArgs) throws Throwable {
                                    if (psMethod.getName().equals("executeQuery")) {
                                        return Proxy.newProxyInstance(
                                            ResultSet.class.getClassLoader(),
                                            new Class<?>[] { ResultSet.class },
                                            new InvocationHandler() {
                                                private boolean hasNextCalled = false;
                                                @Override
                                                public Object invoke(Object rsProxy, Method rsMethod, Object[] rsArgs) throws Throwable {
                                                    if (rsMethod.getName().equals("next")) {
                                                        if (!hasNextCalled && encontrarRegistro) {
                                                            hasNextCalled = true;
                                                            return true;
                                                        }
                                                        return false;
                                                    }
                                                    if (rsMethod.getName().equals("getInt")) {
                                                        return idRetornado;
                                                    }
                                                    return null;
                                                }
                                            }
                                        );
                                    }
                                    return null;
                                }
                            }
                        );
                    }
                    return null;
                }
            }
        );
    }

    // STUB 2: Para simular Statement (Usado en mostrarMedidasIngredientes)
    private Connection crearConexionStubStatement() {
        return (Connection) Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[] { Connection.class },
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (method.getName().equals("createStatement")) {
                        return Proxy.newProxyInstance(
                            Statement.class.getClassLoader(),
                            new Class<?>[] { Statement.class },
                            new InvocationHandler() {
                                @Override
                                public Object invoke(Object stProxy, Method stMethod, Object[] stArgs) throws Throwable {
                                    if (stMethod.getName().equals("executeQuery")) {
                                        return Proxy.newProxyInstance(
                                            ResultSet.class.getClassLoader(),
                                            new Class<?>[] { ResultSet.class },
                                            new InvocationHandler() {
                                                private int count = 0;
                                                @Override
                                                public Object invoke(Object rsProxy, Method rsMethod, Object[] rsArgs) throws Throwable {
                                                    if (rsMethod.getName().equals("next")) {
                                                        count++;
                                                        return count <= 2; // Simulamos que encuentra 2 registros
                                                    }
                                                    if (rsMethod.getName().equals("getString")) {
                                                        if (count == 1) return "Kilogramos";
                                                        if (count == 2) return "Litros";
                                                    }
                                                    return null;
                                                }
                                            }
                                        );
                                    }
                                    return null;
                                }
                            }
                        );
                    }
                    return null;
                }
            }
        );
    }

    // PRUEBAS PARA: obtenerIdCategoria
    @Test
    public void testObtenerIdCategoria_CaminoFeliz() {
        Connection conexionStub = crearConexionStubPreparedStatement(true, 77);
        CategoriaProductoDAO dao = new CategoriaProductoDAO(conexionStub);
        int resultado = dao.obtenerIdCategoria("Pasteles");
        assertEquals("Debe retornar el ID 77 para una categoría válida", 77, resultado);
    }

    @Test
    public void testObtenerIdCategoria_CaminoExcepcion_CategoriaInexistente() {
        Connection conexionStub = crearConexionStubPreparedStatement(false, 0);
        CategoriaProductoDAO dao = new CategoriaProductoDAO(conexionStub);
        int resultado = dao.obtenerIdCategoria("Inexistente999");
        assertEquals("Debe retornar 0 para una categoría inexistente", 0, resultado);
    }

    @Test
    public void testObtenerIdCategoria_CaminoExcepcion_NombreNulo() {
        Connection conexionStub = crearConexionStubPreparedStatement(false, 0);
        CategoriaProductoDAO dao = new CategoriaProductoDAO(conexionStub);
        int resultado = dao.obtenerIdCategoria(null);
        assertEquals("Debe retornar 0 si se pasa un valor null", 0, resultado);
    }

    // PRUEBAS PARA: mostrarMedidasIngredientes
    @Test
    public void testMostrarMedidasIngredientes_CaminoFeliz() {
        try {
            JComboBox<String> combo = new JComboBox<>();
            Connection conexionStub = crearConexionStubStatement();
            CategoriaProductoDAO dao = new CategoriaProductoDAO(conexionStub);
            
            dao.mostrarMedidasIngredientes(combo);

            assertEquals("El ComboBox debe contener exactamente 2 elementos", 2, combo.getItemCount());
            assertEquals("El primer elemento debe ser Kilogramos", "Kilogramos", combo.getItemAt(0));
            assertEquals("El segundo elemento debe ser Litros", "Litros", combo.getItemAt(1));
        } catch (Exception e) {
            fail("La prueba falló por una excepción inesperada: " + e.getMessage());
        }
    }

    @Test
    public void testMostrarMedidasIngredientes_CaminoExcepcion_ComboNulo() {
        CategoriaProductoDAO dao = new CategoriaProductoDAO();
        try {
            dao.mostrarMedidasIngredientes(null);
            assertTrue("El método controló correctamente la excepción al recibir un JComboBox nulo", true);
        } catch (Exception e) {
            fail("El método propagó una excepción no controlada: " + e.getMessage());
        }
    }
}