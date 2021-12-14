/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:20
 */

package controller;

import dao.AccountDao;
import dao.UserDao;
import model.Account;
import model.OpResponse;
import model.User;

import java.util.List;

public class LoginController {

    private UserDao userDao;
    private AccountDao accountDao;

    public OpResponse getAllUsers() {
        List<User> users = userDao.getAll();
        return new OpResponse(1, true, "Success", users);
    }

    public User login(String username, String password) {
        if (username == null || password == null) {
//            throw new IllegalArgumentException("Invalid input!");
            System.out.println("Invalid input!");
            return null;
        }
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
        if (name == null || address == null || birthday == null || username == null)    return false;
        if (userDao.getById(username) != null || accountDao.getById(username) != null)  return false;

        User user = new User(name, address, birthday, username);
        Account account = new Account(username, password, "User");
        userDao.save(user);
        accountDao.save(account);
        return true;
    }

    public OpResponse updateAccount(String username, String name, String address, String birthday, String password) {
        if (name == null || address == null || birthday == null || username == null) {
            return new OpResponse(0, false, "Invalid request!");
        }
        User user = userDao.getById(username);
        Account account = accountDao.getById(username);
        if (user != null && account != null) {
            user.setName(name);
            user.setAddress(address);
            user.setBirthday(birthday);
            account.setPassword(password);
            userDao.update(user);
            accountDao.update(account);
        }
        return new OpResponse(0, false, "Invalid request!", new Object[]{user, account});
    }

//    public static void main(String[] args) {
//        new LoginController().login("1", "2");
//    }
}
