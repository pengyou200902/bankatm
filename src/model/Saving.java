/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package model;


public class Saving extends BankAccount {
    private final BaseCurrency balance;

    public Saving(String username, String accountNumber) {
        super(username, accountNumber);
        this.balance = new BaseCurrency("USD", 0);
    }

    public Saving(String username, String accountNumber, BaseCurrency balance) {
        super(username, accountNumber);
        this.balance = balance;
    }

    public BaseCurrency getBalance() {
        return balance;
    }

    @Override
    public AccountTypes getType() {
        return AccountTypes.SAVING;
    }
}
