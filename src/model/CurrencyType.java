/**
 * @author You Peng
 * @date 12/8/2021 4:20 PM
 */


package model;

import java.util.HashMap;

public class CurrencyType {
    public static final int count = 8;
    public static final String[] types = {
            "USD", // 0
            "EUR", // 1
            "JPY", // 2
            "GBP", // 3
            "AUD", // 4
            "CAD", // 5
            "CNY", // 6
            "HKD"  // 7
    };
//    public static final String USD = "USD"; // 0
//    public static final String EUR = "EUR"; // 1
//    public static final String JPY = "JPY"; // 2
//    public static final String GBP = "GBP"; // 3
//    public static final String AUD = "AUD"; // 4
//    public static final String CAD = "CAD"; // 5
//    public static final String CNY = "CNY"; // 6
//    public static final String HKD = "HKD"; // 7

    public static final HashMap<String, Integer> idToCurrency = new HashMap<>();

    public static void init() {
        idToCurrency.put("USD", 0);
        idToCurrency.put("EUR", 1);
        idToCurrency.put("JPY", 2);
        idToCurrency.put("GBP", 3);
        idToCurrency.put("AUD", 4);
        idToCurrency.put("CAD", 5);
        idToCurrency.put("CNY", 6);
        idToCurrency.put("HKD", 7);
    }

}
