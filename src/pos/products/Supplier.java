/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos.products;

import java.io.ByteArrayOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pos.DbManager;

/**
 *
 * @author sander
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
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Supplier other = (Supplier) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        return hash;
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
    
    public static void update(Supplier supplier, DbManager dbManager) {
        try {
            PreparedStatement stmt = dbManager.connection.prepareStatement("UPDATE Supplier "
                    + "SET Name=?"
                    + "WHERE id=?");
            stmt.setString(1, supplier.getName());

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            stmt.setInt(2,supplier.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static boolean remove(DbManager dbManager, Object id) {
        boolean result = false;
        try {
            String sql = "DELETE FROM Supplier WHERE id = '" + id + "'";
            result = dbManager.removeQuery(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(e.getMessage().contains("MySQLIntegrityConstraintViolationException"))
                JOptionPane.showMessageDialog(null, "Er bestaan nog 1 of meerdere producten die deze supplier in gebruik hebben. Verwijder deze eerst.");
        }
        if (result) {
            System.out.println("Remove gelukt!");
        } else {
            System.err.println("Remove niet gelukt.");
        }
        return result;
    }
}
