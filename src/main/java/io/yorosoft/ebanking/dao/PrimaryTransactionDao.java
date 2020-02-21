package io.yorosoft.ebanking.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.yorosoft.ebanking.model.PrimaryTransaction;

/**
 * PrimaryTransactionDao
 */
@Repository
public interface PrimaryTransactionDao extends CrudRepository<PrimaryTransaction, Long> {
    java.util.List<PrimaryTransaction> findAll();
}