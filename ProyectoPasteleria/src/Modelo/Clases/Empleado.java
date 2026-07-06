/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

/**
 *
 * @author ingri
 */
public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private int idDepartamento;
    private String contrasena;
    private Double salarioHora;

    public Empleado(int idEmpleado, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, int idDepartamento, String contrasena) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.idDepartamento = idDepartamento;
        this.contrasena = contrasena;
    }
    
    public Empleado(){
        
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }
    
    public String getContrasena(){
        return contrasena;
    }

    public Double getSalarioHora() {
        return salarioHora;
    }
    
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }
    
    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }

    public void setSalarioHora(Double salarioHora) {
        this.salarioHora = salarioHora;
    }
}
