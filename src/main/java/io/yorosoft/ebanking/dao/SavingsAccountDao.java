package io.yorosoft.ebanking.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.yorosoft.ebanking.model.SavingsAccount;

/**
 * SavingsAccountDao
 */
@Repository
public interface SavingsAccountDao extends CrudRepository<SavingsAccount, Long>{
    SavingsAccount findByAccountNumber(int accountNumber);
}