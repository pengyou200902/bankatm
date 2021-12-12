/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package model;

import java.sql.Date;

public class Transaction {

    private long id;  // auto increment pk
    private double interestRate;
    private BankAccount account;
    private BaseCurrency currency;
    private Date date;

    public Transaction(double interestRate, BankAccount account, BaseCurrency currency, Date date) {
        this.interestRate = interestRate;
        this.account = account;
        this.currency = currency;
        this.date = date;
    }

    public Transaction(long id, double interestRate, BankAccount account, BaseCurrency currency, Date date) {
        this(interestRate, account, currency, date);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public BaseCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(BaseCurrency currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
