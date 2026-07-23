package Modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;

public class CategoriaProductoDAOTest {

    // Método auxiliar para crear la conexión falsa (Stub) y ahorrarnos repetir código
    private Connection crearConexionStub(boolean encontrarRegistro, int idRetornado) {
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
                                                            return true; // Simula que encontró el registro
                                                        }
                                                        return false; // Simula que no encontró nada o fin
                                                    }
                                                    if (rsMethod.getName().equals("getInt")) {
                                                        return idRetornado; // Devuelve el ID configurado
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

    @Test
    public void testObtenerIdCategoria_CaminoFeliz_StubNativo() throws Exception {
        Connection conexionStub = crearConexionStub(true, 77);
        CategoriaProductoDAO dao = new CategoriaProductoDAO(conexionStub);
        
        int resultado = dao.obtenerIdCategoria("Pasteles");

        assertEquals("El DAO debe retornar el ID 77 simulado", 77, resultado);
    }

    @Test
    public void testObtenerIdCategoria_CaminoExcepcion_CategoriaInexistente() throws Exception {
        // Simulamos que la BD no encuentra el registro (encontrarRegistro = false)
        Connection conexionStub = crearConexionStub(false, 0);
        CategoriaProductoDAO dao = new CategoriaProductoDAO(conexionStub);
        
        int resultado = dao.obtenerIdCategoria("Inexistente999");

        assertEquals("El ID retornado para una categoría inexistente debe ser 0", 0, resultado);
    }

    @Test
    public void testObtenerIdCategoria_CaminoExcepcion_NombreNulo() {
        // Para nulo o vacío el DAO ni siquiera abre conexión, pero le pasamos el stub por seguridad del constructor
        Connection conexionStub = crearConexionStub(false, 0);
        CategoriaProductoDAO dao = new CategoriaProductoDAO(conexionStub);
        
        int resultado = dao.obtenerIdCategoria(null);

        assertEquals("El ID retornado para un nombre null debe ser 0", 0, resultado);
    }

    @Test
    public void testObtenerIdCategoria_CaminoExcepcion_NombreVacio() {
        Connection conexionStub = crearConexionStub(false, 0);
        CategoriaProductoDAO dao = new CategoriaProductoDAO(conexionStub);
        
        int resultado = dao.obtenerIdCategoria("");

        assertEquals("El ID retornado para un nombre vacío debe ser 0", 0, resultado);
    }
}