package io.yorosoft.ebanking.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yorosoft.ebanking.dao.UserDao;
import io.yorosoft.ebanking.model.User;
import io.yorosoft.ebanking.service.UserService;

/**
 * UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    public void save(User user) {
        userDao.save(user);
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