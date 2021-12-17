/**
 * @author You Peng
 * @date 12/11/2021 2:23 PM
 */


package controller;

//import dao.DaoFactory;
import dao.SecurityDao;
import dao.StockDao;
import model.BaseCurrency;
import model.OpResponse;
import model.Security;
import model.Stock;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockController {
    private final StockDao stockDao;
    private final SecurityDao securityDao;
    private final BankAccountController bankAccountController;

    public StockController() {
        stockDao = StockDao.getInstance();
        securityDao = SecurityDao.getInstance();
        bankAccountController = new BankAccountController();
    }

    public OpResponse getAllStocks() {
        List<Stock> stocks = stockDao.getAll();
        if (stocks.size() == 0) return new OpResponse(1, true, "No stocks yet!", null);
        return new OpResponse(1, true, "Succeed!", stocks);
    }

    public OpResponse updateStock(String stockName, double price, boolean enabled) {
        Stock real = stockDao.getById(stockName);
        if (real == null)   return new OpResponse(0, false, "No such stock.");

        BaseCurrency realPrice = real.getPrice();
        if (realPrice.getAmount() == price && real.isEnabled() == enabled) {
            return new OpResponse(1, true, "Nothing changes", real);
        }

        StringBuilder stringBuilder = new StringBuilder("Stock updates.");
        // true -> false
        if (real.isEnabled() && !enabled) {
            stringBuilder.append(" Stock is disabled.");
        }
        // false -> true
        else if (!real.isEnabled() && enabled) {
            stringBuilder.append(" Stock is enabled.");
        }
        real.setEnabled(enabled);
        real.getPrice().setAmount(price);
        stockDao.update(real);

        return new OpResponse(1, true, stringBuilder.toString(), real);
    }

    public OpResponse addStock(String stockName, double price) {
        Stock real = stockDao.getById(stockName);
        if (real != null)   return new OpResponse(1, true, "Stock is already in the list.");

        real = new Stock(stockName, price, true);
        stockDao.save(real);
        return new OpResponse(1, true, "Stock is added.", real);
    }

    public OpResponse removeStock(String stockName) {
        Stock real = stockDao.getById(stockName);
        if (real == null)   return new OpResponse(1, true, "No such stock.");

//        stockDao.update(real);
        stockDao.delete(real);
        // before returning it, set the enabled to false
        real.setEnabled(false);
        return new OpResponse(1, true, "Stock removed.", real);
    }

    public OpResponse buyStock(Security account, String stockName, int quantity) {
//        String stockName = stock.getName();
        Stock real = stockDao.getById(stockName);
        BaseCurrency curPrice = real.getPrice();
        if (account.isEnabled() && real != null && real.isEnabled() && quantity > 0) {
            BaseCurrency cost = new BaseCurrency(real.getPrice().getName(), real.getPrice().getAmount());
            cost.setAmount(cost.getAmount() * quantity / (1 - ChargeConfig.STOCK_INTEREST));
            if (account.getBalance().getAmount() < cost.getAmount()) {
                return new OpResponse(0, false, "Not enough money!");
            }
            else {
                Timestamp date = new Timestamp(System.currentTimeMillis());
                OpResponse response = bankAccountController.withdraw(
                        account, cost, String.format("Buy %d %s", quantity, real), ChargeConfig.STOCK_INTEREST, date);
                BaseCurrency newPrice = averagePrice(account, real, getOldPrice(account, real), quantity);
                int total = account.getOwned().getOrDefault(real, 0) + quantity;
                real.setPrice(newPrice);
                account.getOwned().put(real, total);
                updateUnrealizedProfit(account);
                securityDao.update(account);

                // covert back the price
                real.setPrice(curPrice);
                return new OpResponse(
                        1, true, String.format("Successfully buy %s quantity %d", real, quantity), account);
            }
        }
        return new OpResponse(0, false, "Failed to buy! Error in the request.");
    }

    public OpResponse sellStock(Security account, String stockName, int quantity) {
//        String stockName = stock.getName();
        Stock real = stockDao.getById(stockName);
        BaseCurrency curPrice = real.getPrice();
        if (!account.isEnabled()
                || real == null
                || !real.isEnabled()
                || quantity == 0
                || quantity > account.getOwned().getOrDefault(real, 0)) {
            return new OpResponse(0, false, "Failed to sell! Error in the request.");
        }
        BaseCurrency realPrice = new BaseCurrency(real.getPrice().getName(), real.getPrice().getAmount());
        Timestamp date = new Timestamp(System.currentTimeMillis());
        realPrice.setAmount(realPrice.getAmount() * quantity);
        bankAccountController.deposit(
                account, realPrice, date, String.format("Sell %d %s", quantity, real), ChargeConfig.STOCK_INTEREST);

        HashMap<Stock, Integer> owned = account.getOwned();
        BaseCurrency newPrice = averagePrice(account, real, getOldPrice(account, real), -quantity);
        double oldUnrealizedProfit = account.getUnrealizedProfit().getAmount();
        double oldRealizedProfit = account.getUnrealizedProfit().getAmount();
        real.setPrice(newPrice);
        owned.put(real, owned.get(real) - quantity);
        updateUnrealizedProfit(account);
        // update realized profit
        double newUnrealizedProfit = account.getUnrealizedProfit().getAmount();
        account.getRealizedProfit().setAmount(oldRealizedProfit + oldUnrealizedProfit - newUnrealizedProfit);
        securityDao.update(account);

        // covert back the price
        real.setPrice(curPrice);
        return new OpResponse(1, true, String.format("Sell %d %s", quantity, real));
    }

    private BaseCurrency averagePrice(Security account, Stock real, BaseCurrency oldPrice, int quantity) {
        HashMap<Stock, Integer> owned = account.getOwned();
//        BaseCurrency price = getOldPrice(account, real);

        int amount = owned.getOrDefault(real, 0);
        int total = quantity + amount;
        double oldAmount = oldPrice.getAmount();
        double newAmount = (oldAmount * amount + real.getPrice().getAmount() * quantity) / total;
//        oldPrice.setAmount(newAmount);
        return new BaseCurrency(oldPrice.getName(), newAmount);
    }

    private void updateUnrealizedProfit(Security account) {
        HashMap<Stock, Integer> owned = account.getOwned();
        BaseCurrency unrealizedProfit = account.getUnrealizedProfit();
        double sum = 0;
        for (Stock ownedStock: owned.keySet()) {
            Stock real = stockDao.getById(ownedStock.getName());
            double diff = real.getPrice().getAmount() - ownedStock.getPrice().getAmount();
            int quantity = owned.get(ownedStock);
            sum += diff * quantity;
        }
        unrealizedProfit.setAmount(sum);

    }

    private BaseCurrency getOldPrice(Security account, Stock stock) {
        HashMap<Stock, Integer> owned = account.getOwned();
        if (!owned.containsKey(stock)) {
            return new BaseCurrency("USD", 0);
        }
        for (Stock s: owned.keySet()){
            // compare only name
            if (s.equals(stock)) {
                return s.getPrice();
            }
        }
        return new BaseCurrency("USD", 0);
    }

}
