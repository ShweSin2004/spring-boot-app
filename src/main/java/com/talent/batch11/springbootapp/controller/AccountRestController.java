package com.talent.batch11.springbootapp.controller;

import com.talent.batch11.springbootapp.dto.request.RegisterInfo;
import com.talent.batch11.springbootapp.dto.request.ServiceRequest;
import com.talent.batch11.springbootapp.dto.request.TransferMoneyInfo;
import com.talent.batch11.springbootapp.dto.response.*;
import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.dto.request.LoginInfo;
import com.talent.batch11.springbootapp.service.AccountService;
import com.talent.batch11.springbootapp.service.TokenService;
import com.talent.batch11.springbootapp.service.TransactionService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/account")
public class AccountRestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity loginAccount(@RequestBody LoginInfo loginInfo) {
        logger.info("[[Reached login controller]]");
        return accountService.handleLoginRequest(loginInfo);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAccountByAccountId(@PathVariable long id){
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterInfo registerInfo){
        logger.info("[[Reached register controller]]");
        return ResponseEntity.ok(accountService.register(registerInfo));
    }

    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestHeader("Authorization") String authHeader, @RequestBody ServiceRequest serviceRequest){
        try {
            String token = authHeader.substring(7);
            if (token != null) {
                Account account = tokenService.getAccountByToken(token);
                return accountService.withdrawMoney(serviceRequest.getAmount(), account);
            }
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token.");
        }
        return ResponseEntity.ok("Withdraw failed");
    }

    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestHeader("Authorization") String authHeader, @RequestBody ServiceRequest serviceRequest){
        try {
            String token = authHeader.substring(7);
            if (token != null) {
                Account account = tokenService.getAccountByToken(token);
                return accountService.depositMoney(serviceRequest.getAmount(), account);
            }
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token.");
        }
            return ResponseEntity.ok("Deposit failed");
    }

    @PostMapping("/topup")
    public ResponseEntity topUp(@RequestHeader("Authorization") String authHeader, @RequestBody ServiceRequest serviceRequest){

        try {
            String token = authHeader.substring(7);
            if (token != null) {
                Account account = tokenService.getAccountByToken(token);
                return accountService.topUp(serviceRequest.getAmount(), account);
            }
        } catch (JwtException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token.");
        }
        return ResponseEntity.ok("Top up failed");
    }

    @PostMapping("/transfer")
    public ResponseEntity transfer(@RequestHeader("Authorization") String authHeader, @RequestBody TransferMoneyInfo transfer){
        String token = authHeader.substring(7);
        try {
            logger.info("Reached ransfer controller");
            if (token != null) {
                Account account = tokenService.getAccountByToken(token);
                return accountService.transferMoney(transfer, account);
            }
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token.");
        }
        return ResponseEntity.ok("Transfer failed.");
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader String apiKey, HttpSession session) {
        return ResponseEntity.ok("Unfinished");
    }
}