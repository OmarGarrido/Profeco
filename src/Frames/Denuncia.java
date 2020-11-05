/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author omarg
 */
public class Denuncia extends javax.swing.JFrame {

    Connection con;
    ResultSet rs;
    Statement st;
    CallableStatement call;
    FileInputStream fis=null;
    InputStream is;
    int tam, i = 0;
    String id, d;

    public void Conectar() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                    + "databaseName=profeco;user=sa;password=sasa;");
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates new form Consumidor
     */
    public Denuncia() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public Denuncia(String id) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.id = id;
        Conectar();
        Modificar.setVisible(false);
        Eliminar.setVisible(false);
    }

    public Denuncia(String id, String d) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.id = id;
        this.d = d;
        Conectar();
        Vista();
    }

    public void Editar(boolean a){
    txtProvedor.setEditable(a);
        txtAsunto.setEditable(a);
        txtTel.setEditable(a);
        txtNarr.setEditable(a);
        txtDir.setEditable(a);
        txtCol.setEditable(a);
        txtMun.setEditable(a);
        txtCd.setEditable(a);
    }
    
    public void Vista() {
        i = 1;
        Guardar.setVisible(false);
        Editar(false);
        
        ImageIcon img;
        try {
            try {
                call = con.prepareCall("{call CON_DENUNCIA (?)}");
                call.setString(1, d);
                call.execute();
                rs = call.getResultSet();
                while (rs.next()) {
                    txtProvedor.setText(rs.getString(1));
                    txtTel.setText(rs.getString(2));
                    txtAsunto.setText(rs.getString(3));
                    txtNarr.setText(rs.getString(4));

                    is = rs.getBinaryStream(5);
                    if (is.available() != 0) {
                        BufferedImage bi = ImageIO.read(is);
                        img = new ImageIcon(bi);
                        Image im = img.getImage();
                        Image fimg = im.getScaledInstance(Foto.getWidth(), Foto.getHeight(), java.awt.Image.SCALE_SMOOTH);
                        ImageIcon evidencia = new ImageIcon(fimg);
                        Foto.setIcon(evidencia);
                    } else {
                        JOptionPane.showMessageDialog(null, "no hay ni merga");
                    }
                    txtDir.setText(rs.getString(6));
                    txtCol.setText(rs.getString(7));
                    txtMun.setText(rs.getString(8));
                    txtCd.setText(rs.getString(9));
                }
            } catch (IOException ex) {
                Logger.getLogger(Denuncia.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Denuncia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Guardar() {
        try {
            if (txtProvedor.getText().isEmpty() || txtAsunto.getText().isEmpty() || txtTel.getText().isEmpty()
                    || txtNarr.getText().isEmpty() || txtDir.getText().isEmpty() || txtCol.getText().isEmpty()
                    || txtMun.getText().isEmpty() || txtCd.getText().isEmpty()|| fis==null) {
                JOptionPane.showMessageDialog(null, "Campos vacios\nVerificalos", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                call = con.prepareCall("{call add_den (?,?,?,?,?,?,?,?,?,?,?)}");
                call.setString(1, txtAsunto.getText());
                call.setBinaryStream(2, fis, tam);
                call.setString(3, txtNarr.getText());
                call.setString(4, txtProvedor.getText());
                call.setString(5, txtTel.getText());
                call.setString(6, "");
                call.setString(7, txtDir.getText());
                call.setString(8, txtCol.getText());
                call.setString(9, txtMun.getText());
                call.setString(10, txtCd.getText());
                call.setString(11, id);
                call.execute();
                JOptionPane.showMessageDialog(null, "Se ha guardado Correctamente");
                Limpiar();
                new Lobby(id).setVisible(true);
                dispose();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Denuncia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Modificar() {
        try {
            if (txtProvedor.getText().isEmpty() || txtAsunto.getText().isEmpty() || txtTel.getText().isEmpty()
                    || txtNarr.getText().isEmpty() || txtDir.getText().isEmpty() || txtCol.getText().isEmpty()
                    || txtMun.getText().isEmpty() || txtCd.getText().isEmpty()|| Foto.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Campos vacios\nVerificalos", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                call = con.prepareCall("{call upd_den (?,?,?,?,?,?,?,?,?,?,?)}");
                call.setString(1, txtAsunto.getText());
                call.setBinaryStream(2, fis, tam);
                call.setString(3, txtNarr.getText());
                call.setString(4, txtProvedor.getText());
                call.setString(5, txtTel.getText());
                call.setString(6, "");
                call.setString(7, txtDir.getText());
                call.setString(8, txtCol.getText());
                call.setString(9, txtMun.getText());
                call.setString(10, txtCd.getText());
                call.setString(11, d);
                call.execute();
                JOptionPane.showMessageDialog(null, "Se ha actualizado Correctamente");
                new Lobby(id).setVisible(true);
                dispose();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Denuncia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Limpiar() {
        txtAsunto.setText("");
        txtProvedor.setText("");
        txtNarr.setText("");
        txtTel.setText("");
        txtDir.setText("");
        txtCol.setText("");
        txtMun.setText("");
        txtCd.setText("");
        Foto.setIcon(null);
        Foto.setText("");
    }
    
    public void Eliminar(){
        try {
            call=con.prepareCall("{call del_den (?)}");
            call.setString(1, d);
            call.execute();
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente la queja");
        } catch (SQLException ex) {
            Logger.getLogger(Queja.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMun = new javax.swing.JTextField();
        txtProvedor = new javax.swing.JTextField();
        txtTel = new javax.swing.JTextField();
        txtDir = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Guardar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNarr = new javax.swing.JTextArea();
        Foto = new javax.swing.JLabel();
        txtAsunto = new javax.swing.JTextField();
        txtCd = new javax.swing.JTextField();
        txtCol = new javax.swing.JTextField();
        Modificar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(930, 560));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Asunto");
        jLabel1.setAutoscrolls(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 101, -1, -1));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Denuncia");
        jLabel4.setAutoscrolls(true);
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, -1, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Direccion");
        jLabel5.setAutoscrolls(true);
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 101, -1, -1));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Provedor");
        jLabel8.setAutoscrolls(true);
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 41, -1, -1));

        txtMun.setBackground(new java.awt.Color(39, 40, 50));
        txtMun.setFont(new java.awt.Font("Century Schoolbook", 0, 18)); // NOI18N
        txtMun.setForeground(new java.awt.Color(255, 255, 255));
        txtMun.setFocusTraversalPolicyProvider(true);
        txtMun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMunActionPerformed(evt);
            }
        });
        getContentPane().add(txtMun, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 240, -1));

        txtProvedor.setBackground(new java.awt.Color(39, 40, 50));
        txtProvedor.setFont(new java.awt.Font("Century Schoolbook", 0, 18)); // NOI18N
        txtProvedor.setForeground(new java.awt.Color(255, 255, 255));
        txtProvedor.setFocusTraversalPolicyProvider(true);
        txtProvedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProvedorActionPerformed(evt);
            }
        });
        getContentPane().add(txtProvedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 41, 324, -1));

        txtTel.setBackground(new java.awt.Color(39, 40, 50));
        txtTel.setFont(new java.awt.Font("Century Schoolbook", 0, 18)); // NOI18N
        txtTel.setForeground(new java.awt.Color(255, 255, 255));
        txtTel.setFocusTraversalPolicyProvider(true);
        txtTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelActionPerformed(evt);
            }
        });
        getContentPane().add(txtTel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 40, 150, -1));

        txtDir.setBackground(new java.awt.Color(39, 40, 50));
        txtDir.setFont(new java.awt.Font("Century Schoolbook", 0, 18)); // NOI18N
        txtDir.setForeground(new java.awt.Color(255, 255, 255));
        txtDir.setFocusTraversalPolicyProvider(true);
        txtDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDirActionPerformed(evt);
            }
        });
        getContentPane().add(txtDir, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 97, 324, -1));

        jLabel6.setBackground(new java.awt.Color(39, 40, 50));
        jLabel6.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Colonia");
        jLabel6.setAutoscrolls(true);
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, -1, -1));

        jLabel9.setBackground(new java.awt.Color(39, 40, 50));
        jLabel9.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Municipio");
        jLabel9.setAutoscrolls(true);
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, -1, -1));

        jLabel10.setBackground(new java.awt.Color(39, 40, 50));
        jLabel10.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Ciudad");
        jLabel10.setAutoscrolls(true);
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 140, -1, -1));

        Guardar.setBackground(new java.awt.Color(0, 102, 102));
        Guardar.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        Guardar.setText("Guardar");
        Guardar.setAutoscrolls(true);
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
            }
        });
        getContentPane().add(Guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(745, 331, -1, -1));

        jButton3.setBackground(new java.awt.Color(0, 102, 102));
        jButton3.setFont(new java.awt.Font("Century Schoolbook", 1, 14)); // NOI18N
        jButton3.setText("Cerrar");
        jButton3.setAutoscrolls(true);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, -1, -1));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Telefono");
        jLabel11.setAutoscrolls(true);
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, -1, -1));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Narracion de prolematica");
        jLabel2.setAutoscrolls(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        txtNarr.setBackground(new java.awt.Color(39, 40, 50));
        txtNarr.setColumns(20);
        txtNarr.setFont(new java.awt.Font("Century Schoolbook", 0, 18)); // NOI18N
        txtNarr.setForeground(new java.awt.Color(255, 255, 255));
        txtNarr.setRows(5);
        getContentPane().add(txtNarr, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 530, -1));

        Foto.setFont(new java.awt.Font("Century Schoolbook", 1, 18)); // NOI18N
        Foto.setText("Evidencia");
        Foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(39, 40, 50), 2));
        Foto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FotoMouseClicked(evt);
            }
        });
        getContentPane().add(Foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 240, 100, 125));

        txtAsunto.setBackground(new java.awt.Color(39, 40, 50));
        txtAsunto.setFont(new java.awt.Font("Century Schoolbook", 0, 18)); // NOI18N
        txtAsunto.setForeground(new java.awt.Color(255, 255, 255));
        txtAsunto.setFocusTraversalPolicyProvider(true);
        txtAsunto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAsuntoActionPerformed(evt);
            }
        });
        getContentPane().add(txtAsunto, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 97, 150, -1));

        txtCd.setBackground(new java.awt.Color(39, 40, 50));
        txtCd.setFont(new java.awt.Font("Century Schoolbook", 0, 18)); // NOI18N
        txtCd.setForeground(new java.awt.Color(255, 255, 255));
        txtCd.setFocusTraversalPolicyProvider(true);
        txtCd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCdActionPerformed(evt);
            }
        });
        getContentPane().add(txtCd, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 170, 210, -1));

        txtCol.setBackground(new java.awt.Color(39, 40, 50));
        txtCol.setFont(new java.awt.Font("Century Schoolbook", 0, 18)); // NOI18N
        txtCol.setForeground(new java.awt.Color(255, 255, 255));
        txtCol.setFocusTraversalPolicyProvider(true);
        txtCol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtColActionPerformed(evt);
            }
        });
        getContentPane().add(txtCol, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 240, -1));

        Modificar.setBackground(new java.awt.Color(0, 102, 102));
        Modificar.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        Modificar.setText("Modificar");
        Modificar.setAutoscrolls(true);
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });
        getContentPane().add(Modificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 330, -1, -1));

        Eliminar.setBackground(new java.awt.Color(0, 102, 102));
        Eliminar.setFont(new java.awt.Font("Century Schoolbook", 1, 24)); // NOI18N
        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });
        getContentPane().add(Eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 270, -1, -1));

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Fondo.png"))); // NOI18N
        Fondo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 51), 3, true));
        getContentPane().add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMunActionPerformed

    private void txtProvedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProvedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProvedorActionPerformed

    private void txtTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelActionPerformed

    private void txtDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDirActionPerformed

    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
        // TODO add your handling code here:
        if (i == 0) {
            Guardar();
        } else {
            Modificar();
            Editar(false);
            Guardar.setVisible(false);
            Modificar.setVisible(true);
            Eliminar.setVisible(true);
        }
    }//GEN-LAST:event_GuardarActionPerformed

    private void FotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FotoMouseClicked
        // TODO add your handling code here:
        if (Guardar.isVisible() == true) {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int estado = fc.showOpenDialog(null);
            if (estado == JFileChooser.APPROVE_OPTION) {
                try {
                    fis = new FileInputStream(fc.getSelectedFile());
                    this.tam = (int) fc.getSelectedFile().length();
                    try {
                        Image icono = ImageIO.read(fc.getSelectedFile()).getScaledInstance(Foto.getWidth(), Foto.getHeight(), Image.SCALE_DEFAULT);
                        Foto.setIcon(new ImageIcon(icono));
                        Foto.setText("a");
                        Foto.updateUI();
                    } catch (IOException ex) {
                        Logger.getLogger(Denuncia.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Denuncia.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_FotoMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new Lobby(id).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtAsuntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAsuntoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAsuntoActionPerformed

    private void txtCdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCdActionPerformed

    private void txtColActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtColActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtColActionPerformed

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        // TODO add your handling code here:
        Editar(true);
        JOptionPane.showMessageDialog(null, "Ya puedes Editar los datos");
        Modificar.setVisible(false);
        Eliminar.setVisible(false);
        Guardar.setVisible(true);
        if(Foto!=null){
        Foto.setText("");
        Foto.setIcon(null);
        }
    }//GEN-LAST:event_ModificarActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        // TODO add your handling code here:
        Eliminar();
        new Lobby(id).setVisible(true);
        dispose();
    }//GEN-LAST:event_EliminarActionPerformed

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
            java.util.logging.Logger.getLogger(Denuncia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Denuncia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Denuncia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Denuncia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //Fondotor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Denuncia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Eliminar;
    private javax.swing.JLabel Fondo;
    private javax.swing.JLabel Foto;
    private javax.swing.JButton Guardar;
    private javax.swing.JButton Modificar;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtAsunto;
    private javax.swing.JTextField txtCd;
    private javax.swing.JTextField txtCol;
    private javax.swing.JTextField txtDir;
    private javax.swing.JTextField txtMun;
    private javax.swing.JTextArea txtNarr;
    private javax.swing.JTextField txtProvedor;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
