/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:16
 */

package model;


public class BaseCurrency {


    private String name;
    private double amount;

    public BaseCurrency(String name) {
        this.name = name;
        this.amount = 0;
    }

    public BaseCurrency(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public boolean isSameKind(BaseCurrency other) {
        return this.name.equals(other.getName());
    }

    public void addValue(double value) {
        amount += value;
    }

    public boolean minusValue(double value) {
        if (amount < value) return false;
        else {
            amount -= value;
            return true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



}
