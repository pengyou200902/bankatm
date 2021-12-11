/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package dao;

import model.Saving;

import java.util.List;

public class SavingDao implements BaseDao<Saving, String> {
    private static SavingDao instance = null;

    public static SavingDao getInstance() {
        if (instance == null) {
            instance = new SavingDao();
        }

        return instance;
    }

    private SavingDao() {
        
    }

    @Override
    public boolean save(Saving model) {
        return false;
    }

    @Override
    public Saving getById(String id) {
        return null;
    }

    @Override
    public List<Saving> getAll() {
        return null;
    }

    @Override
    public boolean update(Saving model) {
        return false;
    }

    @Override
    public boolean delete(Saving model) {
        return false;
    }
}
