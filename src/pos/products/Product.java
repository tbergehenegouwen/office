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
import pos.DbManager;

/**
 *
 * @author ralph
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private int price;
    private Image image;
    private int stock;
    private Category category;

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
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
            String sql = "SELECT * FROM Product WHERE " + category.getId() + " ORDER BY Name ASC";
            ResultSet result = dbManager.doQuery(sql);
            while (result.next()) {
                Product product = new Product();
                product.setId(result.getInt("Id"));
                product.setName(result.getString("Name"));
                product.setCategory(category);
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
            stmt.setInt(7, 1);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
