package com.talent.batch11.springbootapp.account;

import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.repository.AccountRepository;
import com.talent.batch11.springbootapp.serviceimpl.AccountServiceimpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Commit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountServiceimplTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountServiceimpl accountService;

    @Test
    @Commit
    void saveAccount() {
        Account account = new Account();
        account.setName("myat");
        account.setEmail("myat@gmail.com");
        account.setPhoneNumber("09222333444");
        account.setPassword("1234");
        account.setAddress("Ygn");
        accountService.saveAccount(account);

        System.out.println(account);

    }

    @Test
    void findByEmail(){
         assertNotNull(accountRepository.findAccountByEmail("myat@gmail.com"));
    }

    @Test
    void findByPhoneNumber() {
        assertNotNull(accountRepository.findAccountByPhoneNumber("09222333444"));
    }

    @Test
    void updateMoney() {
        double updatedMoney = 45000;
        accountRepository.findAccountByEmail("myat@gmail.com").setBalance(updatedMoney);
    }

    @Test
    void logIn(){
        String email = "myat@gmail.com";
        String password = "1234";

        Account logIn = accountRepository.findAccountByEmail(email);

        if (logIn == null) {
            System.out.println("Account not found.");
            return;
        }

        if (!logIn.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
            return;
        }

        System.out.println("Logged in successfully!");
    }

    @Test
    @Commit
    void signUp() {
        Account account = new Account();
        account.setName("shwe");
        account.setEmail("shwe@gmail.com");
        account.setPhoneNumber("09111222333");
        account.setPassword("1234");
        account.setAddress("Ygn");
        System.out.println("Signed up successfully");
        accountRepository.save(account);
    }

    @Test
    void depositMoney() {
        double amount = 20000;
        Account depositMoneyAcc = accountRepository.findAccountByEmail("shwe@gmail.com");

        if(depositMoneyAcc == null) {
            System.out.println("Account not found.");
            return;
        }

        depositMoneyAcc.setBalance(depositMoneyAcc.getBalance() + amount);
    }

    @Test
    void withdrawMoney() {
        double amount = 2000;
        Account withdrawMoneyAcc = accountRepository.findAccountByEmail("shwe@gmail.com");

        if(withdrawMoneyAcc == null) {
            System.out.println("Account not found.");
            return;
        }

        withdrawMoneyAcc.setBalance(withdrawMoneyAcc.getBalance() - amount);
    }

    @Test
    void transferMoney() {
        Account owner = accountRepository.findAccountByEmail("shwe@gmail.com");
        Account receiver = accountRepository.findAccountByEmail("myat@gmail.com");

        double amount = 10000;

        if (owner == null || receiver == null) {
            System.out.println("Account not found.");
        }

        owner.setBalance(owner.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
    }

    @Test
    void topUp() {
        Account owner = accountRepository.findAccountByEmail("shwe@gmail.com");

        double amount = 1000;

        if (owner == null) {
            System.out.println("Account not found.");
        }

        owner.setBalance(owner.getBalance() - amount);
    }


}
