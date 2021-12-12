/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package model;

import java.sql.Date;

public class Loan {

    private long id;  // auto increment pk
    private int status;
    private Checking account;
    private BaseCurrency currency;
    private Date date;
    private double interestRate;

    public Loan(int status, Checking account, BaseCurrency currency, Date date, double interestRate) {
        this.status = status;
        this.account = account;
        this.currency = currency;
        this.date = date;
        this.interestRate = interestRate;
    }

    public Loan(long id, int status, Checking account, BaseCurrency currency, Date date, double interestRate) {
        this(status, account, currency, date, interestRate);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Checking getAccount() {
        return account;
    }

    public void setAccount(Checking account) {
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

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
