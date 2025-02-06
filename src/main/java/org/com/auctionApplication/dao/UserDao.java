package org.com.auctionApplication.dao;

import org.com.auctionApplication.exception.DataAccessException;
import org.com.auctionApplication.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class UserDao {

    private final List<User> userData = new ArrayList<>();
    private final Map<String, User> userNameIndexedData = new HashMap<>();

    public void initUserData() {
        userData.clear();
        userNameIndexedData.clear();
    }

    public void insertUser(User user) {
        userData.add(user);
        userNameIndexedData.put(user.getUserName(), user);
    }

    public User findUserByName(String userName) {
        if (userNameIndexedData.containsKey(userName)) {
            return userNameIndexedData.get(userName);
        } else {
            throw new DataAccessException(format("User: %s not found.", userName));
        }
    }


}
