
package Vistas;

import Modelo.clientes;
import Modelo.clientesDAO;
import Modelo.productos;
import Modelo.productosDao;
import Modelo.proveedor;
import Modelo.proveedorDAO;
import java.awt.HeadlessException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

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
    DefaultTableModel modelo = new DefaultTableModel();
    Object[] ob = new Object[6];
    
    public Sistema() {
        initComponents();
        this.setLocationRelativeTo(null);
        jButton_guar_camb.setVisible(false);

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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
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
        jLabel21 = new javax.swing.JLabel();
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
        cbx_marca_productos = new javax.swing.JComboBox<>();
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
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jButton23 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 153));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Nventa.png"))); // NOI18N
        jButton1.setText("Nueva venta ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Clientes.png"))); // NOI18N
        jButton2.setText("Clientes");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/proveedor.png"))); // NOI18N
        jButton3.setText("Proveedor");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/producto.png"))); // NOI18N
        jButton4.setText("Productos");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/compras.png"))); // NOI18N
        jButton5.setText("Ventas");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/config.png"))); // NOI18N
        jButton6.setText("Config");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/ebrey.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SINDICATO");

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
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 560));

        jLabelPuntoVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/encabezado.png"))); // NOI18N
        jLabelPuntoVenta.setText("               ");
        getContentPane().add(jLabelPuntoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 790, 160));

        jTabbedPane2.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Codigo");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Descripcion Prd");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Cantidad");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Precio");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Stock Disp");

        btn_eliminar_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N

        txtCantidadVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadVentaActionPerformed(evt);
            }
        });

        txtPrecioVenta.setEnabled(false);

        table_venta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Precio", "TOTAL"
            }
        ));
        jScrollPane2.setViewportView(table_venta);
        if (table_venta.getColumnModel().getColumnCount() > 0) {
            table_venta.getColumnModel().getColumn(0).setPreferredWidth(30);
            table_venta.getColumnModel().getColumn(1).setPreferredWidth(100);
            table_venta.getColumnModel().getColumn(2).setPreferredWidth(30);
            table_venta.getColumnModel().getColumn(3).setPreferredWidth(30);
            table_venta.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("DNI/RUC");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("NOMBRE");

        btn_imprimirgenerar_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/print.png"))); // NOI18N

        txt_dniruc_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dniruc_ventaActionPerformed(evt);
            }
        });

        txt_nombrecliente_venta.setEditable(false);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/money.png"))); // NOI18N
        jLabel20.setText("TOTAL A PAGAR:");

        label_total_venta.setBackground(new java.awt.Color(255, 255, 255));
        label_total_venta.setEnabled(false);

        txt_telefonocliente_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telefonocliente_ventaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(txtDescripcionVenta))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(txtCantidadVenta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(txtPrecioVenta))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(txtStockVenta)
                                .addGap(18, 18, 18)
                                .addComponent(txt_idproducto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(btn_eliminar_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_dniruc_venta)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_nombrecliente_venta)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_telefonocliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_direccioncliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_razoncliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_imprimirgenerar_venta)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(label_total_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDescripcionVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCodigoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtStockVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_idproducto_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_eliminar_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_dniruc_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_nombrecliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_telefonocliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_direccioncliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_razoncliente_venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btn_imprimirgenerar_venta, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_total_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 795, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 372, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("tab1", jPanel4);

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
        txt_dniruc_clientes.setBackground(new java.awt.Color(235, 235, 235));

        txt_nombre_clientes.setEditable(false);
        txt_nombre_clientes.setBackground(new java.awt.Color(235, 235, 235));

        txt_telefono_clientes.setEditable(false);
        txt_telefono_clientes.setBackground(new java.awt.Color(235, 235, 235));

        txt_direccion_clientes.setEditable(false);
        txt_direccion_clientes.setBackground(new java.awt.Color(235, 235, 235));

        txt_razonsocial_clientes.setEditable(false);
        txt_razonsocial_clientes.setBackground(new java.awt.Color(235, 235, 235));

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

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setText("Id");

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
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(check_opt_actu, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel1)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(4, 4, 4)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(check_opt_actu)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(40, Short.MAX_VALUE))
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

        jTabbedPane2.addTab("tab2", jPanel5);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("RUC:");

        txt_ruc_proveedor.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("NOMBRE:");

        txt_nombre_proveedor.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("TELEFONO:");

        txt_telefono_proveedor.setEditable(false);

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

        jLabelNuevoProveed.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelNuevoProveed.setText("Nuevo");

        jLabelGuardProveed.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelGuardProveed.setText("Guardar");

        jLabelActuProveed.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelActuProveed.setText("Actualizar");

        jLabelElimProveed.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabelElimProveed.setText("Eliminar");

        txt_texto_cambiante.setEditable(false);
        txt_texto_cambiante.setBackground(new java.awt.Color(219, 219, 219));
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
        txt_text_indicTabla.setBackground(new java.awt.Color(219, 219, 219));
        txt_text_indicTabla.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_text_indicTabla.setBorder(null);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(btn_nuevo_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelNuevoProveed))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(86, 86, 86)
                                        .addComponent(txt_ruc_proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                        .addGap(10, 10, 10)
                                        .addComponent(txt_id_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                            .addComponent(jLabelGuardProveed)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(txt_texto_cambiante)))
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
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_razonsocial_proveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                    .addComponent(txt_direccion_proveedor)
                                    .addComponent(txt_nombre_proveedor)
                                    .addComponent(txt_telefono_proveedor))
                                .addGap(0, 0, Short.MAX_VALUE)))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txt_texto_cambiante, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_text_indicTabla))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_ruc_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txt_id_proveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jTabbedPane2.addTab("tab3", jPanel6);

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Codigo:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Producto:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("Stock disp:");

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Precio:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("Marca:");

        cbx_marca_productos.setEditable(true);

        table_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "STOCK", "PRECIO", "PROVEEDOR"
            }
        ));
        jScrollPane4.setViewportView(table_productos);
        if (table_productos.getColumnModel().getColumnCount() > 0) {
            table_productos.getColumnModel().getColumn(0).setPreferredWidth(50);
            table_productos.getColumnModel().getColumn(1).setPreferredWidth(100);
            table_productos.getColumnModel().getColumn(2).setPreferredWidth(40);
            table_productos.getColumnModel().getColumn(3).setPreferredWidth(50);
            table_productos.getColumnModel().getColumn(4).setPreferredWidth(60);
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

        btn_eliminar_productos.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        btn_eliminar_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar.png"))); // NOI18N
        btn_eliminar_productos.setText("Eliminar");
        btn_eliminar_productos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_nuevo_productos.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        btn_nuevo_productos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/nuevo.png"))); // NOI18N
        btn_nuevo_productos.setText("Nuevo");
        btn_nuevo_productos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_precio_productos)
                            .addComponent(txt_Stock_productos, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_pro_nombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbx_marca_productos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txt_codigo_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_id_productos, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_guardar_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_eliminar_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(btn_excel_productos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14)))
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
                        .addGap(33, 33, 33)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txt_codigo_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_id_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(cbx_marca_productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(68, 68, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab4", jPanel7);

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
                .addContainerGap(56, Short.MAX_VALUE))
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

        jTabbedPane2.addTab("tab5", jPanel8);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("RUC");

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("NOMBRE");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("TELEFONO");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("DIRECCION");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("RAZON SOCIAL");

        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/Actualizar (2).png"))); // NOI18N
        jButton23.setText("ACTUALIZAR");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setText("DATOS DE LA EMPRESA");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(318, 318, 318)
                        .addComponent(jButton23))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel31)
                            .addComponent(jLabel28)
                            .addComponent(jTextField22, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(jTextField25))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel32)
                            .addComponent(jLabel29)
                            .addComponent(jTextField23, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                            .addComponent(jTextField26))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 301, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addGap(284, 284, 284))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel33)
                .addGap(65, 65, 65)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab6", jPanel9);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 800, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_dniruc_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dniruc_ventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dniruc_ventaActionPerformed

    private void txtCantidadVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadVentaActionPerformed

    private void btn_excel_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_excel_productosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_excel_productosActionPerformed

    private void txt_telefonocliente_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telefonocliente_ventaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telefonocliente_ventaActionPerformed

    private void txt_id_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_productosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_productosActionPerformed

    private void btn_nuevo_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevo_clientesActionPerformed
        // TODO add your handling code here:
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
                    }
                }
            }
        }else 
            JOptionPane.showMessageDialog(null, "por favor llene todos los campos para guardar (solo se puede omititr razon social de ser necesario)");
    }//GEN-LAST:event_btn_guardar_clientesActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TABLA clientes
        jTabbedPane2.setSelectedIndex(1);
        desactivarCampos(txt_dniruc_clientes,txt_nombre_clientes,txt_telefono_clientes,txt_direccion_clientes,txt_razonsocial_clientes);
        check_opt_actu.setSelected(false);
        limpiarCampos();
        limpiarTabla();
        listar_clientes();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TABLA ventas
        jTabbedPane2.setSelectedIndex(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TABLA proveedor
        jTabbedPane2.setSelectedIndex(2);
        txt_id_proveedor.setVisible(true);
        desactivarCampos(txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
        activar_vista_proveedor();
        limpiarTabla();
        listar_proveedor();
        limpiarCampos2(txt_id_proveedor,txt_ruc_proveedor,txt_nombre_proveedor,txt_telefono_proveedor,txt_direccion_proveedor,txt_razonsocial_proveedor);
        txt_text_indicTabla.setText("toque 2 veces sobre el nombre del proveedor para seleccionar");
        txt_texto_cambiante.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(3);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(4);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        jTabbedPane2.setSelectedIndex(5);
    }//GEN-LAST:event_jButton6ActionPerformed
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
        }
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
        if (!"".equals(txt_codigo_productos.getText())&&!"".equals(txt_Stock_productos.getText())&&!"".equals(txt_precio_productos.getText())&&!"".equals(txt_pro_nombre.getText())&&!"".equals(cbx_marca_productos.getSelectedItem())){
            if (proDao.existeCodigo(txt_codigo_productos.getText())) {
                JOptionPane.showMessageDialog(null, "El producto ya está registrado");
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿desea guardar?");
                if (pregunta==0){
                    pregunta = JOptionPane.showConfirmDialog(null, "¿A continuacion se guardaran los datos, desea continuar?");
                    if (pregunta==0){
                    pro.setCodigo(txt_codigo_productos.getText());
                    pro.setPro_nombre(txt_pro_nombre.getText());
                    pro.setStock(Integer.parseInt(  txt_Stock_productos.getText()));
                    pro.setPrecio(Double.parseDouble(txt_precio_productos.getText()));
                    pro.setMarca(cbx_marca_productos.getSelectedItem().toString());
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
    }//GEN-LAST:event_btn_guardar_productosActionPerformed

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
    private javax.swing.JButton btn_editar_clientes;
    private javax.swing.JButton btn_editar_productos;
    private javax.swing.JButton btn_editar_proveedor;
    private javax.swing.JButton btn_eliminar_clientes;
    private javax.swing.JButton btn_eliminar_productos;
    private javax.swing.JButton btn_eliminar_proveedor;
    private javax.swing.JButton btn_eliminar_venta;
    private javax.swing.JButton btn_excel_productos;
    private javax.swing.JButton btn_guardar_clientes;
    private javax.swing.JButton btn_guardar_productos;
    private javax.swing.JButton btn_guardar_proveedor;
    private javax.swing.JButton btn_imprimirgenerar_venta;
    private javax.swing.JButton btn_nuevo_clientes;
    private javax.swing.JButton btn_nuevo_productos;
    private javax.swing.JButton btn_nuevo_proveedor;
    private javax.swing.JButton btn_pdf_ventas;
    private javax.swing.JComboBox<String> cbx_marca_productos;
    private javax.swing.JCheckBox check_opt_actu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
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
    private javax.swing.JTextField txt_Stock_productos;
    private javax.swing.JTextField txt_codigo_productos;
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
    private javax.swing.JTextField txt_nombre_proveedor;
    private javax.swing.JTextField txt_nombrecliente_venta;
    private javax.swing.JTextField txt_precio_productos;
    private javax.swing.JTextField txt_pro_nombre;
    private javax.swing.JTextField txt_razoncliente_venta;
    private javax.swing.JTextField txt_razonsocial_clientes;
    private javax.swing.JTextField txt_razonsocial_proveedor;
    private javax.swing.JTextField txt_ruc_proveedor;
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
}
