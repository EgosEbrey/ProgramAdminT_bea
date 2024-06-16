
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ebrey
 */
public class proveedorDAO {
    Connection con;
    conexion cn= new conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrar_proveedor (proveedor pr){
        String sql = "INSERT INTO proveedor(ruc,nombre,telefono,direccion,razon) VALUES (?,?,?,?,?)";
        try {
           con = cn.GetConnection();
           ps= con.prepareStatement(sql);
           ps.setString(1, pr.getRuc());
           ps.setString(2, pr.getNombre());
           ps.setString(3, pr.getTelefono());
           ps.setString(4, pr.getDireccion());
           ps.setString(5, pr.getRazon());
           ps.execute();
           return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(proveedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return false;
    }
    
    public List listarProveedor(){
        List<proveedor> listPr= new ArrayList(); 
        String sql="SELECT * FROM proveedor";
        try {
            con = cn.GetConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                proveedor pr = new proveedor ();
                    pr.setId(rs.getInt("id"));
                    pr.setRuc(rs.getString("ruc"));
                    pr.setNombre(rs.getString("nombre"));
                    pr.setTelefono(rs.getString("telefono"));
                    pr.setDireccion(rs.getString("direccion"));
                    pr.setRazon(rs.getString("razon"));
                    listPr.add(pr);
            }      
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(proveedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPr;
    }
    
    public boolean eliminar_proveedor(int id){
        String sql= "DELETE FROM proveedor WHERE id=?";
        try {
            con = cn.GetConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(proveedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        return false;
    }  
    
    public boolean existeDNIRUC(String dni) {
      
        boolean existe = false;
        try {
            con = cn.GetConnection(); 
            ps = con.prepareStatement("SELECT COUNT(*) FROM proveedor WHERE ruc = ?");
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(proveedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
 //ESTE METODO DEVUELVE tRUE SI EL NOMBRE QUE INGRESA EL USUARIO YA ESTA EN LA BASE DE DATOS   
    public boolean existeNombre(String nombre) {
      
        boolean existe = false;
        try {
            con = cn.GetConnection(); 
            ps = con.prepareStatement("SELECT COUNT(*) FROM proveedor WHERE nombre = ?");
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(proveedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
    
    public boolean modificar_proveedor(proveedor pr) {
        String sql= "UPDATE proveedor SET  ruc=?, nombre=?, telefono=?, direccion=?, razon=? WHERE id=?";
        try {
            con = cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, pr.getRuc());
            ps.setString(2, pr.getNombre());
            ps.setString(3, pr.getTelefono());
            ps.setString(4, pr.getDireccion());
            ps.setString(5, pr.getRazon());
            ps.setInt(6, pr.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(proveedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return false;
    }
}


