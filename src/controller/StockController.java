/**
 * @author You Peng
 * @date 12/11/2021 2:23 PM
 */


package controller;

//import dao.DaoFactory;
import dao.StockDao;
import model.OpResponse;
import model.Security;
import model.Stock;

public class StockController {
    private final StockDao stockDao;

    public StockController() {
        stockDao = StockDao.getInstance();
    }

    public OpResponse buyStock(Security account, Stock stock) {
        // TODO:
        return null;
    }

    public OpResponse sellStock(Security account, Stock stock) {
        // TODO:
        return null;
    }

}
