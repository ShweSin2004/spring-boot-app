package com.talent.batch11.springbootapp.service;

import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.model.Transaction;

public interface TransactionService {
    public void saveTransactions(Transaction transaction);

    public void viewHistory(Account account);

}
