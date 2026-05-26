package com.talent.batch11.springbootapp.serviceimpl;

import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.model.Transaction;
import com.talent.batch11.springbootapp.repository.TransactionRepository;
import com.talent.batch11.springbootapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceimpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransactions(Transaction transaction) {
        System.out.println("Saving transaction." + transaction);
        transactionRepository.save(transaction);
    }

    @Override
    public void viewHistory(Account account) {
        transactionRepository.getAllByAccountId(account.getId());
    }
}
