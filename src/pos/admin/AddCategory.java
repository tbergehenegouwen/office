/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos.admin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import pos.MainWindow;
import pos.products.Category;

/**
 *
 * @author sander
 */
public class AddCategory extends javax.swing.JPanel {
    private final MainWindow mainWindow;
    private final Category category;
    private File imageFile;
    private boolean editMode = false;

    /**
     * Creates new form AddCategory
     * @param mainWindow
     */
    public AddCategory(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        category = new Category();
        initComponents();
    }
    
    public AddCategory(MainWindow mainWindow, int id){
        this.editMode = true;
        this.mainWindow = mainWindow;
        this.category = Category.findById(id, mainWindow.getDbManager());
        initComponents();
        setValues();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        cancelBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        imageField = new javax.swing.JTextField();
        browseBtn = new javax.swing.JButton();
        imageHolder = new javax.swing.JLabel();

        jLabel2.setText("Name");

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        jLabel7.setText("Image");

        browseBtn.setText("Browse ...");
        browseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(imageHolder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageField, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseBtn)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel7});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(imageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelBtn)
                    .addComponent(saveBtn)
                    .addComponent(imageHolder))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        mainWindow.showPanel(new CategoryList(mainWindow));
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        category.setName(nameField.getText());
        try {
            if(imageFile != null){
                category.setImage(ImageIO.read(imageFile));
            }
        } catch (IOException ex) {
            Logger.getLogger(AddCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!editMode){
            Category.save(category, mainWindow.getDbManager());
        }else{
            Category.update(category, mainWindow.getDbManager());
        }
        mainWindow.showPanel(new CategoryList(mainWindow));
    }//GEN-LAST:event_saveBtnActionPerformed

    private void browseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseBtnActionPerformed
        JFileChooser chooser = new JFileChooser();
        if(chooser.showDialog(mainWindow, "Kies een Foto!") == JFileChooser.APPROVE_OPTION) {
            imageFile = chooser.getSelectedFile();
            try {
                category.setImage(ImageIO.read(imageFile));
            } catch (IOException ex) {
                Logger.getLogger(AddCategory.class.getName()).log(Level.SEVERE, null, ex);
            }
            imageField.setText(imageFile.getName());
            imageHolder.setIcon(category.getImageIcon());
        }
    }//GEN-LAST:event_browseBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseBtn;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField imageField;
    private javax.swing.JLabel imageHolder;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton saveBtn;
    // End of variables declaration//GEN-END:variables

    private void setValues() {
        nameField.setText(category.getName());
        imageHolder.setIcon(category.getImageIcon());
    }
}
