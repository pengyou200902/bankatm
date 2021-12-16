/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package dao;

import model.BaseCurrency;
import model.Saving;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SavingDao implements BaseDao<Saving, String> {
    private static SavingDao instance = null;
    private static String tableName = "SAVING";

    public static SavingDao getInstance() {
        if (instance == null) {
            instance = new SavingDao();
        }

        return instance;
    }

    private SavingDao() {
        try {
            if (!ConnectionManager.getInstance().tableExists(tableName)) {
                createTable();
            }

            if(getByUsername("admin") == null) {
                save(new Saving("admin", "SAV00123454"));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean save(Saving model) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "INSERT INTO " + tableName + " (ACCOUNTNUMBER, USERNAME, BALANCE) VALUES ('"
                + model.getAccountNumber() + "', '" 
                + model.getUsername() + "', '" 
                + model.getBalance().serialize() + "');";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Saving getById(String id) {
        try {
            Saving account = null;

            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM " 
                + tableName
                + " WHERE ACCOUNTNUMBER='" 
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

    public Saving getByUsername(String username) {
        try {
            Saving account = null;

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
    public List<Saving> getAll() {
        try {
            LinkedList<Saving> accList = new LinkedList<Saving>();

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
    public boolean update(Saving model) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "UPDATE " + tableName + " set "
                + "USERNAME='" + model.getUsername() + "', " 
                + "BALANCE='" + model.getBalance().serialize() + "' "
                + "WHERE ACCOUNTNUMBER='" + model.getAccountNumber() + "'";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Saving model) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            statement.executeUpdate("DELETE FROM " 
                + tableName
                + " WHERE ACCOUNTNUMBER='" 
                + model.getAccountNumber() + "'");

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
                "(ACCOUNTNUMBER TEXT PRIMARY KEY NOT NULL," +
                " USERNAME TEXT NOT NULL, " +
                " BALANCE TEXT)";
        
        statement.executeUpdate(sql);
        statement.close();

        System.out.println("TABLE [" + tableName + "] INIT");
    }

    private Saving parseResultSet(ResultSet rs) throws SQLException {
        String username = rs.getString("USERNAME"),
            accountNumber = rs.getString("ACCOUNTNUMBER");
        BaseCurrency balance = BaseCurrency.deserialize(rs.getString("BALANCE"));
        
        return new Saving(username, accountNumber, balance);
    }
}
