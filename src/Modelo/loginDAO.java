
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                l.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }
    
    public boolean registrar_usuario(login reg){
        String sql = "INSERT INTO usuarios (nombre, correo, password, rol) VALUES (?,?,?,?)";
        try {
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, reg.getNombre());
            ps.setString(2, reg.getCorreo());
            ps.setString(3, reg.getPassword());
            ps.setString(4, reg.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }    
    }
    
    public List listarUsuarios(){
        List<login> listUsua= new ArrayList(); 
        String sql="SELECT * FROM usuarios";
        try {
            con = cn.GetConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                login usua= new login ();
                usua.setId(rs.getInt("id"));
                usua.setNombre(rs.getString("nombre"));
                usua.setCorreo(rs.getString("correo"));
                usua.setPassword(rs.getString("password"));
                usua.setRol(rs.getString("rol"));

                listUsua.add(usua);
            }      
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally{
                try {
                    con.close();
                    System.out.println("con.close.execute");
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
        }
        return listUsua;
    }
    
    public boolean eliminarUsuarios(int id){
        String sql= "DELETE FROM usuarios WHERE id=?";
        try {
            con = cn.GetConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
}
