package com.talent.batch11.springbootapp.service;

import com.talent.batch11.springbootapp.dto.request.LoginInfo;
import com.talent.batch11.springbootapp.dto.request.RegisterInfo;
import com.talent.batch11.springbootapp.dto.request.TransferMoneyInfo;
import com.talent.batch11.springbootapp.dto.requestApi.ServiceRequestApi;
import com.talent.batch11.springbootapp.dto.requestApi.TransferMoneyInfoApi;
import com.talent.batch11.springbootapp.exception.CommonResponse;
import com.talent.batch11.springbootapp.model.Account;
import java.util.List;

import com.talent.batch11.springbootapp.model.Transaction;
import com.talent.batch11.springbootapp.dto.requestApi.RegisterInfoApi;
import com.talent.batch11.springbootapp.dto.requestApi.LoginInfoApi;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    public void saveAccount(Account account);
    public Account findByEmail(String email);
    public Account findByPhone(String phone_number);
    public void updateMoney(double updatedMoney, String email);
    public void deleteAcc(Account account);

    public Account logIn(LoginInfo loginInfoApi);
    public Account register(RegisterInfo registerInfo);

    public void depositMoney(double amount, Account account);
    public void withdrawMoney(double amount, Account account);
    public void transferMoney(TransferMoneyInfo transferMoneyInfo, Account account);
    public void topUp(double amount, Account account);

    public ResponseEntity<CommonResponse> getAccountInfo(String authHeader);

    public Account getAccountById(long id);

    public List<Account> getAllAccounts();

    public List<Transaction> getAllTransactionsByAccountId(long accountId);

    public ResponseEntity<CommonResponse> handleLoginRequest(LoginInfoApi loginInfoApi);
    public ResponseEntity<CommonResponse> depositMoneyApi(String authHeader, ServiceRequestApi serviceRequestApi);
    public ResponseEntity<CommonResponse> registerApi(RegisterInfoApi registerInfo);
    public ResponseEntity<CommonResponse> withdrawMoneyApi(String authHeader, ServiceRequestApi serviceRequestApi);
    public ResponseEntity<CommonResponse> transferMoneyApi(String authHeader, TransferMoneyInfoApi transferMoneyInfoApi);
    public ResponseEntity<CommonResponse> topUpApi(String authHeader, ServiceRequestApi serviceRequestApi);
}
