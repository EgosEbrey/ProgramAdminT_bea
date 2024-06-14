
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            String sql= "INSERT INTO productos (codigo,nombre,nombreProveedor,stock,precio) VALUES (?,?,?,?,?)";
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, prod.getCodigo());
            ps.setString(2, prod.getNombre());
            ps.setString(3, prod.getNombreProveedor());
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
                    pro.setNombre(rs.getString("nombre"));
                    pro.setNombreProveedor(rs.getString("nombreProveedor"));
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
        String sql= "SELECT nombre FROM proveedor"; //ESTE METODO LLENA LOS ITEM DE UN CBX, los datos aca en este caso vienen de una base de datos y se almacena en un string sql
        
        try {
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                marca.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println("error al cargar jComboBXProveedorProductos");
        }
    }
    
    public void consult_codigo_prod_cbx(JComboBox marca) {
        String sql= "SELECT codigo FROM productos"; //ESTE METODO LLENA LOS ITEM DE UN CBX, los datos aca en este caso vienen de una base de datos y se almacena en un string sql
        
        try {
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                marca.addItem(rs.getString("codigo"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println("error al cargar jComboBXProveedorProductos");
        }
    }
    
    public boolean eliminarProductos(int id){
        String sql= "DELETE FROM productos WHERE id=?";
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
    
    public void resetAutoIncrement3() {
        try {
            con = cn.GetConnection(); // tu conexión a la base de datos;
            Statement stmt = con.createStatement();
            // Obtener el valor máximo actual de ID
            rs = stmt.executeQuery("SELECT IFNULL(MAX(id), 0) FROM productos");
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
            // Reiniciar el contador de auto incremento
            stmt.executeUpdate("ALTER TABLE productos AUTO_INCREMENT = " + (maxId + 1));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public boolean modificarProductos(productos pro) {
        String sql= "UPDATE productos SET  codigo=?,nombre=?,nombreProveedor=?,stock=?,precio=? WHERE id=?";
        try {
            con = cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getNombreProveedor());
            ps.setInt(4, pro.getStock());
            ps.setDouble(5, pro.getPrecio());
            ps.setInt(6, pro.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString()+" este error es en modificarProductos()");
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString()+" este error es en modificarProductos()");
            }
        }
    }
    
    public productos buscarProductos(String codigo){
        productos prod = new productos();
        String sql= "SELECT * FROM productos WHERE codigo = ?";
        try {
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, codigo);
            rs= ps.executeQuery();
            if (rs.next()){
                prod.setNombre(rs.getString("nombre"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setStock(rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return prod;
    }
    
    public config buscarDatosConfig(){
        config conf = new config();
        String sql= "SELECT * FROM config ";
        try {
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            rs= ps.executeQuery();
            if (rs.next()){
                conf.setId(rs.getInt("id"));
                conf.setNombre(rs.getString("nombre"));
                conf.setRuc(rs.getString("ruc"));
                conf.setTelefono(rs.getString("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setRazon(rs.getString("razon"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
    
    public boolean modificarDatosEmpresa(config conf) {
        String sql= "UPDATE config SET  ruc=?,nombre=?,telefono=?,direccion=?,razon=? WHERE id=?";
        try {
            con = cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, conf.getRuc());
            ps.setString(2, conf.getNombre());
            ps.setString(3, conf.getTelefono());
            ps.setString(4, conf.getDireccion());
            ps.setString(5, conf.getRazon());
            ps.setInt(6, conf.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString()+" este error es en modificarProductos()");
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString()+" este error es en modificarProductos()");
            }
        }
    }
}


