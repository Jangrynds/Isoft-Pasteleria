/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Clases;

import java.util.Date;

/**
 *
 * @author ingri
 */
public class DetalleTiempo {
    
    private int idDetalleTarjeta;
    private int idTarjeta;

    private String dia;
    private String horaInicio;
    private String horaFin;

    private double tiempoTotal;
    private double tasaHora;
    private double costoTotal;
    
    public DetalleTiempo(){
        
    };

    public int getIdDetalleTarjeta() {
        return idDetalleTarjeta;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public String getDia() {
        return dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public double getTiempoTotal() {
        return tiempoTotal;
    }

    public double getTasaHora() {
        return tasaHora;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setIdDetalleTarjeta(int idDetalleTarjeta) {
        this.idDetalleTarjeta = idDetalleTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public void setTiempoTotal(double tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public void setTasaHora(double tasaHora) {
        this.tasaHora = tasaHora;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    
}
