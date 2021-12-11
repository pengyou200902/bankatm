/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package model;

import java.util.ArrayList;
import java.util.List;

public class Checking extends BankAccount{

    private final List<BaseCurrency> currencies;

    public Checking(String username, String accountNumber) {
        super(username, accountNumber);
        currencies = new ArrayList<>();
        for (String s : CurrencyType.types) {
            currencies.add(new BaseCurrency(s));
        }
    }

    public Checking(String username, String accountNumber, List<BaseCurrency> currencies) {
        super(username, accountNumber);
        this.currencies = currencies;
    }


    public List<BaseCurrency> getCurrencies() {
        return currencies;
    }
}
