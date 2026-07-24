package Modelo.DAO;

import Modelo.Clases.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javax.swing.JTable;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmpleadoDAOTest {

    // STUB 1: Simular Statement y ResultSet (Para mostrarEmpleados)
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
                                                private boolean leido = false;
                                                @Override
                                                public Object invoke(Object rsProxy, Method rsMethod, Object[] rsArgs) throws Throwable {
                                                    if (rsMethod.getName().equals("next")) {
                                                        if (!leido) { leido = true; return true; }
                                                        return false;
                                                    }
                                                    if (rsMethod.getName().equals("getString")) {
                                                        String columna = (String) rsArgs[0];
                                                        switch (columna) {
                                                            case "idEmpleado": return "1";
                                                            case "nombre": return "Carlos";
                                                            case "apellidoP": return "Lopez";
                                                            case "apellidoM": return "Gomez";
                                                            case "telefono": return "5551234";
                                                            case "nombreDepartamento": return "Sistemas";
                                                            case "salarioHora": return "200.50";
                                                        }
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

    // STUB 2: Simular PreparedStatement (Para CRUD)
    private Connection crearConexionStubPreparedStatement(boolean exito) {
        return (Connection) Proxy.newProxyInstance(
            Connection.class.getClassLoader(),
            new Class<?>[] { Connection.class },
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (method.getName().equals("prepareStatement")) {
                        if (!exito) {
                            throw new SQLException("Simulación de error en la BD");
                        }
                        return Proxy.newProxyInstance(
                            PreparedStatement.class.getClassLoader(),
                            new Class<?>[] { PreparedStatement.class },
                            new InvocationHandler() {
                                @Override
                                public Object invoke(Object psProxy, Method psMethod, Object[] psArgs) throws Throwable {
                                    // Simula la ejecución exitosa de execute o executeUpdate
                                    if (psMethod.getName().equals("execute") || psMethod.getName().equals("executeUpdate")) {
                                        return psMethod.getName().equals("executeUpdate") ? 1 : true;
                                    }
                                    return null; // Ignora los setString, setInt, etc.
                                }
                            }
                        );
                    }
                    return null;
                }
            }
        );
    }

    // PRUEBAS: mostrarEmpleados
    @Test
    public void testMostrarEmpleados_CaminoFeliz() {
        JTable tabla = new JTable();
        Connection stub = crearConexionStubStatement();
        EmpleadoDAO dao = new EmpleadoDAO(stub);
        
        dao.mostrarEmpleados(tabla);
        
        assertEquals("La tabla debe contener 1 fila de resultados", 1, tabla.getRowCount());
        assertEquals("El nombre en la primera celda debe ser Carlos", "Carlos", tabla.getValueAt(0, 1));
    }

  @Test
    public void testMostrarEmpleados_CaminoExcepcion_TablaNula() {
        Connection stub = crearConexionStubStatement();
        EmpleadoDAO dao = new EmpleadoDAO(stub);
        
        try {
            dao.mostrarEmpleados(null);
            // Si llega aquí sin fallar, algo está mal, porque debió lanzar error
            fail("El método debió lanzar NullPointerException porque la tabla se configura antes del try-catch");
        } catch (NullPointerException e) {
            // La prueba PASA exitosamente porque interceptamos el error esperado
            assertTrue("La prueba detectó correctamente el NullPointerException esperado", true);
        } catch (Exception e) {
            fail("Lanzó una excepción diferente a la esperada: " + e.getMessage());
        }
    }

    // PRUEBAS: guardarEmpleado
    @Test
    public void testGuardarEmpleado_CaminoFeliz() {
        // Llenamos TODOS los datos para evitar el NullPointerException por valores vacíos
        Empleado emp = new Empleado();
        emp.setNombre("Ana");
        emp.setApellidoPaterno("Gomez");
        emp.setApellidoMaterno("Perez");
        emp.setTelefono("5551234567");
        emp.setIdDepartamento(1);
        emp.setSalarioHora(150.0);
        
        Connection stub = crearConexionStubPreparedStatement(true);
        EmpleadoDAO dao = new EmpleadoDAO(stub);
        
        boolean resultado = dao.guardarEmpleado(emp);
        assertTrue("Debe retornar true al guardar exitosamente", resultado);
    }

    @Test
    public void testGuardarEmpleado_CaminoExcepcion_ObjetoNulo() {
        Connection stub = crearConexionStubPreparedStatement(true);
        EmpleadoDAO dao = new EmpleadoDAO(stub);
        
        boolean resultado = dao.guardarEmpleado(null);
        assertFalse("Debe retornar false al manejar la excepción por objeto nulo", resultado);
    }

    // PRUEBAS: editarEmpleado
    @Test
   public void testEditarEmpleado_CaminoFeliz() {
        // Llenamos TODOS los datos, incluyendo el ID para el WHERE del UPDATE
        Empleado emp = new Empleado();
        emp.setIdEmpleado(1);
        emp.setNombre("Ana");
        emp.setApellidoPaterno("Gomez");
        emp.setApellidoMaterno("Perez");
        emp.setTelefono("5551234567");
        emp.setIdDepartamento(1);
        emp.setSalarioHora(150.0);
        
        Connection stub = crearConexionStubPreparedStatement(true);
        EmpleadoDAO dao = new EmpleadoDAO(stub);
        
        boolean resultado = dao.editarEmpleado(emp);
        assertTrue("Debe retornar true al editar exitosamente", resultado);
    }

    @Test
    public void testEditarEmpleado_CaminoExcepcion_ObjetoNulo() {
        Connection stub = crearConexionStubPreparedStatement(true);
        EmpleadoDAO dao = new EmpleadoDAO(stub);
        
        boolean resultado = dao.editarEmpleado(null);
        assertFalse("Debe retornar false al manejar la excepción por objeto nulo", resultado);
    }

    // PRUEBAS: eliminarEmpleado
    @Test
    public void testEliminarEmpleado_CaminoFeliz() {
        Connection stub = crearConexionStubPreparedStatement(true);
        EmpleadoDAO dao = new EmpleadoDAO(stub);
        
        boolean resultado = dao.eliminarEmpleado(1);
        assertTrue("Debe retornar true al eliminar exitosamente", resultado);
    }

    @Test
    public void testEliminarEmpleado_CaminoExcepcion_FalloBD() {
        // Inyectamos false para forzar una SQLException en el Stub
        Connection stub = crearConexionStubPreparedStatement(false);
        EmpleadoDAO dao = new EmpleadoDAO(stub);
        
        boolean resultado = dao.eliminarEmpleado(999);
        assertFalse("Debe retornar false al atrapar el error de la BD simulada", resultado);
    }
}