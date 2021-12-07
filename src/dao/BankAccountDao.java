/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package dao;

import model.BankAccount;
import java.util.List;

public class BankAccountDao implements BaseDao<BankAccount, String> {

    @Override
    public boolean save(BankAccount user) {
        return false;
    }

    @Override
    public BankAccount getById(String username) {
        return null;
    }

    @Override
    public List<BankAccount> getAll() {
        return null;
    }

    @Override
    public boolean update(BankAccount user) {
        return false;
    }

    @Override
    public boolean delete(BankAccount user) {
        return false;
    }
}
