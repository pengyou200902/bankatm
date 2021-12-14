/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package model;

public abstract class BankAccount {
    private String username;
    private String accountNumber; // pk as Adarsh suggested

    public BankAccount(String username, String accountNumber) {
        this.username = username;
        this.accountNumber = accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountTypes getType() {
        return null;
    }

//    public abstract void deposit(BaseCurrency currency);
//
//    public abstract void withdraw(BaseCurrency currency);
}
