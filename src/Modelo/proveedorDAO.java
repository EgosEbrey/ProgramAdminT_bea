
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ebrey
 */
public class proveedorDAO {
    Connection con;
    conexion cn= new conexion();
    PreparedStatement ps;
    
    public boolean registrar_proveedor (proveedor pr){
        String sql = "INSERT INTO proveedor(ruc,nombre,telefono,direccion,razon) VALUES (?,?,?,?,?)";
        try {
           con = cn.GetConnection();
           ps= con.prepareStatement(sql);
           ps.setInt(1, pr.getRuc());
           ps.setString(2, pr.getNombre());
           ps.setString(3, pr.getTelefono());
           ps.setString(4, pr.getDireccion());
           ps.setString(5, pr.getRazon());
           ps.execute();
           return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}
