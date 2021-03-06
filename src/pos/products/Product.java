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
import pos.DbManager;
import pos.ImageRenderer;

/**
 *
 * @author sander
 */
public class Product {

    private int id;
    private String name;
    private String description;
    private int price;
    private Image image;
    private int stock;
    private Category category;
    private Supplier supplier;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public static List<Product> findByCategory(Category category, DbManager dbManager) {
        List<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Product WHERE Category_id=" + category.getId() + " ORDER BY Name ASC";
            ResultSet result = dbManager.doQuery(sql);
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getInt("Id"));
                product.setName(result.getString("Name"));
                product.setCategory(category);
                product.setDescription(result.getString("Description"));
                product.setSupplier(Supplier.findById(result.getInt("Supplier_Id"), dbManager));
                product.setPrice(result.getInt("Price"));
                product.setStock(result.getInt("Stock"));
                product.setImage(ImageIO.read(result.getBinaryStream("Image")));
                products.add(product);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public static List<Product> findAll(DbManager dbManager) {
        List<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Product ORDER BY Name ASC";
            ResultSet result = dbManager.doQuery(sql);
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getInt("Id"));
                product.setName(result.getString("Name"));
                product.setCategory(Category.findById(result.getInt("Category_Id"), dbManager));
                product.setSupplier(Supplier.findById(result.getInt("Supplier_Id"), dbManager));                
                product.setDescription(result.getString("Description"));
                product.setPrice(result.getInt("Price"));
                product.setStock(result.getInt("Stock"));
                product.setImage(ImageIO.read(result.getBinaryStream("Image")));
                products.add(product);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public static Product find(DbManager dbManager, Object id) {
        Product product = null;
        try {
            String sql = "SELECT * FROM Product WHERE id = '" + id + "' LIMIT 1";
            ResultSet result = dbManager.doQuery(sql);
            while (result.next()) {
                product = new Product();
                product.setId(result.getInt("Id"));
                product.setName(result.getString("Name"));
                product.setCategory(Category.findById(result.getInt("Category_Id"), dbManager));
                product.setSupplier(Supplier.findById(result.getInt("Supplier_Id"), dbManager));
                product.setDescription(result.getString("Description"));
                product.setPrice(result.getInt("Price"));
                product.setStock(result.getInt("Stock"));
                product.setImage(ImageIO.read(result.getBinaryStream("Image")));
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public static boolean remove(DbManager dbManager, Object id) {
        boolean result = false;
        try {
            String sql = "DELETE FROM Product WHERE id = '" + id + "'";
            result = dbManager.removeQuery(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (result) {
            System.out.println("Remove gelukt!");
        } else {
            System.err.println("Remove niet gelukt.");
        }
        return result;
    }

    public static void save(Product product, DbManager dbManager) {
        try {
            PreparedStatement stmt = dbManager.connection.prepareStatement("INSERT INTO Product"
                    + "(Name, Description, Price, Image, Stock, Category_Id, Supplier_Id) VALUES"
                    + "(?,?,?,?,?,?,?)");
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getPrice());
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) product.getImage(), "png", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            stmt.setBlob(4, fis);
            
            stmt.setInt(5, product.getStock());
            stmt.setInt(6, product.getCategory().getId());
            stmt.setInt(7, product.getSupplier().getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void update(Product product, DbManager dbManager) {
        try {
            PreparedStatement stmt = dbManager.connection.prepareStatement("UPDATE Product "
                    + "SET Name=?, Description=?, Price=?, Image=?, Stock=?, Category_Id=?, Supplier_Id=? "
                    + "WHERE id=?");
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getPrice());
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) product.getImage(), "png", os);
            InputStream fis = new ByteArrayInputStream(os.toByteArray());
            stmt.setBlob(4, fis);
            
            stmt.setInt(5, product.getStock());
            stmt.setInt(6, product.getCategory().getId());
            stmt.setInt(7, product.getSupplier().getId());
            stmt.setInt(8,product.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
