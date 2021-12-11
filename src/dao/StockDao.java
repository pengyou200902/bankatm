/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package dao;

import model.Stock;
import java.util.List;

public class StockDao implements BaseDao<Stock, String>{
    private static StockDao instance = null;

    public static StockDao getInstance() {
        if (instance == null) {
            instance = new StockDao();
        }

        return instance;
    }

    private StockDao() {
        
    }

    @Override
    public boolean save(Stock stock) {
        return false;
    }

    @Override
    public Stock getById(String id) {
        return null;
    }

    @Override
    public List<Stock> getAll() {
        return null;
    }

    @Override
    public boolean update(Stock stock) {
        return false;
    }

    @Override
    public boolean delete(Stock stock) {
        return false;
    }
}
