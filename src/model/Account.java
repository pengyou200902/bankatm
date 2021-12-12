/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:14
 */

package model;

import java.util.Objects;

public class Account {
    private String username; // pk
    private String password;
    private String type;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return username.equals(account.username) && password.equals(account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, type);
    }
}
