package io.yorosoft.ebanking.service;

import java.util.List;

import io.yorosoft.ebanking.model.PrimaryAccount;
import io.yorosoft.ebanking.model.PrimaryTransaction;
import io.yorosoft.ebanking.model.SavingsAccount;
import io.yorosoft.ebanking.model.SavingsTransaction;

/**
 * TransactionService
 */
public interface TransactionService {

    List<PrimaryTransaction> findPrimaryTransactionList(String username);
    List<SavingsTransaction> findSavingsTransactionList(String username);
    void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);
    void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);
    void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);
    void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);
    void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception;

}