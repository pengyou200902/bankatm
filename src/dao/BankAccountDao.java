/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package dao;

import model.BankAccount;

import java.sql.SQLException;
import java.util.List;

public class BankAccountDao<T extends BankAccount> implements BaseDao<T, String> {

    @Override
    public boolean save(T account) {
        return false;
    }

    @Override
    public T getById(String username) {
        return null;
    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public boolean update(T account) {
        return false;
    }

    @Override
    public boolean delete(T account) {
        return false;
    }

    @Override
    public void createTable() throws SQLException {
        return;
    }
}
