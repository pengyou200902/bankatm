/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package dao;


import model.BaseCurrency;
import model.Security;
import model.Stock;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SecurityDao implements BaseDao<Security, String> {
    private static SecurityDao instance = null;
    private static String tableName = "SECURITY";

    public static SecurityDao getInstance() {
        if (instance == null) {
            instance = new SecurityDao();
        }

        return instance;
    }

    private SecurityDao() {
        try {
            if (!ConnectionManager.getInstance().tableExists(tableName)) {
                createTable();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean save(Security model) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "INSERT INTO " + tableName + " (ACCOUNTNUMBER, USERNAME, BALANCE, REALIZEDPROFIT, UNREALIZEDPROFIT, OWNED, OPEN) VALUES ('"
                + model.getAccountNumber() + "', '" 
                + model.getUsername() + "', '" 
                + model.getBalance().serialize() + "', '"
                + model.getRealizedProfit().serialize() + "', '"
                + model.getUnrealizedProfit().serialize() + "', '"
                + getStockString(model.getOwned()) + "', '"
                + getStockString(model.getOpen()) + "');";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Security getById(String id) {
        try {
            Security account = null;

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

    public Security getByUsername(String username) {
        try {
            Security account = null;

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
    public List<Security> getAll() {
        try {
            LinkedList<Security> accList = new LinkedList<Security>();

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
    public boolean update(Security model) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            String sql = "UPDATE " + tableName + " set "
                + "USERNAME='" + model.getUsername() + "', " 
                + "BALANCE='" + model.getBalance().serialize() + "', "
                + "UNREALIZEDPROFIT='" + model.getUnrealizedProfit().serialize() + "', "
                + "REALIZEDPROFIT='" + model.getRealizedProfit().serialize() + "', "
                + "OWNED='" + getStockString(model.getOwned()) + "', "
                + "OPEN='" + getStockString(model.getOpen())+ "' "
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
    public boolean delete(Security model) {
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
                "(ACCOUNTNUMBER TEXT PRIMARY KEY NOT NULL, " +
                " USERNAME TEXT NOT NULL, " +
                " BALANCE TEXT, " + 
                " REALIZEDPROFIT TEXT, " + 
                " UNREALIZEDPROFIT TEXT, " + 
                " OWNED TEXT[], " +
                " OPEN TEXT[])";
        
        statement.executeUpdate(sql);
        statement.close();

        System.out.println("TABLE [" + tableName + "] INIT");
    }

    private Security parseResultSet(ResultSet rs) throws SQLException {
        String username = rs.getString("USERNAME"),
            accountNumber = rs.getString("ACCOUNTNUMBER");
        
        BaseCurrency balance = BaseCurrency.deserialize(rs.getString("BALANCE")),
            realizedProfit = BaseCurrency.deserialize(rs.getString("REALIZEDPROFIT")),
            unrealizedProfit = BaseCurrency.deserialize(rs.getString("UNREALIZEDPROFIT"));

        HashMap<Stock, Integer> owned = parseStockStrings((String []) rs.getArray("OWNED").getArray()),
            open = parseStockStrings((String []) rs.getArray("OPEN").getArray());
        
        return new Security(username, accountNumber, balance, owned, open, realizedProfit, unrealizedProfit);
    }

    private HashMap<Stock, Integer> parseStockStrings(String[] stockStrings) {
        HashMap<Stock, Integer> hashMap = new HashMap<Stock, Integer>();

        for (String stockString: stockStrings) {
            String[] slices = stockString.split(" +");
            assert slices.length == 2: "Malformed Stock String " + stockString;

            String name = slices[0];
            Integer value = Integer.parseInt(slices[1]);

            Stock stock = StockDao.getInstance().getById(name);
            hashMap.put(stock, value);
        }

        return hashMap;
    }

    private String getStockString(HashMap<Stock, Integer> hashMap) {
        Set<Stock> keySet = hashMap.keySet();
        int counter = 0;
        String acc = "";

        for (Stock stock: keySet) {
            Integer value = hashMap.get(stock);
            acc += "\"" + stock.getName() + " " 
                + Integer.toString(value) + "\"";
            
            if (counter != keySet.size() - 1) {
                acc += ", ";
            }

            counter += 1;
        }

        return "{" + acc + "}";
    }
}
