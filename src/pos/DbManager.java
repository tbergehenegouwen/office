package pos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager {
    public Connection connection;

    public void openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/pos";
            String user = "root", pass = "root";

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
