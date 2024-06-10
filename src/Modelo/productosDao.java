
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

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
    
    public List listarProductos(){
        List<productos> listPro= new ArrayList(); 
        String sql="SELECT * FROM productos";
        try {
            con = cn.GetConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                productos pro = new productos ();
                    pro.setId(rs.getInt("id"));
                    pro.setCodigo(rs.getString("codigo"));
                    pro.setPro_nombre(rs.getString("pro_nombre"));
                    pro.setMarca(rs.getString("marca"));
                    pro.setStock(rs.getInt("stock"));
                    pro.setPrecio(rs.getDouble("precio"));
                    listPro.add(pro);
            }      
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listPro;
    }
    
    public void consult_proveed_prod_cbx(JComboBox marca) {
        String sql= "SELECT marca FROM productos";
        
        try {
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                marca.addItem(rs.getString("marca"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println("error al cargar jCBXmarcaProductos");
        }
    }
}


