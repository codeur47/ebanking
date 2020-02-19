package io.yorosoft.ebanking.service;

import io.yorosoft.ebanking.model.User;

/**
 * UserService
 */
public interface UserService {

    void save(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    public boolean CheckUsernameExists(String username);

    public boolean checkEmailExists(String email);

    public boolean checkUserExists(String username, String email);
}