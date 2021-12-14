/**
 * @Author Friende.Peng_You
 * @Timestamp 2021-12-07 17:20
 */

package model;

import java.sql.Timestamp;

public class Transaction {

    private long id;  // auto increment pk
    private double interestRate;
    private BankAccount account;
    private BaseCurrency currency;
    private String comment;
    private Timestamp date;

    public Transaction(double interestRate, BankAccount account, BaseCurrency currency, String comment, Timestamp date) {
        this.interestRate = interestRate;
        this.account = account;
        this.currency = currency;
        this.comment = comment;
        this.date = date;
    }

    public Transaction(long id, double interestRate, BankAccount account, BaseCurrency currency, String comment, Timestamp date) {
        this(interestRate, account, currency, comment, date);
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

    public String getComment() {
        return comment;
    }

    public void setCurrency(BaseCurrency currency) {
        this.currency = currency;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
