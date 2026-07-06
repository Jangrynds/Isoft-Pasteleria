/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

/**
 *
 * @author ingri
 */
public class Sesion {
    
    public static int idEmpleado;
    public static int idDepartamento;
    
    public Sesion(){
        
    }

    public static int getIdEmpleado() {
        return idEmpleado;
    }

    public static int getIdDepartamento() {
        return idDepartamento;
    }

    public static void setIdEmpleado(int idEmpleado) {
        Sesion.idEmpleado = idEmpleado;
    }

    public static void setIdDepartamento(int idDepartamento) {
        Sesion.idDepartamento = idDepartamento;
    }
    
    
}
