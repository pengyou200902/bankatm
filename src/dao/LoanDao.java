/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package dao;

import model.Loan;

import java.util.List;

public class LoanDao implements BaseDao<Loan, Integer>{
    private static LoanDao instance = null;

    public static LoanDao getInstance() {
        if (instance == null) {
            instance = new LoanDao();
        }

        return instance;
    }

    private LoanDao() {
        
    }

    @Override
    public boolean save(Loan user) {
        return false;
    }

    @Override
    public Loan getById(Integer loanID) {
        return null;
    }

    @Override
    public List<Loan> getAll() {
        return null;
    }

    @Override
    public boolean update(Loan user) {
        return false;
    }

    @Override
    public boolean delete(Loan user) {
        return false;
    }
}
