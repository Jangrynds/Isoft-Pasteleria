/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

/**
 *
 * @author ingri
 */
public class Departamento {
    private int idDepartamento;
    private String nombreDepartamento;
    private String descripcion;
    
    public Departamento(){
        
    };

    public int getIdDepartamento() {
        return idDepartamento;
    };

    public String getNombreDepartamento() {
        return nombreDepartamento;
    };

    public String getDescripcion() {
        return descripcion;
    };

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    };

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    };

    public void setDescripcion(String descripción) {
        this.descripcion = descripción;
    };   
};
