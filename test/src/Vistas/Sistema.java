
package Vistas;

import Reportes.Excel;
import Modelo.clientes;
import Modelo.clientesDAO;
import Modelo.config;
import Modelo.detalle;
import Modelo.eventos;
import Modelo.productos;
import Modelo.productosDao;
import Modelo.proveedor;
import Modelo.proveedorDAO;
import Modelo.ventas;
import Modelo.ventasDao;
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
        initComponents();
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
        listarConfig();
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

        // Solo permite la inserción de caracteres que son dígitos
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return;
            }
        }
        
        
        if (str == null) {
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return;
            }
        }
        super.insertString(offs, str, a);
    }
    };

    
    public void listar_clientes() {
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
    
    public void listar_proveedor() {
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
        jLabelPuntoVenta = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btn_eliminar_venta = new javax.swing.JButton();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockVenta = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_venta = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btn_imprimirgenerar_venta = new javax.swing.JButton();
        txt_dniruc_venta = new javax.swing.JTextField();
        txt_nombrecliente_venta = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        label_total_venta = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        txt_telefonocliente_venta = new javax.swing.JTextField();
        txt_direccioncliente_venta = new javax.swing.JTextField();
        txt_razoncliente_venta = new javax.swing.JTextField();
        txt_idproducto_venta = new javax.swing.JTextField();
        comboBoxProduct = new javax.swing.JComboBox<>();
        btn_eliminar_venta1 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
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
        label_id_clientes = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        check_opt_actu = new javax.swing.JCheckBox();
        jLabel38 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
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
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        table_ventas = new javax.swing.JTable();
        btn_pdf_ventas = new javax.swing.JButton();
        txt_id_ventas = new javax.swing.JTextField();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 153));

        btn_nueva_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Nventa.png"))); // NOI18N
        btn_nueva_venta.setText("Nueva venta ");
        btn_nueva_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nueva_ventaActionPerformed(evt);
            }
        });

        btn_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Clientes.png"))); // NOI18N
        btn_clientes.setText("Clientes");
        btn_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clientesActionPerformed(evt);
            }
        });

        btn_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N
        btn_proveedor.setText("Proveedor");
        btn_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_proveedorActionPerformed(evt);
            }
        });

        btn_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/producto.png"))); // NOI18N
        btn_productos.setText("Productos");
        btn_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_productosActionPerformed(evt);
            }
        });

        btn_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/compras.png"))); // NOI18N
        btn_ventas.setText("Ventas");
        btn_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ventasActionPerformed(evt);
            }
        });

        btn_config.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/config.png"))); // NOI18N
        btn_config.setText("Config");
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

        Label_vendedor.setText("VENDEDOR");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btn_nueva_venta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_clientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_ventas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_config, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(Label_vendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Label_vendedor)
                .addGap(19, 19, 19)
                .addComponent(btn_nueva_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_clientes)
                .addGap(18, 18, 18)
                .addComponent(btn_proveedor)
                .addGap(18, 18, 18)
                .addComponent(btn_productos)
                .addGap(18, 18, 18)
                .addComponent(btn_ventas)
                .addGap(18, 18, 18)
                .addComponent(btn_config)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 560));

        jLabelPuntoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/encabezado.png"))); // NOI18N
        jLabelPuntoVenta.setText("               ");
        getContentPane().add(jLabelPuntoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 790, 160));

        jTabbedPane2.setBackground(new java.awt.Color(250, 250, 250));
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));
        jTabbedPane2.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane2.setEnabled(false);
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(230, 230, 230));
        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Codigo");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Descripcion Prd");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Cantidad");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Precio");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Stock Disp");

        btn_eliminar_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btn_eliminar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_ventaActionPerformed(evt);
            }
        });

        txtDescripcionVenta.setEditable(false);

        txtCantidadVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCantidadVentaMouseClicked(evt);
            }
        });
        txtCantidadVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadVentaActionPerformed(evt);
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

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.setBackground(new java.awt.Color(248, 248, 248));

        txtStockVenta.setEditable(false);

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

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("DNI/RUC:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("NOMBRE Cliente:");

        btn_imprimirgenerar_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/print.png"))); // NOI18N
        btn_imprimirgenerar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirgenerar_ventaActionPerformed(evt);
            }
        });

        txt_dniruc_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dniruc_ventaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dniruc_ventaKeyTyped(evt);
            }
        });

        txt_nombrecliente_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombrecliente_ventaKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/money.png"))); // NOI18N
        jLabel20.setText("TOTAL A PAGAR:");

        label_total_venta.setBackground(new java.awt.Color(255, 255, 255));
        label_total_venta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label_total_venta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_total_venta.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        label_total_venta.setFocusable(false);
        label_total_venta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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

        txt_telefonocliente_venta.setEditable(false);
        txt_telefonocliente_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telefonocliente_ventaActionPerformed(evt);
            }
        });

        txt_direccioncliente_venta.setEditable(false);

        txt_razoncliente_venta.setEditable(false);

        txt_idproducto_venta.setEditable(false);
        txt_idproducto_venta.setEnabled(false);

        comboBoxProduct.setEditable(true);
        comboBoxProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxProductActionPerformed(evt);
            }
        });
        comboBoxProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comboBoxProductKeyTyped(evt);
            }
        });

        btn_eliminar_venta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/compras.png"))); // NOI18N
        btn_eliminar_venta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_venta1ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("TELEFONO:");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("DIRECCION:");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("RAZON SOCIAL:");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton1.setText("BUSCAR CLIENTE por ruc o dni");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 7)); // NOI18N
        jLabel41.setText("BUSCAR CLIENTE POR RUC O DNI");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboBoxProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(txtDescripcionVenta))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(txtCantidadVenta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(txtPrecioVenta))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(txtStockVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_idproducto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_eliminar_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_eliminar_venta1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_nombrecliente_venta, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_dniruc_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 111, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_telefonocliente_venta)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_direccioncliente_venta)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_razoncliente_venta)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))))
                        .addGap(30, 30, 30)
                        .addComponent(btn_imprimirgenerar_venta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(label_total_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(jLabel15)
                                .addComponent(jLabel16)
                                .addComponent(jLabel17)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtStockVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_idproducto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboBoxProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_eliminar_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_eliminar_venta1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_total_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_nombrecliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_dniruc_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel40))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_telefonocliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_direccioncliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_razoncliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_imprimirgenerar_venta)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 799, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("     Nueva venta    ", jPanel4);

        jPanel5.setBackground(new java.awt.Color(225, 225, 225));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("DNI/RUC:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("NOMBRE:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("TELEFONO:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("DIRECCON:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("RAZON SOCIAL:");

        txt_dniruc_clientes.setEditable(false);

        txt_nombre_clientes.setEditable(false);
        txt_nombre_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_clientesKeyTyped(evt);
            }
        });

        txt_telefono_clientes.setEditable(false);
        txt_telefono_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefono_clientesKeyTyped(evt);
            }
        });

        txt_direccion_clientes.setEditable(false);

        txt_razonsocial_clientes.setEditable(false);

        table_clientes.setBackground(new java.awt.Color(255, 250, 235));
        table_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "DNI/RUC", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
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

        btn_guardar_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btn_guardar_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_guardar_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar_clientesActionPerformed(evt);
            }
        });

        btn_editar_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btn_editar_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_editar_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_clientesActionPerformed(evt);
            }
        });

        btn_eliminar_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btn_eliminar_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_eliminar_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_clientesActionPerformed(evt);
            }
        });

        btn_nuevo_clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btn_nuevo_clientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_nuevo_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo_clientesActionPerformed(evt);
            }
        });

        txt_id_clientes.setEditable(false);
        txt_id_clientes.setBackground(new java.awt.Color(210, 210, 210));

        label_id_clientes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        label_id_clientes.setText("Id");

        jLabel34.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel34.setText("Guardar");

        jLabel35.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel35.setText("Nuevo");

        jLabel36.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel36.setText("Actualizar");

        jLabel37.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel37.setText("Eliminar");

        check_opt_actu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_opt_actuActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel38.setText("Seleccione para habilitar la escritura y editar los datos del cliente.");

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel1.setText("Toque 2 veces sobre el nombre del cliente para seleccionarlo.");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_nuevo_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_guardar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btn_editar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel36))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btn_eliminar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel37))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_direccion_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_telefono_clientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_nombre_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_razonsocial_clientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_dniruc_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_id_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(label_id_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(check_opt_actu, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_id_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(4, 4, 4)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(check_opt_actu)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(32, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_dniruc_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_id_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_nombre_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_telefono_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_direccion_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txt_razonsocial_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btn_editar_clientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(btn_nuevo_clientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btn_eliminar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btn_guardar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );

        jTabbedPane2.addTab("          Clientes          ", jPanel5);

        jPanel6.setBackground(new java.awt.Color(230, 230, 230));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("RUC:");

        txt_ruc_proveedor.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("NOMBRE:");

        txt_nombre_proveedor.setEditable(false);
        txt_nombre_proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_proveedorKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("TELEFONO:");

        txt_telefono_proveedor.setEditable(false);
        txt_telefono_proveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefono_proveedorKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("DIRECCON:");

        txt_direccion_proveedor.setEditable(false);

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("RAZON SOCIAL:");

        txt_razonsocial_proveedor.setEditable(false);

        btn_guardar_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btn_guardar_proveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_guardar_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar_proveedorActionPerformed(evt);
            }
        });

        btn_eliminar_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btn_eliminar_proveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_eliminar_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_proveedorActionPerformed(evt);
            }
        });

        btn_editar_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btn_editar_proveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_editar_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_proveedorActionPerformed(evt);
            }
        });

        btn_nuevo_proveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btn_nuevo_proveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_nuevo_proveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo_proveedorActionPerformed(evt);
            }
        });

        table_proveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "RUC", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
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
        txt_id_proveedor.setEnabled(false);

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
        txt_texto_cambiante.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_texto_cambiante.setBorder(null);

        jButton_guar_camb.setBackground(new java.awt.Color(153, 255, 153));
        jButton_guar_camb.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_guar_camb.setText("GUARDAR CAMBIOS");
        jButton_guar_camb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_guar_cambMouseClicked(evt);
            }
        });

        txt_text_indicTabla.setEditable(false);
        txt_text_indicTabla.setBackground(new java.awt.Color(229, 229, 229));
        txt_text_indicTabla.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_text_indicTabla.setBorder(null);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btn_nuevo_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelNuevoProveed))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btn_editar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelActuProveed)
                                .addGap(10, 10, 10)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_guardar_proveedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_eliminar_proveedor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelElimProveed)
                                    .addComponent(jLabelGuardProveed))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(txt_texto_cambiante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_id_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_guar_camb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_razonsocial_proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                            .addComponent(txt_direccion_proveedor)
                                            .addComponent(txt_nombre_proveedor)
                                            .addComponent(txt_telefono_proveedor))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(txt_ruc_proveedor)
                                        .addGap(30, 30, 30)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(txt_text_indicTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txt_texto_cambiante, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_text_indicTabla)
                    .addComponent(txt_id_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_ruc_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nombre_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_telefono_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_direccion_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_razonsocial_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btn_nuevo_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_guardar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelNuevoProveed)
                            .addComponent(jLabelGuardProveed))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btn_editar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_eliminar_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelActuProveed)
                            .addComponent(jLabelElimProveed))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_guar_camb)
                .addGap(70, 70, 70))
        );

        jTabbedPane2.addTab("         Proveedor       ", jPanel6);

        jPanel7.setBackground(new java.awt.Color(230, 230, 230));
        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Codigo:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Producto:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Stock disp:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Precio:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Proveedor:");

        txt_codigo_productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_codigo_productosKeyTyped(evt);
            }
        });

        txt_Stock_productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_Stock_productosKeyTyped(evt);
            }
        });

        txt_precio_productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_precio_productosKeyTyped(evt);
            }
        });

        cbx_proveedor.setEditable(true);

        table_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "CODIGO", "PRODUCTO", "STOCK", "PRECIO", "PROVEEDOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_productosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(table_productos);
        if (table_productos.getColumnModel().getColumnCount() > 0) {
            table_productos.getColumnModel().getColumn(0).setPreferredWidth(20);
            table_productos.getColumnModel().getColumn(1).setPreferredWidth(50);
            table_productos.getColumnModel().getColumn(2).setPreferredWidth(60);
            table_productos.getColumnModel().getColumn(3).setPreferredWidth(50);
            table_productos.getColumnModel().getColumn(4).setPreferredWidth(50);
            table_productos.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        btn_guardar_productos.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        btn_guardar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/GuardarTodo.png"))); // NOI18N
        btn_guardar_productos.setText("Guardar");
        btn_guardar_productos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_guardar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar_productosActionPerformed(evt);
            }
        });

        btn_editar_productos.setFont(new java.awt.Font("Tahoma", 2, 9)); // NOI18N
        btn_editar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        btn_editar_productos.setText("Actualiz");
        btn_editar_productos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_editar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editar_productosActionPerformed(evt);
            }
        });

        btn_eliminar_productos.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        btn_eliminar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btn_eliminar_productos.setText("Eliminar");
        btn_eliminar_productos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_eliminar_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminar_productosActionPerformed(evt);
            }
        });

        btn_nuevo_productos.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        btn_nuevo_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btn_nuevo_productos.setText("Nuevo");
        btn_nuevo_productos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_nuevo_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevo_productosActionPerformed(evt);
            }
        });

        btn_excel_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/excel.png"))); // NOI18N
        btn_excel_productos.setText("Imprimir");
        btn_excel_productos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 204), 2));
        btn_excel_productos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_excel_productos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_excel_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_excel_productosActionPerformed(evt);
            }
        });

        txt_id_productos.setEditable(false);
        txt_id_productos.setEnabled(false);
        txt_id_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_productosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btn_nuevo_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btn_editar_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_guardar_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_eliminar_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btn_excel_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_id_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_precio_productos)
                                .addComponent(txt_Stock_productos, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_pro_nombre, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cbx_proveedor, 0, 132, Short.MAX_VALUE)
                                .addComponent(txt_codigo_productos)))
                        .addGap(28, 28, 28)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txt_id_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txt_codigo_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txt_pro_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(txt_Stock_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txt_precio_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(cbx_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_nuevo_productos, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                                    .addComponent(btn_guardar_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btn_eliminar_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_editar_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btn_excel_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("           Productos          ", jPanel7);

        jPanel8.setBackground(new java.awt.Color(230, 230, 230));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));

        table_ventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        jScrollPane5.setViewportView(table_ventas);
        if (table_ventas.getColumnModel().getColumnCount() > 0) {
            table_ventas.getColumnModel().getColumn(0).setPreferredWidth(20);
            table_ventas.getColumnModel().getColumn(1).setPreferredWidth(60);
            table_ventas.getColumnModel().getColumn(2).setPreferredWidth(60);
            table_ventas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        btn_pdf_ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf.png"))); // NOI18N
        btn_pdf_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pdf_ventasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btn_pdf_ventas)
                        .addGap(18, 18, 18)
                        .addComponent(txt_id_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_pdf_ventas)
                    .addComponent(txt_id_ventas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        jTabbedPane2.addTab("              Ventas             ", jPanel8);

        jPanel9.setBackground(new java.awt.Color(180, 180, 180));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel28.setBackground(new java.awt.Color(204, 255, 255));
        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("RUC");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("NOMBRE");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("TELEFONO");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("DIRECCION");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("RAZON SOCIAL");

        txt_ruc_config.setBackground(new java.awt.Color(204, 255, 255));

        txt_nombre_config.setBackground(new java.awt.Color(204, 255, 255));
        txt_nombre_config.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_configKeyTyped(evt);
            }
        });

        txt_telef_config.setBackground(new java.awt.Color(204, 255, 255));
        txt_telef_config.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telef_configKeyTyped(evt);
            }
        });

        txt_direcc_config.setBackground(new java.awt.Color(204, 255, 255));

        txt_razon_config.setBackground(new java.awt.Color(204, 255, 255));

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
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGap(273, 273, 273)
                            .addComponent(btn_Actualizar_config)
                            .addGap(59, 59, 59)
                            .addComponent(btn_guardar_config, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGap(62, 62, 62)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(txt_Id_Config, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(143, 143, 143)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(32, 32, 32))
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txt_ruc_config, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel9Layout.createSequentialGroup()
                                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txt_nombre_config)
                                                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                                .addComponent(txt_direcc_config, javax.swing.GroupLayout.Alignment.LEADING))))
                                    .addGap(18, 18, 18)))
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_telef_config)
                                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                        .addComponent(txt_razon_config, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Id_Config, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_nombre_config, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(txt_telef_config)
                    .addComponent(txt_ruc_config))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_direcc_config, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_razon_config, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(btn_Actualizar_config, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(btn_guardar_config, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
        );

        jTabbedPane2.addTab("                 Config                ", jPanel9);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 800, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaActionPerformed

    private void btn_excel_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_excel_productosActionPerformed
        // TODO add your handling code here:
       Excel.reporte();
    }//GEN-LAST:event_btn_excel_productosActionPerformed

    private void txt_telefonocliente_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telefonocliente_ventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telefonocliente_ventaActionPerformed

    private void txt_id_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_productosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_productosActionPerformed

    private void btn_nuevo_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo_clientesActionPerformed
        // TODO add your handling code here:
        label_id_clientes.setVisible(false);
        txt_id_clientes.setVisible(false);
        check_opt_actu.setSelected(false);
        limpiarCampos2(txt_id_clientes,txt_nombre_clientes,txt_dniruc_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
        //limpiarCampos();    ////ESTE METODO LIMPIA LOS CAMPOS, EL DE ARRIBA ES OTRA FORMA DE HACERLO PASANDOLE LOS PARAMETROS JTextField)
        activarCampos(txt_dniruc_clientes,txt_nombre_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
        txt_dniruc_clientes.setDocument(doc);
    }//GEN-LAST:event_btn_nuevo_clientesActionPerformed
    //Codigo boton para guardar clientes
    private void btn_guardar_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardar_clientesActionPerformed
        
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
                    label_id_clientes.setVisible(true);
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
        label_id_clientes.setVisible(true);
    }//GEN-LAST:event_btn_clientesActionPerformed

    private void btn_nueva_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nueva_ventaActionPerformed
 
        jTabbedPane2.setSelectedIndex(0);
    }//GEN-LAST:event_btn_nueva_ventaActionPerformed

    private void btn_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proveedorActionPerformed
        // TABLA proveedor
        jTabbedPane2.setSelectedIndex(2);
        desactivarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
        activar_vista_proveedor();
        limpiarTabla();
        listar_proveedor();
        limpiarCampos2(txt_id_proveedor,txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
        txt_text_indicTabla.setText("toque 2 veces sobre el nombre del proveedor para seleccionar");
        txt_texto_cambiante.setText("");
    }//GEN-LAST:event_btn_proveedorActionPerformed

    private void btn_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_productosActionPerformed
        // TODO add your handling code here:
 
        limpiarTabla();
        limpiar_campos_productos();
        listar_productos();
        
        jTabbedPane2.setSelectedIndex(3);
        
    }//GEN-LAST:event_btn_productosActionPerformed

    private void btn_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ventasActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(4);
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
                
                //INVOCAR ESTE METODO PERMITE LIMPIAR LOS ID DE CLIENTES LUEGO DE QUE SEAN ELIMINADOS
                client.resetAutoIncrement();
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
                    label_id_clientes.setVisible(true);
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
                    /*desactivarCampos();
                    check_opt_actu.setSelected(false);
                    limpiarTabla();
                    limpiarCampos2(txt_id_proveedor,txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
                    listar_proveedor();*/
                    }
                }
            }
        }else 
            JOptionPane.showMessageDialog(null, "por favor llene todos los campos para guardar");                                 
    }//GEN-LAST:event_btn_guardar_proveedorActionPerformed

    private void table_proveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_proveedorMouseClicked
        // TODO add your handling code here:
        //ESTE EVENTO ES EL QUE PERMITE QUE AL DAR CLICK EN UN PROVEEDOR SE MUESTREN LOS DATOS EN LOS CAMPOS TXTFIELD
        txt_id_proveedor.setVisible(true);
        txt_texto_cambiante.setText("");
        txt_text_indicTabla.setText("toque 2 veces sobre el nombre del proveedor para seleccionar");
        activar_vista_proveedor();
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
        if (!"".equals(txt_id_proveedor.getText())){
            //ESTO MJESTRA UN CUADRO CON OPCIONES "si,no,cancelar" que capture un boolean
            int pregunta = JOptionPane.showConfirmDialog(null, "esta seguro de eliminar");
            if (pregunta==0){
                int id= Integer.parseInt(txt_id_proveedor.getText());
                prDao.eliminar_proveedor(id);
                limpiarTabla();
                limpiarCampos2(txt_id_proveedor,txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
                listar_proveedor();
                JOptionPane.showMessageDialog(null, "eliminado correctamente");
                
                //INVOCAR ESTE METODO PERMITE LIMPIAR LOS ID DE CLIENTES LUEGO DE QUE SEAN ELIMINADOS
                prDao.resetAutoIncrement2();              
            }
        }
    }//GEN-LAST:event_btn_eliminar_proveedorActionPerformed

    private void btn_nuevo_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo_proveedorActionPerformed
        // TODO add your handling code here:
        txt_id_proveedor.setVisible(false);
        txt_text_indicTabla.setText("");
        txt_texto_cambiante.setText("por favor ingrese los datos");
        limpiarCampos2(txt_id_proveedor,txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
        activarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
        activar_vista_proveedor();
        
    }//GEN-LAST:event_btn_nuevo_proveedorActionPerformed

    private void btn_editar_proveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editar_proveedorActionPerformed
        // TODO add your handling code here:
        txt_text_indicTabla.setText("");
        if ("".equals(txt_id_proveedor.getText())){
            JOptionPane.showMessageDialog(null, "para ACTUALIZAR los datos seleccione una fila en la lista");
            //txt_text_indicTabla.setText("toque 2 veces sobre el nombre del proveedor para seleccionar");
        }else{
            int pregunta = JOptionPane.showConfirmDialog(null, "¿desea actualizar los datos?");
            if (pregunta==0){
                activarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
                txt_texto_cambiante.setText("los campos estan habilitados para editar");
                activar_boton_guardar();
            }else
                txt_text_indicTabla.setText("toque 2 veces sobre el nombre del proveedor para seleccionar");
        } 
        
    }//GEN-LAST:event_btn_editar_proveedorActionPerformed

    private void jButton_guar_cambMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_guar_cambMouseClicked
        // TODO add your handling code here:
        txt_text_indicTabla.setText("");
        String ruc = txt_ruc_proveedor.getText();
        String nombre = txt_nombre_proveedor.getText();
        if (!"".equals(txt_ruc_proveedor.getText())&&!"".equals(txt_nombre_proveedor.getText())&&!"".equals(txt_telefono_proveedor.getText())&&!"".equals(txt_direccion_proveedor.getText())&&!"".equals(txt_razonsocial_proveedor.getText())){  
            int pregunta = JOptionPane.showConfirmDialog(null, "¿desea guardar los datos que han sido actualizados?");
            if ((pregunta==0&&prDao.existeDNIRUC(ruc))||(pregunta==0&&prDao.existeNombre(nombre))){
                JOptionPane.showMessageDialog(null, "El Proveedor ya está registrado");
                txt_texto_cambiante.setText("");
                activar_vista_proveedor();
                desactivarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
            } else if ((pregunta==0&&!prDao.existeDNIRUC(ruc))||(pregunta==0&&!prDao.existeNombre(nombre))){
                txt_texto_cambiante.setText("");
                pr.setRuc(txt_ruc_proveedor.getText());
                pr.setNombre(txt_nombre_proveedor.getText());
                pr.setTelefono(txt_telefono_proveedor.getText());
                pr.setDireccion(txt_direccion_proveedor.getText());
                pr.setRazon(txt_razonsocial_proveedor.getText());
                pr.setId(Integer.parseInt(txt_id_proveedor.getText()));
                prDao.modificar_proveedor(pr);
                limpiarTabla();
                listar_proveedor();
                //limpiarCampos2(txt_id_proveedor,txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor); 
                JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");
                desactivarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
                activar_vista_proveedor();
            }else {
                limpiarTabla();
                listar_proveedor();
                desactivarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
                activar_vista_proveedor();
                txt_texto_cambiante.setText("");
                txt_text_indicTabla.setText("toque 2 veces sobre el nombre del proveedor para seleccionar");
            }
              

                //txt_text_indicTabla.setText("toque 2 veces sobre el nombre del proveedor para seleccionar");
        }else {
            JOptionPane.showMessageDialog(null, "los campos estan vacios");
            activar_vista_proveedor();
            txt_texto_cambiante.setText("");
            desactivarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
            //txt_text_indicTabla.setText("toque 2 veces sobre el nombre del proveedor para seleccionar");
        }
        txt_text_indicTabla.setText("toque 2 veces sobre el nombre del proveedor para seleccionar");
    }//GEN-LAST:event_jButton_guar_cambMouseClicked

    private void btn_pdf_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pdf_ventasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_pdf_ventasActionPerformed

    private void btn_guardar_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardar_productosActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txt_codigo_productos.getText())&&!"".equals(txt_Stock_productos.getText())&&!"".equals(txt_precio_productos.getText())&&!"".equals(txt_pro_nombre.getText())&&!"".equals(cbx_proveedor.getSelectedItem())){
            if (proDao.existeCodigo(txt_codigo_productos.getText())) {
                JOptionPane.showMessageDialog(null, "El producto ya está registrado");
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿desea guardar?");
                if (pregunta==0){
                    pregunta = JOptionPane.showConfirmDialog(null, "¿A continuacion se guardaran los datos, desea continuar?");
                    if (pregunta==0){
                    pro.setCodigo(txt_codigo_productos.getText());
                    pro.setNombre(txt_pro_nombre.getText());
                    pro.setNombreProveedor(((String)cbx_proveedor.getSelectedItem().toString()));
                    pro.setStock(Integer.parseInt(  txt_Stock_productos.getText()));
                    pro.setPrecio(Double.parseDouble(txt_precio_productos.getText()));
                    
                    //pro.setMarca(((String) cbx_marca_productos.getSelectedItem()));
                        try {
                            proDao.registrar_producto(pro);
                            JOptionPane.showMessageDialog(null, "producto registrado");
                        } catch (HeadlessException e) {
                            JOptionPane.showMessageDialog(null, "Algo salio mal");
                        }
                    
                    }
                }
            }
        }else 
            JOptionPane.showMessageDialog(null, "por favor llene todos los campos para guardar");
        
        limpiarTabla();
        listar_productos();
    }//GEN-LAST:event_btn_guardar_productosActionPerformed

    private void table_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_productosMouseClicked
        // TODO add your handling code here
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
         if (!"".equals(txt_id_productos.getText())){
            //ESTO MJESTRA UN CUADRO CON OPCIONES "si,no,cancelar" que capture un boolean
            int pregunta = JOptionPane.showConfirmDialog(null, "esta seguro de eliminar");
            if (pregunta==0){
                int id= Integer.parseInt(txt_id_productos.getText());
                proDao.eliminarProductos(id);
                limpiarTabla();
                listar_productos();
                JOptionPane.showMessageDialog(null, "eliminado correctamente");
                
                //INVOCAR ESTE METODO PERMITE LIMPIAR LOS ID DE CLIENTES LUEGO DE QUE SEAN ELIMINADOS
                proDao.resetAutoIncrement3();              
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
            if (pregunta==0){
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
                }else
                    JOptionPane.showMessageDialog(null, "los campos estan vacios o usted no tiene seleccionada la ocpion para actualizar los datos");
            }
        }
        
    }//GEN-LAST:event_btn_editar_productosActionPerformed

    private void btn_nuevo_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo_productosActionPerformed
        // TODO add your handling code here:
        limpiar_campos_productos();
        listar_productos();
    }//GEN-LAST:event_btn_nuevo_productosActionPerformed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        // TODO add your handling code here:
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "debe seleccionar el producto que desea eliminar");
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
        if (table_venta.getRowCount()>0){
            if (!"".equals(txt_nombrecliente_venta.getText())){
                registarVenta();
                txtCodigoVenta.requestFocus();
                registrarDetalle();
                actualizarStock();
                pdf(); /// hay que agregar la libreria swingx-all-1.6.4.jar.....
                limpiarTablaVenta();
                limpiarDatosClientesVenta();
            }else{
                JOptionPane.showMessageDialog(null, "¡la venta debe contener los datos del cliente!");
                 txt_nombrecliente_venta.requestFocus();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Para ejecutar e imprimir la venta es necesario tener un Producto ingresado en la tabla");
            txtCodigoVenta.requestFocus();
        }
    }//GEN-LAST:event_btn_imprimirgenerar_ventaActionPerformed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        // TODO add your handling code here:
        //event.verificarSoloNumberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txt_dniruc_ventaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dniruc_ventaKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
    }//GEN-LAST:event_txt_dniruc_ventaKeyTyped

    private void txt_precio_productosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precio_productosKeyTyped
        // TODO add your handling code here:
        event.verifNumberDecimalKeyPress(evt,  txt_precio_productos);
    }//GEN-LAST:event_txt_precio_productosKeyTyped

    private void btn_Actualizar_configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Actualizar_configActionPerformed

        int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea actualizar los datos de la empresa?");
        if (pregunta == 0) {
             activarCampos(txt_ruc_config, txt_nombre_config, txt_telef_config, txt_direcc_config, txt_razon_config);
             JOptionPane.showMessageDialog(null, "Se han HABILITADO los campos para su modificacion");
             btn_guardar_config.setVisible(true);
             btn_Actualizar_config.setVisible( false);
        }
      /*  int pregunta = JOptionPane.showConfirmDialog(null, "¿desea actualizar los datos de la empresa?");
        if (pregunta==0){ 
            if (!"".equals(txt_ruc_config.getText())&&!"".equals(txt_nombre_config.getText())&&!"".equals(txt_telef_config.getText())&&!"".equals(txt_direcc_config.getText())&&!"".equals(txt_razon_config.getText())){  
                conf.setRuc(txt_ruc_config.getText());
                conf.setNombre(txt_nombre_config.getText());
                conf.setTelefono(txt_telef_config.getText());
                conf.setDireccion(txt_direcc_config.getText());
                conf.setRazon(txt_razon_config.getText());
                conf.setId(Integer.parseInt(txt_Id_Config.getText()));
                proDao.modificarDatosEmpresa(conf);
                JOptionPane.showMessageDialog(null, "Datos actualizados correctamente");
                listarConfig();
            }else
                JOptionPane.showMessageDialog(null, "los campos estan vacios o usted no tiene seleccionada la ocpion para actualizar los datos");
        }*/
    }//GEN-LAST:event_btn_Actualizar_configActionPerformed

    private void txt_nombre_configKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_configKeyTyped
        // TODO add your handling code here:
        event.verificarSoloTextKeyPress(evt);
    }//GEN-LAST:event_txt_nombre_configKeyTyped

    private void txt_telef_configKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telef_configKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
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

    private void comboBoxProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxProductActionPerformed
        // TODO add your handling code here:
        
         //txtCantidadVenta.setEditable(false);
        
    }//GEN-LAST:event_comboBoxProductActionPerformed

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

    private void comboBoxProductKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comboBoxProductKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
    }//GEN-LAST:event_comboBoxProductKeyTyped

    private void txt_nombre_clientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_clientesKeyTyped
        // TODO add your handling code here:
        event.verificarSoloTextKeyPress(evt);
    }//GEN-LAST:event_txt_nombre_clientesKeyTyped

    private void txt_telefono_clientesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefono_clientesKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
    }//GEN-LAST:event_txt_telefono_clientesKeyTyped

    private void txt_telefono_proveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefono_proveedorKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
    }//GEN-LAST:event_txt_telefono_proveedorKeyTyped

    private void txt_codigo_productosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigo_productosKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
    }//GEN-LAST:event_txt_codigo_productosKeyTyped

    private void txt_Stock_productosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_Stock_productosKeyTyped
        // TODO add your handling code here:
        event.verificarSoloNumberKeyPress(evt);
    }//GEN-LAST:event_txt_Stock_productosKeyTyped

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
    private javax.swing.JButton btn_ventas;
    private javax.swing.JComboBox<String> cbx_proveedor;
    private javax.swing.JCheckBox check_opt_actu;
    private javax.swing.JComboBox<String> comboBoxProduct;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_guar_camb;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelActuProveed;
    private javax.swing.JLabel jLabelElimProveed;
    private javax.swing.JLabel jLabelGuardProveed;
    private javax.swing.JLabel jLabelNuevoProveed;
    private javax.swing.JLabel jLabelPuntoVenta;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JLabel label_id_clientes;
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
        try {
            int id= vDao.idVenta();
            FileOutputStream archivo;
            File file = new File("src/pdf/venta_"+id+".pdf");
            archivo = new FileOutputStream(file);
            Document docum = new Document() ;
            PdfWriter.getInstance(docum, archivo);
            docum.open();
            Image img = Image.getInstance("src/img/ebrey.png");
            
            Paragraph fecha = new Paragraph();
            Font negrita = new Font (Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Factura: "+ id+"\n"+ "Fecha: "+ new SimpleDateFormat("dd-mm-yyyy").format(date)+"\n\n");
            
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
            
            Paragraph pro = new Paragraph();
            pro.add(Chunk.NEWLINE);
            Chunk proChunk = new Chunk("DATOS PRODUCTOS", negrita);
            proChunk.setBackground(BaseColor.WHITE);
            pro.add(proChunk);
            pro.add(Chunk.NEWLINE);
            pro.add(Chunk.NEWLINE);
            pro.setAlignment(Element.ALIGN_CENTER);
            docum.add(pro);
            
            PdfPTable tablePro = new PdfPTable(4);
            tablePro.setWidthPercentage(100);
            tablePro.getDefaultCell().setBorder(0);
            float[] ColumnaPro = new float[]{15f, 40f, 25f, 25f};
            tablePro.setWidths(ColumnaPro);
            tablePro.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell pro1 = new PdfPCell(new Phrase("Cantidad", negrita));
            PdfPCell pro2 = new PdfPCell(new Phrase("Producto", negrita));
            PdfPCell pro3 = new PdfPCell(new Phrase("Precio Ud", negrita));
            PdfPCell pro4 = new PdfPCell(new Phrase("Precio total", negrita));
            pro1.setBorder(0);
            pro2.setBorder(0);
            pro3.setBorder(0);
            pro4.setBorder(0);
            pro1.setBackgroundColor(BaseColor.CYAN);
            pro2.setBackgroundColor(BaseColor.CYAN);
            pro3.setBackgroundColor(BaseColor.CYAN);
            pro4.setBackgroundColor(BaseColor.GREEN);
            tablePro.addCell(pro1);
            tablePro.addCell(pro2);
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
               
            docum.close();
            archivo.close();
            Desktop.getDesktop().open(file);
            
        } catch (DocumentException | IOException e) {
                System.out.println(e.toString());
        }
        }
}
