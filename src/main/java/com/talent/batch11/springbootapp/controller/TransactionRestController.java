package com.talent.batch11.springbootapp.controller;

import com.talent.batch11.springbootapp.dto.response.TransactionResponse;
import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class TransactionRestController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/checkhistory")
    public ResponseEntity checkhistory(@RequestHeader String apiKey, HttpSession session) {

        if (accountService.checkapi(apiKey)) {
            Account account = (Account) session.getAttribute("account");
            TransactionResponse tx = new TransactionResponse();

            tx.setTransactionList(accountService.getAllTransactionsByAccountId(account.getId()));

            return ResponseEntity.ok(tx);
        } else {
            return ResponseEntity.ok("API key mismatched.");
        }
    }
}
