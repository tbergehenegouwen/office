/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos.products;

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
public class Supplier {

    private int id;
    private String name;

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
    
    public static List<Supplier> findAll(DbManager dbManager) {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Supplier ORDER BY Name ASC";
            ResultSet result = dbManager.doQuery(sql);
            while (result.next()) {
                Supplier supplier = new Supplier();
                supplier.setId(result.getInt("Id"));
                supplier.setName(result.getString("Name"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return suppliers;
    }
    
    public static Supplier findById(int supplierId, DbManager dbManager) {
        Supplier supplier = new Supplier();
        try {
            String sql = "SELECT * FROM Supplier WHERE Id = " + supplierId + " ORDER BY Name ASC";
            ResultSet result = dbManager.doQuery(sql);
            if (result.next()) {
                supplier.setId(result.getInt("Id"));
                supplier.setName(result.getString("Name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return supplier;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public static void save(Supplier supplier, DbManager dbManager) {
        try {
            PreparedStatement stmt = dbManager.connection
                    .prepareStatement("INSERT INTO Supplier(Name) VALUES(?)");
            stmt.setString(1, supplier.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static boolean remove(DbManager dbManager, Object id) {
        Product product = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM Category WHERE id = '" + id + "'";
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
}