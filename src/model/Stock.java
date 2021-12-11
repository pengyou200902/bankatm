/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package model;

public class Stock {
    private String name;
    private BaseCurrency price;

    public Stock(String name, BaseCurrency price) {
        this.name = name;
        this.price = price;
    }
}
