package Reportes;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Modelo.conexion;
import java.awt.HeadlessException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class Excel {
    
    public static void reporte() {
 
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Productos");
 
        try {
            //ACA SE INTENTA RECUPERAR LA IMAGEN PARA COLOCRLA EN EL REPORTE
            String home = System.getProperty("user.home");
            int imgIndex;
            try (InputStream is = new FileInputStream(home+"/Documents/reportes/ebrey.png")) {
                byte[] bytes = IOUtils.toByteArray(is);
                imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            }
             CreationHelper help = book.getCreationHelper();
            Drawing draw = sheet.createDrawingPatriarch();
 
            ClientAnchor anchor = help.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(1);
            Picture pict = draw.createPicture(anchor, imgIndex);
            pict.resize(1, 3);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
            
 
           
 
            CellStyle tituloEstilo = book.createCellStyle();
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
            Font fuenteTitulo = book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setFontHeightInPoints((short) 14);
            tituloEstilo.setFont(fuenteTitulo);
 
            Row filaTitulo = sheet.createRow(1);
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Reporte de Productos");
 
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 3));
 
            String[] cabecera = new String[]{"Código", "Nombre", "Precio", "Existencia"};
 
            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
 
            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);
 
            Row filaEncabezados = sheet.createRow(4);
 
            for (int i = 0; i < cabecera.length; i++) {
                Cell celdaEnzabezado = filaEncabezados.createCell(i);
                celdaEnzabezado.setCellStyle(headerStyle);
                celdaEnzabezado.setCellValue(cabecera[i]);
            }
 
            
            try {
                conexion con = new conexion();
            PreparedStatement ps;
            ResultSet rs;
            Connection conn = con.GetConnection();
 
            int numFilaDatos = 5;
 
            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderBottom(BorderStyle.THIN);
 
            ps = conn.prepareStatement("SELECT codigo, nombre, precio, stock FROM productos");
            rs = ps.executeQuery();
 
            int numCol = rs.getMetaData().getColumnCount();
 
            while (rs.next()) {
                Row filaDatos = sheet.createRow(numFilaDatos);
 
                for (int a = 0; a < numCol; a++) {
 
                    Cell CeldaDatos = filaDatos.createCell(a);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString(a + 1));
                }
 
 
                numFilaDatos++;
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("error en la clase excel");
            }
            
            sheet.setZoom(150);
            String fileName = "reporteProductos";
            String home = System.getProperty("user.home");
            String rutaCarpetas = home + "/Documents/reportes/";
            //se hace esto para poder crear la carpeta si no esta creada
            File rutaCompleta = new File(rutaCarpetas);
        
            // ACA SE CREA EL DIRECTORIO SI NO EXISTE
            if (!rutaCompleta.exists()) {
                rutaCompleta.mkdirs();
            }
            
            File file = new File(home + "/Documents/reportes/" + fileName + ".xlsx");
            int unContador = 1;
             // ACA se verifica si el archivo existe, si existe se vueelve a crear sumandole el contador, asi hasta que llegue a un nro que ya no exista y se cree satisfactiriamente
            while (file.exists()) {
                file = new File(rutaCarpetas + fileName + unContador + ".xlsx");
                unContador++;
            }
            try {
          
                FileOutputStream fileOut = new FileOutputStream(file);
                book.write(fileOut);
                fileOut.close();
                Desktop.getDesktop().open(file);
                JOptionPane.showMessageDialog(null, "Reporte Generado");
 
             } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "error generando reporte");
        }
            

 
    }
    
    public static void reporte2() {
 
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Clientes");
 
        try {
            //ACA SE INTENTA RECUPERAR LA IMAGEN PARA COLOCRLA EN EL REPORTE
            String home = System.getProperty("user.home");
            int imgIndex;
            try (InputStream is = new FileInputStream(home+"/Documents/reportes/ebrey.png")) {
                byte[] bytes = IOUtils.toByteArray(is);
                imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            }
             CreationHelper help = book.getCreationHelper();
            Drawing draw = sheet.createDrawingPatriarch();
 
            ClientAnchor anchor = help.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(1);
            Picture pict = draw.createPicture(anchor, imgIndex);
            pict.resize(1, 3);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
            
 
           
 
            CellStyle tituloEstilo = book.createCellStyle();
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
            Font fuenteTitulo = book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setFontHeightInPoints((short) 14);
            tituloEstilo.setFont(fuenteTitulo);
 
            Row filaTitulo = sheet.createRow(1);
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Reporte de Clientes");
 
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 3));
 
            String[] cabecera = new String[]{"dni", "Nombre", "telefono", "direccion","ruc"};
 
            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
 
            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);
 
            Row filaEncabezados = sheet.createRow(4);
 
            for (int i = 0; i < cabecera.length; i++) {
                Cell celdaEnzabezado = filaEncabezados.createCell(i);
                celdaEnzabezado.setCellStyle(headerStyle);
                celdaEnzabezado.setCellValue(cabecera[i]);
            }
 
            
            try {
                conexion con = new conexion();
            PreparedStatement ps;
            ResultSet rs;
            Connection conn = con.GetConnection();
 
            int numFilaDatos = 5;
 
            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderBottom(BorderStyle.THIN);
 
            ps = conn.prepareStatement("SELECT dni, nombre, telefono, direccion, razon FROM clientes");
            rs = ps.executeQuery();
 
            int numCol = rs.getMetaData().getColumnCount();
 
            while (rs.next()) {
                Row filaDatos = sheet.createRow(numFilaDatos);
 
                for (int a = 0; a < numCol; a++) {
 
                    Cell CeldaDatos = filaDatos.createCell(a);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString(a + 1));
                }
 
 
                numFilaDatos++;
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("error en la clase excel");
            }
            
            sheet.setZoom(150);
            String fileName = "reporteClientes";
            String home = System.getProperty("user.home");
            String rutaCarpetas = home + "/Documents/reportes/";
            //se hace esto para poder crear la carpeta si no esta creada
            File rutaCompleta = new File(rutaCarpetas);
        
            // ACA SE CREA EL DIRECTORIO SI NO EXISTE
            if (!rutaCompleta.exists()) {
                rutaCompleta.mkdirs();
            }
            
            File file = new File(home + "/Documents/reportes/" + fileName + ".xlsx");
            int unContador = 1;
             // ACA se verifica si el archivo existe, si existe se vueelve a crear sumandole el contador, asi hasta que llegue a un nro que ya no exista y se cree satisfactiriamente
            while (file.exists()) {
                file = new File(rutaCarpetas + fileName + unContador + ".xlsx");
                unContador++;
            }
            try {
          
                FileOutputStream fileOut = new FileOutputStream(file);
                book.write(fileOut);
                fileOut.close();
                Desktop.getDesktop().open(file);
                JOptionPane.showMessageDialog(null, "Reporte Generado");
 
             } catch (HeadlessException | IOException e) {
                JOptionPane.showMessageDialog(null, "error generando reporte");
        }
    }
    
    public static void reporte3() {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Proveedores");
 
        try {
            //ACA SE INTENTA RECUPERAR LA IMAGEN PARA COLOCRLA EN EL REPORTE
            String home = System.getProperty("user.home");
            int imgIndex;
            try (InputStream is = new FileInputStream(home+"/Documents/reportes/ebrey.png")) {
                byte[] bytes = IOUtils.toByteArray(is);
                imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            }
             CreationHelper help = book.getCreationHelper();
            Drawing draw = sheet.createDrawingPatriarch();
 
            ClientAnchor anchor = help.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(1);
            Picture pict = draw.createPicture(anchor, imgIndex);
            pict.resize(1, 3);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
            
 
           
 
            CellStyle tituloEstilo = book.createCellStyle();
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
            Font fuenteTitulo = book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setFontHeightInPoints((short) 14);
            tituloEstilo.setFont(fuenteTitulo);
 
            Row filaTitulo = sheet.createRow(1);
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Reporte de Proveedores");
 
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 3));
 
            String[] cabecera = new String[]{"ruc", "Nombre", "telefono", "direccion","razon social"};
 
            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
 
            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);
 
            Row filaEncabezados = sheet.createRow(4);
 
            for (int i = 0; i < cabecera.length; i++) {
                Cell celdaEnzabezado = filaEncabezados.createCell(i);
                celdaEnzabezado.setCellStyle(headerStyle);
                celdaEnzabezado.setCellValue(cabecera[i]);
            }
 
            
            try {
                conexion con = new conexion();
            PreparedStatement ps;
            ResultSet rs;
            Connection conn = con.GetConnection();
 
            int numFilaDatos = 5;
 
            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderBottom(BorderStyle.THIN);
 
            ps = conn.prepareStatement("SELECT ruc, nombre, telefono, direccion, razon FROM proveedor");
            rs = ps.executeQuery();
 
            int numCol = rs.getMetaData().getColumnCount();
 
            while (rs.next()) {
                Row filaDatos = sheet.createRow(numFilaDatos);
 
                for (int a = 0; a < numCol; a++) {
 
                    Cell CeldaDatos = filaDatos.createCell(a);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString(a + 1));
                }
 
 
                numFilaDatos++;
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("error en la clase excel");
            }
            
            sheet.setZoom(150);
            String fileName = "reporteProveedores";
            String home = System.getProperty("user.home");
            String rutaCarpetas = home + "/Documents/reportes/";
            //se hace esto para poder crear la carpeta si no esta creada
            File rutaCompleta = new File(rutaCarpetas);
        
            // ACA SE CREA EL DIRECTORIO SI NO EXISTE
            if (!rutaCompleta.exists()) {
                rutaCompleta.mkdirs();
            }
            
            File file = new File(home + "/Documents/reportes/" + fileName + ".xlsx");
            int unContador = 1;
             // ACA se verifica si el archivo existe, si existe se vueelve a crear sumandole el contador, asi hasta que llegue a un nro que ya no exista y se cree satisfactiriamente
            while (file.exists()) {
                file = new File(rutaCarpetas + fileName + unContador + ".xlsx");
                unContador++;
            }
            try {
          
                FileOutputStream fileOut = new FileOutputStream(file);
                book.write(fileOut);
                fileOut.close();
                Desktop.getDesktop().open(file);
                JOptionPane.showMessageDialog(null, "Reporte Generado");
 
             } catch (HeadlessException | IOException e) {
                JOptionPane.showMessageDialog(null, "error generando reporte");
        }
    }
    
    public static void reporte4() {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Ventas");
 
        try {
            //ACA SE INTENTA RECUPERAR LA IMAGEN PARA COLOCRLA EN EL REPORTE
            String home = System.getProperty("user.home");
            int imgIndex;
            try (InputStream is = new FileInputStream(home+"/Documents/reportes/ebrey.png")) {
                byte[] bytes = IOUtils.toByteArray(is);
                imgIndex = book.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            }
             CreationHelper help = book.getCreationHelper();
            Drawing draw = sheet.createDrawingPatriarch();
 
            ClientAnchor anchor = help.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(1);
            Picture pict = draw.createPicture(anchor, imgIndex);
            pict.resize(1, 3);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
            
 
           
 
            CellStyle tituloEstilo = book.createCellStyle();
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
            Font fuenteTitulo = book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setFontHeightInPoints((short) 14);
            tituloEstilo.setFont(fuenteTitulo);
 
            Row filaTitulo = sheet.createRow(1);
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Reporte de Ventas");
 
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 3));
 
            String[] cabecera = new String[]{"ID", "Cliente", "Vendedor", "Total", "fecha"};
 
            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
 
            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);
 
            Row filaEncabezados = sheet.createRow(4);
 
            for (int i = 0; i < cabecera.length; i++) {
                Cell celdaEnzabezado = filaEncabezados.createCell(i);
                celdaEnzabezado.setCellStyle(headerStyle);
                celdaEnzabezado.setCellValue(cabecera[i]);
            }
 
            
            try {
                conexion con = new conexion();
            PreparedStatement ps;
            ResultSet rs;
            Connection conn = con.GetConnection();
 
            int numFilaDatos = 5;
 
            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderBottom(BorderStyle.THIN);
 
            ps = conn.prepareStatement("SELECT id, cliente, vendedor, total, fecha FROM ventas");
            rs = ps.executeQuery();
 
            int numCol = rs.getMetaData().getColumnCount();
 
            while (rs.next()) {
                Row filaDatos = sheet.createRow(numFilaDatos);
 
                for (int a = 0; a < numCol; a++) {
 
                    Cell CeldaDatos = filaDatos.createCell(a);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString(a + 1));
                }
 
 
                numFilaDatos++;
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            } catch (SQLException e) {
                System.out.println(e.toString());
                System.out.println("error en la clase excel");
            }
            
            sheet.setZoom(150);
            String fileName = "reporteVentas";
            String home = System.getProperty("user.home");
            String rutaCarpetas = home + "/Documents/reportes/";
            //se hace esto para poder crear la carpeta si no esta creada
            File rutaCompleta = new File(rutaCarpetas);
        
            // ACA SE CREA EL DIRECTORIO SI NO EXISTE
            if (!rutaCompleta.exists()) {
                rutaCompleta.mkdirs();
            }
            
            File file = new File(home + "/Documents/reportes/" + fileName + ".xlsx");
            int unContador = 1;
             // ACA se verifica si el archivo existe, si existe se vueelve a crear sumandole el contador, asi hasta que llegue a un nro que ya no exista y se cree satisfactiriamente
            while (file.exists()) {
                file = new File(rutaCarpetas + fileName + unContador + ".xlsx");
                unContador++;
            }
            try {
          
                FileOutputStream fileOut = new FileOutputStream(file);
                book.write(fileOut);
                fileOut.close();
                Desktop.getDesktop().open(file);
                JOptionPane.showMessageDialog(null, "Reporte Generado");
 
             } catch (HeadlessException | IOException e) {
                JOptionPane.showMessageDialog(null, "error generando reporte");
        }
    }
}