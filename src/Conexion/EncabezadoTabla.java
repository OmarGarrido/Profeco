/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author omarg
 */
public class EncabezadoTabla implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JComponent jc =new JLabel((String) value);//creamos componente tipo label
        ((JLabel)jc).setHorizontalAlignment(SwingConstants.CENTER);//alineamos al centro los campos
        ((JLabel)jc).setSize(30, jc.getWidth());//les asignamos un tamaño (alto)
        ((JLabel)jc).setPreferredSize(new Dimension(10,jc.getWidth()));//manda el tamaño con precision(?)
        //muestra la division de campos columnas
        jc.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE));
        jc.setOpaque(true);//permite cambiar color a columnas campos
        jc.setBackground(new Color(39,40,50));//asignamos color
//mostrar titulo al poner cursor encima jc.setToolTipText("Tabla Denincia");
        jc.setForeground(Color.WHITE);
        return jc;
    }
    
}