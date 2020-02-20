package io.yorosoft.ebanking.service.UserServiceImpl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.yorosoft.ebanking.dao.RoleDao;
import io.yorosoft.ebanking.dao.UserDao;
import io.yorosoft.ebanking.model.User;
import io.yorosoft.ebanking.model.security.UserRole;
import io.yorosoft.ebanking.service.AccountService;
import io.yorosoft.ebanking.service.UserService;

/**
 * UserServiceImpl
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;

    private RoleDao roleDao;

    private BCryptPasswordEncoder passwordEncoder;

    private AccountService accountService;
    
    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder, AccountService accountService){
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }

    public void save(User user) {
        userDao.save(user);
    }

    @Transactional
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userDao.findByUsername(user.getUsername());
        if(localUser != null) LOG.info("Utilisateur avec le nom utilisateur {} existe déjà. Rien ne sera fait. ", user.getUsername());
        else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            for(UserRole ur : userRoles) {
                roleDao.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            user.setPrimaryAccount(accountService.createPrimaryAccount(user));
            user.setSavingsAccount(accountService.createSavingsAccount(user));
            localUser = userDao.save(user);
        }
        return localUser;
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findByEmail(String email){
        return userDao.findByEmail(email);
    }

    public boolean CheckUsernameExists(String username) {
        if(null != findByUsername(username)) return true;
        return false;
    }

    public boolean checkEmailExists(String email) {
        if(null != findByEmail(email)) return true;
        return false;
    }

    public boolean checkUserExists(String username, String email){
        if(CheckUsernameExists(username) || checkEmailExists(email)) return true;
        else return false;
    }
}