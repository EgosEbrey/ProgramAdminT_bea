
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
import javax.swing.JOptionPane;

/**
 *
 * @author ebrey
 */
public class clientesDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    //metodo para registrar a los clientes en la base de datos 
    public boolean registrarCliente(clientes cl) {
        String sql= "INSERT INTO clientes (dni,nombre,telefono,direccion,razon) VALUES (?,?,?,?,?)";
        try {
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.setString(5, cl.getRazon());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            JOptionPane.showMessageDialog(null, "A ocurrido un error");
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(clientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {   //supuestamente esto es para finakizar la conexion
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return false;
    } 
    
    public List listarClientes(){
        List<clientes> listCl= new ArrayList(); 
        String sql="SELECT * FROM clientes";
        try {
            con = cn.GetConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                clientes cl = new clientes ();
                    cl.setId(rs.getInt("id"));
                    cl.setDni(rs.getString("dni"));
                    cl.setNombre(rs.getString("nombre"));
                    cl.setTelefono(rs.getString("telefono"));
                    cl.setDireccion(rs.getString("direccion"));
                    cl.setRazon(rs.getString("razon"));
                    listCl.add(cl);
            }      
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(clientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listCl;
    }
    
///ESTE METODO DEVUELVE TRUE SI EL DNI INGRESADO POR EL USUARIO YA ESTA REGiSITRADO EN LA BASE DE DATOS
    public boolean existeDNIRUC(String dni) {
      
        boolean existe = false;
        try {
            con = cn.GetConnection(); 
            ps = con.prepareStatement("SELECT COUNT(*) FROM clientes WHERE dni = ?");
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
            Logger.getLogger(clientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
 //ESTE METODO DEVUELVE tRUE SI EL NOMBRE QUE INGRESA EL USUARIO YA ESTA EN LA BASE DE DATOS   
    public boolean existeNombre(String nombre) {
      
        boolean existe = false;
        try {
            con = cn.GetConnection(); 
            ps = con.prepareStatement("SELECT COUNT(*) FROM clientes WHERE nombre = ?");
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
            Logger.getLogger(clientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
    public boolean eliminarClientes(int id){
        String sql= "DELETE FROM clientes WHERE id=?";
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
            Logger.getLogger(clientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
        return false;
    }  
    
    //ESTE METODO ESTA DISEÑADO PARA REESTABLECER EL CONTEO DE ID EN LA BASE DE DATOS DE LOS CLIENTES
    public void resetAutoIncrement() {
        try {
            con = cn.GetConnection(); // tu conexión a la base de datos;
            Statement stmt = con.createStatement();
            // Obtener el valor máximo actual de ID
            rs = stmt.executeQuery("SELECT IFNULL(MAX(id), 0) FROM clientes");
            int maxId = 0;
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
            // Reiniciar el contador de auto incremento
            stmt.executeUpdate("ALTER TABLE clientes AUTO_INCREMENT = " + (maxId + 1));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(clientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean modificarClientes(clientes cl) {
        String sql= "UPDATE clientes SET  dni=?, nombre=?, telefono=?, direccion=?, razon=? WHERE id=?";
        try {
            con = cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.setString(5, cl.getRazon());
            ps.setInt(6, cl.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(clientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return false;
    }
    
    public clientes buscar_cliente(String dni) {
        clientes cl = new clientes ();
        try {
            con = cn.GetConnection(); 
            ps = con.prepareStatement("SELECT * FROM clientes WHERE dni = ?");
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                cl.setNombre(rs.getString("nombre"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setRazon(rs.getString("razon"));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(clientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cl;
    }  
}


