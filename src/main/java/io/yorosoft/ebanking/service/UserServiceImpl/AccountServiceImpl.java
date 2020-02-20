package io.yorosoft.ebanking.service.UserServiceImpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yorosoft.ebanking.dao.PrimaryAccountDao;
import io.yorosoft.ebanking.dao.SavingsAccountDao;
import io.yorosoft.ebanking.model.PrimaryAccount;
import io.yorosoft.ebanking.model.SavingsAccount;
import io.yorosoft.ebanking.service.AccountService;
import io.yorosoft.ebanking.service.UserService;

/**
 * AccountServiceImpl
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static int nextAccountNumber = 1122345;

    private PrimaryAccountDao primaryAccountDao;

    private SavingsAccountDao savingsAccountDao;

    @Autowired
    public AccountServiceImpl(PrimaryAccountDao primaryAccountDao, SavingsAccountDao savingsAccountDao) {
        this.primaryAccountDao = primaryAccountDao;
        this.savingsAccountDao = savingsAccountDao;
    }


    @Override
    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        primaryAccount.setAccountNumber(accountGen());
        primaryAccountDao.save(primaryAccount);
        return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    @Override
    public SavingsAccount createSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        savingsAccount.setAccountNumber(accountGen());
        savingsAccountDao.save(savingsAccount);
        return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
    }

    private int accountGen() {
        return ++nextAccountNumber;
    }

    
}