package io.yorosoft.ebanking.service.UserServiceImpl;

import java.math.BigDecimal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yorosoft.ebanking.dao.PrimaryAccountDao;
import io.yorosoft.ebanking.dao.SavingsAccountDao;
import io.yorosoft.ebanking.model.PrimaryAccount;
import io.yorosoft.ebanking.model.SavingsAccount;
import io.yorosoft.ebanking.model.User;
import io.yorosoft.ebanking.service.AccountService;
import io.yorosoft.ebanking.service.UtilityService;

/**
 * AccountServiceImpl
 */
@Service
public class AccountServiceImpl implements AccountService {

    private PrimaryAccountDao primaryAccountDao;

    private SavingsAccountDao savingsAccountDao;

    private UtilityService utilityService; 

    @Autowired
    public AccountServiceImpl(PrimaryAccountDao primaryAccountDao, SavingsAccountDao savingsAccountDao, UtilityService utilityService) {
        this.primaryAccountDao = primaryAccountDao;
        this.savingsAccountDao = savingsAccountDao;
        this.utilityService = utilityService;

    }


    @Override
    public PrimaryAccount createPrimaryAccount(User user) {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        String initialUserPrimary = concatUserInitial(user.getLastName().toUpperCase().charAt(0),user.getUsername().toUpperCase().charAt(0));
        String accountNumber = utilityService.generateAccountNumber(new String("P"), initialUserPrimary);
        primaryAccount.setAccountNumber(accountNumber);
        primaryAccountDao.save(primaryAccount);
        return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    @Override
    public SavingsAccount createSavingsAccount(User user) {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        String initialUserSavings = concatUserInitial(user.getLastName().toUpperCase().charAt(0),user.getUsername().toUpperCase().charAt(0));
        String accountNumber = utilityService.generateAccountNumber(new String("S"), initialUserSavings);
        savingsAccount.setAccountNumber(accountNumber);
        savingsAccountDao.save(savingsAccount);
        return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
    }    

    private String concatUserInitial(char lastNameInitial, char userNameInitial) {
        return lastNameInitial+""+userNameInitial;
    }
    
}