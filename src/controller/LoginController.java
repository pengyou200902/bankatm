/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package controller;

import dao.AccountDao;
import dao.UserDao;
import model.Account;
import model.User;

public class LoginController {

    private UserDao userDao;
    private AccountDao accountDao;

    public User login(String username, String password) {
        Account account = accountDao.getById(username);
        if (account != null) {
            Account loginAccount = new Account(username, password);
            if (account.equals(loginAccount)) {
                return userDao.getById(username);
            }
        }
        return null;
    }


    public boolean signUp(String name, String address, String birthday, String username, String password) {
        if (userDao.getById(username) != null || accountDao.getById(username) != null)  return false;

        User user = new User(name, address, birthday, username);
        Account account = new Account(username, password, "User");
        userDao.save(user);
        accountDao.save(account);
        return true;
    }
}
