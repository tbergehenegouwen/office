/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
         if(value.getClass() == ImageIcon.class){
            ImageIcon imageIcon = (ImageIcon)value;
            image.setIcon(imageIcon);
            image.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
            table.setRowHeight(row,imageIcon.getIconHeight());
         }else{
             System.err.println("No ImageIcon found");
             System.out.println(value.getClass());
         }
         return image;
     }
     
     public static BufferedImage getDefaultImage(){
         Image defaultImage = new javax.swing.ImageIcon(Object.class.getResource("/pos/resources/default_image.png")).getImage();
            // construct the buffered image
            BufferedImage bImage      = new BufferedImage(defaultImage.getWidth(null), defaultImage.getHeight(null), BufferedImage.TYPE_INT_RGB);

            //obtain it's graphics
            Graphics2D bImageGraphics = bImage.createGraphics();

            //draw the Image (image) into the BufferedImage (bImage)
            bImageGraphics.drawImage(defaultImage, null, null);
            return bImage;
     }
}
