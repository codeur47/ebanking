package io.yorosoft.ebanking.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.yorosoft.ebanking.service.TransactionService;
import io.yorosoft.ebanking.service.UserService;

/**
 * TransfertController
 */
@Controller
@RequestMapping("/transfer")
public class TransfertController {

    private TransactionService transactionService;

    private UserService userService;

    @Autowired
    public TransfertController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/betweenAccounts")
    public String betweenAccounts(Model model) {
        model.addAttribute("transferFrom", "");
        model.addAttribute("transferTo", "");
        model.addAttribute("amount", "");
        return "betweenAccounts";
    }

    @PostMapping("/betweenAccounts")
    public String betweenAccountsPost(
        @ModelAttribute("transferFrom") String transferFrom,
        @ModelAttribute("transferTo") String transferTo,
        @ModelAttribute("amount") String amount,
        Principal principal
    ) throws Exception {
        transactionService.betweenAccountsTransfer(transferFrom,transferTo,amount,userService.findByUsername(principal.getName()).getPrimaryAccount(),userService.findByUsername(principal.getName()).getSavingsAccount());
        return "redirect:/userFront";
    }
}