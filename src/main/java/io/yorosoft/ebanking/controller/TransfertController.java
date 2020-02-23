package io.yorosoft.ebanking.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.yorosoft.ebanking.model.Recipient;
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

    @GetMapping("/recipient")
    public String recipient(Model model, Principal principal) {
        model.addAttribute("recipientList", transactionService.findRecipientList(principal));
        model.addAttribute("recipient", new Recipient());
        return "recipient";
    }

    @PostMapping("/recipient/save")
    public String recipientPost(@ModelAttribute("recipient") Recipient recipient, Principal principal) {
        recipient.setUser(userService.findByUsername(principal.getName()));
        transactionService.saveRecipient(recipient);
        return "redirect:/transfer/recipient";
    }

    @GetMapping("/recipient/edit")
    public String recipientEdit(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal) {
        model.addAttribute("recipientList", transactionService.findRecipientList(principal));
        model.addAttribute("recipient", transactionService.findRecipientByName(recipientName));
        return "recipient";
    }

    @GetMapping("/recipient/delete")
    public String recipientDelete(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal) {
        transactionService.deleteRecipientByName(recipientName);
        model.addAttribute("recipientList", transactionService.findRecipientList(principal));
        model.addAttribute("recipient", new Recipient());
        return "recipient";
    }

    @GetMapping("/toSomeoneElse")
    public String toSomeoneElse(Model model, Principal principal) {
        model.addAttribute("recipientList", transactionService.findRecipientList(principal));
        model.addAttribute("accountType", "");
        return "toSomeoneElse";
    }

    @PostMapping("/toSomeoneElse")
    public String toSomeoneElse(@ModelAttribute("recipientName") String recipientName, Principal principal,
                                @ModelAttribute("amount") String amount,  
                                @ModelAttribute("accountType") String accountType) {
        transactionService.toSomeoneElseTransfer(transactionService.findRecipientByName(recipientName),accountType,amount,
        userService.findByUsername(principal.getName()).getPrimaryAccount(),userService.findByUsername(principal.getName()).getSavingsAccount());
        return "redirect:/userFront";
    }
}