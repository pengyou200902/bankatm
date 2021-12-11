/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package dao;

import model.Checking;

import java.util.List;

public class CheckingDao extends BankAccountDao<Checking> {
    private static CheckingDao instance = null;

    public static CheckingDao getInstance() {
        if (instance == null) {
            instance = new CheckingDao();
        }

        return instance;
    }

    private CheckingDao() {
        
    }

    @Override
    public boolean save(Checking account) {
        return false;
    }

    @Override
    public Checking getById(String username) {
        return null;
    }

    @Override
    public List<Checking> getAll() {
        return null;
    }

    @Override
    public boolean update(Checking account) {
        return false;
    }

    @Override
    public boolean delete(Checking account) {
        return false;
    }
}
