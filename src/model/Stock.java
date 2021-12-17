/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package model;

import java.util.Objects;

public class Stock {
    private String name; // pk
    private BaseCurrency price;
    private boolean enabled;

    public Stock(String name, BaseCurrency price, boolean enabled) {
        this.name = name;
        this.price = price;
        this.enabled = enabled;
    }

    public Stock(String name, double price, boolean enabled) {
        this.name = name;
        this.price = new BaseCurrency("USD", price);
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseCurrency getPrice() {
        return price;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setPrice(BaseCurrency price) {
        this.price = price;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return name.equals(stock.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "name=" + name +
                ", price=" + price + '}';
    }
}
