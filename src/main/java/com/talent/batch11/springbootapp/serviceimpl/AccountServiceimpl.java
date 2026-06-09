package com.talent.batch11.springbootapp.serviceimpl;

import com.talent.batch11.springbootapp.dto.requestApi.LoginInfoApi;
import com.talent.batch11.springbootapp.dto.requestApi.RegisterInfoApi;
import com.talent.batch11.springbootapp.dto.requestApi.ServiceRequestApi;
import com.talent.batch11.springbootapp.exception.CommonResponse;
import com.talent.batch11.springbootapp.exception.ResponseUtils;
import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.model.Transaction;
import com.talent.batch11.springbootapp.model.enumDemo.TransactionType;
import com.talent.batch11.springbootapp.repository.AccountRepository;
import com.talent.batch11.springbootapp.repository.TransactionRepository;
import com.talent.batch11.springbootapp.dto.request.RegisterInfo;
import com.talent.batch11.springbootapp.dto.request.TransferMoneyInfo;
import com.talent.batch11.springbootapp.dto.requestApi.TransferMoneyInfoApi;
import com.talent.batch11.springbootapp.service.AccountService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.talent.batch11.springbootapp.dto.request.LoginInfo;
import org.slf4j.Logger;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceimpl implements AccountService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    String apiName = "";

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TokenServiceImpl tokenService;

    @Override
    @Transactional
    public void saveAccount(Account account) {
        System.out.println("Saving Account " + account );
        accountRepository.save(account);

    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    @Override
    public Account findByPhone(String phone_number) {
        return accountRepository.findAccountByPhoneNumber(phone_number);
    }

    @Override
    @Transactional
    public void updateMoney(double updatedMoney, String email) {
        accountRepository.findAccountByEmail(email).setBalance(updatedMoney);
    }

    @Override
    public void deleteAcc(Account account) {
        accountRepository.delete(account);
    }

    @Override
    @Transactional
    public Account logIn(LoginInfo loginInfo) {

        if (loginInfo.getEmail() == null) {
            System.out.println("Account does not exist.");
            return null;
        }
        Account findAccount = findByEmail(loginInfo.getEmail());
        if (findAccount == null){
            throw new RuntimeException("Account not found.");
        }

        if (!findAccount.getPassword().equals(loginInfo.getPassword())) {
            throw new RuntimeException("Incorrect password.");
        }

        System.out.println("Logged in successfully!");
        return findAccount;
    }
    @Override
    @Transactional
    public Account register(RegisterInfo registerInfo) {
        logger.info("[[Reached register method inside Account Service Impl.]]");
        Account signUpAcc = new Account();

        signUpAcc.setName(registerInfo.getName());
        signUpAcc.setEmail(registerInfo.getEmail());
        signUpAcc.setPassword(registerInfo.getPassword());
        signUpAcc.setPhoneNumber(registerInfo.getPhoneNumber());
        signUpAcc.setAddress(registerInfo.getAddress());
        signUpAcc.setRole(registerInfo.getRole());

        signUpAcc.setBalance(0);
        saveAccount(signUpAcc);

        logger.info("[[Finished register method.]]");

        return signUpAcc;
    }

    @Override
    @Transactional
    public void depositMoney(double amount, Account account) {
        logger.info("[[Reached deposit method inside Account Service Impl.]]");
        double previous_amount = account.getBalance();

        if (amount <= 0) {
            throw new RuntimeException("Invalid amount.");
        }

        account.setBalance(account.getBalance() + amount);
        updateMoney(account.getBalance(), account.getEmail());

        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setPrevious_amount(previous_amount);
        tx.setTransactionType(TransactionType.DEPOSIT_MONEY);
        transactionRepository.save(tx);
    }

    @Override
    @Transactional
    public void withdrawMoney(double amount, Account account) {
        logger.info("[[Reached withdraw method inside Account Service Impl.]]");
        double previous_amount = account.getBalance();

        if (amount <= 0 || amount > account.getBalance()) {
            throw new RuntimeException("Invalid amount.");
        }

        account.setBalance(account.getBalance() - amount);
        updateMoney(account.getBalance(), account.getEmail());

        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setPrevious_amount(previous_amount);
        tx.setTransactionType(TransactionType.WITHDRAW_MONEY);
        transactionRepository.save(tx);
    }

    @Override
    @Transactional
    public void transferMoney(TransferMoneyInfo transferMoneyInfo, Account account) {
        logger.info("[[Reached transfer method inside Account Service Impl.]]");
        double accountPreviousAmount = account.getBalance();
        double amount = transferMoneyInfo.getAmount();

        Account receiver = accountRepository.findAccountByPhoneNumber(transferMoneyInfo.getReceiver_phone());

        if (receiver == null) {
            System.out.println("Receipient not found.");
            return;
        }

        double receiverPreviousAmount = receiver.getBalance();

        if (!account.getPassword().equals(transferMoneyInfo.getPassword())) {
            System.out.println("Incorrect password.");
            return;
        }

        if (amount <= 0 || amount > account.getBalance()) {
            System.out.println("Invalid amount.");
            return;
        }

        account.setBalance(account.getBalance() - amount);
        updateMoney(account.getBalance(), account.getEmail());
        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setPrevious_amount(accountPreviousAmount);
        tx.setTransactionType(TransactionType.TRANSFER_MONEY);
        transactionRepository.save(tx);

        receiver.setBalance(receiver.getBalance() + amount);
        updateMoney(receiver.getBalance(), receiver.getEmail());
        Transaction tr = new Transaction();
        tr.setAccount(receiver);
        tr.setAmount(amount);
        tr.setPrevious_amount(receiverPreviousAmount);
        tr.setTransactionType(TransactionType.RECEIVE_MONEY);
        transactionRepository.save(tr);
    }

    @Override
    @Transactional
    public void topUp(double amount, Account account) {
        logger.info("[[Reached top up method inside Account Service Impl.]]");
        double previous_amount= account.getBalance();

        if (amount <= 0 || amount > account.getBalance()) {
            throw new RuntimeException("Invalid amount.");
        }

        account.setBalance(account.getBalance() - amount);
        updateMoney(account.getBalance(), account.getEmail());

        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setPrevious_amount(previous_amount);
        tx.setTransactionType(TransactionType.TOP_UP);
        transactionRepository.save(tx);
    }

    @Override
    public ResponseEntity<CommonResponse> getAccountInfo(String authHeader) {
        logger.info("[[Reached get account method inside Account Service Impl.]]");
        apiName = "/account";
        String token = authHeader.substring(7);
        Account account = tokenService.getAccountByToken(token);
        logger.info("[[Completed get account method.]]");
        return ResponseUtils.makeCommonResponse(apiName, HttpStatus.OK, account, Boolean.TRUE,
                "This is your account info.");
    }

    @Override
    public Account getAccountById(long id) {
        return accountRepository.findAccountById(id);
    }


    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


    @Override
    @Transactional
    public ResponseEntity<CommonResponse> handleLoginRequest(LoginInfoApi loginInfoApi) {
        logger.info("[[Reached Login request method inside Account Service Impl.]]");

        apiName = "/account/login";

        Account account = accountRepository.findAccountByEmail(loginInfoApi.email());

        if (account == null || !account.getPassword().equals(loginInfoApi.password())) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        String accessToken = tokenService.generateAccessToken(account);
        String refreshToken = tokenService.generateRefreshToken(account);

        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken",  accessToken);
        headers.add("refreshToken",  refreshToken);

        CommonResponse commonResponse = new CommonResponse();

        if (account.getRole().equalsIgnoreCase("user")) {
            commonResponse.setData(account);
            commonResponse.setSuccess(true);
            commonResponse.setMessage("Logged in successfully");
            commonResponse.setApiName(apiName);
            commonResponse.setSystemDateTime(LocalDateTime.now());
            commonResponse.setHttpStatusCode(HttpStatus.OK);
        }  else {
            commonResponse.setData(accountRepository.findAll());
            commonResponse.setSuccess(true);
            commonResponse.setMessage("Logged in successfully");
            commonResponse.setApiName(apiName);
            commonResponse.setSystemDateTime(LocalDateTime.now());
            commonResponse.setHttpStatusCode(HttpStatus.OK);
        }
        logger.info("[[Completed Login method.]]");
        return new ResponseEntity<> (commonResponse, headers, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> depositMoneyApi(String authHeader, ServiceRequestApi serviceRequestApi) {

        apiName = "/account/deposit";
        logger.info("[[Reached deposit method inside Account Service Impl.]]");

        String token = authHeader.substring(7);
        Account account = tokenService.getAccountByToken(token);

        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        double previous_amount = account.getBalance();

        if (serviceRequestApi.amount() <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        account.setBalance(account.getBalance() + serviceRequestApi.amount());
        updateMoney(account.getBalance(), account.getEmail());

        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(serviceRequestApi.amount());
        tx.setPrevious_amount(previous_amount);
        tx.setTransactionType(TransactionType.DEPOSIT_MONEY);
        transactionRepository.save(tx);
        logger.info("[[Completed Deposit method.]]");
        return ResponseUtils.makeCommonResponse(apiName, HttpStatus.OK, account, Boolean.TRUE,
                "Deposit money successfully.");
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> registerApi(RegisterInfoApi registerInfo) {
        logger.info("[[Reached register method inside Account Service Impl.]]");

        apiName = "/account/register";
        Account signUpAcc = new Account();

        signUpAcc.setName(registerInfo.name());
        signUpAcc.setEmail(registerInfo.email());
        signUpAcc.setPassword(registerInfo.password());
        signUpAcc.setPhoneNumber(registerInfo.phoneNumber());
        signUpAcc.setAddress(registerInfo.address());
        if (registerInfo.role() == null) {
            signUpAcc.setRole("User");
        } else {
            signUpAcc.setRole(registerInfo.role());
        }

        signUpAcc.setBalance(0);
        saveAccount(signUpAcc);

        logger.info("[[Completed register method.]]");
        return ResponseUtils.makeCommonResponse(apiName, HttpStatus.OK, signUpAcc, Boolean.TRUE,
                "Deposit money successfully.");
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> withdrawMoneyApi(String authHeader, ServiceRequestApi serviceRequestApi) {
        logger.info("[[Reached withdraw method inside Account Service Impl.]]");

        apiName = "/account/withdraw";

        String token = authHeader.substring(7);
        Account account = tokenService.getAccountByToken(token);

        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        double previous_amount = account.getBalance();

        if (serviceRequestApi.amount() <= 0 || serviceRequestApi.amount() > account.getBalance()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        account.setBalance(account.getBalance() - serviceRequestApi.amount());
        updateMoney(account.getBalance(), account.getEmail());

        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(serviceRequestApi.amount());
        tx.setPrevious_amount(previous_amount);
        tx.setTransactionType(TransactionType.WITHDRAW_MONEY);
        transactionRepository.save(tx);
        logger.info("[[Completed Withdraw method.]]");
        return ResponseUtils.makeCommonResponse(apiName, HttpStatus.OK, account, Boolean.TRUE,
                "Transferred money successfully.");
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> transferMoneyApi(String authHeader, TransferMoneyInfoApi transferMoneyInfoApi) {
        logger.info("[[Reached transfer method inside Account Service Impl.]]");

        String token = authHeader.substring(7);
        Account account = tokenService.getAccountByToken(token);
        apiName = "/account/transfer";

        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        Account receiver = accountRepository.findAccountByPhoneNumber(transferMoneyInfoApi.receiver_phone());

        double accountPreviousAmount = account.getBalance();
        double amount = transferMoneyInfoApi.amount();

        if (receiver == null) {
            System.out.println("Receipient not found.");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        double receiverPreviousAmount = receiver.getBalance();

        if (!account.getPassword().equals(transferMoneyInfoApi.password())) {
            System.out.println("Incorrect password.");
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
        }

        if (amount <= 0 || amount > account.getBalance()) {
            System.out.println("Invalid amount.");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        account.setBalance(account.getBalance() - amount);
        updateMoney(account.getBalance(), account.getEmail());
        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(amount);
        tx.setPrevious_amount(accountPreviousAmount);
        tx.setTransactionType(TransactionType.TRANSFER_MONEY);
        transactionRepository.save(tx);

        receiver.setBalance(receiver.getBalance() + amount);
        updateMoney(receiver.getBalance(), receiver.getEmail());
        Transaction tr = new Transaction();
        tr.setAccount(receiver);
        tr.setAmount(amount);
        tr.setPrevious_amount(receiverPreviousAmount);
        tr.setTransactionType(TransactionType.RECEIVE_MONEY);
        transactionRepository.save(tr);
        logger.info("[[Completed Transfer method.]]");
        return ResponseUtils.makeCommonResponse(apiName, HttpStatus.OK, account, Boolean.TRUE,
                "Transferred money successfully.");
    }

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> topUpApi(String authHeader, ServiceRequestApi serviceRequestApi) {
        logger.info("[[Reached top up method inside Account Service Impl.]]");

        String token = authHeader.substring(7);
        Account account = tokenService.getAccountByToken(token);
        apiName = "/account/topup";

        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        double previous_amount = account.getBalance();

        if (serviceRequestApi.amount() <= 0 || serviceRequestApi.amount() > account.getBalance()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        account.setBalance(account.getBalance() - serviceRequestApi.amount());
        updateMoney(account.getBalance(), account.getEmail());

        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setAmount(serviceRequestApi.amount());
        tx.setPrevious_amount(previous_amount);
        tx.setTransactionType(TransactionType.WITHDRAW_MONEY);
        transactionRepository.save(tx);

        logger.info("[[Completed Top up method.]]");
        return ResponseUtils.makeCommonResponse(apiName, HttpStatus.OK, account, Boolean.TRUE,
                "Top up money successfully.");
    }

}
