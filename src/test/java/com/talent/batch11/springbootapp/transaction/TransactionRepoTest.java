package com.talent.batch11.springbootapp.transaction;

import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.model.Transaction;
import com.talent.batch11.springbootapp.model.enumDemo.TransactionType;
import com.talent.batch11.springbootapp.repository.AccountRepository;
import com.talent.batch11.springbootapp.repository.TransactionRepository;
import jakarta.persistence.Access;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Commit;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class TransactionRepoTest {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Commit
    void saveTransaction() {
        Transaction transaction = new Transaction();
        Account  account = accountRepository.findAccountByEmail("myat@gmail.com");

        transaction.setTransactionType(TransactionType.DEPOSIT_MONEY);
        transaction.setAmount(24500);
        transaction.setPrevious_amount(0);
        account.setBalance(account.getBalance() + 24500);
        transactionRepository.save(transaction);
    }
}
