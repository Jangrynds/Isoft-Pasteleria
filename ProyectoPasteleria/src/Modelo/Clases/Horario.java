/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

/**
 *
 * @author ingri
 */
public class Horario {
    private int idHorario;
    private int idEmpleado;
    private String dia;
    private int horaInicio;
    private int horaFin;
    
    public Horario(){
        
    };

    public int getIdHorario() {
        return idHorario;
    };

    public int getIdEmpleado() {
        return idEmpleado;
    };

    public String getDia() {
        return dia;
    };

    public int getHoraInicio() {
        return horaInicio;
    };

    public int getHoraFin() {
        return horaFin;
    };

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    };

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    };

    public void setDia(String dia) {
        this.dia = dia;
    };

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    };

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    };   
}
