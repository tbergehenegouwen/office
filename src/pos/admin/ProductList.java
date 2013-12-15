/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos.admin;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pos.Home;
import pos.ImageRenderer;
import pos.MainWindow;
import pos.products.Product;

/**
 *
 * @author sander
 */
public class ProductList extends javax.swing.JPanel {

    private final MainWindow mainWindow;

    /**
     * Creates new form ProductList
     * @param mainWindow
     */
    public ProductList(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        initComponents();
        addProducts();
    }

    private void addProducts() {
        List<Product> products = Product.findAll(mainWindow.getDbManager());
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        for (Product product : products) {
            model.addRow(new Object[]{product.getId(), product.getName(), product.getDescription(),
                java.text.NumberFormat.getCurrencyInstance(java.util.Locale.GERMANY).format(product.getPrice() / 100.0),
                product.getStock(), product.getCategory().getName(), product.getSupplier().getName(), product.getImageIcon()});
        }
    }
    
    public ImageRenderer getImageRenderer(){
        return new ImageRenderer();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        categoriesBtn = new javax.swing.JButton();
        removeProductBtn = new javax.swing.JButton();
        editProductBtn = new javax.swing.JButton();
        addProductBtn = new javax.swing.JButton();
        suppliersButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Naam", "Omschrijving", "Prijs", "Voorraad", "Categorie", "Leverancier", "Afbeelding"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(productTable);
        if (productTable.getColumnModel().getColumnCount() > 0) {
            productTable.getColumnModel().getColumn(0).setMinWidth(0);
            productTable.getColumnModel().getColumn(0).setPreferredWidth(0);
            productTable.getColumnModel().getColumn(0).setMaxWidth(0);
            productTable.getColumnModel().getColumn(7).setCellRenderer(getImageRenderer());
        }

        categoriesBtn.setText("Categoriën");
        categoriesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoriesBtnActionPerformed(evt);
            }
        });

        removeProductBtn.setText("Verwijder Product ...");
        removeProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeProductBtnActionPerformed(evt);
            }
        });

        editProductBtn.setText("Bewerk Product");
        editProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductBtnActionPerformed(evt);
            }
        });

        addProductBtn.setText("Voeg Product Toe");
        addProductBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductBtnActionPerformed(evt);
            }
        });

        suppliersButton.setText("Leveranciers");
        suppliersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suppliersButtonActionPerformed(evt);
            }
        });

        backButton.setText("Terug");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addComponent(addProductBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editProductBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeProductBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(categoriesBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(suppliersButton))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(categoriesBtn)
                    .addComponent(removeProductBtn)
                    .addComponent(editProductBtn)
                    .addComponent(addProductBtn)
                    .addComponent(suppliersButton)
                    .addComponent(backButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductBtnActionPerformed
        mainWindow.showPanel(new AddProduct(mainWindow));
    }//GEN-LAST:event_addProductBtnActionPerformed

    private void removeProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeProductBtnActionPerformed
        int[] rows = productTable.getSelectedRows();
        boolean[] rowsToRemoveFromList = new boolean[rows.length];
        for (int rowNum = 0; rowNum < rows.length; rowNum++) {
            Object toRemoveId = productTable.getValueAt(rowNum, 0);
            rowsToRemoveFromList[rowNum] = Product.remove(mainWindow.getDbManager(), toRemoveId);
        }
        //remove listed as true
        for (int i = rowsToRemoveFromList.length - 1; i >= 0; i--) {
            if (rowsToRemoveFromList[i]) {
                ((DefaultTableModel) productTable.getModel()).removeRow(rows[i]);
            }
        }
    }//GEN-LAST:event_removeProductBtnActionPerformed

    private void suppliersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suppliersButtonActionPerformed
        //mainWindow.showPanel(new AddSupplier(mainWindow));
        mainWindow.showPanel(new SupplierList(mainWindow));
    }//GEN-LAST:event_suppliersButtonActionPerformed

    private void categoriesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoriesBtnActionPerformed
        //mainWindow.showPanel(new AddCategory(mainWindow));
        mainWindow.showPanel(new CategoryList(mainWindow));
    }//GEN-LAST:event_categoriesBtnActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        mainWindow.showPanel(new Home(mainWindow));
    }//GEN-LAST:event_backButtonActionPerformed

    private void editProductBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductBtnActionPerformed
        int[] rows = productTable.getSelectedRows();
        if(rows.length == 1){
           int id = (int)productTable.getValueAt(rows[0], 0);
           mainWindow.showPanel(new AddProduct(mainWindow, id)); 
        }else if(rows.length < 1){
            JOptionPane.showMessageDialog(null, "Selecteer een product om te bewerken.");
        }else if(rows.length > 1){
            JOptionPane.showMessageDialog(null, "Er kan maximaal 1 product tegelijk bewerkt worden.");
        }
    }//GEN-LAST:event_editProductBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProductBtn;
    private javax.swing.JButton backButton;
    private javax.swing.JButton categoriesBtn;
    private javax.swing.JButton editProductBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable productTable;
    private javax.swing.JButton removeProductBtn;
    private javax.swing.JButton suppliersButton;
    // End of variables declaration//GEN-END:variables
}
