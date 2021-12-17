/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package dao;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import model.AccountTypes;
import model.BankAccount;
import model.BaseCurrency;
import model.Transaction;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDao implements BaseDao<Transaction, Integer> {
    private static TransactionDao instance;
    private static String tableName = "TRANSACTIONS";

    public static TransactionDao getInstance() {
        if (instance == null) {
            instance = new TransactionDao();
        }

        return instance;
    }

    private TransactionDao() {
        try {
            if (!ConnectionManager.getInstance().tableExists(tableName)) {
                createTable();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean save(Transaction transaction) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            
            String sql = "INSERT INTO " + tableName + " (INTERESTRATE, ACCOUNT, ACCTYPE, CURRENCY, COMMENT, DATE) VALUES ("
                + transaction.getInterestRate() + ", '" 
                + transaction.getAccount().getAccountNumber() + "', '" 
                + transaction.getAccount().getType().getTypeString() + "', '"
                + transaction.getCurrency().serialize() + "', '"
                + transaction.getComment() + "', '"
                + formatter.format(transaction.getDate()) + "');";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override 
    public Transaction getById(Integer id) {
        try {
            Transaction account = null;

            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
                
            ResultSet rs = statement.executeQuery("SELECT * FROM " 
                + tableName
                + " WHERE ID=" 
                + id);
            
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
    public List<Transaction> getAll() {
        return fetchAll("SELECT * FROM " + tableName);
    }

    public List<Transaction> getByDate(Timestamp date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return fetchAll("SELECT * FROM " 
            + tableName
            + " WHERE DATE(DATE) = '"
            + formatter.format(date) + "'"
        );
    }

    @Override
    public boolean update(Transaction transaction) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            
            String sql = "UPDATE " + tableName + " set "
                + "ACCOUNT='" + transaction.getAccount().getAccountNumber() + "', " 
                + "ACCTYPE='" + transaction.getAccount().getType().getTypeString() + "', " 
                + "CURRENCY='" + transaction.getCurrency().serialize()+ "', " 
                + "DATE='" + formatter.format(transaction.getDate()) + "', " 
                + "COMMENT='" + transaction.getComment()+ "', " 
                + "INTERESTRATE=" + Double.toString(transaction.getInterestRate()) + " "
                + "WHERE ID=" + transaction.getId();

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Transaction transaction) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            statement.executeUpdate("DELETE FROM " 
                + tableName
                + " WHERE ID=" 
                + transaction.getId());

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
                "(ID SERIAL PRIMARY KEY," +
                " INTERESTRATE REAL, " +
                " ACCOUNT TEXT, " +
                " ACCTYPE TEXT, " +
                " CURRENCY TEXT, " +
                " COMMENT TEXT, " +
                " DATE TIMESTAMP)";
        
        statement.executeUpdate(sql);
        statement.close();

        System.out.println("TABLE [" + tableName + "] INIT");
    }

    private Transaction parseResultSet(ResultSet rs) throws SQLException {
        long id = rs.getLong("ID");
        double interestRate = rs.getDouble("INTERESTRATE");
        Timestamp date = rs.getTimestamp("DATE");
        BaseCurrency currency = BaseCurrency.deserialize(rs.getString("CURRENCY"));
        String accType = rs.getString("ACCTYPE"), 
            accNum = rs.getString("ACCOUNT"),
            comment = rs.getString("COMMENT");
        BankAccount account = null;

        if (accType.equals(AccountTypes.CHECKING.getTypeString())) {
            account = CheckingDao.getInstance().getById(accNum);
        } else if (accType.equals(AccountTypes.SAVING.getTypeString())) {
            account = SavingDao.getInstance().getById(accNum);
        } else if (accType.equals(AccountTypes.SECURITY.getTypeString())) {
            account = SecurityDao.getInstance().getById(accNum);
        }

        return new Transaction(id, interestRate, account, currency, comment, date);
    }

    private List<Transaction> fetchAll(String query) {
        try {
            LinkedList<Transaction> accList = new LinkedList<Transaction>();

            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            ResultSet rs = statement.executeQuery(query);
            
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
}
