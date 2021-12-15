/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package dao;

import model.BaseCurrency;
import model.Stock;

import java.util.LinkedList;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockDao implements BaseDao<Stock, String>{
    private static StockDao instance = null;
    private static String tableName = "STOCK";

    public static StockDao getInstance() {
        if (instance == null) {
            instance = new StockDao();
        }

        return instance;
    }

    private StockDao() {
        try {
            if (!ConnectionManager.getInstance().tableExists(tableName)) {
                createTable();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean save(Stock stock) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "INSERT INTO " + tableName + " (NAME, PRICE) VALUES ('"
                + stock.getName() + "', '"
                + stock.getPrice().serialize() + "');";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Stock getById(String id) {
        try {
            Stock stock = null;

            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
                
            ResultSet rs = statement.executeQuery("SELECT * FROM " 
                + tableName
                + " WHERE NAME='" 
                + id + "'");
            
            if (rs.next()) {
                stock = parseResultSet(rs);
            }

            rs.close();
            statement.close();
            return stock;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Stock> getAll() {
        try {
            LinkedList<Stock> accList = new LinkedList<Stock>();

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
    public boolean update(Stock stock) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "UPDATE " + tableName + " set "
                + "PRICE='" + stock.getPrice().serialize() + "' " 
                + "WHERE NAME='" + stock.getName() + "'";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Stock stock) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            statement.executeUpdate("DELETE FROM " 
                + tableName
                + " WHERE NAME='" 
                + stock.getName() + "'");

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
                "(NAME TEXT PRIMARY KEY NOT NULL," +
                " ENABLED BOOLEAN," + 
                " PRICE TEXT)";
        
        statement.executeUpdate(sql);
        statement.close();

        System.out.println("TABLE [" + tableName + "] INIT");
    }

    private Stock parseResultSet(ResultSet rs) throws SQLException {
        String name = rs.getString("NAME");
        BaseCurrency price = BaseCurrency.deserialize(rs.getString("PRICE"));
        boolean enabled = rs.getBoolean("ENABLED");
        
        return new Stock(name, price, enabled);
    }
}
