/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:14
 */

package dao;

import model.User;

import java.util.List;

public class UserDao implements BaseDao<User, String> {

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public User getById(String username) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

}
