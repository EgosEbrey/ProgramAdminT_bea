
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ebrey
 */
public class productosDao {
    Connection con;  
    conexion cn =new conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrar_producto(productos prod){
        try {
            String sql= "INSERT INTO productos (codigo,pro_nombre,marca,stock,precio) VALUES (?,?,?,?,?)";
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getPro_nombre());
            ps.setString(3, prod.getMarca());
            ps.setInt(4, prod.getStock());
            ps.setDouble(5, prod.getPrecio());
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
    
    public boolean existeCodigo(String cod) {
      
        boolean existe = false;
        try {
            con = cn.GetConnection(); 
            ps = con.prepareStatement("SELECT COUNT(*) FROM productos WHERE codigo = ?");
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return existe;
    }
    
    /*public boolean existeCodigo(String cod) {
    boolean existe = false;
    try (Connection con = cn.GetConnection(); 
         PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM productos WHERE codigo = ?")) {
        
        if (con == null) {
            System.out.println("La conexión es nula");
            return false;
        }

        ps.setString(1, cod);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.toString());
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener la conexión: " + e.toString());
    }
    return existe;
}*/
}
