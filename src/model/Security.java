/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package model;

public class Security extends BankAccount {
    private final BaseCurrency balance;

    public Security(String username, String accountNumber) {
        super(username, accountNumber);
        this.balance = new BaseCurrency("USD", 0);
    }


    public Security(String username, String accountNumber, BaseCurrency balance) {
        super(username, accountNumber);
        this.balance = balance;
    }

    public BaseCurrency getBalance() {
        return balance;
    }
}