/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author omarg
 */
public class CeldasTabla extends DefaultTableCellRenderer {
    
    DefaultTableCellRenderer render = new DefaultTableCellRenderer();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
        if (row % 2 == 0) {
            table.setBackground(Color.DARK_GRAY);
            table.setForeground(Color.WHITE);
        } else {
            table.setBackground(Color.GRAY);
            table.setForeground(Color.WHITE);
        }
        render.setHorizontalAlignment(CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(render);
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }

}
