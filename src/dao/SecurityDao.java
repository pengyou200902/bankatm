/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:15
 */

package dao;


import model.Security;

import java.util.List;

public class SecurityDao implements BaseDao<Security, String> {
    @Override
    public boolean save(Security model) {
        return false;
    }

    @Override
    public Security getById(String id) {
        return null;
    }

    @Override
    public List<Security> getAll() {
        return null;
    }

    @Override
    public boolean update(Security model) {
        return false;
    }

    @Override
    public boolean delete(Security model) {
        return false;
    }
}
