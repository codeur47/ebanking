package io.yorosoft.ebanking.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yorosoft.ebanking.dao.PrimaryAccountDao;
import io.yorosoft.ebanking.dao.PrimaryTransactionDao;
import io.yorosoft.ebanking.dao.RecipientDao;
import io.yorosoft.ebanking.dao.SavingsAccountDao;
import io.yorosoft.ebanking.dao.SavingsTransactionDao;
import io.yorosoft.ebanking.model.PrimaryAccount;
import io.yorosoft.ebanking.model.PrimaryTransaction;
import io.yorosoft.ebanking.model.Recipient;
import io.yorosoft.ebanking.model.SavingsAccount;
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

    private PrimaryAccountDao primaryAccountDao;

    private SavingsAccountDao savingsAccountDao;

    private RecipientDao recipientDao; 

    @Autowired
    public TransactionServiceImpl(UserService userService, PrimaryTransactionDao primaryTransactionDao,
            SavingsTransactionDao savingsTransactionDao, PrimaryAccountDao primaryAccountDao,
            SavingsAccountDao savingsAccountDao, RecipientDao recipientDao) {
        this.userService = userService;
        this.primaryTransactionDao = primaryTransactionDao;
        this.savingsTransactionDao = savingsTransactionDao;
        this.primaryAccountDao = primaryAccountDao;
        this.savingsAccountDao = savingsAccountDao;
        this.recipientDao = recipientDao;
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

    @Override
    public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
            PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception {
        if (transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")) {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            primaryAccountDao.save(primaryAccount);
            savingsAccountDao.save(savingsAccount);
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(new Date(),
                    "Virement du compte " + transferFrom + " au compte " + transferTo, "Virement", "Terminé",
                    Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
            primaryTransactionDao.save(primaryTransaction);
        } else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")) {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountDao.save(primaryAccount);
            savingsAccountDao.save(savingsAccount);
            SavingsTransaction savingsTransaction = new SavingsTransaction(new Date(),
                    "Virement du compte " + transferFrom + " au compte " + transferTo, "Virement", "Terminé",
                    Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
            savingsTransactionDao.save(savingsTransaction);
        } else throw new Exception("Virement invalide");

    }

    @Override
    public List<Recipient> findRecipientList(Principal principal) {
        return recipientDao.findAll().stream()
                                     .filter(recipient -> principal.getName().equalsIgnoreCase(recipient.getUser().getUsername()))
                                     .collect(Collectors.toList());
    }

    @Override
    public Recipient saveRecipient(Recipient recipient) {
        return recipientDao.save(recipient);
    }

    @Override
    public Recipient findRecipientByName(String recipientName) {
        return recipientDao.findByName(recipientName);
    }

    @Override
    public void deleteRecipientByName(String recipientName) {
       recipientDao.deleteByName(recipientName);
    }

    @Override
    public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount,
            PrimaryAccount primaryAccount, SavingsAccount savingsAccount) {
       
        if (accountType.equalsIgnoreCase("Courant")) {
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            primaryAccountDao.save(primaryAccount);
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(new Date(),
                    "Virement du compte " + accountType + " au compte de " + recipient.getAccountNumber(), "Virement", "Terminé",
                    Double.parseDouble(amount), primaryAccount.getAccountBalance(), primaryAccount);
            primaryTransactionDao.save(primaryTransaction);
        } else if (accountType.equalsIgnoreCase("Epargne")) {
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);
            SavingsTransaction savingsTransaction = new SavingsTransaction(new Date(),
            "Virement du compte " + accountType + " au compte de " + recipient.getAccountNumber(), "Virement", "Terminé",
            Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
            savingsTransactionDao.save(savingsTransaction);
        }
    }

}