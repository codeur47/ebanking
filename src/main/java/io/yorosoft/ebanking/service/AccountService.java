package io.yorosoft.ebanking.service;

import java.security.Principal;

import io.yorosoft.ebanking.model.PrimaryAccount;
import io.yorosoft.ebanking.model.SavingsAccount;
import io.yorosoft.ebanking.model.User;

/**
 * AccountService
 */
public interface AccountService {
    PrimaryAccount createPrimaryAccount(User user);
    SavingsAccount createSavingsAccount(User user);
    void deposit(String accountType, double amount, Principal principal);
    void withdraw(String accountType, double amount, Principal principal);

}