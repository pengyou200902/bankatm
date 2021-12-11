/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package model;

import java.util.HashMap;

public class Security extends BankAccount {

    private final BaseCurrency balance;
    private final HashMap<Stock, Integer> owned;
    private final HashMap<Stock, Integer> open;
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
                    HashMap<Stock, Integer> owned,
                    HashMap<Stock, Integer> open,
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

    public HashMap<Stock, Integer> getOwned() {
        return owned;
    }

    public HashMap<Stock, Integer> getOpen() {
        return open;
    }

    public BaseCurrency getRealizedProfit() {
        return realizedProfit;
    }

    private boolean isEnabled() {
        return this.balance.getAmount() >= 5000;
    }
}