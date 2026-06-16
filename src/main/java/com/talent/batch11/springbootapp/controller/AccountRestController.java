package com.talent.batch11.springbootapp.controller;

import com.talent.batch11.springbootapp.dto.requestApi.*;
import com.talent.batch11.springbootapp.exception.CommonResponse;
import com.talent.batch11.springbootapp.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account", description = "Log in/register to be able to use the other services")
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

    @PostMapping("/registration")
    public ResponseEntity<CommonResponse> register(@Valid @RequestBody RegisterInfoApi registerInfo){
        logger.info("[[Reached register controller]]");
        return accountService.registerApi(registerInfo);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(@RequestHeader("Authorization") String authHeader) {
        return accountService.logoutApi(authHeader);
    }
}