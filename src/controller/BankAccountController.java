/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package controller;

import model.*;

public class BankAccountController {

    public <T extends BankAccount> OpResponse deposit(T account, BaseCurrency currency) {
        BaseCurrency matched = matchCurrency(account, currency);
        if (matched != null) {
            matched.addValue(currency.getAmount());
            return new OpResponse(1, "Deposit Successfully!");
        }

        return new OpResponse(0, "Deposit Failed!");
    }

    public <T extends BankAccount> OpResponse withdraw(T account, BaseCurrency currency) {
        boolean status = false;
        BaseCurrency matched = matchCurrency(account, currency);
        if (matched != null) {
            status = matched.minusValue(currency.getAmount());
        }
        if (status) {
            return new OpResponse(1, "Withdraw Successfully!");
        }
        else return new OpResponse(0, "Withdraw Failed!");
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

}
