package org.com.auctionApplication.factory.budgetUpdate;

import org.com.auctionApplication.model.User;

public interface BudgetUpdateExecutor {

    void updateBudget(User user, Double amount);

}
