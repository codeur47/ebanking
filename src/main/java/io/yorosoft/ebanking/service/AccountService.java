package io.yorosoft.ebanking.service;

import io.yorosoft.ebanking.model.PrimaryAccount;
import io.yorosoft.ebanking.model.SavingsAccount;

/**
 * AccountService
 */
public interface AccountService {
    PrimaryAccount createPrimaryAccount();
    SavingsAccount createSavingsAccount();
}