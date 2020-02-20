package io.yorosoft.ebanking.service;

import io.yorosoft.ebanking.model.PrimaryAccount;
import io.yorosoft.ebanking.model.SavingsAccount;
import io.yorosoft.ebanking.model.User;

/**
 * AccountService
 */
public interface AccountService {
    PrimaryAccount createPrimaryAccount(User user);
    SavingsAccount createSavingsAccount(User user);
}