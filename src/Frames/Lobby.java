/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import Conexion.CeldasTabla;
import Conexion.EncabezadoTabla;
import Conexion.MotrarImagen;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author omargsssss
 */
public class Lobby extends javax.swing.JFrame {

    /**
     * Creates new form Consumidor
     */
    Connection con;
    ResultSet rs;
    Statement st;
    CallableStatement call;
    String id, d;
    EncabezadoTabla modelo = new EncabezadoTabla();
    CeldasTabla cell = new CeldasTabla();

    public void Queja() {
        Queja.setDefaultRenderer(Object.class, cell);
        
        JTableHeader head = Queja.getTableHeader();
        head.setDefaultRenderer(modelo);
        Queja.setTableHeader(head);
        jScrollPane3.setViewportView(Queja);
        try {
            call = con.prepareCall("{call ver_queja (?)}");
            call.setString(1, id);
            call.execute();
            ResultSet rs = call.getResultSet();
            DefaultTableModel tabla = new DefaultTableModel();
            tabla.addColumn("ID QUEJA");
            tabla.addColumn("ASUNTO");
            tabla.addColumn("PROVEDOR");
            tabla.addColumn("FECHA");
            if (rs!=null) {
                while (rs.next()) {
                    Object ob[] = new Object[4];
                    for (int i = 0; i < 4; i++) {
                        ob[i] = rs.getString(i + 1);
                    }
                    tabla.addRow(ob);
                }
                Queja.setModel(tabla);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Denuncia() {
        Denuncia.setDefaultRenderer(Object.class, cell);

        JTableHeader head = Denuncia.getTableHeader();
        head.setDefaultRenderer(modelo);
        Denuncia.setTableHeader(head);
        jScrollPane4.setViewportView(Denuncia);
        try {
            call = con.prepareCall("{call VER_DENUNCIA(?)}");
            call.setString(1, id);
            call.execute();
            rs = call.getResultSet();
            DefaultTableModel tabla = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tabla.addColumn("ID DENUNCIA");
            tabla.addColumn("ASUNTO");
            tabla.addColumn("PROVEDOR");
            tabla.addColumn("FECHA");
            if (rs != null) {
                while (rs.next()) {
                    Object ob[] = new Object[4];
                    for (int i = 0; i < 4; i++) {
                        ob[i] = rs.getString(i + 1);
                    }
                    tabla.addRow(ob);
                }
                Denuncia.setModel(tabla);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Conectar() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                    + "databaseName=profeco;user=sa;password=sasa;");
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Info() {
        try {
            call = con.prepareCall("{call ver_info (?,?)}");
            call.setString(1, id);
            call.registerOutParameter(2, Types.VARCHAR);
            call.execute();
            Nombre.setText(call.getString(2));
        } catch (SQLException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Lobby() {
        initComponents();
        this.setSize(910, 416);
        this.setLocationRelativeTo(null);
        Conectar();
        Info();
    }

    public Lobby(String id) {

        initComponents();
        this.setSize(910, 416);
        this.setLocationRelativeTo(null);
        this.id = id;
        Conectar();
        Info();
        Denuncia();
        Queja();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Nombre = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        canvas1 = new java.awt.Canvas();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Queja = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        Denuncia = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        VerQueja = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(930, 560));
        getContentPane().setLayout(null);

        Nombre.setBackground(new java.awt.Color(255, 255, 255));
        Nombre.setFont(new java.awt.Font("Century Schoolbook", 1, 28)); // NOI18N
        Nombre.setForeground(new java.awt.Color(255, 255, 255));
        Nombre.setAutoscrolls(true);
        getContentPane().add(Nombre);
        Nombre.setBounds(10, 80, 540, 30);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("CONSUMIDOR");
        jLabel4.setAutoscrolls(true);
        getContentPane().add(jLabel4);
        jLabel4.setBounds(360, 0, 190, 30);

        jButton3.setBackground(new java.awt.Color(0, 102, 102));
        jButton3.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        jButton3.setText("Nueva");
        jButton3.setAutoscrolls(true);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(790, 270, 90, 33);
        getContentPane().add(canvas1);
        canvas1.setBounds(30, 260, 0, 10);

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Bienvenido:");
        jLabel16.setAutoscrolls(true);
        getContentPane().add(jLabel16);
        jLabel16.setBounds(10, 30, 160, 30);

        Queja.setBackground(new java.awt.Color(102, 102, 102));
        Queja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID QUEJA", "ASUNTO", "PROVEDOR", "FECHA"
            }
        ));
        jScrollPane3.setViewportView(Queja);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(20, 260, 760, 90);

        jScrollPane4.setBackground(new java.awt.Color(0, 51, 51));
        jScrollPane4.setForeground(new java.awt.Color(102, 102, 102));

        Denuncia.setBackground(new java.awt.Color(51, 51, 51));
        Denuncia.setForeground(new java.awt.Color(255, 255, 255));
        Denuncia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID DENUNCIA", "ASUNTO", "PROVEDOR", "FECHA"
            }
        ));
        Denuncia.setGridColor(new java.awt.Color(0, 0, 0));
        Denuncia.setSelectionBackground(new java.awt.Color(0, 102, 102));
        jScrollPane4.setViewportView(Denuncia);

        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(20, 160, 760, 90);

        jButton4.setBackground(new java.awt.Color(0, 102, 102));
        jButton4.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        jButton4.setText("Cerrar");
        jButton4.setAutoscrolls(true);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(811, 6, 79, 33);

        jButton5.setBackground(new java.awt.Color(0, 102, 102));
        jButton5.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        jButton5.setText("Ver");
        jButton5.setAutoscrolls(true);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(810, 200, 56, 33);

        jButton6.setBackground(new java.awt.Color(0, 102, 102));
        jButton6.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        jButton6.setText("Nueva");
        jButton6.setAutoscrolls(true);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6);
        jButton6.setBounds(790, 160, 90, 33);

        VerQueja.setBackground(new java.awt.Color(0, 102, 102));
        VerQueja.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        VerQueja.setText("Ver");
        VerQueja.setAutoscrolls(true);
        VerQueja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerQuejaActionPerformed(evt);
            }
        });
        getContentPane().add(VerQueja);
        VerQueja.setBounds(810, 310, 56, 33);

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Fondo.png"))); // NOI18N
        Fondo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 51), 3, true));
        getContentPane().add(Fondo);
        Fondo.setBounds(0, 0, 910, 416);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new Queja(id).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int fila;
        if(Denuncia.getSelectedRow()>=0){
        fila = Denuncia.getSelectedRow();
        d = Denuncia.getValueAt(fila,0).toString();
        System.out.println(d);
        new Denuncia(id, d).setVisible(true);
        this.dispose();
        }else{
            JOptionPane.showMessageDialog(null, "No has selecccionado ningun registro de la tabla","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new Login().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        new Denuncia(id).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void VerQuejaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerQuejaActionPerformed
        // TODO add your handling code here:
        int fila;
        if(Queja.getSelectedRow()>=0){
        fila = Queja.getSelectedRow();
        d = Queja.getValueAt(fila,0).toString();
        System.out.println(d);
        new Queja(id,d).setVisible(true);
        this.dispose();
        }else{
        JOptionPane.showMessageDialog(null, "No has selecccionado ningun registro de la tabla","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_VerQuejaActionPerformed

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
            java.util.logging.Logger.getLogger(Lobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lobby().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Denuncia;
    private javax.swing.JLabel Fondo;
    private javax.swing.JLabel Nombre;
    private javax.swing.JTable Queja;
    private javax.swing.JButton VerQueja;
    private java.awt.Canvas canvas1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
