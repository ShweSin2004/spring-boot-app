package com.talent.batch11.springbootapp.controller;

import com.talent.batch11.springbootapp.model.Account;
import com.talent.batch11.springbootapp.dto.request.RegisterInfo;
import com.talent.batch11.springbootapp.dto.request.TransferMoneyInfo;
import com.talent.batch11.springbootapp.serviceimpl.AccountServiceimpl;
import com.talent.batch11.springbootapp.serviceimpl.TransactionServiceimpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.talent.batch11.springbootapp.dto.request.LoginInfo;

@Controller
public class AccountController {
    @Autowired
    AccountServiceimpl accountService;

    @Autowired
    private TransactionServiceimpl transactionServiceimpl;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("allaccount", accountService.getAllAccounts());
        return "index";
    }

    @GetMapping("/register")
    //to show register page
    public String register(Model model) {
        RegisterInfo registerInfo = new RegisterInfo();
        model.addAttribute("registerInfo", registerInfo);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    // to save account after submit register page
    public String registerAccount(@ModelAttribute("registerInfo") RegisterInfo registerInfo, HttpSession session) {

        Account account = accountService.register(registerInfo);
        session.setAttribute("accountInfo", account);

        if (account.getRole().equalsIgnoreCase("admin")) {
            account.setRole("Admin");
            return "redirect:/admindashboard";
        } else if(account.getRole().equalsIgnoreCase("user")) {
            account.setRole("User");
            return "redirect:/dashboard";
        } else {
            accountService.deleteAcc(account);
            return "redirect:/register";
        }
    }

    @GetMapping("/login")
    // to show login page
    public String login(Model model) {
        LoginInfo loginInfo = new LoginInfo();
        model.addAttribute("logininfo", loginInfo);
        return "login";
    }

    @PostMapping("/login")
    //after submit login page
    public String loginAccount(@ModelAttribute("logininfo") LoginInfo loginInfo, HttpSession session) {

        Account account = accountService.logIn(loginInfo);
        if (account == null) {
            return "login";
        }
        session.setAttribute("accountInfo", account);

        if (account.getRole().equalsIgnoreCase("admin")) {
            return "redirect:/admindashboard";
        } else {
            return "redirect:/dashboard";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    // to show dashboard page
    public String dashboardPage(Model model, HttpSession session) {

        Account loginAccount = (Account) session.getAttribute("accountInfo");
        model.addAttribute("currentAccount", loginAccount);
        model.addAttribute("transactions",
                accountService.getAllTransactionsByAccountId(loginAccount.getId()));
        return "dashboard";
    }


    @PostMapping("/deposit")
    public String depositMoney(@ModelAttribute("amount") double amount, HttpSession session) {
        Account account = (Account) session.getAttribute("accountInfo");
        accountService.depositMoney(amount, account);
        return  "redirect:/dashboard";
    }

    @PostMapping("/withdraw")
    public String withdraw(@ModelAttribute("amount") double amount, HttpSession session) {
        Account account = (Account) session.getAttribute("accountInfo");
        accountService.withdrawMoney(amount, account);
        return  "redirect:/dashboard";
    }

    @PostMapping("/topup")
    public String topUp(@ModelAttribute("amount") double amount, HttpSession session) {
        Account account = (Account) session.getAttribute("accountInfo");
        accountService.topUp(amount, account);
        return  "redirect:/dashboard";
    }

    @PostMapping("/transfer")
    public String transfer(@ModelAttribute("transferMoneyInfo") TransferMoneyInfo transferMoneyInfo,
                           HttpSession session) {
        Account account = (Account) session.getAttribute("accountInfo");
        accountService.transferMoney(transferMoneyInfo, account);

        return  "redirect:/dashboard";
    }

    @GetMapping("/admindashboard")
    public String adminDashboard(Model model, HttpSession session) {

        Account loginAccount = (Account) session.getAttribute("accountInfo");
        model.addAttribute("currentAccount", loginAccount);
        model.addAttribute("allaccount", accountService.getAllAccounts());
        model.addAttribute("transactions", transactionServiceimpl.getAllTransactions());
        return "admindashboard";
    }
}
