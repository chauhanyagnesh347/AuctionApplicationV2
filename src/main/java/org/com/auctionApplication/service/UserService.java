package org.com.auctionApplication.service;

import org.com.auctionApplication.dao.UserDao;
import org.com.auctionApplication.enums.UserRole;
import org.com.auctionApplication.factory.budgetUpdate.BudgetUpdateExecutorFactory;
import org.com.auctionApplication.factory.budgetUpdate.CreditBudgetUpdateExecutor;
import org.com.auctionApplication.factory.budgetUpdate.DebitBudgetUpdateExecutor;
import org.com.auctionApplication.model.User;

import static org.com.auctionApplication.enums.TransactionType.CREDIT;
import static org.com.auctionApplication.enums.TransactionType.DEBIT;

public class UserService {

    private UserDao userDao;
    private BudgetUpdateExecutorFactory budgetUpdateExecutorFactory;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
        budgetUpdateExecutorFactory = new BudgetUpdateExecutorFactory();
        budgetUpdateExecutorFactory.registerBudgetUpdateExecutor(CREDIT, new CreditBudgetUpdateExecutor());
        budgetUpdateExecutorFactory.registerBudgetUpdateExecutor(DEBIT, new DebitBudgetUpdateExecutor());
    }

    public void createUser(String userName, UserRole userRole) {
        User user = new User(userName, userRole);
        userDao.insertUser(user);
    }

    public void createUser(String userName, Double userBudget, UserRole userRole) {
        User user = new User(userName, userBudget, userRole);
        userDao.insertUser(user);
    }

    public User fetchUserDetails(String userName) {
        return userDao.findUserByName(userName);
    }

    public void creditBudget(User user, Double amount) {
        budgetUpdateExecutorFactory.getBudgetUpdateExecutorFor(CREDIT).updateBudget(user, amount);
    }

    public void debitBudget(User user, Double amount) {
        budgetUpdateExecutorFactory.getBudgetUpdateExecutorFor(DEBIT).updateBudget(user, amount);
    }

    public Boolean authenticateUser(String userName) {
        userDao.findUserByName(userName);
        return true;
    }

    public Boolean authenticateUserForRole(String userName, UserRole userRole) {
        User user = userDao.findUserByName(userName);
        return user.getRoles().contains(userRole);
    }


}
