package com.talent.batch11.springbootapp.serviceimpl;

import com.talent.batch11.springbootapp.exception.CommonResponse;
import com.talent.batch11.springbootapp.exception.ResponseUtils;
import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.model.Transaction;
import com.talent.batch11.springbootapp.repository.AccountRepository;
import com.talent.batch11.springbootapp.repository.TransactionRepository;
import com.talent.batch11.springbootapp.service.TokenService;
import com.talent.batch11.springbootapp.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceimpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TokenService tokenService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountId(long accountId) {

        Account account = accountRepository.findAccountById(accountId);
        return  account.getTransactions();
    }

    @Override
    public ResponseEntity<CommonResponse> getAllTransactionsByAccountIdApi(String authHeader) {
        logger.info("[[Reached get trasactions method inside Account Service Impl.]]");

        String apiName = "/account/checkhistory";

        String token = authHeader.substring(7);
        Account account = tokenService.getAccountByToken(token);

        if (account == null) {
            throw new NullPointerException();
        }

        List<Transaction> transactions = account.getTransactions();

        logger.info("[[Get transaction method completed.]]");
        return  ResponseUtils.makeCommonResponse(apiName, HttpStatus.OK, transactions, Boolean.TRUE,
                "Transaction history");
    }
}
