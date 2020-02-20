package io.yorosoft.ebanking.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.yorosoft.ebanking.model.User;

/**
 * UserDao
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    
}