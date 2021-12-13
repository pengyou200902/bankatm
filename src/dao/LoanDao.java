/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package dao;

import model.BaseCurrency;
import model.Checking;
import model.Loan;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class LoanDao implements BaseDao<Loan, Integer>{
    private static LoanDao instance = null;
    private static String tableName = "LOAN";

    public static LoanDao getInstance() {
        if (instance == null) {
            instance = new LoanDao();
        }

        return instance;
    }

    private LoanDao() {
        try {
            if (!ConnectionManager.getInstance().tableExists(tableName)) {
                createTable();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean save(Loan loan) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            
            String sql = "INSERT INTO " + tableName + " (STATUS, CHECKING, CURRENCY, DATE, INTERESTRATE) VALUES ('"
                + loan.getStatus() + "', '" 
                + loan.getAccount().getAccountNumber() + "', '" 
                + loan.getCurrency().serialize() + "', '"
                + formatter.format(loan.getDate()) + "', "
                + loan.getInterestRate() + ");";

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Loan getById(Integer id) {
        try {
            Loan account = null;

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
    public List<Loan> getAll() {
        try {
            LinkedList<Loan> accList = new LinkedList<Loan>();

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
    public boolean update(Loan loan) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            
            String sql = "UPDATE " + tableName + " set "
                + "STATUS=" + loan.getStatus() + ", " 
                + "CHECKING='" + loan.getAccount().getAccountNumber() + "', " 
                + "CURRENCY='" + loan.getCurrency().serialize()+ "', " 
                + "DATE='" + formatter.format(loan.getDate()) + "', " 
                + "INTERESTRATE=" + Double.toString(loan.getInterestRate()) + " "
                + "WHERE ID=" + loan.getId();

            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Loan loan) {
        try {
            Statement statement = ConnectionManager
                .getInstance()
                .getConnection()
                .createStatement();

            statement.executeUpdate("DELETE FROM " 
                + tableName
                + " WHERE ID=" 
                + loan.getId());

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
                "(ID SERIAL PRIMARY KEY, " +
                " STATUS INT, " +
                " CHECKING TEXT, " + 
                " CURRENCY TEXT, " +
                " DATE DATE, " + 
                " INTERESTRATE REAL)";
        
        statement.executeUpdate(sql);
        statement.close();

        System.out.println("TABLE [" + tableName + "] INIT");
    }

    private Loan parseResultSet(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("ID"), status = rs.getInt("STATUS");
        Double interestRate = rs.getDouble("INTERESTRATE");
        Date date = rs.getDate("DATE");
        BaseCurrency currency = BaseCurrency.deserialize(rs.getString("CURRENCY"));
        Checking account = CheckingDao
            .getInstance()
            .getById(rs.getString("CHECKING"));
        
        return new Loan(id, status, account, currency, date, interestRate);
    }
}
