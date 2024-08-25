
package Vistas;

import Reportes.Excel;
import Modelo.clientes;
import Modelo.clientesDAO;
import Modelo.config;
import Modelo.detalle;
import Modelo.eventos;
import Modelo.login;
import Modelo.loginDAO;
import Modelo.productos;
import Modelo.productosDao;
import Modelo.proveedor;
import Modelo.proveedorDAO;
import Modelo.ventas;
import Modelo.ventasDao;
import Reportes.grafica;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author ebrey
 */
public class Sistema extends javax.swing.JFrame {
    
    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    /*SE PUEDE INSTANCIAR UN OBJETO sin un contructor creado, solo cuando es sin parametros, osea el sistema
    ofrece un contructor por defecto, el cual se instancia y se usa sin problema mientras no se intancie con parametros especificos*/                                       
    clientes cl= new clientes();
    clientesDAO client= new clientesDAO(); /////PUUTOO CONSTRUCTORR VACIOOOO!!!!!
    proveedor pr = new proveedor();
    proveedorDAO prDao = new proveedorDAO();
    productos pro= new productos();
    productosDao proDao=new productosDao();
    ventas v= new ventas();
    ventasDao vDao = new ventasDao();
    detalle dv= new detalle();
    config conf= new config();
    eventos event = new eventos();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    Object[] ob = new Object[6];
    int item;
    double totalPagar = 0.00;
    public Sistema() {
        /*initComponents();
        this.setLocationRelativeTo(null);
        jButton_guar_camb.setVisible(false);
        btn_guardar_config.setVisible(false);
        proDao.consult_proveed_prod_cbx(cbx_proveedor); /// ACA SE ESTA LLAMANDO AL METODO QUE LLENA LOS CAMPOS DEL jCOMBOX EN "MARCA" DE PRODUCTOS 
        AutoCompleteDecorator.decorate(cbx_proveedor);   ///CON ESTA DECLARACION SE LOGRA QUE EN EL COMOBX DE MARCAPRODUCTOS AL ESCRIBIR SE VAYAN MOSTRANDO las opciones que estan disponibles
        ///AGREGADO
        proDao.consult_codigo_prod_cbx(comboBoxProduct);
        AutoCompleteDecorator.decorate(comboBoxProduct);
        txt_Id_Config.setVisible(false);
        txtCodigoVenta.setVisible(false);
        txt_idproducto_venta.setVisible(false);
        txt_id_proveedor.setVisible(false);
        txt_id_productos.setVisible(false);
        listarConfig();*/
    }
    
    public Sistema(login priv) {
        
        //ESTE ES UN EVENTO LANISTER PARA CAPTURAR EN TIEMPO REAL LOS DATOS EN NRO VENTA Y CARGAR TABLA VENTA 
        initComponents();
        
        txt_id_ventas.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTable();
            }
        });
        
        checkboxProduct.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if ("".equals(txt_id_productos.getText())){
                    JOptionPane.showMessageDialog(null, "para ACTUALIZAR los datos seleccione un producto de la tabla");
                    checkboxProduct.setState(false);
                }else{
                    int pregunta = JOptionPane.showConfirmDialog(null, "<html><font color='red'>¿Desea activar los campos y actualizar los datos?</font></html>");
                    if (pregunta == 0){
                        checkboxProduct.setState(true);
                        txt_codigo_productos.setEditable(true);
                        txt_pro_nombre.setEditable(true);
                        txt_Stock_productos.setEditable(true);
                        txt_precio_productos.setEditable(true);
                        JOptionPane.showMessageDialog(null, "Campos actvios para escritura");
                        txt_codigo_productos.requestFocusInWindow();
                    }else {
                        checkboxProduct.setState(false);
                        txt_codigo_productos.setEditable(false);
                        txt_pro_nombre.setEditable(false);
                        txt_Stock_productos.setEditable(false);
                        txt_precio_productos.setEditable(false);
                    }
                }
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                checkboxProduct.setState(true);
            }
        });
        
        this.setLocationRelativeTo(null);
        //jLabel54.setText("Gest de Productos");
        jButton_guar_camb.setVisible(false);
        btn_guardar_config.setVisible(false);
        proDao.consult_proveed_prod_cbx(cbx_proveedor); /// ACA SE ESTA LLAMANDO AL METODO QUE LLENA LOS CAMPOS DEL jCOMBOX EN "MARCA" DE PRODUCTOS 
        AutoCompleteDecorator.decorate(cbx_proveedor);   ///CON ESTA DECLARACION SE LOGRA QUE EN EL COMOBX DE MARCAPRODUCTOS AL ESCRIBIR SE VAYAN MOSTRANDO las opciones que estan disponibles
        ///AGREGADO
        proDao.consult_codigo_prod_cbx(comboBoxProduct);
        AutoCompleteDecorator.decorate(comboBoxProduct);
        txt_Id_Config.setVisible(false);
        txtCodigoVenta.setVisible(false);
        txt_idproducto_venta.setVisible(false);
        btn_atras_nuevProv.setVisible(false);
        
        listarConfig();
        
        if (priv.getRol().equals("Asistente")){
            btn_graficar.setEnabled(false);
            jDateChooser_venta.setEnabled(false);
            btn_productos.setEnabled(false);
            btn_proveedor.setEnabled(false);
            btn_usuarios.setEnabled(false);
            btn_config.setEnabled(false);
            Label_vendedor.setText(priv.getNombre());
        }else
            Label_vendedor.setText(priv.getNombre());
    }
    
    
    
    
    ///ESSTEEE FUCKING METODO HACE QUE EN UN CAMPO JTEXTFIELD SOLO SE PUEDAN INGRESAR NUMEROS Y LOS LIMITA HASTA UNA CANTIDAD MAXIMA... MARAVILLOSSOOO!!
    PlainDocument doc = new PlainDocument() {
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        // Solo permite la inserción de caracteres que son dígitos
        
        if (getLength() + str.length() > 13) {
            // Si excede 9 caracteres, solo se insertan los primeros caracteres necesarios para alcanzar los 9 caracteres
            str = str.substring(0, 13 - getLength());
        }
        
        if (str == null) {
            return;
        }
        super.insertString(offs, str, a);
    }
    };
  
    public void listar_clientes(){
        List<clientes> listaCl = client.listarClientes();
        modelo = (DefaultTableModel)table_clientes.getModel();//ACA HAY UN CASTEO QUE SE REALIZO CON LA OPCION QUE DA JAVA 
        Object[] columnNames = {"ID", "DNI", "Nombre", "Teléfono", "Dirección", "Razón Social"};
        Object[][] data = new Object[listaCl.size()][6];
        for (int i = 0; i < listaCl.size(); i++) {
            data[i][0] = listaCl.get(i).getId();
            data[i][1] = listaCl.get(i).getDni();
            data[i][2] = listaCl.get(i).getNombre();
            data[i][3] = listaCl.get(i).getTelefono();
            data[i][4] = listaCl.get(i).getDireccion();
            data[i][5] = listaCl.get(i).getRazon();
            /*ob[0]= listaCl.get(i).getId();
            ob[1]= listaCl.get(i).getDni();
            ob[2]= listaCl.get(i).getNombre();
            ob[3]= listaCl.get(i).getTelefono();
            ob[4]= listaCl.get(i).getDireccion();
            ob[5]= listaCl.get(i).getRazon();*/
            modelo.addRow(data);
        }
        NonEditableTableModel modelo = new NonEditableTableModel(data, columnNames);
        table_clientes.setModel(modelo);
    }
    
    public void listar_proveedor(){
        List<proveedor> listaPr = prDao.listarProveedor();
        modelo = (DefaultTableModel)table_proveedor.getModel();//ACA HAY UN CASTEO QUE SE REALIZO CON LA OPCION QUE DA JAVA 
        Object[] columnNames = {"ID", "RUC", "Nombre", "Teléfono", "Dirección", "Razón Social"};
        Object[][] data = new Object[listaPr.size()][6];
        for (int i = 0; i < listaPr.size(); i++) {
            data[i][0] = listaPr.get(i).getId();
            data[i][1] = listaPr.get(i).getRuc();
            data[i][2] = listaPr.get(i).getNombre();
            data[i][3] = listaPr.get(i).getTelefono();
            data[i][4] = listaPr.get(i).getDireccion();
            data[i][5] = listaPr.get(i).getRazon();
            /*ob[0]= listaCl.get(i).getId();
            ob[1]= listaCl.get(i).getDni();
            ob[2]= listaCl.get(i).getNombre();
            ob[3]= listaCl.get(i).getTelefono();
            ob[4]= listaCl.get(i).getDireccion();
            ob[5]= listaCl.get(i).getRazon();*/
            modelo.addRow(data);
        }
        NonEditableTableModel modelo = new NonEditableTableModel(data, columnNames);
        table_proveedor.setModel(modelo);
    }
    
    public void listar_productos() {
        List<productos> listaPro = proDao.listarProductos();
        modelo = (DefaultTableModel)table_productos.getModel();//ACA HAY UN CASTEO QUE SE REALIZO CON LA OPCION QUE DA JAVA 
        Object[] columnNames = {"id", "CODIGO", "PRODUCTO", "STOCK", "PRECIO", "PROVEEDOR"};
        Object[][] data = new Object[listaPro.size()][6];
        for (int i = 0; i < listaPro.size(); i++) {
            data[i][0] = listaPro.get(i).getId();
            data[i][1] = listaPro.get(i).getCodigo();
            data[i][2] = listaPro.get(i).getNombre();
            data[i][3] = listaPro.get(i).getStock();
            data[i][4] = listaPro.get(i).getPrecio(); 
            data[i][5] =  listaPro.get(i).getNombreProveedor();   //listaPro.get(i).getProveedor();
            
             /*
            ob[0]=listaPro.get(i).getId();
            ob[1]= listaPro.get(i).getCodigo();
            ob[2]=listaPro.get(i).getPro_nombre();
            ob[3]= listaPro.get(i).getStock();
            ob[4]=listaPro.get(i).getPrecio();
            ob[5]= listaPro.get(i).getProveedor();*/
            modelo.addRow(data);
        }
        NonEditableTableModel modelo = new NonEditableTableModel(data, columnNames);
        table_productos.setModel(modelo);
    }
    
    
    public void listarConfig(){
        try {
            conf=proDao.buscarDatosConfig();
            txt_Id_Config.setText(""+conf.getId());
            txt_nombre_config.setText(""+conf.getNombre());
            txt_ruc_config.setText(""+conf.getRuc());
            txt_telef_config.setText(""+conf.getTelefono());
            txt_direcc_config.setText(""+conf.getDireccion());
            txt_razon_config.setText(""+conf.getRazon());
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("aca hubi un error con listarConfig()");
        }
        
    }
    
    private void updateTable() {
        //ESTE METODO FUNIONA CO EL LANISTER DE NRO VENTA, ACTUALIZA LOS DATOS MIENRAS SE INGRESAN
        String text = txt_id_ventas.getText();
        List<ventas> listaVenta = vDao.listarVentasNro(text);
        modelo = (DefaultTableModel)table_ventas.getModel();

        //Limpiar la tabla
        modelo.setRowCount(0);
        // Rellenar la tabla con los datos obtenidos
        for (ventas ventas : listaVenta) {
            modelo.addRow(new Object[]{ventas.getId(), ventas.getCliente(), ventas.getVendedor(), ventas.getTotal()});
        }
    }
    
    /*private void updateTableProdu() {
        //ESTE METODO FUNIONA CO EL LANISTER DE NRO VENTA, ACTUALIZA LOS DATOS MIENRAS SE INGRESAN
        String text = txt_id_ventas.getText();
        List<ventas> listaVenta = proDao.listarVentasNro(text);
        modelo = (DefaultTableModel)table_ventas.getModel();

        //Limpiar la tabla
        modelo.setRowCount(0);
        // Rellenar la tabla con los datos obtenidos
        for (ventas ventas : listaVenta) {
            modelo.addRow(new Object[]{ventas.getId(), ventas.getCliente(), ventas.getVendedor(), ventas.getTotal()});
        }
    }*/
    
    public void listar_ventas(){
        List<ventas> listaVenta = vDao.listarVentas();
        modelo = (DefaultTableModel)table_ventas.getModel();//ACA HAY UN CASTEO QUE SE REALIZO CON LA OPCION QUE DA JAVA 
        Object[] columnNames = {"id", "CLIENTE", "VENDEDOR", "TOTAL"};
        Object[][] data = new Object[listaVenta.size()][6];
        for (int i = 0; i < listaVenta.size(); i++) {
            data[i][0] = listaVenta.get(i).getId();
            data[i][1] = listaVenta.get(i).getCliente();
            data[i][2] = listaVenta.get(i).getVendedor();
            data[i][3] = listaVenta.get(i).getTotal();
            modelo.addRow(data);
        }
        NonEditableTableModel modelo = new NonEditableTableModel(data, columnNames);
        table_ventas.setModel(modelo);
    }
    
    
    public class NonEditableTableModel extends DefaultTableModel {
    public NonEditableTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Todas las celdas no son editables
    }
}
    
    

    public void limpiarTabla(){
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i=i-1;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_nueva_venta = new javax.swing.JButton();
        btn_clientes = new javax.swing.JButton();
        btn_proveedor = new javax.swing.JButton();
        btn_productos = new javax.swing.JButton();
        btn_ventas = new javax.swing.JButton();
        btn_config = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Label_vendedor = new javax.swing.JLabel();
        btn_usuarios = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel61 = new javax.swing.JLabel();
        jLabelPuntoVenta = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_venta = new javax.swing.JTable();
        btn_imprimirgenerar_venta = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        label_total_venta = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        txt_idproducto_venta = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txt_dniruc_venta = new javax.swing.JTextField();
        txt_nombrecliente_venta = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_telefonocliente_venta = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txt_direccioncliente_venta = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txt_razoncliente_venta = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        btn_eliminar_venta = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        comboBoxProduct = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtStockVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCantidadVenta = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btn_eliminar_venta1 = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_dniruc_clientes = new javax.swing.JTextField();
        txt_nombre_clientes = new javax.swing.JTextField();
        txt_telefono_clientes = new javax.swing.JTextField();
        txt_direccion_clientes = new javax.swing.JTextField();
        txt_razonsocial_clientes = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_clientes = new javax.swing.JTable();
        btn_guardar_clientes = new javax.swing.JButton();
        btn_editar_clientes = new javax.swing.JButton();
        btn_eliminar_clientes = new javax.swing.JButton();
        btn_nuevo_clientes = new javax.swing.JButton();
        txt_id_clientes = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        check_opt_actu = new javax.swing.JCheckBox();
        jLabel38 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txt_ruc_proveedor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_nombre_proveedor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_telefono_proveedor = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_direccion_proveedor = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txt_razonsocial_proveedor = new javax.swing.JTextField();
        btn_guardar_proveedor = new javax.swing.JButton();
        btn_eliminar_proveedor = new javax.swing.JButton();
        btn_editar_proveedor = new javax.swing.JButton();
        btn_nuevo_proveedor = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_proveedor = new javax.swing.JTable();
        txt_id_proveedor = new javax.swing.JTextField();
        jLabelNuevoProveed = new javax.swing.JLabel();
        jLabelGuardProveed = new javax.swing.JLabel();
        jLabelActuProveed = new javax.swing.JLabel();
        jLabelElimProveed = new javax.swing.JLabel();
        txt_texto_cambiante = new javax.swing.JTextField();
        jButton_guar_camb = new javax.swing.JButton();
        txt_text_indicTabla = new javax.swing.JTextField();
        btn_atras_nuevProv = new javax.swing.JButton();
        label1 = new java.awt.Label();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txt_codigo_productos = new javax.swing.JTextField();
        txt_pro_nombre = new javax.swing.JTextField();
        txt_Stock_productos = new javax.swing.JTextField();
        txt_precio_productos = new javax.swing.JTextField();
        cbx_proveedor = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_productos = new javax.swing.JTable();
        btn_guardar_productos = new javax.swing.JButton();
        btn_editar_productos = new javax.swing.JButton();
        btn_eliminar_productos = new javax.swing.JButton();
        btn_nuevo_productos = new javax.swing.JButton();
        btn_excel_productos = new javax.swing.JButton();
        txt_id_productos = new javax.swing.JTextField();
        checkboxProduct = new java.awt.Checkbox();
        jLabel54 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jTextBuscCodProd = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        table_ventas = new javax.swing.JTable();
        btn_pdf_ventas = new javax.swing.JButton();
        txt_id_ventas = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txt_ruc_config = new javax.swing.JTextField();
        txt_nombre_config = new javax.swing.JTextField();
        txt_telef_config = new javax.swing.JTextField();
        txt_direcc_config = new javax.swing.JTextField();
        txt_razon_config = new javax.swing.JTextField();
        btn_Actualizar_config = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        txt_Id_Config = new javax.swing.JTextField();
        btn_guardar_config = new javax.swing.JButton();
        btn_graficar = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jDateChooser_venta = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 153));

        btn_nueva_venta.setBackground(new java.awt.Color(240, 239, 239));
        btn_nueva_venta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_nueva_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Nventa.png"))); // NOI18N
        btn_nueva_venta.setText("Nueva venta ");
        btn_nueva_venta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        btn_nueva_venta.setRequestFocusEnabled(false);
        btn_nueva_venta.setRolloverEnabled(false);
        btn_nueva_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nueva_ventaActionPerformed(evt);
            }
        });

        btn_clientes.setBackground(new java.awt.Color(240, 239, 239));
        btn_clientes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Clientes.png"))); // NOI18N
        btn_clientes.setText("Clientes");
        btn_clientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        btn_clientes.setRequestFocusEnabled(false);
        btn_clientes.setRolloverEnabled(false);
        btn_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clientesActionPerformed(evt);
            }
        });

        btn_proveedor.setBackground(new java.awt.Color(240, 239, 239));
        btn_proveedor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N
        btn_proveedor.setText("Proveedor");
        btn_proveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        btn_proveedor.setRequestFocusEnabled(false);
        btn_proveedor.setRolloverEnabled(false);
        btn_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proveedorActionPerformed(evt);
            }
        });

        btn_productos.setBackground(new java.awt.Color(240, 239, 239));
        btn_productos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/producto.png"))); // NOI18N
        btn_productos.setText("Productos");
        btn_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        btn_productos.setRequestFocusEnabled(false);
        btn_productos.setRolloverEnabled(false);
        btn_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_productosActionPerformed(evt);
            }
        });

        btn_ventas.setBackground(new java.awt.Color(240, 239, 239));
        btn_ventas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/compras.png"))); // NOI18N
        btn_ventas.setText("Ventas");
        btn_ventas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        btn_ventas.setRequestFocusEnabled(false);
        btn_ventas.setRolloverEnabled(false);
        btn_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ventasActionPerformed(evt);
            }
        });

        btn_config.setBackground(new java.awt.Color(240, 239, 239));
        btn_config.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_config.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/config.png"))); // NOI18N
        btn_config.setText("Config");
        btn_config.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        btn_config.setRequestFocusEnabled(false);
        btn_config.setRolloverEnabled(false);
        btn_config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_configActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ebrey.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SINDICATO");

        Label_vendedor.setFont(new java.awt.Font("Tahoma", 3, 16)); // NOI18N
        Label_vendedor.setForeground(new java.awt.Color(255, 255, 255));
        Label_vendedor.setText("vendedor");

        btn_usuarios.setBackground(new java.awt.Color(240, 239, 239));
        btn_usuarios.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_usuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/usuariosAdmin.png"))); // NOI18N
        btn_usuarios.setText("Usuarios");
        btn_usuarios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        btn_usuarios.setRequestFocusEnabled(false);
        btn_usuarios.setRolloverEnabled(false);
        btn_usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuariosActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(246, 62, 62));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/salir_1.png"))); // NOI18N
        jButton2.setText("SALIR");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 255, 153), 2, true));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(204, 255, 255));
        jLabel61.setText("Usuario:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_proveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_clientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_nueva_venta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(btn_productos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_ventas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_usuarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_config, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel61)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Label_vendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(27, 27, 27))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Label_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_nueva_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_proveedor)
                .addGap(18, 18, 18)
                .addComponent(btn_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_usuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_config)
                .addGap(45, 45, 45)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 730));

        jLabelPuntoVenta.setBackground(new java.awt.Color(0, 0, 255));
        jLabelPuntoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/encabezado.png"))); // NOI18N
        jLabelPuntoVenta.setText("               ");
        jLabelPuntoVenta.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelPuntoVenta.setOpaque(true);
        getContentPane().add(jLabelPuntoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 1120, 130));

        jTabbedPane2.setBackground(new java.awt.Color(250, 250, 250));
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));
        jTabbedPane2.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane2.setEnabled(false);
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(223, 221, 221));
        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));

        table_venta.setBackground(new java.awt.Color(204, 255, 204));
        table_venta.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        table_venta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Precio", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(table_venta);
        if (table_venta.getColumnModel().getColumnCount() > 0) {
            table_venta.getColumnModel().getColumn(0).setPreferredWidth(4);
            table_venta.getColumnModel().getColumn(1).setPreferredWidth(190);
            table_venta.getColumnModel().getColumn(2).setPreferredWidth(4);
            table_venta.getColumnModel().getColumn(3).setPreferredWidth(30);
            table_venta.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        btn_imprimirgenerar_venta.setBackground(new java.awt.Color(48, 234, 48));
        btn_imprimirgenerar_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/print.png"))); // NOI18N
        btn_imprimirgenerar_venta.setBorder(javax.swing.BorderFactory.createMatteBorder(8, 8, 8, 8, new java.awt.Color(204, 255, 255)));
        btn_imprimirgenerar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirgenerar_ventaActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 3, 17)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/money.png"))); // NOI18N
        jLabel20.setText("TOTAL A COBRAR:");

        label_total_venta.setBackground(new java.awt.Color(243, 243, 243));
        label_total_venta.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        label_total_venta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_total_venta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        label_total_venta.setFocusable(false);
        label_total_venta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        label_total_venta.setOpaque(true);

        txtCodigoVenta.setEditable(false);
        txtCodigoVenta.setBackground(new java.awt.Color(230, 230, 230));
        txtCodigoVenta.setEnabled(false);
        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });

        txt_idproducto_venta.setEditable(false);
        txt_idproducto_venta.setEnabled(false);

        jLabel42.setBackground(new java.awt.Color(204, 255, 255));
        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("IMPRIMIR");
        jLabel42.setOpaque(true);

        jButton1.setBackground(new java.awt.Color(153, 255, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 3, 9)); // NOI18N
        jButton1.setText("Buscar Nro DC.");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Ingrese DNI/RUC cliente");

        txt_dniruc_venta.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        txt_dniruc_venta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dniruc_venta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        txt_dniruc_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dniruc_ventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dniruc_ventaKeyTyped(evt);
            }
        });

        txt_nombrecliente_venta.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txt_nombrecliente_venta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nombrecliente_venta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        txt_nombrecliente_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombrecliente_ventaKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("NOMBRE Cliente:");

        txt_telefonocliente_venta.setEditable(false);
        txt_telefonocliente_venta.setBackground(new java.awt.Color(248, 248, 248));
        txt_telefonocliente_venta.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txt_telefonocliente_venta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telefonocliente_venta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Telefono:");

        txt_direccioncliente_venta.setEditable(false);
        txt_direccioncliente_venta.setBackground(new java.awt.Color(248, 248, 248));
        txt_direccioncliente_venta.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txt_direccioncliente_venta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_direccioncliente_venta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Direccion:");

        txt_razoncliente_venta.setEditable(false);
        txt_razoncliente_venta.setBackground(new java.awt.Color(248, 248, 248));
        txt_razoncliente_venta.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txt_razoncliente_venta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_razoncliente_venta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Razon social:");

        btn_eliminar_venta.setBackground(new java.awt.Color(255, 51, 102));
        btn_eliminar_venta.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_eliminar_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btn_eliminar_venta.setText("ELIMINAR un producto de la tabla");
        btn_eliminar_venta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 3, true));
        btn_eliminar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_ventaActionPerformed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(0, 255, 0));
        jLabel13.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Buscar Prod");
        jLabel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 255, 204), new java.awt.Color(204, 255, 204), new java.awt.Color(204, 255, 204), new java.awt.Color(204, 255, 204)));
        jLabel13.setOpaque(true);
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        comboBoxProduct.setBackground(new java.awt.Color(244, 244, 244));
        comboBoxProduct.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        comboBoxProduct.setMaximumRowCount(1000);
        comboBoxProduct.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        comboBoxProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel17.setBackground(new java.awt.Color(255, 204, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Stock");

        txtStockVenta.setEditable(false);
        txtStockVenta.setBackground(new java.awt.Color(248, 248, 248));
        txtStockVenta.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtStockVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStockVenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(245, 39, 39), 4, true));

        txtDescripcionVenta.setEditable(false);
        txtDescripcionVenta.setBackground(new java.awt.Color(248, 248, 248));
        txtDescripcionVenta.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        txtDescripcionVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescripcionVenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        txtDescripcionVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionVentaActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Producto");

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.setBackground(new java.awt.Color(248, 248, 248));
        txtPrecioVenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPrecioVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecioVenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Precio");

        txtCantidadVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCantidadVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidadVenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 0), 4, true));
        txtCantidadVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCantidadVentaMouseClicked(evt);
            }
        });
        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Cantidad:");

        btn_eliminar_venta1.setBackground(new java.awt.Color(204, 255, 204));
        btn_eliminar_venta1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_eliminar_venta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/compras.png"))); // NOI18N
        btn_eliminar_venta1.setText("AGREGAR producto");
        btn_eliminar_venta1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 51), 3, true));
        btn_eliminar_venta1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_eliminar_venta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_venta1ActionPerformed(evt);
            }
        });

        jLabel68.setBackground(new java.awt.Color(227, 254, 254));
        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel68.setText("Si ya tiene productos agregados en la tabla y desea generar la venta, proceda con los datos del cliente para la factura.");
        jLabel68.setOpaque(true);

        jLabel69.setBackground(new java.awt.Color(227, 254, 254));
        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        jLabel69.setText("Si el cliente no esta registrado puede hacerlo en \"Gestios clientes\". Tambien puede generar la venta y la factura agragando manualmente el nombre del ciente. ");

        jLabel70.setBackground(new java.awt.Color(227, 254, 254));
        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        jLabel70.setText("Una vez ingresados los datos del cliente de click en \"Imprimir\" para generar la venta y la factura.");

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("Codigo Producto");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_idproducto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(txt_nombrecliente_venta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_telefonocliente_venta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_direccioncliente_venta)
                            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_razoncliente_venta)
                            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_imprimirgenerar_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboBoxProduct, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtStockVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(10, 10, 10)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_eliminar_venta1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(btn_eliminar_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_dniruc_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel10Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addGap(67, 67, 67)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_total_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCantidadVenta)
                            .addComponent(btn_eliminar_venta1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel71, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel17))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboBoxProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtStockVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_eliminar_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_dniruc_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(label_total_venta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btn_imprimirgenerar_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21)
                            .addComponent(jLabel39)
                            .addComponent(jLabel40))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_nombrecliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_telefonocliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_direccioncliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txt_razoncliente_venta))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_idproducto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel69))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel70)
                        .addContainerGap(30, Short.MAX_VALUE))))
        );

        jLabel41.setBackground(new java.awt.Color(173, 173, 247));
        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Area de facturación");
        jLabel41.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255)));
        jLabel41.setOpaque(true);

        jLabel67.setBackground(new java.awt.Color(175, 231, 231));
        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel67.setText("Seleccione el codigo y de Click en \"Buscar Prod\", si tiene Stock disponible ingrese la cantidad deseada por el cliente y luego click en \"agregar\". Si desea quitar un producto de la tabla seleccionelo y de click en \"eliminar\".");
        jLabel67.setOpaque(true);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel67)
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel67)
                .addGap(5, 5, 5)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("     Nueva venta    ", jPanel4);

        jPanel5.setBackground(new java.awt.Color(218, 215, 215));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("DNI/RUC");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("NOMBRE");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("TELEFONO");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("DIRECCON");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("RAZON SOCIAL");
        jLabel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_dniruc_clientes.setEditable(false);
        txt_dniruc_clientes.setBackground(new java.awt.Color(246, 245, 245));
        txt_dniruc_clientes.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_dniruc_clientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dniruc_clientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        txt_dniruc_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dniruc_clientesKeyTyped(evt);
            }
        });

        txt_nombre_clientes.setEditable(false);
        txt_nombre_clientes.setBackground(new java.awt.Color(246, 245, 245));
        txt_nombre_clientes.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_nombre_clientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nombre_clientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        txt_nombre_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_clientesKeyTyped(evt);
            }
        });

        txt_telefono_clientes.setEditable(false);
        txt_telefono_clientes.setBackground(new java.awt.Color(246, 245, 245));
        txt_telefono_clientes.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_telefono_clientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telefono_clientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        txt_telefono_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefono_clientesKeyTyped(evt);
            }
        });

        txt_direccion_clientes.setEditable(false);
        txt_direccion_clientes.setBackground(new java.awt.Color(246, 245, 245));
        txt_direccion_clientes.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_direccion_clientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_direccion_clientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        txt_razonsocial_clientes.setEditable(false);
        txt_razonsocial_clientes.setBackground(new java.awt.Color(246, 245, 245));
        txt_razonsocial_clientes.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_razonsocial_clientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_razonsocial_clientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        table_clientes.setBackground(new java.awt.Color(232, 232, 254));
        table_clientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "DNI/RUC", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        table_clientes.setRowHeight(19);
        table_clientes.setRowMargin(2);
        table_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_clientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_clientes);
        if (table_clientes.getColumnModel().getColumnCount() > 0) {
            table_clientes.getColumnModel().getColumn(0).setPreferredWidth(40);
            table_clientes.getColumnModel().getColumn(1).setPreferredWidth(60);
            table_clientes.getColumnModel().getColumn(2).setPreferredWidth(50);
            table_clientes.getColumnModel().getColumn(3).setPreferredWidth(80);
            table_clientes.getColumnModel().getColumn(4).setPreferredWidth(70);
        }

        btn_guardar_clientes.setBackground(new java.awt.Color(234, 254, 245));
        btn_guardar_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btn_guardar_clientes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 1, true));
        btn_guardar_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_guardar_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar_clientesActionPerformed(evt);
            }
        });

        btn_editar_clientes.setBackground(new java.awt.Color(239, 242, 247));
        btn_editar_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btn_editar_clientes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        btn_editar_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_editar_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_clientesActionPerformed(evt);
            }
        });

        btn_eliminar_clientes.setBackground(new java.awt.Color(253, 227, 253));
        btn_eliminar_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btn_eliminar_clientes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 51), 1, true));
        btn_eliminar_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_eliminar_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_clientesActionPerformed(evt);
            }
        });

        btn_nuevo_clientes.setBackground(new java.awt.Color(250, 251, 243));
        btn_nuevo_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btn_nuevo_clientes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        btn_nuevo_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_nuevo_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo_clientesActionPerformed(evt);
            }
        });

        txt_id_clientes.setEditable(false);
        txt_id_clientes.setBackground(new java.awt.Color(210, 210, 210));
        txt_id_clientes.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 102, 0));
        jLabel34.setText("Guardar");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(102, 102, 0));
        jLabel35.setText("Nuevo");

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 51, 102));
        jLabel36.setText("Actualizar");

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(204, 0, 0));
        jLabel37.setText("Eliminar");

        check_opt_actu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_opt_actuActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel38.setText("Active esta casilla si desea actualizar los datos del cliente");

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel1.setText("Toque 2 veces sobre el nombre del cliente para seleccionarlo.");

        jLabel64.setBackground(new java.awt.Color(173, 173, 247));
        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("Gestion de Clientes");
        jLabel64.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 255), 1, true));
        jLabel64.setOpaque(true);

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel65.setText("Para registrar un nuevo cliente, de click en NUEVO y llene los datos, luego de click en Guardar para finalizar.");

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel66.setText("Para actualizar los datos del cliente active la casilla, luego edite los datos y posteriormente de click en Actualizar para finalizar.");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_nombre_clientes)
                                    .addComponent(txt_dniruc_clientes, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_razonsocial_clientes)
                                    .addComponent(txt_direccion_clientes, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_telefono_clientes, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(check_opt_actu, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_id_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_nuevo_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_guardar_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 201, Short.MAX_VALUE)
                                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_eliminar_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_editar_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel66)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(369, 369, 369))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btn_eliminar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_editar_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(txt_id_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_dniruc_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_nombre_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_telefono_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_direccion_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_razonsocial_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(81, 81, 81)
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(check_opt_actu)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btn_nuevo_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_guardar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("          Clientes          ", jPanel5);

        jPanel6.setBackground(new java.awt.Color(218, 215, 215));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("RUC:");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_ruc_proveedor.setEditable(false);
        txt_ruc_proveedor.setBackground(new java.awt.Color(246, 245, 245));
        txt_ruc_proveedor.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_ruc_proveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_ruc_proveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        txt_ruc_proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ruc_proveedorKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("NOMBRE:");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_nombre_proveedor.setEditable(false);
        txt_nombre_proveedor.setBackground(new java.awt.Color(246, 245, 245));
        txt_nombre_proveedor.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_nombre_proveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nombre_proveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        txt_nombre_proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_proveedorKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("TELEFONO:");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_telefono_proveedor.setEditable(false);
        txt_telefono_proveedor.setBackground(new java.awt.Color(246, 245, 245));
        txt_telefono_proveedor.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_telefono_proveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telefono_proveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        txt_telefono_proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefono_proveedorKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("DIRECCON:");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_direccion_proveedor.setEditable(false);
        txt_direccion_proveedor.setBackground(new java.awt.Color(246, 245, 245));
        txt_direccion_proveedor.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_direccion_proveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_direccion_proveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("RAZON SOCIAL:");
        jLabel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_razonsocial_proveedor.setEditable(false);
        txt_razonsocial_proveedor.setBackground(new java.awt.Color(246, 245, 245));
        txt_razonsocial_proveedor.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_razonsocial_proveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_razonsocial_proveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        btn_guardar_proveedor.setBackground(new java.awt.Color(209, 208, 208));
        btn_guardar_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btn_guardar_proveedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 255, 102), 2, true));
        btn_guardar_proveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_guardar_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar_proveedorActionPerformed(evt);
            }
        });

        btn_eliminar_proveedor.setBackground(new java.awt.Color(209, 208, 208));
        btn_eliminar_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btn_eliminar_proveedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 51, 51), 2, true));
        btn_eliminar_proveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_eliminar_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_proveedorActionPerformed(evt);
            }
        });

        btn_editar_proveedor.setBackground(new java.awt.Color(209, 208, 208));
        btn_editar_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btn_editar_proveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204), 2));
        btn_editar_proveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_editar_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_proveedorActionPerformed(evt);
            }
        });

        btn_nuevo_proveedor.setBackground(new java.awt.Color(209, 208, 208));
        btn_nuevo_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btn_nuevo_proveedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 0), 2, true));
        btn_nuevo_proveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_nuevo_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo_proveedorActionPerformed(evt);
            }
        });

        table_proveedor.setBackground(new java.awt.Color(232, 232, 254));
        table_proveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        table_proveedor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table_proveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "RUC", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        table_proveedor.setRowHeight(19);
        table_proveedor.setRowMargin(2);
        table_proveedor.getTableHeader().setReorderingAllowed(false);
        table_proveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_proveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_proveedor);
        if (table_proveedor.getColumnModel().getColumnCount() > 0) {
            table_proveedor.getColumnModel().getColumn(0).setPreferredWidth(20);
            table_proveedor.getColumnModel().getColumn(1).setPreferredWidth(40);
            table_proveedor.getColumnModel().getColumn(2).setPreferredWidth(60);
            table_proveedor.getColumnModel().getColumn(3).setPreferredWidth(55);
            table_proveedor.getColumnModel().getColumn(4).setPreferredWidth(80);
            table_proveedor.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        txt_id_proveedor.setEditable(false);
        txt_id_proveedor.setBackground(new java.awt.Color(210, 210, 210));

        jLabelNuevoProveed.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelNuevoProveed.setText("Nuevo");

        jLabelGuardProveed.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelGuardProveed.setText("Guardar");

        jLabelActuProveed.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelActuProveed.setText("Actualizar");

        jLabelElimProveed.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelElimProveed.setText("Eliminar");

        txt_texto_cambiante.setEditable(false);
        txt_texto_cambiante.setBackground(new java.awt.Color(229, 229, 229));
        txt_texto_cambiante.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_texto_cambiante.setForeground(new java.awt.Color(0, 102, 51));
        txt_texto_cambiante.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_texto_cambiante.setText("text");
        txt_texto_cambiante.setBorder(null);
        txt_texto_cambiante.setOpaque(false);

        jButton_guar_camb.setBackground(new java.awt.Color(153, 255, 153));
        jButton_guar_camb.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_guar_camb.setText("GUARDAR CAMBIOS");
        jButton_guar_camb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_guar_cambActionPerformed(evt);
            }
        });

        txt_text_indicTabla.setEditable(false);
        txt_text_indicTabla.setBackground(new java.awt.Color(229, 229, 229));
        txt_text_indicTabla.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_text_indicTabla.setText("text");
        txt_text_indicTabla.setBorder(null);
        txt_text_indicTabla.setOpaque(false);

        btn_atras_nuevProv.setBackground(new java.awt.Color(255, 51, 51));
        btn_atras_nuevProv.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        btn_atras_nuevProv.setText("ATRAS");
        btn_atras_nuevProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atras_nuevProvActionPerformed(evt);
            }
        });

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setBackground(new java.awt.Color(173, 173, 247));
        label1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label1.setText("Gestion de Proveedores");

        jLabel62.setText("Para registrar  un nuevo proveedor de click en NUEVO e ingrese todos los datos, luego de click en GUARDAR para terminar.");

        jLabel63.setText("Asegurese de tener un proveedor seleccionado para poder Eliminar/Actualizar los datos.");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_telefono_proveedor))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_nombre_proveedor))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_ruc_proveedor))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_direccion_proveedor)
                                            .addComponent(txt_razonsocial_proveedor))))
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(txt_texto_cambiante, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addComponent(txt_id_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btn_eliminar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelElimProveed)
                                .addGap(18, 18, 18)
                                .addComponent(btn_editar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelActuProveed)
                                .addGap(32, 32, 32)
                                .addComponent(btn_nuevo_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelNuevoProveed, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_guardar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelGuardProveed, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                                .addGap(65, 65, 65)
                                .addComponent(jButton_guar_camb, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(212, 212, 212))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                        .addComponent(txt_text_indicTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(210, 210, 210))))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel62))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_atras_nuevProv, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txt_text_indicTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ruc_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_id_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombre_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_telefono_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_direccion_proveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_razonsocial_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_texto_cambiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_eliminar_proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                    .addComponent(btn_guardar_proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_nuevo_proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelGuardProveed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelNuevoProveed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_editar_proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelActuProveed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                .addComponent(jLabelElimProveed)
                                .addGap(58, 58, 58)))
                        .addComponent(jLabel62)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel63))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton_guar_camb, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_atras_nuevProv, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane2.addTab("         Proveedor       ", jPanel6);

        jPanel7.setBackground(new java.awt.Color(218, 215, 215));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));
        jPanel7.setAutoscrolls(true);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel23.setText("Codigo:");
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel24.setText("Nombre Pro:");
        jLabel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel25.setText("Stock disp:");
        jLabel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel26.setText("Precio:");
        jLabel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel27.setText("Proveedor:");
        jLabel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txt_codigo_productos.setEditable(false);
        txt_codigo_productos.setBackground(new java.awt.Color(246, 245, 245));
        txt_codigo_productos.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_codigo_productos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_codigo_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        txt_codigo_productos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txt_codigo_productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_codigo_productosKeyTyped(evt);
            }
        });

        txt_pro_nombre.setEditable(false);
        txt_pro_nombre.setBackground(new java.awt.Color(246, 245, 245));
        txt_pro_nombre.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_pro_nombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_pro_nombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        txt_pro_nombre.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txt_Stock_productos.setEditable(false);
        txt_Stock_productos.setBackground(new java.awt.Color(246, 245, 245));
        txt_Stock_productos.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_Stock_productos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_Stock_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        txt_Stock_productos.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_Stock_productos.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txt_Stock_productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_Stock_productosKeyTyped(evt);
            }
        });

        txt_precio_productos.setEditable(false);
        txt_precio_productos.setBackground(new java.awt.Color(246, 245, 245));
        txt_precio_productos.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        txt_precio_productos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_precio_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        txt_precio_productos.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txt_precio_productos.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txt_precio_productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_precio_productosKeyTyped(evt);
            }
        });

        cbx_proveedor.setBackground(new java.awt.Color(239, 237, 237));
        cbx_proveedor.setEditable(true);
        cbx_proveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ninguno" }));
        cbx_proveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));

        table_productos.setBackground(new java.awt.Color(232, 232, 254));
        table_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        table_productos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "CODIGO", "PRODUCTO", "STOCK", "PRECIO", "PROVEEDOR"
            }
        ));
        table_productos.setMinimumSize(new java.awt.Dimension(140, 0));
        table_productos.setRowHeight(19);
        table_productos.setRowMargin(2);
        table_productos.getTableHeader().setReorderingAllowed(false);
        table_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_productosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(table_productos);
        if (table_productos.getColumnModel().getColumnCount() > 0) {
            table_productos.getColumnModel().getColumn(0).setPreferredWidth(110);
            table_productos.getColumnModel().getColumn(1).setPreferredWidth(140);
            table_productos.getColumnModel().getColumn(2).setPreferredWidth(290);
            table_productos.getColumnModel().getColumn(3).setPreferredWidth(120);
            table_productos.getColumnModel().getColumn(4).setPreferredWidth(140);
            table_productos.getColumnModel().getColumn(5).setPreferredWidth(280);
        }

        btn_guardar_productos.setBackground(new java.awt.Color(102, 255, 102));
        btn_guardar_productos.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        btn_guardar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btn_guardar_productos.setText("Guardar producto");
        btn_guardar_productos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0), 1, true));
        btn_guardar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar_productosActionPerformed(evt);
            }
        });

        btn_editar_productos.setBackground(new java.awt.Color(204, 204, 255));
        btn_editar_productos.setFont(new java.awt.Font("Tahoma", 2, 9)); // NOI18N
        btn_editar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btn_editar_productos.setText("Actualizar producto");
        btn_editar_productos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153), 1, true));
        btn_editar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_productosActionPerformed(evt);
            }
        });

        btn_eliminar_productos.setBackground(new java.awt.Color(248, 153, 153));
        btn_eliminar_productos.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        btn_eliminar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btn_eliminar_productos.setText("Eliminar producto");
        btn_eliminar_productos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 1, true));
        btn_eliminar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_productosActionPerformed(evt);
            }
        });

        btn_nuevo_productos.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        btn_nuevo_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btn_nuevo_productos.setText("Agregar Nuevo");
        btn_nuevo_productos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 0), 1, true));
        btn_nuevo_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo_productosActionPerformed(evt);
            }
        });

        btn_excel_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/excel.png"))); // NOI18N
        btn_excel_productos.setText("Imprimir lista de productos detallada");
        btn_excel_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 51), 2));
        btn_excel_productos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_excel_productos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_excel_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_excel_productosActionPerformed(evt);
            }
        });

        txt_id_productos.setEditable(false);
        txt_id_productos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_id_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 255)));
        txt_id_productos.setEnabled(false);

        checkboxProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        checkboxProduct.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        checkboxProduct.setLabel("Seleccione para activar la escritura y actualizar");
        checkboxProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkboxProductMouseClicked(evt);
            }
        });

        jLabel54.setBackground(new java.awt.Color(173, 173, 247));
        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("Gestión de Productos");
        jLabel54.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 255), 1, true));
        jLabel54.setOpaque(true);

        jLabel56.setBackground(new java.awt.Color(170, 183, 195));
        jLabel56.setFont(new java.awt.Font("Arial", 1, 9)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(51, 51, 51));
        jLabel56.setText("Si necesita mas espacio para visualizar el dato de un producto en esta tabla, puede aumentar el espacio dando click en la linea que divide la columna y ampliar el tamaño de la misma arrastrando hacia la derecha/ izquierda.");

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Seleccione con el click derecho el producto que desea gestionar");

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Si va a actualizar los datos de algun producto, seleccione el check para activar los campos y poder escribir.");

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Si tiene inconvenientes para visualizar o gestionar los datos contacte con el administrador o con el soporte tecnico.");

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel59.setText("Para registrar un producto nuevo, de click en agregar nuevo y esto habilitara los campos de escritura, luego de click en guardar.");

        jTextBuscCodProd.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jTextBuscCodProd.setText("BUSCAR COD PRODUCTO...");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(txt_codigo_productos)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_id_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_pro_nombre, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                                    .addComponent(txt_precio_productos))
                                .addGap(74, 74, 74)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkboxProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_eliminar_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_editar_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jTextBuscCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_Stock_productos)
                            .addComponent(cbx_proveedor, 0, 434, Short.MAX_VALUE))
                        .addGap(74, 74, 74)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_nuevo_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_guardar_productos, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                        .addGap(45, 45, 45)
                        .addComponent(btn_excel_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(38, 38, 38))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1077, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 1052, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel56)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addComponent(txt_precio_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jTextBuscCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_eliminar_productos)
                            .addComponent(txt_id_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_codigo_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_pro_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel24))
                            .addComponent(checkboxProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_editar_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_nuevo_productos))
                            .addComponent(txt_Stock_productos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_guardar_productos))
                            .addComponent(cbx_proveedor)))
                    .addComponent(btn_excel_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jLabel57)
                .addGap(7, 7, 7)
                .addComponent(jLabel58)
                .addGap(7, 7, 7)
                .addComponent(jLabel59)
                .addContainerGap())
        );

        jTabbedPane2.addTab("           Productos          ", jPanel7);

        jPanel8.setBackground(new java.awt.Color(218, 215, 215));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));

        table_ventas.setBackground(new java.awt.Color(232, 232, 254));
        table_ventas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        table_ventas.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        table_ventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro de venta", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        table_ventas.setRowHeight(19);
        table_ventas.setRowMargin(2);
        table_ventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_ventasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(table_ventas);
        if (table_ventas.getColumnModel().getColumnCount() > 0) {
            table_ventas.getColumnModel().getColumn(0).setPreferredWidth(10);
            table_ventas.getColumnModel().getColumn(1).setPreferredWidth(120);
            table_ventas.getColumnModel().getColumn(2).setPreferredWidth(85);
            table_ventas.getColumnModel().getColumn(3).setPreferredWidth(50);
        }

        btn_pdf_ventas.setBackground(new java.awt.Color(255, 204, 255));
        btn_pdf_ventas.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        btn_pdf_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf.png"))); // NOI18N
        btn_pdf_ventas.setText("GENERAR PDF");
        btn_pdf_ventas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 0, 102), 2, true));
        btn_pdf_ventas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_pdf_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pdf_ventasActionPerformed(evt);
            }
        });

        txt_id_ventas.setBackground(new java.awt.Color(248, 248, 248));
        txt_id_ventas.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txt_id_ventas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_id_ventas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_id_ventasKeyTyped(evt);
            }
        });

        jLabel46.setBackground(new java.awt.Color(173, 173, 247));
        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(19, 18, 18));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("Gestion Ventas");
        jLabel46.setToolTipText("");
        jLabel46.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 255), 1, true));
        jLabel46.setOpaque(true);

        jLabel47.setBackground(new java.awt.Color(245, 254, 245));
        jLabel47.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(51, 51, 51));
        jLabel47.setText(" Seleccione una venta de la tabla o ingrese el Nro de la venta manualmente");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setText("Nro Venta:");

        jLabel49.setText("Si no encuentra la factura que busca, verifique que la venta si se haya realizado y este registrada.");

        jButton4.setBackground(new java.awt.Color(200, 200, 224));
        jButton4.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(51, 51, 51));
        jButton4.setText("Mostrar todas las ventas ");
        jButton4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 255), 3, true));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel50.setText("Si tiene problemas para visualizar el archivo PDF o con la gestion de la consulta, ponganse en contacto con un administrador.");

        jLabel51.setText("Para resolver cualquier duda o problema con este sistema pongase en contacto con el soporte tecnico.");

        jLabel55.setBackground(new java.awt.Color(170, 183, 195));
        jLabel55.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(81, 78, 78));
        jLabel55.setText("En esta area usted puede buscar y visualizar en un archivo pdf los datos o factura de cualquier venta realizada anteriormente.");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(165, 165, 165))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel49)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(jLabel48)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_id_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_pdf_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                                        .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)))
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel51)
                                    .addComponent(jLabel50))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_pdf_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_id_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48))
                        .addGap(5, 5, 5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel51)
                .addGap(47, 47, 47))
        );

        jLabel46.getAccessibleContext().setAccessibleName("En este apartado usted puede buscar y visualizar en PDF las ventas ya realizadas\nPuede elegir alguna de las ventas que aparezcan en la tabla o puede ingresar el Nro de la venta manualmente \ncon el Nro de la venta ingresado solo de click sobre el Boton PDF y se generara su archivo PDF\n");

        jTabbedPane2.addTab("              Ventas             ", jPanel8);

        jPanel9.setBackground(new java.awt.Color(180, 180, 180));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setBackground(new java.awt.Color(204, 255, 255));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("RUC:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("NOMBRE:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("TELEFONO:");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("DIRECCION:");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("RAZON SOCIAL:");

        txt_ruc_config.setBackground(new java.awt.Color(227, 240, 240));
        txt_ruc_config.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ruc_configKeyTyped(evt);
            }
        });

        txt_nombre_config.setBackground(new java.awt.Color(227, 240, 240));
        txt_nombre_config.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_configKeyTyped(evt);
            }
        });

        txt_telef_config.setBackground(new java.awt.Color(227, 240, 240));
        txt_telef_config.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telef_configKeyTyped(evt);
            }
        });

        txt_direcc_config.setBackground(new java.awt.Color(227, 240, 240));

        txt_razon_config.setBackground(new java.awt.Color(227, 240, 240));

        btn_Actualizar_config.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btn_Actualizar_config.setText("ACTUALIZAR");
        btn_Actualizar_config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Actualizar_configActionPerformed(evt);
            }
        });

        jLabel33.setBackground(new java.awt.Color(204, 204, 255));
        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("DATOS DE LA EMPRESA");
        jLabel33.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));

        txt_Id_Config.setEnabled(false);

        btn_guardar_config.setBackground(new java.awt.Color(153, 255, 153));
        btn_guardar_config.setText("GUARDAR");
        btn_guardar_config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar_configActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_razon_config, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                .addComponent(txt_direcc_config, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Actualizar_config, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(btn_guardar_config, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(141, 141, 141))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_ruc_config, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(171, 171, 171)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_nombre_config, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_telef_config, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_Id_Config, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(188, 188, 188)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 609, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(228, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Id_Config, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_telef_config, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nombre_config, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_ruc_config, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(txt_direcc_config, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32)
                        .addGap(4, 4, 4)
                        .addComponent(txt_razon_config, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_guardar_config, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_Actualizar_config, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(67, 67, 67))
        );

        btn_graficar.setBackground(new java.awt.Color(240, 251, 243));
        btn_graficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/torta.png"))); // NOI18N
        btn_graficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_graficarActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel44.setText("Generar reporte");

        jLabel43.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel43.setText("Seleccione FECHA:");

        jButton5.setBackground(new java.awt.Color(204, 255, 255));
        jButton5.setText("Productos");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(204, 255, 255));
        jButton6.setText("Clientes");
        jButton6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(204, 255, 255));
        jButton7.setText("Proveedores");
        jButton7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(204, 255, 255));
        jButton8.setText("Ventas");
        jButton8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel52.setBackground(new java.awt.Color(169, 220, 239));
        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("AREA DE ADMINISTRADOR");
        jLabel52.setOpaque(true);

        jLabel53.setBackground(new java.awt.Color(235, 214, 251));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("Reportes detallados:");
        jLabel53.setOpaque(true);

        jLabel60.setBackground(new java.awt.Color(170, 183, 195));
        jLabel60.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(81, 78, 78));
        jLabel60.setText("Elija el nombre del reporte que desea ver");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_graficar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jDateChooser_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(78, 78, 78)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton6)
                            .addComponent(jButton7)
                            .addComponent(jButton8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel60))
                    .addComponent(btn_graficar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("                  Config              ", jPanel2);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 1120, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_excel_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_excel_productosActionPerformed
        // TODO add your handling code here:
       Excel.reporte();
    }//GEN-LAST:event_btn_excel_productosActionPerformed

    private void btn_nuevo_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo_clientesActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Ingrese los datos");
        txt_dniruc_clientes.requestFocusInWindow();
        txt_id_clientes.setVisible(false);
        check_opt_actu.setSelected(false);
        limpiarCampos2(txt_id_clientes,txt_nombre_clientes,txt_dniruc_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
        //limpiarCampos();    ////ESTE METODO LIMPIA LOS CAMPOS, EL DE ARRIBA ES OTRA FORMA DE HACERLO PASANDOLE LOS PARAMETROS JTextField)
        activarCampos(txt_dniruc_clientes,txt_nombre_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
    }//GEN-LAST:event_btn_nuevo_clientesActionPerformed
    
    private void btn_guardar_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardar_clientesActionPerformed
        //Codigo boton para guardar clientes
        if (!"".equals(txt_dniruc_clientes.getText())&&!"".equals(txt_nombre_clientes.getText())&&!"".equals(txt_telefono_clientes.getText())&&!"".equals(txt_direccion_clientes.getText())){
            String dni = txt_dniruc_clientes.getText();
            String nombre = txt_nombre_clientes.getText();
            if (client.existeDNIRUC(dni)||client.existeNombre(nombre)) {
                JOptionPane.showMessageDialog(null, "El Cliente ya está registrado");
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿desea guardar?");
                if (pregunta==0){
                    pregunta = JOptionPane.showConfirmDialog(null, "¿A continuacion se guardaran los datos, desea continuar?");
                    if (pregunta==0){
                        cl.setDni(txt_dniruc_clientes.getText());
                        cl.setNombre(txt_nombre_clientes.getText());
                        cl.setTelefono(txt_telefono_clientes.getText());
                        cl.setDireccion(txt_direccion_clientes.getText());
                        cl.setRazon((txt_razonsocial_clientes.getText()));
                        try {
                            client.registrarCliente(cl);
                            JOptionPane.showMessageDialog(null, "cliente registrado");
                        } catch (HeadlessException e) {
                            JOptionPane.showMessageDialog(null, "A ocurrido un error");
                        }
                        desactivarCampos(txt_dniruc_clientes,txt_nombre_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
                        check_opt_actu.setSelected(false);
                        limpiarTabla();
                        //limpiarCampos();
                        listar_clientes();
                    }
                }
            }
        }else 
            JOptionPane.showMessageDialog(null, "por favor llene todos los campos para guardar (solo se puede omititr razon social de ser necesario)");
    }//GEN-LAST:event_btn_guardar_clientesActionPerformed

    private void btn_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clientesActionPerformed
        // TABLA clientes
        jTabbedPane2.setSelectedIndex(1);
        desactivarCampos(txt_dniruc_clientes,txt_nombre_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
        check_opt_actu.setSelected(false);
        limpiarCampos();
        limpiarTabla();
        listar_clientes();
    }//GEN-LAST:event_btn_clientesActionPerformed

    private void btn_nueva_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nueva_ventaActionPerformed
       
        jTabbedPane2.setSelectedIndex(0);
    }//GEN-LAST:event_btn_nueva_ventaActionPerformed

    private void btn_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proveedorActionPerformed
        // TABLA proveedor
        jTabbedPane2.setSelectedIndex(2);
        txt_id_proveedor.setVisible(false);
        txt_text_indicTabla.setVisible(true);
        txt_text_indicTabla.setText("toque 2 veces sobre el nombre del proveedor para seleccionar");
        limpiarTabla();
        listar_proveedor();
        limpiarCamposProveedor(txt_id_proveedor,txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
        desactivarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
        activar_vista_proveedor();
        txt_texto_cambiante.setVisible(false);
        btn_atras_nuevProv.setVisible(false);
        jLabelActuProveed.setVisible(true);
        jLabelElimProveed.setVisible(true);
    }//GEN-LAST:event_btn_proveedorActionPerformed

    private void btn_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_productosActionPerformed
        // TODO add your handling code here:
        checkboxProduct.setState(false);
        limpiarTabla();
        limpiar_campos_productos();
        listar_productos();
        txt_codigo_productos.setEditable(false);
        txt_pro_nombre.setEditable(false);
        txt_Stock_productos.setEditable(false);
        txt_precio_productos.setEditable(false);
        
        jTabbedPane2.setSelectedIndex(3);
        
    }//GEN-LAST:event_btn_productosActionPerformed

    private void btn_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ventasActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(4);
        limpiarTabla();
        listar_ventas();
    }//GEN-LAST:event_btn_ventasActionPerformed

    private void btn_configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_configActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(5);
        listarConfig();
        desactivarCampos(txt_ruc_config, txt_nombre_config, txt_telef_config, txt_direcc_config, txt_razon_config);
        btn_guardar_config.setVisible(false);
        btn_Actualizar_config.setVisible(true);
    }//GEN-LAST:event_btn_configActionPerformed
 ///este ecvento permite capturar una fila de la tabla y mostarla en los campos correspondientes de datos de clientes
    private void table_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_clientesMouseClicked
        // TODO add your handling code here:
        desactivarCampos(txt_dniruc_clientes,txt_nombre_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
        check_opt_actu.setSelected(false);
        txt_id_clientes.setVisible(true);
        int fila = table_clientes.rowAtPoint(evt.getPoint());
        txt_id_clientes.setText(table_clientes.getValueAt(fila, 0).toString());
        txt_dniruc_clientes.setText(table_clientes.getValueAt(fila, 1).toString());
        txt_nombre_clientes.setText(table_clientes.getValueAt(fila, 2).toString());
        txt_telefono_clientes.setText(table_clientes.getValueAt(fila, 3).toString());
        txt_direccion_clientes.setText(table_clientes.getValueAt(fila, 4).toString());
        txt_razonsocial_clientes.setText(table_clientes.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_table_clientesMouseClicked

    private void btn_eliminar_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_clientesActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txt_id_clientes.getText())){
            //ESTO MJESTRA UN CUADRO CON OPCIONES "si,no,cancelar" que capture un boolean
            int pregunta = JOptionPane.showConfirmDialog(null, "esta seguro de eliminar");
            if (pregunta==0){
                int id= Integer.parseInt(txt_id_clientes.getText());
                client.eliminarClientes(id);
                limpiarTabla();
                limpiarCampos();
                listar_clientes();
            }
        }else
            JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla para eliminar");
    }//GEN-LAST:event_btn_eliminar_clientesActionPerformed

    private void btn_editar_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_clientesActionPerformed
        // TODO add your handling code here:
        if ("".equals(txt_id_clientes.getText())){
            JOptionPane.showMessageDialog(null, "para ACTUALIZAR los datos seleccione una fila en la lista");
        }else{
            int pregunta = JOptionPane.showConfirmDialog(null, "¿desea actualizar los datos?");
            if (pregunta==0){
                if (check_opt_actu.isSelected()&&!"".equals(txt_dniruc_clientes.getText())&&!"".equals(txt_nombre_clientes.getText())&&!"".equals(txt_telefono_clientes.getText())&&!"".equals(txt_direccion_clientes.getText())){  
                    cl.setDni(txt_dniruc_clientes.getText());
                    cl.setNombre(txt_nombre_clientes.getText());
                    cl.setTelefono(txt_telefono_clientes.getText());
                    cl.setDireccion(txt_direccion_clientes.getText());
                    cl.setRazon(txt_razonsocial_clientes.getText());
                    cl.setId(Integer.parseInt(txt_id_clientes.getText()));
                    client.modificarClientes(cl);
                    limpiarTabla();
                    listar_clientes();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");
                    check_opt_actu.setSelected(false);
                    desactivarCampos(txt_dniruc_clientes,txt_nombre_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
                }else
                    JOptionPane.showMessageDialog(null, "los campos estan vacios o usted no tiene seleccionada la ocpion para actualizar los datos");
            }
        }
    }//GEN-LAST:event_btn_editar_clientesActionPerformed

    private void check_opt_actuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_opt_actuActionPerformed
        // TODO add your handling code here:
        desactivarCampos(txt_dniruc_clientes,txt_nombre_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
        int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea activar la ediccion en los campos de datos? se recomienda activar solo si se va a actualizar algun dato");
        if (pregunta==0&&!"".equals(txt_id_clientes.getText())){
            activarCampos(txt_dniruc_clientes,txt_nombre_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
            JOptionPane.showMessageDialog(null, "Los campos de datos han sido habilitados");
            txt_dniruc_clientes.requestFocusInWindow();
            txt_id_clientes.setVisible(false);
        }else if (pregunta==0&&"".equals(txt_id_clientes.getText())){
            JOptionPane.showMessageDialog(null,"De click 2 veces sobre el nombre del cliente para activar esta opcion y actualizar los datos");
            check_opt_actu.setSelected(false);
        }else{
            JOptionPane.showMessageDialog(null, "solo active esta opcion si desea editar y actualizar los datos del cliente");
            check_opt_actu.setSelected(false);
        }
        
    }//GEN-LAST:event_check_opt_actuActionPerformed

    private void btn_guardar_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardar_proveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txt_ruc_proveedor.getText())&&!"".equals(txt_nombre_proveedor.getText())&&!"".equals(txt_telefono_proveedor.getText())&&!"".equals(txt_direccion_proveedor.getText())&&!"".equals(txt_razonsocial_proveedor.getText())){
            String ruc = txt_ruc_proveedor.getText();
            String nombre = txt_nombre_proveedor.getText();
            if (prDao.existeDNIRUC(ruc)||prDao.existeNombre(nombre)) {
                JOptionPane.showMessageDialog(null, "El Proveedor ya está registrado");
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿desea guardar?");
                if (pregunta==0){
                    pregunta = JOptionPane.showConfirmDialog(null, "¿A continuacion se guardaran los datos del proveeedor, desea continuar?");
                    if (pregunta==0){
                        pr.setRuc(txt_ruc_proveedor.getText());
                        pr.setNombre(txt_nombre_proveedor.getText());
                        pr.setTelefono(txt_telefono_proveedor.getText());
                        pr.setDireccion(txt_direccion_proveedor.getText());
                        pr.setRazon((txt_razonsocial_proveedor.getText()));
                        prDao.registrar_proveedor(pr);
                        JOptionPane.showMessageDialog(null, "Proveedor registrado correctamente");
                        limpiarTabla();
                        listar_proveedor();
                    }
                }
            }
        }else 
            JOptionPane.showMessageDialog(null, "por favor llene todos los campos para guardar");                                 
    }//GEN-LAST:event_btn_guardar_proveedorActionPerformed

    private void table_proveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_proveedorMouseClicked
        // TODO add your handling code here:
        //ESTE EVENTO ES EL QUE PERMITE QUE AL DAR CLICK EN UN PROVEEDOR SE MUESTREN LOS DATOS EN LOS CAMPOS TXTFIELD
        desactivarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
        int fila = table_proveedor.rowAtPoint(evt.getPoint());
        txt_id_proveedor.setText(table_proveedor.getValueAt(fila, 0).toString());
        txt_ruc_proveedor.setText(table_proveedor.getValueAt(fila, 1).toString());
        txt_nombre_proveedor.setText(table_proveedor.getValueAt(fila, 2).toString());
        txt_telefono_proveedor.setText(table_proveedor.getValueAt(fila, 3).toString());
        txt_direccion_proveedor.setText(table_proveedor.getValueAt(fila, 4).toString());
        txt_razonsocial_proveedor.setText(table_proveedor.getValueAt(fila, 5).toString());
        
    }//GEN-LAST:event_table_proveedorMouseClicked

    private void btn_eliminar_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_proveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txt_id_proveedor.getText())&&!"".equals(txt_ruc_proveedor.getText())&&!"".equals(txt_nombre_proveedor.getText())){
            //ESTO MJESTRA UN CUADRO CON OPCIONES "si,no,cancelar" que capture un boolean
            int pregunta = JOptionPane.showConfirmDialog(null, "esta seguro de eliminar");
            if (pregunta==0){
                int id= Integer.parseInt(txt_id_proveedor.getText());
                prDao.eliminar_proveedor(id);
                JOptionPane.showMessageDialog(null, "eliminado correctamente");      
                btn_proveedor.doClick();
            }
        }
    }//GEN-LAST:event_btn_eliminar_proveedorActionPerformed

    private void btn_nuevo_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo_proveedorActionPerformed
        // TODO add your handling code here:
        int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea agregar un nuevo proveedor?");
        if (pregunta == 0){
            txt_texto_cambiante.setText("por favor ingrese los datos");
            txt_ruc_proveedor.requestFocusInWindow();
            txt_texto_cambiante.setVisible(true);
            btn_editar_proveedor.setVisible(false);
            btn_eliminar_proveedor.setVisible(false);
            btn_atras_nuevProv.setVisible(true);
            jLabelActuProveed.setVisible(false);
            jLabelElimProveed.setVisible(false);
            txt_text_indicTabla.setVisible(false);
            limpiarCamposProveedor(txt_id_proveedor,txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
            activarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
        }
        
    }//GEN-LAST:event_btn_nuevo_proveedorActionPerformed

    private void btn_editar_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_proveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txt_id_proveedor.getText())&&!"".equals(txt_ruc_proveedor.getText())&&!"".equals(txt_nombre_proveedor.getText())){
            int pregunta = JOptionPane.showConfirmDialog(null, "¿desea actualizar los datos?");
            if (pregunta==0){
                activar_boton_guardar();
                btn_atras_nuevProv.setVisible(true);
                activarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
                txt_texto_cambiante.setText("los campos estan habilitados para editar");
                txt_texto_cambiante.setVisible(true);
                txt_text_indicTabla.setVisible(false);
                txt_ruc_proveedor.requestFocusInWindow();
            }   
        }else{
           JOptionPane.showMessageDialog(null, "para ACTUALIZAR los datos seleccione una fila en la lista");
        }
    }//GEN-LAST:event_btn_editar_proveedorActionPerformed

    private void btn_pdf_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pdf_ventasActionPerformed
        if (!"".equals(txt_id_ventas.getText())){
            try {
                // TODO add your handling code here:
                int id = Integer.parseInt(txt_id_ventas.getText());
                String home = System.getProperty("user.home");
                File file = new File(home+"/Downloads/venta_" + id + ".pdf");
                Desktop.getDesktop().open(file);
                String cadena="Factura generada correctamente";
                JOptionPane.showMessageDialog(null, "<html><span style='color:green;'>" +cadena+ "</span></html>");
            } catch (Exception e) {
                String cadena="La Venta NO existe";
                JOptionPane.showMessageDialog(null, "<html><span style='color:red;'>" +cadena+ "</span></html>");
            }
        }else
            JOptionPane.showMessageDialog(null, "¿CUAL es el Nro de la Venta?");
    }//GEN-LAST:event_btn_pdf_ventasActionPerformed

    private void btn_guardar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardar_productosActionPerformed
        // TODO add your handling code here:
        //txt_id_productos.setText("");
        //if (checkboxProduct.getState())
            //checkboxProduct.setState(false);
        if (!"".equals(txt_codigo_productos.getText())&&!"".equals(txt_Stock_productos.getText())&&!"".equals(txt_precio_productos.getText())&&!"".equals(txt_pro_nombre.getText())&&!"".equals(cbx_proveedor.getSelectedItem())){
            if (proDao.existeCodigo(txt_codigo_productos.getText())||!"".equals(txt_id_productos.getText())) {
                JOptionPane.showMessageDialog(null, "El producto ya está registrado");
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿desea guardar?");
                if (pregunta==0&&!"".equals(txt_codigo_productos.getText())&&!"".equals(txt_Stock_productos.getText())&&!"".equals(txt_precio_productos.getText())&&!"".equals(txt_pro_nombre.getText())&&!"".equals(cbx_proveedor.getSelectedItem())){
                    pregunta = JOptionPane.showConfirmDialog(null, "¿A continuacion se guardaran los datos, verifique que esten correctos?");
                    if (pregunta==0&&!"".equals(txt_codigo_productos.getText())&&!"".equals(txt_Stock_productos.getText())&&!"".equals(txt_precio_productos.getText())&&!"".equals(txt_pro_nombre.getText())&&!"".equals(cbx_proveedor.getSelectedItem())){
                        pro.setCodigo(txt_codigo_productos.getText());
                        pro.setNombre(txt_pro_nombre.getText());
                        pro.setStock(Integer.parseInt(  txt_Stock_productos.getText()));
                        pro.setPrecio(Double.parseDouble(txt_precio_productos.getText()));
                        pro.setNombreProveedor(((String)cbx_proveedor.getSelectedItem().toString()));
                        txt_id_productos.setText("");
                        proDao.registrar_producto(pro);
                        JOptionPane.showMessageDialog(null, "producto registrado");
                    }else if ("".equals(txt_codigo_productos.getText())||"".equals(txt_Stock_productos.getText())||"".equals(txt_precio_productos.getText())||"".equals(txt_pro_nombre.getText())||"".equals(cbx_proveedor.getSelectedItem())){
                        JOptionPane.showMessageDialog(null, "por favor llene todos los campos para guardar");
                    }
                }else if ("".equals(txt_codigo_productos.getText())||"".equals(txt_Stock_productos.getText())||"".equals(txt_precio_productos.getText())||"".equals(txt_pro_nombre.getText())||"".equals(cbx_proveedor.getSelectedItem())){
                    JOptionPane.showMessageDialog(null, "por favor llene todos los campos para guardar");
                }
            }
        }else 
            JOptionPane.showMessageDialog(null, "por favor llene todos los campos para guardar");
        
        limpiarTabla();
        listar_productos();
    }//GEN-LAST:event_btn_guardar_productosActionPerformed

    private void table_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_productosMouseClicked
        // TODO add your handling code here
        txt_codigo_productos.setEditable(false);
        txt_pro_nombre.setEditable(false);
        txt_Stock_productos.setEditable(false);
        txt_precio_productos.setEditable(false);
        int fila = table_productos.rowAtPoint(evt.getPoint());
        txt_id_productos.setText(table_productos.getValueAt(fila, 0).toString());
        txt_codigo_productos.setText(table_productos.getValueAt(fila, 1).toString());
        txt_pro_nombre.setText(table_productos.getValueAt(fila, 2).toString());
        txt_Stock_productos.setText(table_productos.getValueAt(fila, 3).toString());
        txt_precio_productos.setText(table_productos.getValueAt(fila, 4).toString());
        cbx_proveedor.setSelectedItem (table_productos.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_table_productosMouseClicked

    private void btn_eliminar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_productosActionPerformed
        // TODO add your handling code here:
        if (checkboxProduct.getState())
            checkboxProduct.setState(false);
         if (!"".equals(txt_id_productos.getText())){
            //ESTO MJESTRA UN CUADRO CON OPCIONES "si,no,cancelar" que capture un boolean
            int pregunta = JOptionPane.showConfirmDialog(null, "esta seguro de eliminar");
            if (pregunta==0){
                int id= Integer.parseInt(txt_id_productos.getText());
                proDao.eliminarProductos(id);
                limpiarTabla();
                listar_productos();
                JOptionPane.showMessageDialog(null, "eliminado correctamente");            
            }
        }
         limpiarTabla();
         limpiar_campos_productos();
         listar_productos();
        
    }//GEN-LAST:event_btn_eliminar_productosActionPerformed

    private void btn_editar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_productosActionPerformed
        // TODO add your handling code here:
         if ("".equals(txt_id_productos.getText())){
            JOptionPane.showMessageDialog(null, "para ACTUALIZAR los datos seleccione una fila de la lista");
        }else{
            int pregunta = JOptionPane.showConfirmDialog(null, "¿desea actualizar los datos?");
            if (pregunta==0&&checkboxProduct.getState()){
                if (!"".equals(txt_codigo_productos.getText())&&!"".equals(txt_pro_nombre.getText())&&!"".equals(txt_Stock_productos.getText())&&!"".equals(txt_precio_productos.getText())&&!"".equals(cbx_proveedor.getSelectedItem().toString())){  
                    pro.setCodigo(txt_codigo_productos.getText());
                    pro.setNombre(txt_pro_nombre.getText());
                    pro.setNombreProveedor(cbx_proveedor.getSelectedItem().toString());
                    pro.setStock(Integer.parseInt(txt_Stock_productos.getText()));
                    pro.setPrecio(Double.parseDouble(txt_precio_productos.getText()));
                    pro.setId(Integer.parseInt(txt_id_productos.getText()));
                    proDao.modificarProductos(pro);
                    limpiarTabla();
                    listar_productos();
                    //limpiar_campos_productos();
                    JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");
                    checkboxProduct.setState(false);
                }else
                    JOptionPane.showMessageDialog(null, "los campos estan vacios");
            }else if (pregunta==0&&!checkboxProduct.getState()){
                JOptionPane.showMessageDialog(null, "Debe tener activo el check para actualizar");
            } else if (pregunta != 0 )
                checkboxProduct.setState(false);
        }
        
    }//GEN-LAST:event_btn_editar_productosActionPerformed

    private void btn_nuevo_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo_productosActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Ingrese los datos");
        txt_codigo_productos.requestFocusInWindow();
        if (checkboxProduct.getState())
            checkboxProduct.setState(false);
        limpiar_campos_productos();
        listar_productos();
        txt_codigo_productos.setEditable(true);
        txt_pro_nombre.setEditable(true);
        txt_Stock_productos.setEditable(true);
        txt_precio_productos.setEditable(true);
        
    }//GEN-LAST:event_btn_nuevo_productosActionPerformed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        // TODO add your handling code here:
        //CODIGOO COMENTADO PORQUE SE AÑADIO UNA MEJORA... EL TEXTFIELD QUE CONTIENE ESTA ACCION ESTA OCULTO DESDE EL SISTEMA, FUNCIONABA COMO EJECUTOR DEL CODIGO DE VENTA
       /* txtCantidadVenta.setEditable(false);
        if (evt.getKeyCode()  == KeyEvent.VK_ENTER){
            if (!"".equals(txtCodigoVenta.getText()) )  {
                String cod= txtCodigoVenta.getText();
                pro=proDao.buscarProductos(cod);
                if(pro.getNombre() != null){
                   if (pro.getStock()>0){
                        txtDescripcionVenta.setText(""+pro.getNombre());
                        txtPrecioVenta.setText(""+pro.getPrecio());
                        txtStockVenta.setText(""+pro.getStock());
                        txtCantidadVenta.setEditable(true);
                        txtCantidadVenta.requestFocus();
                   }else{
                      //EN ESTAA FUCKIIINGG LINEA SE UTILIZO HTML PARA LOGRAR QUE EL NOMBRE DEL PRODUCTO TENGO UN COLOR ESPECIFICO, EN ESTE CASO RED
                       JOptionPane.showMessageDialog(null, "<html>Producto <span style='color:red;'>" +pro.getNombre().toUpperCase()+ "</span> sin Stock</html>");
                       txtCodigoVenta.requestFocus();
                   }
                }else{
                    limpiarCamposVenta();
                    JOptionPane.showMessageDialog(null, "producto no encontrado");
                }
            }else {
                JOptionPane.showMessageDialog(null, "ingrese el codigo del producto");
                txtDescripcionVenta.setText("");
                txtPrecioVenta.setText("");
                txtStockVenta.setText("");
                txtCodigoVenta.requestFocus();
            }
        }*/
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        // TODO add your handling code here:
                
        if (evt.getKeyCode()  == KeyEvent.VK_ENTER){
             if (!"".equals(txtCantidadVenta.getText())){
                String cod= comboBoxProduct.getSelectedItem().toString();//String cod = txtCodigoVenta.getText();
                pro=proDao.buscarProductos(cod);
                txtDescripcionVenta.setText(""+pro.getNombre());
                txtPrecioVenta.setText(""+pro.getPrecio());
                txtStockVenta.setText(""+pro.getStock());
                String descrip = txtDescripcionVenta.getText();
                int cantidad = Integer.parseInt(txtCantidadVenta.getText());
                double precio = Double.parseDouble(txtPrecioVenta.getText());
                double total = cantidad * precio;
                int stock = Integer.parseInt(txtStockVenta.getText());
                if (stock >= cantidad){
                    item +=1 ;
                    tmp = (DefaultTableModel) table_venta.getModel();
                    /*for (int i = 0; i < table_venta.getRowCount(); i++) {    ///ESTE CODIGO ES PARA QUE NO SE REPITA UN MISMO PRODUCTO EN LA TABLA, ACTIVAR A CONSIDERACION
                        if (table_venta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())){
                            JOptionPane.showMessageDialog(null, "producto ya agregado");
                            return;
                        }
                    }*/
                    ArrayList list = new ArrayList();
                    list.add(item);
                    list.add(cod);
                    list.add(descrip);
                    list.add(cantidad);
                    list.add(precio);
                    list.add(total);
                    Object [] O= new Object[5];
                    O[0]= list.get(1);
                    O[1]= list.get(2);
                    O[2]= list.get(3);
                    O[3]= list.get(4);
                    O[4]= list.get(5);
                    tmp.addRow(O);
                    table_venta.setModel(tmp);
                    limpiarCamposVenta();
                    totalPagar();
                    //Double.parseDouble(txt_total_venta.setText(total));
                }else {
                    JOptionPane.showMessageDialog(null, "La cantidad supera al Stock disponible");
                    //txtCodigoVenta.requestFocus();
                }
             }else
                 JOptionPane.showMessageDialog(null, "ingrese cantidad");
         }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void btn_eliminar_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_ventaActionPerformed
        // TODO add your handling code here:
        try {
            modelo= (DefaultTableModel) table_venta.getModel();
            modelo.removeRow(table_venta.getSelectedRow());
            totalPagar();
            txtCodigoVenta.requestFocus();
            JOptionPane.showMessageDialog(null, "Producto eliminado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Fallo al eliminar producto");
        }
        
    }//GEN-LAST:event_btn_eliminar_ventaActionPerformed

    private void txt_dniruc_ventaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dniruc_ventaKeyPressed
        // TODO add your handling code here:
        
         if (evt.getKeyCode()  == KeyEvent.VK_ENTER){
             if (!"".equals(txt_dniruc_venta.getText())){
                 String dni = txt_dniruc_venta.getText();
                 cl=client.buscar_cliente(dni);
                 if (cl.getNombre() != null){
                     txt_nombrecliente_venta.setText(""+cl.getNombre());
                     txt_telefonocliente_venta.setText(""+cl.getTelefono());
                     txt_direccioncliente_venta.setText(""+cl.getDireccion());
                     txt_razoncliente_venta.setText(""+cl.getRazon());
                 }
             
             }else{
                 txt_dniruc_venta.setText("");
                 JOptionPane.showMessageDialog(null, "cliente no encontrado, puede registrarlo o puede cargar la factura sin datos");
             }
         }
    }//GEN-LAST:event_txt_dniruc_ventaKeyPressed

    private void btn_imprimirgenerar_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirgenerar_ventaActionPerformed
        // TODO add your handling code here:
        int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea generar la venta? asegurese de que los datos esten correctos");
        if (pregunta==0){
            if (table_venta.getRowCount()>0){
                if (!"".equals(txt_nombrecliente_venta.getText())){
                    try {
                        registarVenta();
                        registrarDetalle();
                        actualizarStock();
                        pdf(); /// hay que agregar la libreria swingx-all-1.6.4.jar.....
                        JOptionPane.showMessageDialog(null, "¡Venta GENERADA y registrada EXITOSAMENTE!");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "hubo un error al generar la venta, contacte con soporte");
                    }

                    limpiarTablaVenta();
                    limpiarDatosClientesVenta();
                }else{
                    JOptionPane.showMessageDialog(null, "¡la venta debe contener los datos del cliente!");
                     txt_nombrecliente_venta.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Para ejecutar e imprimir la venta es necesario tener un Producto ingresado en la tabla");
            }
        }
    }//GEN-LAST:event_btn_imprimirgenerar_ventaActionPerformed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        // TODO add your handling code here:
        //event.verificarSoloNumberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
        if (txtCantidadVenta.getText().length() >= 3) {
            evt.consume(); // Si excede los 3 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txt_dniruc_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dniruc_ventaKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
        if (txt_dniruc_venta.getText().length() >= 16) {
            evt.consume(); // Si excede los 16 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_dniruc_ventaKeyTyped

    private void txt_precio_productosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precio_productosKeyTyped
        // TODO add your handling code here:
        event.verifNumberDecimalKeyPress(evt,  txt_precio_productos);
        if (txt_precio_productos.getText().length() >= 10) {
            evt.consume(); // Si excede los 13 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_precio_productosKeyTyped

    private void btn_Actualizar_configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Actualizar_configActionPerformed

        int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea actualizar los datos de la empresa?");
        if (pregunta == 0) {
             activarCampos(txt_ruc_config, txt_nombre_config, txt_telef_config, txt_direcc_config, txt_razon_config);
             JOptionPane.showMessageDialog(null, "Se han HABILITADO los campos para su modificacion");
             btn_guardar_config.setVisible(true);
             btn_Actualizar_config.setVisible( false);
        }
    }//GEN-LAST:event_btn_Actualizar_configActionPerformed

    private void txt_nombre_configKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_configKeyTyped
        // TODO add your handling code here:
        event.verificarSoloTextKeyPress(evt);
    }//GEN-LAST:event_txt_nombre_configKeyTyped

    private void txt_telef_configKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telef_configKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
        if (txt_telef_config.getText().length() >= 11) {
            evt.consume(); // Si excede los 13 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_telef_configKeyTyped

    private void txt_nombrecliente_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombrecliente_ventaKeyTyped
        // TODO add your handling code here:
        event.verificarSoloTextKeyPress(evt);
    }//GEN-LAST:event_txt_nombrecliente_ventaKeyTyped

    private void btn_guardar_configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardar_configActionPerformed
        // TODO add your handling code here:
         
         int confirmacion = JOptionPane.showConfirmDialog(null, "¿desea guardar los cambios?");
             if (confirmacion == 0) {
                 if (!"".equals(txt_ruc_config.getText()) && !"".equals(txt_nombre_config.getText()) &&
                 !"".equals(txt_telef_config.getText()) && !"".equals(txt_direcc_config.getText()) &&
                 !"".equals(txt_razon_config.getText())) {
                    conf.setRuc(txt_ruc_config.getText());
                    conf.setNombre(txt_nombre_config.getText());
                    conf.setTelefono(txt_telef_config.getText());
                    conf.setDireccion(txt_direcc_config.getText());
                    conf.setRazon(txt_razon_config.getText());
                    conf.setId(Integer.parseInt(txt_Id_Config.getText()));
                    proDao.modificarDatosEmpresa(conf);
                    JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");
                    listarConfig();
                    // Deshabilitar los campos de texto después de actualizar los datos
                    desactivarCampos(txt_ruc_config, txt_nombre_config, txt_telef_config, txt_direcc_config, txt_razon_config);
                    btn_guardar_config.setVisible(false);
                    btn_Actualizar_config.setVisible(true);
                     } else {
                        JOptionPane.showMessageDialog(null, "Los campos están vacíos");
                    }
             }else{
                desactivarCampos(txt_ruc_config, txt_nombre_config, txt_telef_config, txt_direcc_config, txt_razon_config);
                btn_guardar_config.setVisible(false);
                btn_Actualizar_config.setVisible(true);
                listarConfig();
             }
    }//GEN-LAST:event_btn_guardar_configActionPerformed

    private void txt_nombre_proveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_proveedorKeyTyped
        // TODO add your handling code here:
        event.verificarSoloTextKeyPress(evt);
    }//GEN-LAST:event_txt_nombre_proveedorKeyTyped

    private void txtCantidadVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCantidadVentaMouseClicked
        // TODO add your handling code here:
        
        String cod= comboBoxProduct.getSelectedItem().toString();
        pro=proDao.buscarProductos(cod);
        if (pro.getStock()>0){
            txtDescripcionVenta.setText(""+pro.getNombre());
            txtPrecioVenta.setText(""+pro.getPrecio());
            txtStockVenta.setText(""+pro.getStock());
        }else{
            //EN ESTAA FUCKIIINGG LINEA SE UTILIZO HTML PARA LOGRAR QUE EL NOMBRE DEL PRODUCTO TENGO UN COLOR ESPECIFICO, EN ESTE CASO RED
            JOptionPane.showMessageDialog(null, "<html>Producto <span style='color:red;'>" +pro.getNombre().toUpperCase()+ "</span> sin Stock</html>");
        } 
    }//GEN-LAST:event_txtCantidadVentaMouseClicked

    private void btn_eliminar_venta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminar_venta1ActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtCantidadVenta.getText())){
                String cod= comboBoxProduct.getSelectedItem().toString();//String cod = txtCodigoVenta.getText();
                pro=proDao.buscarProductos(cod);
                txtDescripcionVenta.setText(""+pro.getNombre());
                txtPrecioVenta.setText(""+pro.getPrecio());
                txtStockVenta.setText(""+pro.getStock());
                String descrip = txtDescripcionVenta.getText();
                int cantidad = Integer.parseInt(txtCantidadVenta.getText());
                double precio = Double.parseDouble(txtPrecioVenta.getText());
                double total = cantidad * precio;
                int stock = Integer.parseInt(txtStockVenta.getText());
                if (stock >= cantidad){
                    item +=1 ;
                    tmp = (DefaultTableModel) table_venta.getModel();
                    /*for (int i = 0; i < table_venta.getRowCount(); i++) {    ///ESTE CODIGO ES PARA QUE NO SE REPITA UN MISMO PRODUCTO EN LA TABLA, ACTIVAR A CONSIDERACION
                        if (table_venta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())){
                            JOptionPane.showMessageDialog(null, "producto ya agregado");
                            return;
                        }
                    }*/
                    ArrayList list = new ArrayList();
                    list.add(item);
                    list.add(cod);
                    list.add(descrip);
                    list.add(cantidad);
                    list.add(precio);
                    list.add(total);
                    Object [] O= new Object[5];
                    O[0]= list.get(1);
                    O[1]= list.get(2);
                    O[2]= list.get(3);
                    O[3]= list.get(4);
                    O[4]= list.get(5);
                    tmp.addRow(O);
                    table_venta.setModel(tmp);
                    limpiarCamposVenta();
                    totalPagar();
                    //Double.parseDouble(txt_total_venta.setText(total));
                }else {
                    JOptionPane.showMessageDialog(null, "La cantidad supera al Stock disponible");
                    //txtCodigoVenta.requestFocus();
                }
             }else
                 JOptionPane.showMessageDialog(null, "ingrese cantidad");
    }//GEN-LAST:event_btn_eliminar_venta1ActionPerformed

    private void txt_nombre_clientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_clientesKeyTyped
        // TODO add your handling code here:
        event.verificarSoloTextKeyPress(evt);
    }//GEN-LAST:event_txt_nombre_clientesKeyTyped

    private void txt_telefono_clientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefono_clientesKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
        if (txt_telefono_clientes.getText().length() >= 13) {
            evt.consume(); // Si excede los 13 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_telefono_clientesKeyTyped

    private void txt_telefono_proveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefono_proveedorKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
        if (txt_telefono_proveedor.getText().length() >= 13) {
            evt.consume(); // Si excede los 13 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_telefono_proveedorKeyTyped

    private void txt_codigo_productosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigo_productosKeyTyped
        // TODO add your handling code here:
        //event.verificarSoloNumberKeyPress(evt);
        if (txt_codigo_productos.getText().length() >= 16) {
            evt.consume(); // Si excede los 16 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_codigo_productosKeyTyped

    private void txt_Stock_productosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Stock_productosKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
        if (txt_Stock_productos.getText().length() >= 7) {
            evt.consume(); // Si excede los 13 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_Stock_productosKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        if (!"".equals(txt_dniruc_venta.getText())){
                 String dni = txt_dniruc_venta.getText();
                 cl=client.buscar_cliente(dni);
                 if (cl.getNombre() != null){
                     txt_nombrecliente_venta.setText(""+cl.getNombre());
                     txt_telefonocliente_venta.setText(""+cl.getTelefono());
                     txt_direccioncliente_venta.setText(""+cl.getDireccion());
                     txt_razoncliente_venta.setText(""+cl.getRazon());
                 }else {
                     String cadena="Cliente no encontrado, puede registrarlo o continuar ingresando el nombre manualmente";
                    JOptionPane.showMessageDialog(null, "<html><span style='color:black;'>" +cadena+ "</span></html>");
                 }
             
             }else{
                 txt_dniruc_venta.setText("");
                 String cadena="Ingrese Dni o Ruc";
                 JOptionPane.showMessageDialog(null, "<html><span style='color:red;'>" +cadena+ "</span></html>");
             }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void table_ventasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_ventasMouseClicked
        // TODO add your handling code here:
        int fila = table_ventas.rowAtPoint(evt.getPoint());
        txt_id_ventas.setText(table_ventas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_table_ventasMouseClicked

    private void btn_graficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_graficarActionPerformed
        // TODO add your handling code here:
        if (jDateChooser_venta.getDate()!=null){
            fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(jDateChooser_venta.getDate());
            try {
                grafica.graficar(fechaActual);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "error al generar reporte");
            }
        }else 
            JOptionPane.showMessageDialog(null, "Por favor SELECCIONE una fecha para generar el reporte");
        
    }//GEN-LAST:event_btn_graficarActionPerformed

    private void btn_usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuariosActionPerformed
        // TODO add your handling code here:
        int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea entrar a la GESTION de ususarios?");
        if (pregunta==0){
            RegistroUsuarios reg =new RegistroUsuarios();
            reg.setVisible(true);
            //dispose();
        }
    }//GEN-LAST:event_btn_usuariosActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:                    
        String cadena = "¡¿QUIERE Cerrar la CESION?!";
        int pregunta = JOptionPane.showConfirmDialog(null, "<html><span style='color:red;'>" +cadena+ "</span></html>");
        if (pregunta==0){
            try {
                Login Log =new Login();
                Log.setVisible(true);
                dispose();
            } catch (Exception e) {
                System.out.println(e.toString());
                dispose();
            }
            
        }
       
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton_guar_cambActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_guar_cambActionPerformed
        // TODO add your handling code here:
        String ruc = txt_ruc_proveedor.getText();
        String nombre = txt_nombre_proveedor.getText();
        if (!"".equals(txt_ruc_proveedor.getText())&&!"".equals(txt_nombre_proveedor.getText())&&!"".equals(txt_telefono_proveedor.getText())&&!"".equals(txt_direccion_proveedor.getText())&&!"".equals(txt_razonsocial_proveedor.getText())){  
            int pregunta = JOptionPane.showConfirmDialog(null, "¿desea guardar los datos que han sido actualizados?");
            if ((pregunta==0&&prDao.existeDNIRUC(ruc))||(pregunta==0&&prDao.existeNombre(nombre))&&prDao.existeDNIRUC(ruc)){
                JOptionPane.showMessageDialog(null, "El Proveedor ya está registrado");
                //btn_proveedor.doClick();
            } else if ((pregunta==0&&!prDao.existeDNIRUC(ruc))||(pregunta==0&&!prDao.existeNombre(nombre)&&!prDao.existeDNIRUC(ruc))){
                pr.setRuc(txt_ruc_proveedor.getText());
                pr.setNombre(txt_nombre_proveedor.getText());
                pr.setTelefono(txt_telefono_proveedor.getText());
                pr.setDireccion(txt_direccion_proveedor.getText());
                pr.setRazon(txt_razonsocial_proveedor.getText());
                pr.setId(Integer.parseInt(txt_id_proveedor.getText()));
                prDao.modificar_proveedor(pr);
                JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");
                btn_proveedor.doClick();
            }else {
                btn_proveedor.doClick();
            }
        }else {
            JOptionPane.showMessageDialog(null, "los campos estan vacios");
        }
    }//GEN-LAST:event_jButton_guar_cambActionPerformed

    private void btn_atras_nuevProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atras_nuevProvActionPerformed
        // TODO add your handling code here:
        btn_proveedor.doClick();
    }//GEN-LAST:event_btn_atras_nuevProvActionPerformed

    private void checkboxProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkboxProductMouseClicked
        // TODO add your handling code here:
        /*
        if ("".equals(txt_id_productos.getText())){
            JOptionPane.showMessageDialog(null, "para ACTUALIZAR los datos seleccione una fila en la lista");
            checkboxProduct.setState(false);
        }else{
            int pregunta = JOptionPane.showConfirmDialog(null, "<html><font color='red'>¿Desea activar los campos y actualizar los datos?</font></html>");
            if (pregunta == 0){
                checkboxProduct.setState(true);
                txt_codigo_productos.setEditable(true);
                txt_pro_nombre.setEditable(true);
                txt_Stock_productos.setEditable(true);
                txt_precio_productos.setEditable(true);
                JOptionPane.showMessageDialog(null, "Campos actvios para escritura");
                txt_codigo_productos.requestFocusInWindow();
            }else {
                checkboxProduct.setState(false);
                txt_codigo_productos.setEditable(false);
                txt_pro_nombre.setEditable(false);
                txt_Stock_productos.setEditable(false);
                txt_precio_productos.setEditable(false);
            }
        }*/
    }//GEN-LAST:event_checkboxProductMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        listar_ventas();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Excel.reporte();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        Excel.reporte2();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        Excel.reporte3();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        Excel.reporte4();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txt_dniruc_clientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dniruc_clientesKeyTyped
        // TODO add your handling code here:
        if (txt_dniruc_clientes.getText().length() >= 16) {
            evt.consume(); // Si excede los 13 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_dniruc_clientesKeyTyped

    private void txt_ruc_proveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ruc_proveedorKeyTyped
        // TODO add your handling code here:
        if (txt_ruc_proveedor.getText().length() >= 16) {
            evt.consume(); // Si excede los 13 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_ruc_proveedorKeyTyped

    private void txt_ruc_configKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ruc_configKeyTyped
        // TODO add your handling code here:
        if (txt_ruc_config.getText().length() >= 16) {
            evt.consume(); // Si excede los 13 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_ruc_configKeyTyped

    private void txt_id_ventasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_id_ventasKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
        if (txt_id_ventas.getText().length() >= 6) {
            evt.consume(); // Si excede los 13 caracteres, consumir el evento
        }
    }//GEN-LAST:event_txt_id_ventasKeyTyped

    private void txtDescripcionVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionVentaActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        String cod= comboBoxProduct.getSelectedItem().toString();
        pro=proDao.buscarProductos(cod);
        if (pro.getStock()>0){
            txtDescripcionVenta.setText(""+pro.getNombre());
            txtPrecioVenta.setText(""+pro.getPrecio());
            txtStockVenta.setText(""+pro.getStock());
        }else{
            //EN ESTAA FUCKIIINGG LINEA SE UTILIZO HTML PARA LOGRAR QUE EL NOMBRE DEL PRODUCTO TENGO UN COLOR ESPECIFICO, EN ESTE CASO RED
            JOptionPane.showMessageDialog(null, "<html>Producto <span style='color:red;'>" +pro.getNombre().toUpperCase()+ "</span> sin Stock</html>");
        } 
    }//GEN-LAST:event_jLabel13MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }
    
    
    private void activar_vista_proveedor(){
        btn_nuevo_proveedor.setVisible(true);
        btn_guardar_proveedor.setVisible(true);
        btn_editar_proveedor.setVisible(true);
        btn_eliminar_proveedor.setVisible(true);
        jLabelNuevoProveed.setVisible(true);
        jLabelGuardProveed.setVisible(true);
        jLabelActuProveed.setVisible(true);
        jLabelElimProveed.setVisible(true);
        jButton_guar_camb.setVisible(false);
    }
    
    private void activar_boton_guardar(){
        btn_nuevo_proveedor.setVisible(false);
        btn_guardar_proveedor.setVisible(false);
        btn_editar_proveedor.setVisible(false);
        btn_eliminar_proveedor.setVisible(false);
        jLabelNuevoProveed.setVisible(false);
        jLabelGuardProveed.setVisible(false);
        jLabelActuProveed.setVisible(false);
        jLabelElimProveed.setVisible(false);
        jButton_guar_camb.setVisible(true);
    }
    
    
    
    



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Label_vendedor;
    private javax.swing.JButton btn_Actualizar_config;
    private javax.swing.JButton btn_atras_nuevProv;
    private javax.swing.JButton btn_clientes;
    private javax.swing.JButton btn_config;
    private javax.swing.JButton btn_editar_clientes;
    private javax.swing.JButton btn_editar_productos;
    private javax.swing.JButton btn_editar_proveedor;
    private javax.swing.JButton btn_eliminar_clientes;
    private javax.swing.JButton btn_eliminar_productos;
    private javax.swing.JButton btn_eliminar_proveedor;
    private javax.swing.JButton btn_eliminar_venta;
    private javax.swing.JButton btn_eliminar_venta1;
    private javax.swing.JButton btn_excel_productos;
    private javax.swing.JButton btn_graficar;
    private javax.swing.JButton btn_guardar_clientes;
    private javax.swing.JButton btn_guardar_config;
    private javax.swing.JButton btn_guardar_productos;
    private javax.swing.JButton btn_guardar_proveedor;
    private javax.swing.JButton btn_imprimirgenerar_venta;
    private javax.swing.JButton btn_nueva_venta;
    private javax.swing.JButton btn_nuevo_clientes;
    private javax.swing.JButton btn_nuevo_productos;
    private javax.swing.JButton btn_nuevo_proveedor;
    private javax.swing.JButton btn_pdf_ventas;
    private javax.swing.JButton btn_productos;
    private javax.swing.JButton btn_proveedor;
    private javax.swing.JButton btn_usuarios;
    private javax.swing.JButton btn_ventas;
    private javax.swing.JComboBox<String> cbx_proveedor;
    private javax.swing.JCheckBox check_opt_actu;
    private java.awt.Checkbox checkboxProduct;
    private javax.swing.JComboBox<String> comboBoxProduct;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton_guar_camb;
    private com.toedter.calendar.JDateChooser jDateChooser_venta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelActuProveed;
    private javax.swing.JLabel jLabelElimProveed;
    private javax.swing.JLabel jLabelGuardProveed;
    private javax.swing.JLabel jLabelNuevoProveed;
    private javax.swing.JLabel jLabelPuntoVenta;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextBuscCodProd;
    private java.awt.Label label1;
    private javax.swing.JLabel label_total_venta;
    private javax.swing.JTable table_clientes;
    private javax.swing.JTable table_productos;
    private javax.swing.JTable table_proveedor;
    private javax.swing.JTable table_venta;
    private javax.swing.JTable table_ventas;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtStockVenta;
    private javax.swing.JTextField txt_Id_Config;
    private javax.swing.JTextField txt_Stock_productos;
    private javax.swing.JTextField txt_codigo_productos;
    private javax.swing.JTextField txt_direcc_config;
    private javax.swing.JTextField txt_direccion_clientes;
    private javax.swing.JTextField txt_direccion_proveedor;
    private javax.swing.JTextField txt_direccioncliente_venta;
    private javax.swing.JTextField txt_dniruc_clientes;
    private javax.swing.JTextField txt_dniruc_venta;
    private javax.swing.JTextField txt_id_clientes;
    private javax.swing.JTextField txt_id_productos;
    private javax.swing.JTextField txt_id_proveedor;
    private javax.swing.JTextField txt_id_ventas;
    private javax.swing.JTextField txt_idproducto_venta;
    private javax.swing.JTextField txt_nombre_clientes;
    private javax.swing.JTextField txt_nombre_config;
    private javax.swing.JTextField txt_nombre_proveedor;
    private javax.swing.JTextField txt_nombrecliente_venta;
    private javax.swing.JTextField txt_precio_productos;
    private javax.swing.JTextField txt_pro_nombre;
    private javax.swing.JTextField txt_razon_config;
    private javax.swing.JTextField txt_razoncliente_venta;
    private javax.swing.JTextField txt_razonsocial_clientes;
    private javax.swing.JTextField txt_razonsocial_proveedor;
    private javax.swing.JTextField txt_ruc_config;
    private javax.swing.JTextField txt_ruc_proveedor;
    private javax.swing.JTextField txt_telef_config;
    private javax.swing.JTextField txt_telefono_clientes;
    private javax.swing.JTextField txt_telefono_proveedor;
    private javax.swing.JTextField txt_telefonocliente_venta;
    private javax.swing.JTextField txt_text_indicTabla;
    private javax.swing.JTextField txt_texto_cambiante;
    // End of variables declaration//GEN-END:variables

    
    private void limpiarCampos(){
        txt_id_clientes.setText("");
        txt_dniruc_clientes.setText("");
        txt_nombre_clientes.setText("");
        txt_telefono_clientes.setText("");
        txt_direccion_clientes.setText("");
        txt_razonsocial_clientes.setText("");
    }
    
    
    private void limpiarCamposProveedor( JTextField  id,JTextField dniR, JTextField nom, JTextField tel, JTextField dir, JTextField raz){
        id.setText("");
        dniR.setText("");
        nom.setText("");
        tel.setText("");
        dir.setText("");
        raz.setText("");
    }
    
    private void limpiarCampos2(JTextField id, JTextField dniR, JTextField nom, JTextField tel, JTextField dir, JTextField raz){
        id.setText("");
        dniR.setText("");
        nom.setText("");
        tel.setText("");
        dir.setText("");
        raz.setText("");
    }
    
    private void limpiar_campos_productos(){
        txt_id_productos.setText("");
        txt_codigo_productos.setText("");
        txt_pro_nombre.setText("");
        txt_Stock_productos.setText("");
        txt_precio_productos.setText("");
        cbx_proveedor.setSelectedItem(null);
    }
  
    private void activarCampos(JTextField dniR, JTextField nom, JTextField tel, JTextField dir, JTextField raz) {
        dniR.setEditable(true);
        nom.setEditable(true);
        tel.setEditable(true);
        dir.setEditable(true);
        raz.setEditable(true);
    }
    
    private void desactivarCampos(JTextField dniR, JTextField nom, JTextField tel, JTextField dir, JTextField raz) {
        dniR.setEditable(false);
        nom.setEditable(false);
        tel.setEditable(false);
        dir.setEditable(false);
        raz.setEditable(false);
    }
    
    private void limpiarCamposVenta(){
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtPrecioVenta.setText("");
        txtStockVenta.setText("");
        label_total_venta.setText("");
        txt_id_ventas.setText("");
    }
    
    private void totalPagar(){
        totalPagar=0.00;
        int nunFila = table_venta.getRowCount();
        for (int i = 0; i < nunFila; i++) {
            double cal = (double) table_venta.getModel().getValueAt(i, 4);
            totalPagar+=cal;    
        }
        label_total_venta.setText(String.format("%.2f", totalPagar));
    }
    
    private void registarVenta(){
        
        String cliente = txt_nombrecliente_venta.getText();
        String vendedor = Label_vendedor.getText();
        double monto = totalPagar;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(monto);
        v.setFecha(fechaActual);
        vDao.registrarVentas(v);
    }
    
    private void registrarDetalle(){
        
        int id = vDao.idVenta();
        for (int i = 0; i < table_venta.getRowCount(); i++) {
            String cod =table_venta.getValueAt(i, 0).toString();
            int cant =Integer.parseInt(table_venta.getValueAt(i, 2).toString());
            double precio =Double.parseDouble(table_venta.getValueAt(i, 3).toString());
            dv.setCod_prod(cod);
            dv.setCantidad(cant);
            dv.setPrecio(precio);
            dv.setId(id);
            vDao.registrarDetalle(dv);
        }
    }
    
    private void actualizarStock(){
        for (int i = 0; i < table_venta.getRowCount(); i++) {
            String cod = table_venta.getValueAt(i, 0).toString();
            int cant = Integer.parseInt(table_venta.getValueAt(i, 2).toString());
            pro = proDao.buscarProductos(cod);
            int StockActual=pro.getStock()-cant;
            vDao.actualizarStock(StockActual, cod);
        }
    }
    
    private void limpiarTablaVenta(){
        tmp= (DefaultTableModel) table_venta.getModel();
        int fila = table_venta.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }
    
    private void limpiarDatosClientesVenta(){
        txt_dniruc_venta.setText("");
        txt_nombrecliente_venta.setText("");
        txt_telefonocliente_venta.setText("");
        txt_direccioncliente_venta.setText("");
        txt_razoncliente_venta.setText("");
    }
    
    private void pdf(){
        int id= vDao.idVenta();

        String home = System.getProperty("user.home");
        String carpeta = home+"/Documents/reportes/";
        String nombreArchivo ="venta_" + id + ".pdf";

        // Crear objeto File para la carpeta
        File directorio = new File(carpeta);

        // Verificar si la carpeta existe, si no, crearla

        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // Crear objeto File para el archivo
        File file = new File(directorio, nombreArchivo);

        FileOutputStream archivo;
        String rutaImagen=carpeta+"ebrey.png";
        File file2 = new File(rutaImagen);
            
                try {
                archivo = new FileOutputStream(file);
                Document docum = new Document() ;
                PdfWriter.getInstance(docum, archivo);
                docum.open();
                if (file2.exists()) {
                    try {
                        Image img = Image.getInstance(carpeta+"ebrey.png");
                       
                        Paragraph fecha = new Paragraph(); 
                        fecha.add(Chunk.NEWLINE);
                        Date date = new Date();
                        fecha.add("Factura: "+ id+"\n"+ "Fecha: "+ new SimpleDateFormat("dd-MM-yyyy").format(date)+"\n\n");

                        PdfPTable Encabezado = new PdfPTable(4);
                        Encabezado.setWidthPercentage(100);
                        Encabezado.getDefaultCell().setBorder(0);
                        float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};

                        Encabezado.setWidths(ColumnaEncabezado);
                        Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);
                        Encabezado.addCell(img);

                        String ruc = txt_ruc_config.getText();
                        String nom = txt_nombre_config.getText();
                        String telf= txt_telef_config.getText();
                        String dir= txt_direcc_config.getText();
                        String ra= txt_razon_config.getText();

                        Encabezado.addCell("");
                        Encabezado.addCell("Ruc: "+ruc+ "\nNombre: "+nom+ "\nTelefono: "+telf+ "\nDireccion: "+dir+"\nRazon: "+ra);
                        Encabezado.addCell(fecha);
                        docum.add(Encabezado);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }else {
                    try {
                        JOptionPane.showMessageDialog(null, "La factura no tendra el logo de empresa, por favor agregue la imagen del logo en : Documents/reportes");

                        Paragraph fecha = new Paragraph();

                        fecha.add(Chunk.NEWLINE);
                        Date date = new Date();
                        fecha.add("Factura: "+ id+"\n"+ "Fecha: "+ new SimpleDateFormat("dd-MM-yyyy").format(date)+"\n\n");

                        PdfPTable Encabezado = new PdfPTable(3);
                        Encabezado.setWidthPercentage(100);
                        Encabezado.getDefaultCell().setBorder(0);
                        float[] ColumnaEncabezado = new float[]{30f, 70f, 40f};

                        Encabezado.setWidths(ColumnaEncabezado);
                        Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

                        String ruc = txt_ruc_config.getText();
                        String nom = txt_nombre_config.getText();
                        String telf= txt_telef_config.getText();
                        String dir= txt_direcc_config.getText();
                        String ra= txt_razon_config.getText();

                        Encabezado.addCell("");
                        Encabezado.addCell("Ruc: "+ruc+ "\nNombre: "+nom+ "\nTelefono: "+telf+ "\nDireccion: "+dir+"\nRazon: "+ra);
                        Encabezado.addCell(fecha);
                        docum.add(Encabezado);
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                 }
            

                Font negrita = new Font (Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
                Paragraph cli = new Paragraph();
                cli.add(Chunk.NEWLINE);
                Chunk clienteChunk = new Chunk("DATOS CLIENTE", negrita);
                clienteChunk.setBackground(BaseColor.WHITE);
                cli.add(clienteChunk);
                cli.add(Chunk.NEWLINE);
                cli.add(Chunk.NEWLINE);
                cli.setAlignment(Element.ALIGN_CENTER);
                docum.add(cli);

                //ACA YO AGREGE ESA LINEA MODIFICANDO EL NUMERO; PORQUE QUIERO CREAR LA TABLA CON 5 COLUMNAS Y NO 4
                //PdfPTable tableCli = new PdfPTable(4);
                PdfPTable tableCli = new PdfPTable(5);
                tableCli.setWidthPercentage(100);
                tableCli.getDefaultCell().setBorder(0);
                float[] ColumnaCli = new float[]{25f, 45f, 30f, 40f, 40f};
                tableCli.setWidths(ColumnaCli);
                tableCli.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell cl1 = new PdfPCell(new Phrase("dni/ruc", negrita));
                PdfPCell cl2 = new PdfPCell(new Phrase("nombre", negrita));
                PdfPCell cl3 = new PdfPCell(new Phrase("telefono", negrita));
                PdfPCell cl4 = new PdfPCell(new Phrase("direccion", negrita));
                //ESTA LINEA LA AGREGUE YO
                PdfPCell cl5 = new PdfPCell(new Phrase("razon", negrita));
                cl1.setBorder(0);
                cl2.setBorder(0);
                cl3.setBorder(0);
                cl4.setBorder(0);
                //ESTA LINEA LA AGREGUE YO
                cl5.setBorder(0);

                cl1.setBackgroundColor(BaseColor.CYAN);
                cl2.setBackgroundColor(BaseColor.CYAN);
                cl3.setBackgroundColor(BaseColor.CYAN);
                cl4.setBackgroundColor(BaseColor.CYAN);
                cl5.setBackgroundColor(BaseColor.CYAN);

                tableCli.addCell(cl1);
                tableCli.addCell(cl2);
                tableCli.addCell(cl3);
                tableCli.addCell(cl4);
                //ESTA LINEA LA AGREGUE YO
                tableCli.addCell(cl5);

                tableCli.addCell(txt_dniruc_venta.getText());
                tableCli.addCell(txt_nombrecliente_venta.getText());
                tableCli.addCell(txt_telefonocliente_venta.getText());
                tableCli.addCell(txt_direccioncliente_venta.getText());
                //ESTA LINEA LA AGREGUE YO
                tableCli.addCell(txt_razoncliente_venta.getText());

                docum.add(tableCli);

                //CODIGO PARA GENERAR LA TABLA DE PRODUCTOS 

                Paragraph pro2 = new Paragraph();
                pro2.add(Chunk.NEWLINE);
                Chunk proChunk = new Chunk("DATOS PRODUCTOS", negrita);
                proChunk.setBackground(BaseColor.WHITE);
                pro2.add(proChunk);
                pro2.add(Chunk.NEWLINE);
                pro2.add(Chunk.NEWLINE);
                pro2.setAlignment(Element.ALIGN_CENTER);
                docum.add(pro2);

                PdfPTable tablePro = new PdfPTable(4);
                tablePro.setWidthPercentage(100);
                tablePro.getDefaultCell().setBorder(0);
                float[] ColumnaPro = new float[]{15f, 40f, 25f, 25f};
                tablePro.setWidths(ColumnaPro);
                tablePro.setHorizontalAlignment(Element.ALIGN_CENTER);
                PdfPCell pro1 = new PdfPCell(new Phrase("Cantidad", negrita));
                PdfPCell pro12 = new PdfPCell(new Phrase("Producto", negrita));
                PdfPCell pro3 = new PdfPCell(new Phrase("Precio Ud", negrita));
                PdfPCell pro4 = new PdfPCell(new Phrase("Precio total", negrita));
                pro1.setBorder(0);
                pro12.setBorder(0);
                pro3.setBorder(0);
                pro4.setBorder(0);
                pro1.setBackgroundColor(BaseColor.CYAN);
                pro12.setBackgroundColor(BaseColor.CYAN);
                pro3.setBackgroundColor(BaseColor.CYAN);
                pro4.setBackgroundColor(BaseColor.GREEN);
                tablePro.addCell(pro1);
                tablePro.addCell(pro12);
                tablePro.addCell(pro3);
                tablePro.addCell(pro4);
                for (int i = 0; i < table_venta.getRowCount(); i++) {
                    String producto = table_venta.getValueAt(i, 1).toString();
                    String cantidad = table_venta.getValueAt(i, 2).toString();
                    String precio = table_venta.getValueAt(i, 3).toString();
                    String total = table_venta.getValueAt(i, 4).toString();
                    tablePro.addCell(cantidad);
                    tablePro.addCell(producto);
                    tablePro.addCell(precio);
                    tablePro.addCell(total);
                }

                docum.add(tablePro);

                Paragraph info = new Paragraph();
                info.add(Chunk.NEWLINE);
                Chunk infoChunk = new Chunk("total a pagar: ".toUpperCase()+totalPagar);
                infoChunk.setBackground(BaseColor.GREEN);
                info.add(infoChunk);
                info.setAlignment(Element.ALIGN_RIGHT);
                docum.add(info);

                Paragraph firma = new Paragraph();
                firma.add(Chunk.NEWLINE);
                firma.add(Chunk.NEWLINE);
                firma.add("----------------------------------");
                firma.add(Chunk.NEWLINE);
                firma.add("Declaracion y firma");
                firma.setAlignment(Element.ALIGN_CENTER);
                docum.add(firma);


                Paragraph mensaje = new Paragraph();
                mensaje.add(Chunk.NEWLINE);
                Chunk mensajeChunk = new Chunk ("Gracias por su compra! vaya con bien buen ciudadano honorable", negrita);
                mensajeChunk.setBackground(BaseColor.LIGHT_GRAY);
                mensaje.add(mensajeChunk);
                mensaje.setAlignment(Element.ALIGN_CENTER);

                docum.add(mensaje);
                
                Paragraph mensaje2 = new Paragraph();
                mensaje2.add(Chunk.NEWLINE);
                mensaje2.add(Chunk.NEWLINE);
                mensaje2.add(Chunk.NEWLINE);
                mensaje2.add(Chunk.NEWLINE);
                mensaje2.add(Chunk.NEWLINE);
                Chunk mensaje2Chunk = new Chunk ("Al firmar arriba nuestro cliente confirma quedar absolutamente conforme con la compra y todo el procedimiento dutante el proceso", negrita);
                mensaje2Chunk.setBackground(BaseColor.LIGHT_GRAY);
                mensaje2.add(mensajeChunk);
                mensaje2.setAlignment(Element.ALIGN_CENTER);

                docum.add(mensaje2);

                docum.close();
                archivo.close();
                Desktop.getDesktop().open(file);
            
             } catch (DocumentException | IOException e) {
                 System.out.println(e.toString());   
                 JOptionPane.showMessageDialog(null, "aca el error es empezando el pdf");
            }
        }
}
