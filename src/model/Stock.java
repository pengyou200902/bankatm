/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package model;

public class Stock {
    private String name; // pk
    private BaseCurrency price;

    public Stock(String name, BaseCurrency price) {
        this.name = name;
        this.price = price;
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

    public void setPrice(BaseCurrency price) {
        this.price = price;
    }
}
