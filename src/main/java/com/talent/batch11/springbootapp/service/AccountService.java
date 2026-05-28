package com.talent.batch11.springbootapp.service;

import com.talent.batch11.springbootapp.model.Account;
import java.util.List;

import com.talent.batch11.springbootapp.model.Transaction;
import com.talent.batch11.springbootapp.request.RegisterInfo;
import com.talent.batch11.springbootapp.request.LoginInfo;
import com.talent.batch11.springbootapp.request.TransferMoneyInfo;

public interface AccountService {
    public void saveAccount(Account account);
    public Account findByEmail(String email);
    public Account findByPhone(String phone_number);
    public void updateMoney(double updatedMoney, String email);


    public Account logIn(LoginInfo loginInfo);
    public Account register(RegisterInfo registerInfo);

    public void depositMoney(double amount, Account account);
    public void withdrawMoney(double amount, Account account);
    public void transferMoney(TransferMoneyInfo transferMoneyInfo, Account account);
    public void topUp(double amount, Account account);

    public List<Account> getAllAccounts();

    public List<Transaction> getAllTransactionsByAccountId(long accountId);
}
