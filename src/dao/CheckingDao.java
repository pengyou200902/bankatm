/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package dao;

import model.Checking;

import java.util.List;

public class CheckingDao implements BaseDao<Checking, String> {

    @Override
    public boolean save(Checking model) {
        return false;
    }

    @Override
    public Checking getById(String id) {
        return null;
    }

    @Override
    public List<Checking> getAll() {
        return null;
    }

    @Override
    public boolean update(Checking model) {
        return false;
    }

    @Override
    public boolean delete(Checking model) {
        return false;
    }
}
