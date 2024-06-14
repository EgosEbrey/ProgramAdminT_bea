
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
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
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
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
        }
        return existe;
    }
    
    public void resetAutoIncrement2() {
        /*ESTE FUCKING CODIGO ES EL QUE HACE QUE UNA VEZ ELIMINADO UNA CLIENTE O DATOS DE LA TABLA SE LIMPIE Y QUEDEN 
        //LOS ID AUT:INCREM hASTA EL VALOR MAS PROXIMO, osea es como una limpieza a los id que ya han sido borrados 
        para que se vuelvan a usar con nuevos guardados*/
        try {
            con = cn.GetConnection(); // tu conexión a la base de datos;
            Statement stmt = con.createStatement();
            // Obtener el valor máximo actual de ID
            rs = stmt.executeQuery("SELECT IFNULL(MAX(id), 0) FROM proveedor");
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
            // Reiniciar el contador de auto incremento
            stmt.executeUpdate("ALTER TABLE proveedor AUTO_INCREMENT = " + (maxId + 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}


