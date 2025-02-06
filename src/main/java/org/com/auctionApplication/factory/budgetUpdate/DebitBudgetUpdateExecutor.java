package org.com.auctionApplication.factory.budgetUpdate;

import org.com.auctionApplication.model.User;

public class DebitBudgetUpdateExecutor implements BudgetUpdateExecutor {

    @Override
    public void updateBudget(User user, Double amount) {
        user.setUserBudget(user.getUserBudget()-amount);
    }


}
