package com.talent.batch11.springbootapp.service;

import com.talent.batch11.springbootapp.model.Account;
import java.util.List;

import com.talent.batch11.springbootapp.model.Transaction;
import com.talent.batch11.springbootapp.dto.request.RegisterInfo;
import com.talent.batch11.springbootapp.dto.request.LoginInfo;
import com.talent.batch11.springbootapp.dto.request.TransferMoneyInfo;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    public void saveAccount(Account account);
    public Account findByEmail(String email);
    public Account findByPhone(String phone_number);
    public void updateMoney(double updatedMoney, String email);
    public void deleteAcc(Account account);

    public Account logIn(LoginInfo loginInfo);
    public Account register(RegisterInfo registerInfo);

    public ResponseEntity depositMoney(double amount, Account account);
    public ResponseEntity withdrawMoney(double amount, Account account);
    public ResponseEntity transferMoney(TransferMoneyInfo transferMoneyInfo, Account account);
    public ResponseEntity topUp(double amount, Account account);

    public Account getAccountById(long id);

    public List<Account> getAllAccounts();

    public List<Transaction> getAllTransactionsByAccountId(long accountId);

    public ResponseEntity handleLoginRequest(LoginInfo loginInfo);

}
