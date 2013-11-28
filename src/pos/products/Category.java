/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos.products;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import pos.DbManager;
import pos.ImageRenderer;

/**
 *
 * @author sander
 */
public class Category {

    private int id;
    private String name;
    private java.awt.Image image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public Image getImage() {
        image = (image != null)?image:ImageRenderer.getDefaultImage();
        return image;
    }

    public void setImage(Image image) {
        this.image= image != null ? image : ImageRenderer.getDefaultImage();
    }
    
    public ImageIcon getImageIcon(){
        return new ImageIcon(this.image.getScaledInstance(-1, 100, 0));
    }
    
    public static List<Category> findAll(DbManager dbManager) {
        List<Category> categories = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Category ORDER BY Name ASC";
            ResultSet result = dbManager.doQuery(sql);
            while (result.next()) {
                Category category = new Category();
                category.setId(result.getInt("Id"));
                category.setName(result.getString("Name"));
                category.setImage(ImageIO.read(result.getBinaryStream("Image")));
                categories.add(category);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }
    
    public static Category findById(int categoryId, DbManager dbManager) {
        Category category = new Category();
        try {
            String sql = "SELECT * FROM Category WHERE Id = " + categoryId + " ORDER BY Name ASC";
            ResultSet result = dbManager.doQuery(sql);
            if (result.next()) {
                category.setId(result.getInt("Id"));
                category.setName(result.getString("Name"));
                category.setImage(ImageIO.read(result.getBinaryStream("Image")));
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return category;
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    public static void save(Category category, DbManager dbManager) {
        try {
            PreparedStatement stmt = dbManager.connection.prepareStatement("INSERT INTO Category"
                    + "(Name, Image) VALUES"
                    + "(?,?)");
            stmt.setString(1, category.getName());
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) category.getImage(), "png", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            stmt.setBlob(2, fis);
            stmt.executeUpdate();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void update(Category category, DbManager dbManager) {
        try {
            PreparedStatement stmt = dbManager.connection.prepareStatement("UPDATE Category "
                    + "SET Name=?, Image=? "
                    + "WHERE id=?");
            stmt.setString(1, category.getName());

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) category.getImage(), "png", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            stmt.setBlob(2, fis);
            stmt.setInt(3,category.getId());
            stmt.executeUpdate();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static boolean remove(DbManager dbManager, Object id) {
        boolean result = false;
        try {
            String sql = "DELETE FROM Category WHERE id = '" + id + "'";
            result = dbManager.removeQuery(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("MySQLIntegrityConstraintViolationException"))
                JOptionPane.showMessageDialog(null, "Er bestaan nog 1 of meerdere producten die deze categorie in gebruik hebben. Verwijder deze eerst.");
        }
        if (result) {
            System.out.println("Remove gelukt!");
        } else {
            System.err.println("Remove niet gelukt.");
        }
        return result;
    }
}
