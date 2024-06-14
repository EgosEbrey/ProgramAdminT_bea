
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ebrey
 */
public class loginDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    conexion cn = new conexion ();
    
    public login log(String correo, String password){
        login l= new login();
        String sql= "SELECT * FROM usuarios WHERE correo =? AND password=?";
        try {
            con = cn.GetConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, password);
            rs= ps.executeQuery();
            if (rs.next()){
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }
}
