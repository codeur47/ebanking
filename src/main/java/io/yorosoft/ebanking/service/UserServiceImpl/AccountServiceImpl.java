package io.yorosoft.ebanking.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yorosoft.ebanking.dao.PrimaryAccountDao;
import io.yorosoft.ebanking.dao.SavingsAccountDao;
import io.yorosoft.ebanking.model.PrimaryAccount;
import io.yorosoft.ebanking.model.PrimaryTransaction;
import io.yorosoft.ebanking.model.SavingsAccount;
import io.yorosoft.ebanking.model.SavingsTransaction;
import io.yorosoft.ebanking.model.User;
import io.yorosoft.ebanking.service.AccountService;
import io.yorosoft.ebanking.service.TransactionService;
import io.yorosoft.ebanking.service.UserService;
import io.yorosoft.ebanking.service.UtilityService;

/**
 * AccountServiceImpl
 */
@Service
public class AccountServiceImpl implements AccountService {

    private PrimaryAccountDao primaryAccountDao;

    private SavingsAccountDao savingsAccountDao;

    private UtilityService utilityService;

    private UserService userService; 

    private TransactionService transactionService;

    @Autowired
    public AccountServiceImpl(PrimaryAccountDao primaryAccountDao, SavingsAccountDao savingsAccountDao,
            UtilityService utilityService, UserService userService, TransactionService transactionService) {
        this.primaryAccountDao = primaryAccountDao;
        this.savingsAccountDao = savingsAccountDao;
        this.utilityService = utilityService;
        this.userService = userService;
        this.transactionService = transactionService;

    }

    @Override
    public PrimaryAccount createPrimaryAccount(User user) {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        String initialUserPrimary = concatUserInitial(user.getLastName().toUpperCase().charAt(0),
                user.getUsername().toUpperCase().charAt(0));
        String accountNumber = utilityService.generateAccountNumber(new String("P"), initialUserPrimary);
        primaryAccount.setAccountNumber(accountNumber);
        primaryAccountDao.save(primaryAccount);
        return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    @Override
    public SavingsAccount createSavingsAccount(User user) {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        String initialUserSavings = concatUserInitial(user.getLastName().toUpperCase().charAt(0),
                user.getUsername().toUpperCase().charAt(0));
        String accountNumber = utilityService.generateAccountNumber(new String("S"), initialUserSavings);
        savingsAccount.setAccountNumber(accountNumber);
        savingsAccountDao.save(savingsAccount);
        return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
    }

    @Override
    public void deposit(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (accountType.equalsIgnoreCase("Courant")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
            primaryAccountDao.save(primaryAccount);
            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Depot pour le compte courant", "Compte Courant", "Terminé", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryDepositTransaction(primaryTransaction);
        } else if(accountType.equalsIgnoreCase("Epargne")){
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);
            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Depot pour le compte epargne", "Compte Epargne", "Terminé", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
        }
    }

    @Override
    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (accountType.equalsIgnoreCase("Courant")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountDao.save(primaryAccount);
            Date date = new Date();
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Retrait pour le compte courant", "Compte Courant", "Terminé", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
        } else if(accountType.equalsIgnoreCase("Epargne")){
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);
            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Retrait pour le compte epargne", "Compte Epargne", "Terminé", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
        }
    }

    private String concatUserInitial(char lastNameInitial, char userNameInitial) {
        return lastNameInitial + "" + userNameInitial;
    }

    
}