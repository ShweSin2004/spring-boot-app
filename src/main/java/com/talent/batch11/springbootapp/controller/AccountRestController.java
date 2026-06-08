package com.talent.batch11.springbootapp.controller;

import com.talent.batch11.springbootapp.dto.requestApi.*;
import com.talent.batch11.springbootapp.exception.CommonResponse;
import com.talent.batch11.springbootapp.service.AccountService;
import com.talent.batch11.springbootapp.service.TokenService;
import com.talent.batch11.springbootapp.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@RestController
@RequestMapping("/account")
public class AccountRestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> loginAccount(@Valid @RequestBody LoginInfoApi loginInfoApi) {
        logger.info("[[Reached login controller]]");
        return accountService.handleLoginRequest(loginInfoApi);
    }

    @GetMapping("/")
    public ResponseEntity<CommonResponse> getAccountInfo(@RequestHeader("Authorization") String authHeader){
        logger.info("[[Reached get account info controller]]");
        return accountService.getAccountInfo(authHeader);
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> register(@Valid @RequestBody RegisterInfoApi registerInfo){
        logger.info("[[Reached register controller]]");
        return accountService.registerApi(registerInfo);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<CommonResponse> withdraw(@RequestHeader("Authorization") String authHeader,
                                   @RequestBody ServiceRequestApi serviceRequestApi){
        logger.info("[[Reached withdraw controller]]");
        return accountService.withdrawMoneyApi(authHeader, serviceRequestApi);
    }

    @PostMapping("/deposit")
    public ResponseEntity<CommonResponse> deposit(@RequestHeader("Authorization") String authHeader, @RequestBody ServiceRequestApi serviceRequestApi){
        logger.info("[[Reached deposit controller]]");
        return accountService.depositMoneyApi(authHeader, serviceRequestApi);
    }

    @PostMapping("/topup")
    public ResponseEntity<CommonResponse> topUp(@RequestHeader("Authorization") String authHeader, @RequestBody ServiceRequestApi serviceRequestApi){
        logger.info("[[Reached topup controller]]");
        return accountService.topUpApi(authHeader, serviceRequestApi);
    }

    @PostMapping("/transfer")
    public ResponseEntity<CommonResponse> transfer(@RequestHeader("Authorization") String authHeader, @RequestBody TransferMoneyInfoApi transferMoneyInfo){
        logger.info("[[Reached transfer controller]]");
        return accountService.transferMoneyApi(authHeader, transferMoneyInfo);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader String apiKey, HttpSession session) {
        return ResponseEntity.ok("Unfinished");
    }
}