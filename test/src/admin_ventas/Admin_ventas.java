/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin_ventas;

import Modelo.conexion;
import java.sql.Connection;

/**
 *
 * @author ebrey
 */
public class Admin_ventas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        conexion conexion = new conexion();
        Connection con = conexion.GetConnection();

        if (con != null) {
            System.out.println("Conexión exitosa.");
        } else {
            System.out.println("Error en la conexión.");
        }
    }
    
    
}
