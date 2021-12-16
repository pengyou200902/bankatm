/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package controller;

import dao.*;
import model.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BankAccountController {

    private final CheckingDao checkingDao;
    private final SavingDao savingDao;
    private final SecurityDao securityDao;
    private final TransactionDao transactionDao;
    private final Set<String> types;
    private final Random random;

    public BankAccountController() {
        this.checkingDao = CheckingDao.getInstance();
        this.savingDao = SavingDao.getInstance();
        this.securityDao = SecurityDao.getInstance();
        this.transactionDao = TransactionDao.getInstance();
        this.random = new Random();
        this.types = new HashSet<>();
        types.add(AccountTypes.CHECKING.getTypeString());
        types.add(AccountTypes.SAVING.getTypeString());
    }

//    public <T extends BankAccount> OpResponse deleteAccount(T account) {
//        if (account == null)    return new OpResponse(0, false, "Null account.");
//        boolean status = false;
//        if (account instanceof Checking) {
//            status = checkingDao.delete((Checking) account);
//        } else if (account instanceof Saving) {
//            status = savingDao.delete((Saving) account);
//        } else if (account instanceof Security) {
//            status = securityDao.delete((Security) account);
//        }
//        if (status) return new OpResponse(1, status, "Succeed!", account);
//        return OpResponse
//    }

    public OpResponse getAccountByNumber(String accountNumber) {
        BankAccount account = null;
        account = checkingDao.getById(accountNumber);
        if (account != null)    return new OpResponse(1, true, "Get checking.", account);
        account = savingDao.getById(accountNumber);
        if (account != null)    return new OpResponse(1, true, "Get saving.", account);
        account = securityDao.getById(accountNumber);
        if (account != null)    return new OpResponse(1, true, "Get security.", account);
        return new OpResponse(0, true, "No such account!", null);
    }

    public BankAccount[] getAllBankAccounts(String username) {
        return new BankAccount[] {
                checkingDao.getByUsername(username),
                savingDao.getByUsername(username),
                securityDao.getByUsername(username)
        };
    }

    public OpResponse openAccount(String username, String type) {
        if (!types.contains(type)) {
            return new OpResponse(0, false, "Cannot open such type of account!");
        }

        if (type.equals(AccountTypes.CHECKING.getTypeString())) {
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
        boolean status = true;
        Object data = null;
        if (account instanceof Checking) {
            status = checkingDao.delete((Checking) account);
            data = account;
        }
        else { // saving && security
            Security security = securityDao.getByUsername(account.getUsername());
            Saving saving = savingDao.getByUsername(account.getUsername());
            if (security != null)   status = status && securityDao.delete(security);
            if (saving != null)   status = status && savingDao.delete(saving);
            data = new Object[]{account, security};
        }
        if (status) {
            return new OpResponse(1, status, "Close account successfully!", data);
        }
        else {
            return new OpResponse(0, status, "Failed to close account!");
        }
    }

    public <T extends BankAccount> OpResponse deposit(T account, BaseCurrency currency, Timestamp date, String comment, double interestRate) {
        BaseCurrency matched = matchCurrency(account, currency);
        if (matched != null) {
            if (interestRate > 0 && !account.getUsername().equals("admin")) {
                Checking managerAccount = checkingDao.getByUsername("admin");
                BaseCurrency fee = new BaseCurrency(currency.getName(), currency.getAmount() * interestRate);
                BaseCurrency matchMgr = matchCurrency(managerAccount, currency);
                matchMgr.addValue(fee);
                currency.minusValue(fee);
                checkingDao.update(managerAccount);
                Transaction t = new Transaction(0, managerAccount, fee, String.format("%s fee income", comment), date);
                transactionDao.save(t);
            }
            matched.addValue(currency);
            updateAccount(account);
            Transaction t = new Transaction(interestRate, account, currency, comment, date);
            transactionDao.save(t);
            return new OpResponse(1, true, "Deposit Successfully!", account);
        }

        return new OpResponse(0, false,  "Deposit Failed!");
    }

    public <T extends BankAccount> OpResponse deposit(T account, BaseCurrency currency) {
        return deposit(account, currency, new Timestamp(System.currentTimeMillis()));
    }

    public <T extends BankAccount> OpResponse deposit(T account, BaseCurrency currency, Timestamp date) {
        return deposit(account, currency, date, "Deposit", 0);
    }

    public <T extends BankAccount> OpResponse withdraw(T account, BaseCurrency currency, String comment, double interestRate, Timestamp date) {
        boolean status = false;
        BaseCurrency matched = matchCurrency(account, currency);
        if (matched != null) {
            status = matched.minusValue(currency);
        }
        if (status) {
            if (interestRate > 0 && !account.getUsername().equals("admin")) {
                Checking managerAccount = checkingDao.getByUsername("admin");
                BaseCurrency withdrawFee = new BaseCurrency(currency.getName(), currency.getAmount() * interestRate);
                deposit(managerAccount, withdrawFee, date, String.format("%s fee income", comment), 0);
            }

            currency.setAmount(-currency.getAmount());
            updateAccount(account);
            Transaction t = new Transaction(interestRate, account, currency, comment, date);
            transactionDao.save(t);
            return new OpResponse(1, true, "Withdraw Successfully!", account);
        }
        else return new OpResponse(0, false,  "Withdraw Failed!");
    }

    public <T extends BankAccount> OpResponse withdraw(T account, BaseCurrency currency) {
        return withdraw(account, currency, "Withdraw", ChargeConfig.COMMON_INTEREST, new Timestamp(System.currentTimeMillis()));
    }

    public <S extends BankAccount, D extends BankAccount> OpResponse transfer(S src, D dst, BaseCurrency currency) {
        // ensure enough money
        BaseCurrency matchedSrcBalance = matchCurrency(src, currency);
        BaseCurrency matchedDstBalance = matchCurrency(dst, currency);

        if (matchedSrcBalance == null
                || matchedDstBalance == null
                || matchedSrcBalance.getAmount() < currency.getAmount()) {
            return new OpResponse(0, false, "Failed. Please check your balance or input of account number.");
        }

        // check whether Security account is enabled and belongs to the same person
        if (dst instanceof Security) {
            Security security = (Security) dst;
            if (!security.isEnabled() || !src.getUsername().equals(security.getUsername())) {
                return new OpResponse(0, false, "Failed. The bank account may not be enabled or may not be yours.");
            }
        }
        else if (src instanceof Security) {
            Security security = (Security) src;
            if (!security.isEnabled() || !dst.getUsername().equals(security.getUsername())) {
                return new OpResponse(0, false, "Failed. The bank account may not be enabled or may not be yours.");
            }
        }
//        Checking managerAccount = checkingDao.getByUsername("admin");
        BaseCurrency transferFee = new BaseCurrency(currency.getName(), currency.getAmount() * ChargeConfig.COMMON_INTEREST);
        Timestamp date = new Timestamp(System.currentTimeMillis());
//        deposit(managerAccount, transferFee, date, "Transfer fee income", 0);
//        minus src money
//        matchedSrcBalance.minusValue(currency);
        withdraw(src, currency, "Transfer out", ChargeConfig.COMMON_INTEREST, date);
//        minus target money before addition
        currency.minusValue(transferFee);
        deposit(dst, currency, date, "Receive transfer", 0);
//        matchedDstBalance.addValue(currency);
//        updateAccount(src);
//        updateAccount(dst);

//        all tansactions are recorded during deposit and withdraw
//        Transaction t = new Transaction(ChargeConfig.COMMON_INTEREST, dst, currency, "Receive Transfer", date);
//        transactionDao.save(t);
//        currency.addValue(transferFee);
//        currency.setAmount(-currency.getAmount());
//        t.setAccount(src);
//        t.setCurrency(currency);
//        t.setComment("Transfer out");
//        transactionDao.save(t);
        return new OpResponse(1, true, "Transfer success!", currency);
    }

    public <T extends BankAccount> BaseCurrency matchCurrency(T account, BaseCurrency currency) {
        if (account instanceof Checking) {
            for (BaseCurrency c: ((Checking) account).getCurrencies()) {
                if (c.isSameKind(currency)) {
                    return c;
                }
            }
        }
        else if (account instanceof Saving) {
            Saving saving = (Saving) account;
            if (saving.getBalance().isSameKind(currency)) {
                return saving.getBalance();
            }
        }
        else {
            Security security = (Security) account;
            if (security.getBalance().isSameKind(currency)) {
                return security.getBalance();
            }
        }
        return null;
    }

    private <A extends BankAccount, T extends BaseDao<A, String>> String generateAccountNumber(T dao) {
//        String accountNumber;
//
//        // generate random accountNumber
//        do {
//            accountNumber = String.valueOf(random.nextLong());
//        } while (dao.getById(accountNumber) == null);

        return String.valueOf(System.nanoTime());
//        return accountNumber;
    }

    private <T extends BankAccount> boolean updateAccount(T account) {
        if (account instanceof Checking) {
            return checkingDao.update((Checking) account);
        }
        else if (account instanceof Saving) {
            return savingDao.update((Saving) account);
        }
        else {
            return securityDao.update((Security) account);
        }
    }

}
