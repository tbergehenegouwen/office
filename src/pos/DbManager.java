package pos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DbManager {
    public Connection connection;

    public void openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/pos";
            String user = "root", pass = "password";

            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean removeQuery(String query) {
        boolean result = false;
        try {
            Statement statement = connection.prepareStatement(query);
            statement.execute(query);
            result = true;
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null,"Kan de Category of Leverancier niet verwijderen omdat ze een verbinding met een product hebben. \n"+
                    "Verwijder eerst alle producten die verbonden zijn met de Category of Leverancier", "Verwijder Error",JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    public ResultSet doQuery(String query) {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println(e);
        }
        return result;
    }

    public ResultSet insertQuery(String query) {
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            result = statement.getGeneratedKeys();
        } catch (SQLException e) {
            System.err.println(e);
        }
        return result;
    }
}
