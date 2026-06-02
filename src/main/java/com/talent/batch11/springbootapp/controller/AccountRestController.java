package com.talent.batch11.springbootapp.controller;

import com.talent.batch11.springbootapp.dto.request.RegisterInfo;
import com.talent.batch11.springbootapp.dto.request.ServiceRequest;
import com.talent.batch11.springbootapp.dto.request.TransferMoneyInfo;
import com.talent.batch11.springbootapp.dto.response.*;
import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.dto.request.LoginInfo;
import com.talent.batch11.springbootapp.service.AccountService;
import com.talent.batch11.springbootapp.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/login")
    public ResponseEntity loginAccount(@RequestHeader String apiKey, @RequestBody LoginInfo loginInfo, HttpSession session) {

        if (accountService.checkapi(apiKey)) {
            Account account = accountService.logIn(loginInfo);
            session.setAttribute("account", account);
            account.setTransactions(null);
            if (account.getRole().equalsIgnoreCase("Admin")) {
                AdminLogInResponse admin = new AdminLogInResponse();
                admin.setAccountList(accountService.getAllAccounts());
                admin.setTransactions(transactionService.getAllTransactions());
                return ResponseEntity.ok(admin);
            } else {
                UserLogInResponse user = new UserLogInResponse();
                user.setAccount(account);
                return ResponseEntity.ok(account);
            }
        } else {
            return ResponseEntity.ok("API key mismatched.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getAccountByAccountId(@RequestHeader String apiKey, @PathVariable long id){
        if(accountService.checkapi(apiKey)) {
            Account account = accountService.getAccountById(id);
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.ok("API key mismatched.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestHeader String apiKey, @RequestBody RegisterInfo registerInfo, HttpSession session){
        if (accountService.checkapi(apiKey)) {
            UserLogInResponse user = new UserLogInResponse();
            user.setAccount(accountService.register(registerInfo));

            session.setAttribute("account", user.getAccount());

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.ok("API key mismatched.");
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestHeader String apiKey,
                                   @RequestBody ServiceRequest serviceRequest, HttpSession session){
        if (accountService.checkapi(apiKey)) {
            ServiceResponse sr = new ServiceResponse();

            Account account = (Account) session.getAttribute("account");
            accountService.withdrawMoney(serviceRequest.getAmount(), account);

            sr.setAccount(account);
            sr.setBalance(account.getBalance());

            return ResponseEntity.ok(sr);
        } else {
            return ResponseEntity.ok("API key mismatched.");
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestHeader String apiKey,
                                  @RequestBody ServiceRequest serviceRequest, HttpSession session){

        if (accountService.checkapi(apiKey)) {
            ServiceResponse sr = new ServiceResponse();

            Account account = (Account) session.getAttribute("account");
            accountService.depositMoney(serviceRequest.getAmount(), account);

            sr.setAccount(account);
            sr.setBalance(account.getBalance());

            return ResponseEntity.ok(sr);
        } else {
            return ResponseEntity.ok("API key mismatched.");
        }
    }

    @PostMapping("/topup")
    public ResponseEntity topUp(@RequestHeader String apiKey,
                                @RequestBody ServiceRequest serviceRequest, HttpSession session){

        if (accountService.checkapi(apiKey)) {
            ServiceResponse sr = new ServiceResponse();

            Account account = (Account) session.getAttribute("account");
            accountService.topUp(serviceRequest.getAmount(), account);

            sr.setAccount(account);
            sr.setBalance(account.getBalance());

            return ResponseEntity.ok(sr);
        } else {
            return ResponseEntity.ok("API key mismatched.");
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity transfer(@RequestHeader String apiKey,
                                   @RequestBody TransferMoneyInfo transfer, HttpSession session){

        if (accountService.checkapi(apiKey)) {
            TransferResponse transferResponse = new TransferResponse();

            Account account = (Account) session.getAttribute("account");

            accountService.transferMoney(transfer, account);

            transferResponse.setAmount(transfer.getAmount());

            return ResponseEntity.ok(transferResponse);
        } else {
            return ResponseEntity.ok("API key mismatched.");
        }
    }

    @PostMapping("/checkaccount")
    public ResponseEntity checkAccount(@RequestHeader String apiKey,
                                       HttpSession session) {
        if (accountService.checkapi(apiKey)) {
            Account account = (Account) session.getAttribute("account");
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.ok("API key mismatched.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader String apiKey, HttpSession session) {

        if(accountService.checkapi(apiKey)) {
            session.invalidate();
            return ResponseEntity.ok("Logged out successfully.");
        } else {
            return ResponseEntity.ok("API key mismatched.");
        }
    }


}
