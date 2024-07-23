
package Reportes;

import Modelo.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ebrey
 */
public class grafica {
    
    public static void graficar(String fecha){
        
        Connection con;
        conexion cn = new conexion();
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql= "SELECT total FROM ventas WHERE fecha = ?";
            con = cn.GetConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            DefaultPieDataset dataset = new DefaultPieDataset();
            while (rs.next()){
                dataset.setValue(rs.getString("total"), rs.getDouble("total"));    //  setValue(rs.getString("total"), rs.getDouble("total"));
            }
            JFreeChart jf = ChartFactory.createPieChart("Reporte de Venta", dataset);
            ChartFrame f = new ChartFrame("total de ventas por dia", jf);
            f.setSize(1000, 500);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println("aca hubo un error en graficar(String fecha)");
        }
    }
    
}
