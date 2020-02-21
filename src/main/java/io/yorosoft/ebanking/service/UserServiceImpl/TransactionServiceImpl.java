package io.yorosoft.ebanking.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yorosoft.ebanking.dao.PrimaryTransactionDao;
import io.yorosoft.ebanking.dao.SavingsTransactionDao;
import io.yorosoft.ebanking.model.PrimaryTransaction;
import io.yorosoft.ebanking.model.SavingsTransaction;
import io.yorosoft.ebanking.service.TransactionService;
import io.yorosoft.ebanking.service.UserService;

/**
 * TransactionServiceImpl
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    
    private UserService userService;

    private PrimaryTransactionDao primaryTransactionDao;
    
    private SavingsTransactionDao savingsTransactionDao;

    @Autowired
    public TransactionServiceImpl(UserService userService, PrimaryTransactionDao primaryTransactionDao, SavingsTransactionDao savingsTransactionDao) {
        this.userService = userService;
        this.primaryTransactionDao = primaryTransactionDao;
        this.savingsTransactionDao = savingsTransactionDao;
    }

    @Override
    public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
        return userService.findByUsername(username).getPrimaryAccount().getPrimaryTransactionList();
    }

    @Override
    public List<SavingsTransaction> findSavingsTransactionList(String username) {
        return userService.findByUsername(username).getSavingsAccount().getSavingsTransactionList();
    }

    @Override
    public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
        primaryTransactionDao.save(primaryTransaction);
    }

    @Override
    public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionDao.save(savingsTransaction);
    }

    @Override
    public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
        primaryTransactionDao.save(primaryTransaction);
    }

    @Override
    public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionDao.save(savingsTransaction);
    }
}