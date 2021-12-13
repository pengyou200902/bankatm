/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package dao;

import model.BaseCurrency;
import model.Checking;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CheckingDao implements BaseDao<Checking, String> {
    private static CheckingDao instance = null;
    private static String tableName = "CHECKING";

    public static CheckingDao getInstance() {
        if (instance == null) {
            instance = new CheckingDao();
        }

        return instance;
    }

    private CheckingDao() {
        try {
            if (!ConnectionManager.getInstance().tableExists(tableName)) {
                createTable();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean save(Checking model) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "INSERT INTO " + tableName + " (ACCOUNTNUMBER, USERNAME, CURRENCIES) VALUES ('"
                + model.getAccountNumber() + "', '" 
                + model.getUsername() + "', '" 
                + getCurrencyString(model.getCurrencies()) + "');";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Checking getById(String id) {
        try {
            Checking account = null;

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

    public Checking getByUsername(String username) {
        try {
            Checking account = null;

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
    public List<Checking> getAll() {
        try {
            LinkedList<Checking> accList = new LinkedList<Checking>();

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
    public boolean update(Checking model) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "UPDATE " + tableName + " set "
                + "USERNAME='" + model.getUsername() + "', " 
                + "CURRENCIES='" + getCurrencyString(model.getCurrencies()) + "' "
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
    public boolean delete(Checking model) {
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
                " CURRENCIES TEXT[])";
        
        statement.executeUpdate(sql);
        statement.close();

        System.out.println("TABLE [" + tableName + "] INIT");
    }

    private Checking parseResultSet(ResultSet rs) throws SQLException {
        LinkedList<BaseCurrency> currencies = new LinkedList<BaseCurrency>();

        String username = rs.getString("USERNAME"),
            accountNumber = rs.getString("ACCOUNTNUMBER");
        
        for (String curString: (String[]) rs.getArray("CURRENCIES").getArray()) {
            currencies.add(BaseCurrency.deserialize(curString));
        }
        
        return new Checking(username, accountNumber, currencies);
    }

    private String getCurrencyString(List<BaseCurrency> list) {
        String currenciesString = "";

        for (int i = 0; i < list.size(); i++) {
            BaseCurrency currency = list.get(i);
            currenciesString += "\"" + currency.serialize() + "\"";

            if (i != list.size() - 1) {
                currenciesString += ", ";
            }
        }

        return "{" + currenciesString + "}";
    }
}
