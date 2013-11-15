/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos.products;

import java.awt.Image;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import pos.DbManager;

/**
 *
 * @author ralph
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
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
}
