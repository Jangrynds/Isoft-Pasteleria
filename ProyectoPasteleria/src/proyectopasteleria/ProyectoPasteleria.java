/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectopasteleria;

import Modelo.ConexionBD;
import Vista.FrmLogin;


//import modelo.ConexionBD;
/**
 *
 * @author ingri
 */
public class ProyectoPasteleria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here4
        
        ConexionBD.getConexionBD();
        FrmLogin login = new FrmLogin();
        login.setVisible(true);

        //this.dispose();
    }
    
}
