/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:14
 */

package dao;

import model.Account;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AccountDao implements BaseDao<Account, String> {
    private static AccountDao instance = null;
    private static String tableName = "ACCOUNT";

    public static AccountDao getInstance() {
        if (instance == null) {
            instance = new AccountDao();
        }

        return instance;
    }

    private AccountDao() {
        try {
            if (!ConnectionManager.getInstance().tableExists(tableName)) {
                createTable();
            }

            if(getById("admin") == null) {
                save(new Account("admin", "admin", "MANAGER"));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean save(Account user) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "INSERT INTO " + tableName + " (USERNAME, PASSWORD, TYPE) VALUES ('"
                + user.getUsername() + "', '" 
                + user.getPassword() + "', '" 
                + user.getType() + "');";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Account getById(String username) {
        try {
            Account account = null;

            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM " 
                + tableName
                + " WHERE USERNAME='" 
                + username + "'");
            
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
    public List<Account> getAll() {
        try {
            LinkedList<Account> accList = new LinkedList<Account>();

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
    public boolean update(Account user) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "UPDATE " + tableName + " set "
                + "TYPE='" + user.getType() + "', " 
                + "PASSWORD='" + user.getPassword() + "' "
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
    public boolean delete(Account user) {
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
                " PASSWORD TEXT NOT NULL, " +
                " TYPE TEXT)";
        
        statement.executeUpdate(sql);
        statement.close();

        System.out.println("TABLE [" + tableName + "] INIT");
    }

    private Account parseResultSet(ResultSet rs) throws SQLException {
        String username = rs.getString("USERNAME"),
            password = rs.getString("PASSWORD"),
            type = rs.getString("TYPE");
        
        return new Account(username, password, type);
    }
}
