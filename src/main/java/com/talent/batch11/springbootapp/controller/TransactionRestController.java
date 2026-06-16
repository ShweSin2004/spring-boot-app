package com.talent.batch11.springbootapp.controller;

import com.talent.batch11.springbootapp.exception.CommonResponse;
import com.talent.batch11.springbootapp.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "View your account history")
public class TransactionRestController {
    @Autowired
    private TransactionService transactionService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/history")
    public ResponseEntity<CommonResponse> checkhistory(@RequestHeader("Authorization") String authHeader) {
        logger.info("[[Reached transaction controller.]]");
        return transactionService.getAllTransactionsByAccountIdApi(authHeader);
    }
}
