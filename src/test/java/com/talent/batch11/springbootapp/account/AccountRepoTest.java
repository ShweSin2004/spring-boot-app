package com.talent.batch11.springbootapp.account;

import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Commit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class AccountRepoTest {
    @Autowired
    private AccountRepository accountRepository;

//    @Test
//    @Commit
    void saveAccount() {
        Account account = new Account();
        account.setId(3);
        account.setName("Sin");
        account.setEmail("sin@gmail.com");
        account.setPhoneNumber("09222333444");
        accountRepository.save(account);
    }

//    @Test
    void findbyEmail() {
        Account foundAccount = accountRepository.findAccountByEmail("myat@gmail.com");
        assertNotNull(foundAccount);
    }

    @Test
    void findByPhoneNumber() {
        Account foundAccount = accountRepository.findAccountByPhoneNumber("09222333444");
        assertNotNull(foundAccount);
    }
}
