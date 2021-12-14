/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package model;

import java.util.HashMap;

public class Security extends BankAccount {

    private final BaseCurrency balance;
    private final HashMap<String, Integer> owned;
    private final HashMap<String, Integer> open;
    private final BaseCurrency realizedProfit;

    public Security(String username, String accountNumber) {
        super(username, accountNumber);
        this.balance = new BaseCurrency("USD", 0);
        this.owned = new HashMap<>();
        this.open = new HashMap<>();
        this.realizedProfit = new BaseCurrency("USD", 0);
    }

    public Security(String username,
                    String accountNumber,
                    BaseCurrency balance,
                    HashMap<String, Integer> owned,
                    HashMap<String, Integer> open,
                    BaseCurrency realizedProfit) {
        super(username, accountNumber);
        this.balance = balance;
        this.owned = owned;
        this.open = open;
        this.realizedProfit = realizedProfit;
    }


    public BaseCurrency getBalance() {
        return balance;
    }

    public HashMap<String, Integer> getOwned() {
        return owned;
    }

    public HashMap<String, Integer> getOpen() {
        return open;
    }

    public BaseCurrency getRealizedProfit() {
        return realizedProfit;
    }

    public boolean isEnabled() {
        return this.balance.getAmount() >= 5000;
    }

    @Override
    public AccountTypes getType() {
        return AccountTypes.SECURITY;
    }
}