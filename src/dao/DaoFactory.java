/**
 * @Author Friende.Peng_You
 * @Date 2021-12-09 14:37
 */

package dao;

import model.Checking;
import model.Saving;
import model.Security;

public class DaoFactory {
    private final static SecurityDao securityDao = new SecurityDao();
    private final static CheckingDao checkingDao = new CheckingDao();
    private final static SavingDao savingDao = new SavingDao();
    private final static UserDao userDao = new UserDao();
    private final static LoanDao loanDao = new LoanDao();
    private final static TransactionDao transactionDao = new TransactionDao();
    private final static StockDao stockDao = new StockDao();
    private final static AccountDao accountDao = new AccountDao();
//    private final static BankAccountDao<Checking> checkingDao = new BankAccountDao<>();
//    private final static BankAccountDao<Saving> savingDao = new BankAccountDao<>();
//    private final static BankAccountDao<Security> securityDao = new BankAccountDao<>();


    public static SecurityDao getSecurityDao() {
        return securityDao;
    }

    public static CheckingDao getCheckingDao() {
        return checkingDao;
    }

    public static SavingDao getSavingDao() {
        return savingDao;
    }

    public static UserDao getUserDao() {
        return userDao;
    }

    public static LoanDao getLoanDao() {
        return loanDao;
    }

    public static TransactionDao getTransactionDao() {
        return transactionDao;
    }

    public static StockDao getStockDao() {
        return stockDao;
    }

    public static AccountDao getAccountDao() {
        return accountDao;
    }
}
//SecurityDao
//LoanDao
//CheckingDao
//UserDao
//BaseDao
//TransactionDao
//StockDao
//SavingDao
//DaoFactory
//AccountDao
//BankAccountDao