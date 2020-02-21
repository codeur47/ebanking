package io.yorosoft.ebanking.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import io.yorosoft.ebanking.model.SavingsTransaction;

/**
 * SavingsTransactionDao
 */
@Repository
public interface SavingsTransactionDao extends CrudRepository<SavingsTransaction, Long> {
    List<SavingsTransaction> findAll();
}