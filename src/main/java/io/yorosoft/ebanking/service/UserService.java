package io.yorosoft.ebanking.service;

import java.util.Set;

import io.yorosoft.ebanking.model.User;
import io.yorosoft.ebanking.model.security.UserRole;

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

    User createUser(User user, Set<UserRole> userRoles);
}