package org.com.auctionApplication.model;

import org.com.auctionApplication.enums.UserRole;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class User {
    String userName;
    Set<UserRole> roles;
    Double userBudget;

    /**
     * Constructor with provided balance
     *
     * @param userName
     * @param userRole
     * @param userBudget
     */
    public User(String userName, Double userBudget, UserRole userRole) {
        this.userName = userName;
        roles = new HashSet<>(Arrays.asList(userRole));
        this.userBudget = userBudget;
    }

    /**
     * constructor with default balance
     *
     * @param userName
     * @param userRole
     */
    public User(String userName, UserRole userRole) {
        this(userName, 1000D, userRole);
    }

    public String getUserName() {
        return userName;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public Double getUserBudget() {
        return userBudget;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public void setUserBudget(Double userBudget) {
        this.userBudget = userBudget;
    }

}
