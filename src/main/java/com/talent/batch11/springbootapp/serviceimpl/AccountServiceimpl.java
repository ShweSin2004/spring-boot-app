package com.talent.batch11.springbootapp.serviceimpl;

import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.model.Transaction;
import com.talent.batch11.springbootapp.model.enumDemo.TransactionType;
import com.talent.batch11.springbootapp.repository.AccountRepository;
import com.talent.batch11.springbootapp.repository.TransactionRepository;
import com.talent.batch11.springbootapp.dto.request.RegisterInfo;
import com.talent.batch11.springbootapp.dto.request.TransferMoneyInfo;
import com.talent.batch11.springbootapp.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.talent.batch11.springbootapp.dto.request.LoginInfo;

import java.util.List;
import java.util.Scanner;

@Service
public class AccountServiceimpl implements AccountService {
    Scanner sc = new Scanner(System.in);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void saveAccount(Account account) {
        System.out.println("Saving Account " + account );
        accountRepository.save(account);

    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    @Override
    public Account findByPhone(String phone_number) {
        return accountRepository.findAccountByPhoneNumber(phone_number);
    }

    @Override
    @Transactional
    public void updateMoney(double updatedMoney, String email) {
        accountRepository.findAccountByEmail(email).setBalance(updatedMoney);
    }

    @Override
    public void deleteAcc(Account account) {
        accountRepository.delete(account);
    }

    @Override
    @Transactional
    public Account logIn(LoginInfo loginInfo) {

        if (loginInfo.getEmail() == null) {
            System.out.println("Account does not exist.");
            return null;
        }
        Account findAccount = findByEmail(loginInfo.getEmail());
        if (findAccount == null){
            throw new RuntimeException("Account not found.");
        }

        if (!findAccount.getPassword().equals(loginInfo.getPassword())) {
            throw new RuntimeException("Incorrect password.");
        }

        System.out.println("Logged in successfully!");
        return findAccount;
    }

    @Override
    @Transactional
    public Account register(RegisterInfo registerInfo) {
        Account signUpAcc = new Account();

        signUpAcc.setName(registerInfo.getName());
        signUpAcc.setEmail(registerInfo.getEmail());
        signUpAcc.setPassword(registerInfo.getPassword());
        signUpAcc.setPhoneNumber(registerInfo.getPhoneNumber());
        signUpAcc.setAddress(registerInfo.getAddress());
        signUpAcc.setRole(registerInfo.getRole());

        signUpAcc.setBalance(0);

        saveAccount(signUpAcc);
        return signUpAcc;
    }

    @Override
    @Transactional
    public void depositMoney(double amount, Account account) {
        double previous_amount = account.getBalance();

        if (amount <= 0) {
            throw new RuntimeException("Invalid amount.");
        }

        account.setBalance(account.getBalance() + amount);
        updateMoney(account.getBalance(), account.getEmail());

        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setPrevious_amount(previous_amount);
        tx.setTransactionType(TransactionType.DEPOSIT_MONEY);
        transactionRepository.save(tx);
    }

    @Override
    @Transactional
    public void withdrawMoney(double amount, Account account) {
        double previous_amount = account.getBalance();

        if (amount <= 0 || amount > account.getBalance()) {
            throw new RuntimeException("Invalid amount.");
        }

        account.setBalance(account.getBalance() - amount);
        updateMoney(account.getBalance(), account.getEmail());

        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setPrevious_amount(previous_amount);
        tx.setTransactionType(TransactionType.WITHDRAW_MONEY);
        transactionRepository.save(tx);
    }

    @Override
    @Transactional
    public void transferMoney(TransferMoneyInfo transferMoneyInfo, Account account) {
        double accountPreviousAmount = account.getBalance();
        double amount = transferMoneyInfo.getAmount();

        Account receiver = accountRepository.findAccountByPhoneNumber(transferMoneyInfo.getReceiver_phone());

        if (receiver == null) {
            System.out.println("Recipient not found.");
            return;
        }

        double receiverPreviousAmount = receiver.getBalance();

        if (!account.getPassword().equals(transferMoneyInfo.getPassword())) {
            throw new RuntimeException("Incorrect password.");
        }

        if (amount <= 0 || amount > account.getBalance()) {
            throw new RuntimeException("Invalid amount.");
        }

        account.setBalance(account.getBalance() - amount);
        updateMoney(account.getBalance(), account.getEmail());
        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setPrevious_amount(accountPreviousAmount);
        tx.setTransactionType(TransactionType.TRANSFER_MONEY);
        transactionRepository.save(tx);

        receiver.setBalance(receiver.getBalance() + amount);
        updateMoney(receiver.getBalance(), receiver.getEmail());
        Transaction tr = new Transaction();
        tr.setAccount(receiver);
        tr.setAmount(amount);
        tr.setPrevious_amount(receiverPreviousAmount);
        tr.setTransactionType(TransactionType.RECEIVE_MONEY);
        transactionRepository.save(tr);
    }

    @Override
    @Transactional
    public void topUp(double amount, Account account) {
        double previous_amount= account.getBalance();

        if (amount <= 0 || amount > account.getBalance()) {
            throw new RuntimeException("Invalid amount.");
        }

        account.setBalance(account.getBalance() - amount);
        updateMoney(account.getBalance(), account.getEmail());

        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setPrevious_amount(previous_amount);
        tx.setTransactionType(TransactionType.TOP_UP);
        transactionRepository.save(tx);
    }

    @Override
    public Account getAccountById(long id) {
        return accountRepository.findAccountById(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


    @Override
    public List<Transaction> getAllTransactionsByAccountId(long accountId) {

        Account account = accountRepository.findAccountById(accountId);
        return  account.getTransactions();
    }


}
