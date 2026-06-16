package com.talent.batch11.springbootapp.controller;

import com.talent.batch11.springbootapp.dto.requestApi.ServiceRequestApi;
import com.talent.batch11.springbootapp.dto.requestApi.TransferMoneyInfoApi;
import com.talent.batch11.springbootapp.exception.CommonResponse;
import com.talent.batch11.springbootapp.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "ATM Services", description = "Here are our services")
public class ServiceRestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @PostMapping("/withdrawal")
    public ResponseEntity<CommonResponse> withdraw(@RequestHeader("Authorization") String authHeader,
                                                   @RequestBody ServiceRequestApi serviceRequestApi){
        logger.info("[[Reached withdraw controller]]");
        return accountService.withdrawMoneyApi(authHeader, serviceRequestApi);
    }

    @PostMapping("/deposits")
    public ResponseEntity<CommonResponse> deposit(@RequestHeader("Authorization") String authHeader, @RequestBody ServiceRequestApi serviceRequestApi){
        logger.info("[[Reached deposit controller]]");
        return accountService.depositMoneyApi(authHeader, serviceRequestApi);
    }

    @PostMapping("/topup")
    public ResponseEntity<CommonResponse> topUp(@RequestHeader("Authorization") String authHeader, @RequestBody ServiceRequestApi serviceRequestApi){
        logger.info("[[Reached topup controller]]");
        return accountService.topUpApi(authHeader, serviceRequestApi);
    }

    @PostMapping("/transfers")
    public ResponseEntity<CommonResponse> transfer(@RequestHeader("Authorization") String authHeader, @RequestBody TransferMoneyInfoApi transferMoneyInfo){
        logger.info("[[Reached transfer controller]]");
        return accountService.transferMoneyApi(authHeader, transferMoneyInfo);
    }
}
