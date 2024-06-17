
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
public class ventasDao {
    Connection con ;
    conexion cn=new conexion();
    PreparedStatement ps;
    int r;
    ResultSet rs;
    
    public int idVenta(){
        int id=0;
        String sql ="SELECT MAX(id) FROM ventas";
        try {
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                    id =rs.getInt(1);
            }
        }catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
}
    
    public int registrarVentas(ventas v){
        String sql ="INSERT INTO ventas (cliente,vendedor,total,fecha) VALUES (?,?,?,?)";
        try {
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            ps.setString(4, v.getFecha());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
        }
        return r;
    }
    
    public int registrarDetalle(detalle dv){
        String sql ="INSERT INTO detalle (cod_prod,cantidad,precio,id_venta) VALUES (?,?,?,?)";
            try {
                con =cn.GetConnection();
                ps=con.prepareStatement(sql);
                ps.setString(1,dv.getCod_prod());
                ps.setInt(2,dv.getCantidad());
                ps.setDouble(3,dv.getPrecio());
                ps.setInt(4,dv.getId());
                ps.execute();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }finally{
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
            }
        return r;
    }
    
    public boolean actualizarStock(int cant, String cod){
        String sql="UPDATE productos SET stock=? WHERE codigo=?";
        try {
            con=cn.GetConnection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, cant);
            ps.setString(2, cod);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } 

    }
    
    public List listarVentas(){
        List<ventas> listVenta= new ArrayList(); 
        String sql="SELECT * FROM ventas";
        try {
            con = cn.GetConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {                
                ventas venta= new ventas ();
                    venta.setId(rs.getInt("id"));
                    venta.setCliente(rs.getString("cliente"));
                    venta.setVendedor(rs.getString("vendedor"));
                    venta.setTotal(rs.getDouble("total"));
                    
                    listVenta.add(venta);
            }      
        } catch (SQLException e) {
            System.out.println(e.toString());
        } 
        return listVenta;
    }
    
}
