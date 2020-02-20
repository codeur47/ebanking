package io.yorosoft.ebanking.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.yorosoft.ebanking.model.PrimaryAccount;

/**
 * PrimaryAccountDao
 */
@Repository
public interface PrimaryAccountDao extends CrudRepository<PrimaryAccount, Long> {

    PrimaryAccount findByAccountNumber(int accountNumber);
}