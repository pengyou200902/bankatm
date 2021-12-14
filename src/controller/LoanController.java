/**
 * @author You Peng
 * @date 12/13/2021 9:18 PM
 */


package controller;

import dao.CheckingDao;
import dao.LoanDao;
import model.*;

import java.sql.Timestamp;
import java.util.List;


public class LoanController {
    private final BankAccountController bankAccountController;
    private final CheckingDao checkingDao;
    private final LoanDao loanDao;

    public LoanController() {
        bankAccountController = new BankAccountController();
        checkingDao = CheckingDao.getInstance();
        loanDao = LoanDao.getInstance();
    }

    public OpResponse applyLoan(Checking account, BaseCurrency loanAmount) {
        if (account == null || loanAmount == null
                || loanAmount.getAmount() <= 0) {
            return new OpResponse(0, false, "Failed! Error in your loan request!");
        }

        else {
            Loan loan = new Loan(
                    -1,
                    account,
                    loanAmount,
                    new Timestamp(System.currentTimeMillis()),
                    ChargeConfig.LOAN_INTEREST
            );
            loanDao.save(loan);
            return new OpResponse(1, true, "Loan request sent to the manager.", account);
        }
    }

    public OpResponse reviewLoan(Account loginAccount, long loanId, int newStatus) {
        if (loginAccount == null || !loginAccount.getType().equals("Manager") || newStatus < 0 || newStatus > 1) {
            return new OpResponse(0, false, "No Authority!");
        }
        int id = (int) loanId;
        Loan loan = loanDao.getById(id);
        if (loan == null)   return new OpResponse(0, false, "No such loan!");

        loan.setStatus(newStatus);
        if (newStatus == 1) {
            Checking requestAccount = loan.getAccount();
            BaseCurrency loanCurrency = loan.getCurrency();
//            BaseCurrency loanFee = new BaseCurrency(loanCurrency.getName(), loanCurrency.getAmount() * ChargeConfig.LOAN_FEE);
//            loanCurrency.minusValue(loanFee);
            Timestamp date = new Timestamp(System.currentTimeMillis());
//            update and transaction inside deposit
            bankAccountController.deposit(
                    requestAccount, loanCurrency, date, String.format("Loan deposit id %s", id), ChargeConfig.LOAN_FEE);
//            bankAccountController.deposit(managerAccount, loanFee, date, "Loan", 0);
//            checkingDao.update(requestAccount);
//            checkingDao.update(managerAccount);
        }

        loanDao.update(loan);
        return new OpResponse(1, true, String.format("LoanId %s has been reviewed as status %d", id, newStatus), loan);
    }

    public OpResponse getAccountLoans(String accountNumber) {
        Checking account = checkingDao.getById(accountNumber);
        if (account == null) {
            return new OpResponse(1, true, "None");
        }
        List<Loan> loans = loanDao.getByAccountNumber(accountNumber);
        return new OpResponse(1, true, String.format("Get loans for %s", accountNumber), loans);
    }
}
