package io.yorosoft.ebanking.dao;

import org.springframework.data.repository.CrudRepository;

import io.yorosoft.ebanking.model.User;

/**
 * UserDao
 */
public interface UserDao extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    
}