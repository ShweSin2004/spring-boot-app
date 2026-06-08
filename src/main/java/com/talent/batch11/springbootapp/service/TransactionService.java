package com.talent.batch11.springbootapp.service;

import com.talent.batch11.springbootapp.exception.CommonResponse;
import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.model.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
    public void saveTransactions(Transaction transaction);

    public List<Transaction> getAllTransactions();

    public List<Transaction> getAllTransactionsByAccountId(long accountId);

    public ResponseEntity<CommonResponse> getAllTransactionsByAccountIdApi(String authHeader);

}
