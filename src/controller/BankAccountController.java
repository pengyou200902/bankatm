/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package controller;

import dao.*;
import model.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BankAccountController {

    private final CheckingDao checkingDao;
    private final SavingDao savingDao;
    private final SecurityDao securityDao;
    private final Set<String> types;
    private final Random random;

    public BankAccountController() {
        this.checkingDao = CheckingDao.getInstance();
        this.savingDao = SavingDao.getInstance();
        this.securityDao = SecurityDao.getInstance();
        this.random = new Random();
        this.types = new HashSet<>();
        types.add("Checking");
        types.add("Saving");
//        types.add("Security");
    }

    public BankAccount[] getAllBankAccounts(String username) {
        return new BankAccount[] {
                checkingDao.getById(username),
                savingDao.getById(username),
                securityDao.getById(username)
        };
    }

    public OpResponse openAccount(String username, String type) {
        if (!types.contains(type)) {
            return new OpResponse(0, false, "Cannot open such type of account!");
        }

        if (type.equals("Checking")) {
            if (checkingDao.getById(username) != null) {
                return new OpResponse(0, false, "You have already got a checking account.");
            }

            String accountNumber = generateAccountNumber(checkingDao);
            Checking account = new Checking(username, accountNumber);
            boolean success = checkingDao.save(account);
            if (success) {
                return new OpResponse(1, true, String.format("%s account opened successfully", type), account);
            }
            else {
                return new OpResponse(0, false, String.format("Failed to open %s account", type));
            }
        }
        else { // Saving
            if (savingDao.getById(username) != null) {
                return new OpResponse(0, false,  "You have already got a saving account.");
            }

            String accountNumber = generateAccountNumber(savingDao);
            String securityAccountNumber = generateAccountNumber(securityDao);
            Saving account = new Saving(username, accountNumber);
            Security securityAccount = new Security(username, securityAccountNumber);
            boolean success = savingDao.save(account) && securityDao.save(securityAccount);
            if (success) {
                return new OpResponse(
                        1,
                        true,
                        String.format("%s account opened successfully, also your security account", type),
                        new Object[]{account, securityAccount});
            }
            else {
                return new OpResponse(0, false, String.format("Failed to open %s account", type));
            }

        }
    }

    public <T extends BankAccount> OpResponse closeAccount(T account) {
        boolean status;
        Object data = null;
        if (account instanceof Checking) {
            status = checkingDao.delete((Checking) account);
            data = account;
        }
        else { // saving && security
            Security security = securityDao.getById(account.getUsername());
            status = savingDao.delete((Saving) account) && securityDao.delete(security);
            data = new Object[]{account, security};
        }
        if (status) {
            return new OpResponse(1, status, "Close account successfully!", data);
        }
        else {
            return new OpResponse(0, status, "Failed to close account!");
        }
    }

    public <T extends BankAccount> OpResponse deposit(T account, BaseCurrency currency) {
        BaseCurrency matched = matchCurrency(account, currency);
        if (matched != null) {
            matched.addValue(currency);
            return new OpResponse(1, true, "Deposit Successfully!");
        }

        return new OpResponse(0, false,  "Deposit Failed!");
    }

    public <T extends BankAccount> OpResponse withdraw(T account, BaseCurrency currency) {
        boolean status = false;
        BaseCurrency matched = matchCurrency(account, currency);
        if (matched != null) {
            status = matched.minusValue(currency);
        }
        if (status) {
            return new OpResponse(1, true, "Withdraw Successfully!");
        }
        else return new OpResponse(0, false,  "Withdraw Failed!");
    }

    public boolean transfer(BankAccount src, BankAccount dst, BaseCurrency currency) {
        // TODO: transfer money between accounts
        return false;
    }

    private <T extends BankAccount> BaseCurrency matchCurrency(T account, BaseCurrency currency) {
        if (account instanceof Checking) {
            for (BaseCurrency c: ((Checking) account).getCurrencies()) {
                if (c.isSameKind(currency)) {
                    return c;
                }
            }
        }
        else if (account instanceof Saving) {
            return ((Saving) account).getBalance();
        }
        else {
            return ((Security) account).getBalance();
        }
        return null;
    }

    private <A extends BankAccount, T extends BaseDao<A, String>> String generateAccountNumber(T dao) {
        String accountNumber;

        // generate random accountNumber
        do {
            accountNumber = String.valueOf(random.nextLong());
        } while (dao.getById(accountNumber) == null);

        return accountNumber;
    }

}
