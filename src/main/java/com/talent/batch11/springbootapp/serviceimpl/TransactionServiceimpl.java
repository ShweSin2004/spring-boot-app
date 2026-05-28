package com.talent.batch11.springbootapp.serviceimpl;

import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.model.Transaction;
import com.talent.batch11.springbootapp.repository.TransactionRepository;
import com.talent.batch11.springbootapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void viewAllHistory(Account account) {
        for (int i = 0; i < account.getTransactions().size(); i++) {
            Transaction tx = account.getTransactions().get(i);
            System.out.printf(
                    "ID: %d | Date: %s | Type: %s | Amount: %.2f | Previous Balance: %.2f%n",
                    tx.getId(), tx.getCreatedAt(), tx.getTransactionType(), tx.getAmount(), tx.getPrevious_amount()
            );
        }
    }
}
