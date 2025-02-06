package org.com.auctionApplication.factory.budgetUpdate;

import org.com.auctionApplication.enums.TransactionType;

import java.util.HashMap;
import java.util.Map;

public class BudgetUpdateExecutorFactory {

    private final Map<TransactionType, BudgetUpdateExecutor> transactionTypeBudgetUpdateExecutorMap = new HashMap<>();

    public void registerBudgetUpdateExecutor(TransactionType txnType, BudgetUpdateExecutor executor) {
        transactionTypeBudgetUpdateExecutorMap.put(txnType, executor);
    }

    public BudgetUpdateExecutor getBudgetUpdateExecutorFor(TransactionType transactionType) {
        return transactionTypeBudgetUpdateExecutorMap.get(transactionType);
    }

}
