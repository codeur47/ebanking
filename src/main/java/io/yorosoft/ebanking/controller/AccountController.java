package io.yorosoft.ebanking.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import io.yorosoft.ebanking.model.User;
import io.yorosoft.ebanking.service.AccountService;
import io.yorosoft.ebanking.service.TransactionService;
import io.yorosoft.ebanking.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * AccountController
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private UserService userService;

    private AccountService accountService;

    private TransactionService transactionService;

    @Autowired
    public AccountController(UserService userService, AccountService accountService, TransactionService transactionService){
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping(value="/primaryAccount")
    public String primaryAccount(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("primaryAccount", user.getPrimaryAccount());
        model.addAttribute("primaryTransactionList", transactionService.findPrimaryTransactionList(principal.getName()));
        return "primaryAccount";
    }

    @GetMapping(value="/savingsAccount")
    public String savingsAccount(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("savingsAccount", user.getSavingsAccount());
        model.addAttribute("savingsTransactionList", transactionService.findSavingsTransactionList(principal.getName()));
        return "savingsAccount";
    }
    
    @GetMapping("/deposit")
    public String deposit(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");
        return "deposit";
    }

    @GetMapping("/withdraw")
    public String withdraw(Model model) {
        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");
        return "withdraw";
    }

    @PostMapping(value="/deposit")
    public String depositPOST(@ModelAttribute("amount") String amount, 
                              @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.deposit(accountType, Double.parseDouble(amount),principal);
        return "redirect:/userFront";
    }

    @PostMapping(value="/withdraw")
    public String withdrawPOST(@ModelAttribute("amount") String amount, 
                              @ModelAttribute("accountType") String accountType, Principal principal) {
        accountService.withdraw(accountType, Double.parseDouble(amount),principal);
        return "redirect:/userFront";
    }
    
    
}