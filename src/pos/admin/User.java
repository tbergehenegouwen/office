/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pos.admin;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pos.DbManager;

/**
 *
 * @author tychovanbergehenegouwen
 */
public class User {
    
    private int id;
    private String username;
    private String address;
    private String city;
    private String phone;
    private String role;
    private String office;
    
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
                this.role = result.getString("Role");
                this.office = result.getString("Office");
                
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
