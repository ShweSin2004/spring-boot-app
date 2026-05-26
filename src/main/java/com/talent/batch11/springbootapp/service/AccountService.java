package com.talent.batch11.springbootapp.service;

import com.talent.batch11.springbootapp.model.Account;

public interface AccountService {
    public void saveAccount(Account account);
    public Account findByEmail(String email);
    public Account findByPhone(String phone_number);
    public void updateMoney(double updatedMoney, String email);


    public Account logIn();
    public Account signUp();

    public void depositMoney(Account account);
    public void withdrawMoney(Account account);
    public void transferMoney(Account account);
    public void topUp(Account account);

}
