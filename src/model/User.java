/**
 * @Author Friende.Peng_You
 * @Date 2021-12-07 17:14
 */

package model;

import java.util.Objects;

public class User {
    private String name;
    private String address;
    private String birthday;
    private String username;

    public User(String name, String address, String birthday, String username) {
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) && address.equals(user.address) && birthday.equals(user.birthday) && username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, birthday, username);
    }
}
