/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:14
 */

package dao;

import model.Account;

import java.util.List;

public class AccountDao implements BaseDao<Account, String> {
    private static AccountDao instance = null;

    public static AccountDao getInstance() {
        if (instance == null) {
            instance = new AccountDao();
        }

        return instance;
    }

    private AccountDao() {
        
    }

    @Override
    public boolean save(Account user) {
        return false;
    }

    @Override
    public Account getById(String username) {
        return null;
    }

    @Override
    public List<Account> getAll() {
        return null;
    }

    @Override
    public boolean update(Account user) {
        return false;
    }

    @Override
    public boolean delete(Account user) {
        return false;
    }
}
