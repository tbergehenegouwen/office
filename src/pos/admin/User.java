/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos.admin;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pos.DbManager;
import pos.MainWindow;

/**
 *
 * @author tychovanbergehenegouwen
 */
public class User {
    
    private int id;
    private String username;
    private String password;
    private String address;
    private String city;
    private String phone;
    private int role;
    private int office;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int office) {
        this.office = office;
    }
    
    public static void save(User user, DbManager dbManager) {
        try {
            PreparedStatement stmt = dbManager.connection.prepareStatement("INSERT INTO `pos`.`user` (`Name`, `Address`, `City`, `Phone`, `Password`, `Role_Id`, `Office_Id`)"+
                    " VALUES (?, ?, ?, ?, ?, ?, ?);");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getAddress());
            stmt.setString(3, user.getCity());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getRole());
            stmt.setInt(7, user.getOffice());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Gebruikers succesvol geregistreerd!");
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean Login(String username, String password){
        
//        DbManager dbManager = new DbManager();
//        String sql = "SELECT * FROM User WHERE name='admin' AND password ='admin'";
//        ResultSet result = dbManager.doQuery(sql);
        DbManager db = new DbManager();
        db.openConnection();
        String sql = "SELECT User.Id, User.Name as Name,Role.Name as Role,Office.Name as Office, Address,City,Phone FROM User";
        sql+= " JOIN Role ON Role.Id=Role_id";
        sql+= " JOIN Office ON Office.Id=Office_id";
        sql+= " WHERE User.name='"+username+"' AND password='"+password+"'";
       
        ResultSet result = db.doQuery(sql);
        try {
            while(result.next()){
                this.id = result.getInt("Id");
                this.username = result.getString("Name");
                this.address = result.getString("Address");
                this.city = result.getString("City");
                this.phone = result.getString("Phone");
                this.role = result.getInt("Role");
                this.office = result.getInt("Office");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(this.id != 0){
            return true;
        }else{
            return false;
        }
    }    
}
