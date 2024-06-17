
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ebrey
 */
public class conexion {
    Connection con; 
    public Connection GetConnection() {
        
        //
        
        try {
            Class<?> forName;
            try {
                forName = Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            //String access= "jdbc:ucanaccess://C:/Users/ebrey/Desktop/libreria_proye_jv/Database1.accdb";
            String my_bd= "jdbc:mysql://localhost:3306/sistema_ventas?serveTimezone=UTC";
            con = DriverManager.getConnection(my_bd, "root","");  // con = DriverManager.getConnection(access);  //    
            //JOptionPane.showMessageDialog(null, "sera acan");
            return con;
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "error conexion");
            System.exit(1);
        } 
        return null;
    }
}
