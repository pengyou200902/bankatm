/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:14
 */

package dao;

import model.User;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDao implements BaseDao<User, String> {
    private static UserDao instance = null;
    private static String tableName = "USERINFO";

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }

        return instance;
    }

    private UserDao() {
        try {
            if (!ConnectionManager.getInstance().tableExists(tableName)) {
                createTable();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean save(User user) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "INSERT INTO " + tableName + " (USERNAME, NAME, ADDRESS, BIRTHDAY) VALUES ('"
                + user.getUsername() + "', '" 
                + user.getName() + "', '" 
                + user.getAddress() + "', '" 
                + user.getBirthday() + "');";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getById(String id) {
        try {
            User account = null;

            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
                
            ResultSet rs = statement.executeQuery("SELECT * FROM " 
                + tableName
                + " WHERE USERNAME='" 
                + id + "'");
            
            if (rs.next()) {
                account = parseResultSet(rs);
            }

            rs.close();
            statement.close();
            return account;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        try {
            LinkedList<User> accList = new LinkedList<User>();

            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
            
            while (rs.next()) {
                accList.add(parseResultSet(rs));
            }

            rs.close();
            statement.close();
            return accList;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(User user) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "UPDATE " + tableName + " set "
                + "NAME='" + user.getName()+ "', " 
                + "ADDRESS='" + user.getAddress() + "', " 
                + "BIRTHDAY='" + user.getBirthday() + "' "
                + "WHERE USERNAME='" + user.getUsername() + "'";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            statement.executeUpdate("DELETE FROM " 
                + tableName
                + " WHERE USERNAME='" 
                + user.getUsername() + "'");

            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
    }

    @Override
    public void createTable() throws SQLException {
        Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

        String sql = "CREATE TABLE " + tableName + " " +
                "(USERNAME TEXT PRIMARY KEY NOT NULL," +
                " NAME TEXT, " +
                " ADDRESS TEXT, " +
                " BIRTHDAY TEXT)";
        
        statement.executeUpdate(sql);
        statement.close();

        System.out.println("TABLE [" + tableName + "] INIT");
    }

    private User parseResultSet(ResultSet rs) throws SQLException {
        String username = rs.getString("USERNAME"),
            name = rs.getString("NAME"),
            address = rs.getString("ADDRESS"),
            birthday = rs.getString("BIRTHDAY");
        
        return new User(name, address, birthday, username);
    }
}
