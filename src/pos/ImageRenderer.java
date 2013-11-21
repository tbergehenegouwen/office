/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author sander
 */
public class ImageRenderer extends DefaultTableCellRenderer {
     @Override
     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         JLabel image = new JLabel("");
         ImageIcon imageIcon = (ImageIcon)value;
         image.setIcon(imageIcon);
         image.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
         table.setRowHeight(row,imageIcon.getIconHeight());
         return image;
     }
}
