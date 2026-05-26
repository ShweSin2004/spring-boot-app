package com.talent.batch11.springbootapp.serviceimpl;

import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.model.Transaction;
import com.talent.batch11.springbootapp.model.enumDemo.TransactionType;
import com.talent.batch11.springbootapp.repository.AccountRepository;
import com.talent.batch11.springbootapp.repository.TransactionRepository;
import com.talent.batch11.springbootapp.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Transactional
    public Account logIn() {
        System.out.println("Log In\n---------");
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        if (email == null) {
            System.out.println("Account does not exist.");
            return null;
        }
        Account findAccount = findByEmail(email);
        if (findAccount == null){
            System.out.println("Account does not exist");
            return null;
        }

        System.out.print("Enter your password: ");
        String pw = sc.nextLine();

        if (!findAccount.getPassword().equals(pw)) {
            System.out.println("Incorrect password.");
            return null;
        }

        System.out.println("Logged in successfully!");
        return findAccount;
    }

    @Override
    @Transactional
    public Account signUp() {
        Account signUpAcc = new Account();

        System.out.println("Sign up\n----------");
        System.out.print("Enter your name: ");
        signUpAcc.setName(sc.nextLine());

        System.out.print("Enter your email: ");
        signUpAcc.setEmail(sc.nextLine());

        System.out.print("Enter your phone number: ");
        signUpAcc.setPhoneNumber(sc.nextLine());

        System.out.print("Enter your password: ");
        signUpAcc.setPassword(sc.nextLine());

        System.out.print("Enter your address: ");
        signUpAcc.setAddress(sc.nextLine());

        signUpAcc.setBalance(0);

        saveAccount(signUpAcc);
        return signUpAcc;
    }

    @Override
    @Transactional
    public void depositMoney(Account account) {
        double previous_amount = account.getBalance();
        System.out.println("Deposit money\n------------");
        System.out.print("Enter deposit money amount: ");
        double amount = sc.nextDouble();
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
    public void withdrawMoney(Account account) {
        double previous_amount = account.getBalance();
        System.out.println("Withdraw money\n------------");
        System.out.print("Enter withdraw money amount: ");
        double amount = sc.nextDouble();

        while (amount <= 0) {
            System.out.println("Amount invalid");
            System.out.print("Enter withdraw money amount: ");
            amount = sc.nextDouble();
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
    public void transferMoney(Account account) {
        double accountPreviousAmount = account.getBalance();
        System.out.println("Transfer money\n---------------");
        System.out.print("Enter receiver's phone number: ");
        String receiver_phone = sc.nextLine();

        Account receiver = accountRepository.findAccountByPhoneNumber(receiver_phone);

        if (receiver == null) {
            System.out.println("Recipient not found.");
            return;
        }

        double receiverPreviousAmount = receiver.getBalance();

        System.out.print("Enter password: ");
        String pw = sc.nextLine();

        while(!account.getPassword().equals(pw)) {
            System.out.println("Incorrect password.");
            System.out.print("Enter password: ");
            pw = sc.nextLine();
        }

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        while (amount <= 0) {
            System.out.println("Amount invalid");
            System.out.print("Enter withdraw money amount: ");
            amount = sc.nextDouble();
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
    public void topUp(Account account) {
        double previous_amount = account.getBalance();
        System.out.println("Top up\n---------");
        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        while (amount <= 0) {
            System.out.println("Amount invalid");
            System.out.print("Enter withdraw money amount: ");
            amount = sc.nextDouble();
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

}
