/**
 * @Author Friende.Peng_You
 * @Date 2021-12-09 14:37
 */

package dao;

import model.Checking;
import model.Saving;
import model.Security;

public class DaoFactory {
    private final SecurityDao securityDao = new SecurityDao();
    private final CheckingDao checkingDao = new CheckingDao();
    private final SavingDao savingDao = new SavingDao();
    private final UserDao userDao = new UserDao();
    private final LoanDao loanDao = new LoanDao();
    private final TransactionDao transactionDao = new TransactionDao();
    private final StockDao stockDao = new StockDao();
    private final AccountDao accountDao = new AccountDao();
//    private final static BankAccountDao<Checking> checkingDao = new BankAccountDao<>();
//    private final static BankAccountDao<Saving> savingDao = new BankAccountDao<>();
//    private final static BankAccountDao<Security> securityDao = new BankAccountDao<>();



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