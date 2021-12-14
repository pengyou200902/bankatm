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
    // TODO: record transactions

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
        matchedSrcBalance.minusValue(currency);
        matchedDstBalance.addValue(currency);
        updateAccount(src);
        updateAccount(dst);
        return new OpResponse(1, true, "Transfer success!", currency);
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
        String accountNumber;

        // generate random accountNumber
        do {
            accountNumber = String.valueOf(random.nextLong());
        } while (dao.getById(accountNumber) == null);

        return accountNumber;
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
